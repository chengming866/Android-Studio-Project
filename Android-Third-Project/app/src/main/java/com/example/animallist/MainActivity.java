package com.example.animallist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(root, (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(
                    view.getPaddingLeft() + systemBars.left,
                    view.getPaddingTop() + systemBars.top,
                    view.getPaddingRight() + systemBars.right,
                    view.getPaddingBottom() + systemBars.bottom
            );
            return insets;
        });

        bindModuleButton(R.id.module_list, AnimalListActivity.class);
        bindModuleButton(R.id.module_dialog, CustomDialogActivity.class);
        bindModuleButton(R.id.module_menu, XmlMenuActivity.class);
        bindModuleButton(R.id.module_action_mode, ActionModeActivity.class);
    }

    private void bindModuleButton(int buttonId, Class<?> targetActivity) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(view ->
                startActivity(new Intent(this, targetActivity))
        );
    }
}

