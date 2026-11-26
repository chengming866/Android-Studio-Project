package com.example.androidcompose

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ConstraintLayout2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.WHITE),
            navigationBarStyle = SystemBarStyle.light(Color.WHITE, Color.WHITE),
        )
        setContentView(R.layout.activity_constraint_layout_2)
    }
}

