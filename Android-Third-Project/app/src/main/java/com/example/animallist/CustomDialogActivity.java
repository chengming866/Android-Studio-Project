package com.example.animallist;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CustomDialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_dialog);

        View root = findViewById(R.id.custom_dialog_screen);
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

        findViewById(R.id.open_dialog_button).setOnClickListener(view -> showSignInDialog());
        root.post(this::showSignInDialog);
    }

    private void showSignInDialog() {
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_sign_in, (ViewGroup) getWindow().getDecorView(), false);
        EditText usernameInput = dialogView.findViewById(R.id.username_input);
        EditText passwordInput = dialogView.findViewById(R.id.password_input);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        dialogView.findViewById(R.id.dialog_cancel).setOnClickListener(view -> dialog.dismiss());
        dialogView.findViewById(R.id.dialog_sign_in).setOnClickListener(view -> {
            String username = usernameInput.getText().toString().trim();
            if (TextUtils.isEmpty(username)) {
                usernameInput.setError(getString(R.string.username_required));
                usernameInput.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(passwordInput.getText().toString())) {
                passwordInput.setError(getString(R.string.password_required));
                passwordInput.requestFocus();
                return;
            }
            Toast.makeText(
                    this,
                    getString(R.string.sign_in_success, username),
                    Toast.LENGTH_SHORT
            ).show();
            dialog.dismiss();
        });

        dialog.setOnShowListener(ignored -> {
            Window window = dialog.getWindow();
            if (window != null) {
                int width = Math.round(getResources().getDisplayMetrics().widthPixels * 0.88f);
                window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
        dialog.show();
    }
}
