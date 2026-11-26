package com.example.androidcompose

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TableLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.BLACK),
            navigationBarStyle = SystemBarStyle.dark(Color.BLACK),
        )
        setContentView(R.layout.activity_table_layout)
    }
}

