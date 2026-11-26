package com.example.animallist;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class XmlMenuActivity extends AppCompatActivity {
    private TextView testText;
    private float selectedTextSize = 16f;
    private int selectedTextColor = R.color.text_primary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xml_menu);

        View root = findViewById(R.id.xml_menu_screen);
        ViewCompat.setOnApplyWindowInsetsListener(root, (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    0
            );
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);
        testText = findViewById(R.id.test_text);
        applyTextStyle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.font_small).setChecked(selectedTextSize == 10f);
        menu.findItem(R.id.font_medium).setChecked(selectedTextSize == 16f);
        menu.findItem(R.id.font_large).setChecked(selectedTextSize == 20f);
        menu.findItem(R.id.font_red).setChecked(selectedTextColor == R.color.font_red);
        menu.findItem(R.id.font_black).setChecked(selectedTextColor == R.color.text_primary);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.font_small) {
            selectedTextSize = 10f;
        } else if (itemId == R.id.font_medium) {
            selectedTextSize = 16f;
        } else if (itemId == R.id.font_large) {
            selectedTextSize = 20f;
        } else if (itemId == R.id.font_red) {
            selectedTextColor = R.color.font_red;
        } else if (itemId == R.id.font_black) {
            selectedTextColor = R.color.text_primary;
        } else if (itemId == R.id.normal_menu_item) {
            Toast.makeText(this, R.string.normal_menu_toast, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
        applyTextStyle();
        invalidateOptionsMenu();
        return true;
    }

    private void applyTextStyle() {
        testText.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedTextSize);
        testText.setTextColor(ContextCompat.getColor(this, selectedTextColor));
    }
}
