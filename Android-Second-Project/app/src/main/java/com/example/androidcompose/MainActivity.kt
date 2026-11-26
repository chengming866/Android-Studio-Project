package com.example.androidcompose

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.androidcompose.ui.LabMenuScreen
import com.example.androidcompose.ui.theme.AndroidComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.WHITE, Color.WHITE),
        )
        setContent {
            AndroidComposeTheme {
                LabMenuScreen(
                    onLinearLayoutClick = {
                        startActivity(Intent(this, LinearLayoutActivity::class.java))
                    },
                    onTableLayoutClick = {
                        startActivity(Intent(this, TableLayoutActivity::class.java))
                    },
                    onConstraintLayout1Click = {
                        startActivity(Intent(this, ConstraintLayout1Activity::class.java))
                    },
                    onConstraintLayout2Click = {
                        startActivity(Intent(this, ConstraintLayout2Activity::class.java))
                    },
                    onStudyTaskClick = {
                        startActivity(Intent(this, StudyTaskActivity::class.java))
                    },
                )
            }
        }
    }
}
