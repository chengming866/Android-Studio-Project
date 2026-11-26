package com.example.androidcompose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcompose.ui.theme.TaskRed
import com.example.androidcompose.ui.theme.TextPrimary
import com.example.androidcompose.ui.theme.TextSecondary

@Composable
fun LabMenuScreen(
    onLinearLayoutClick: () -> Unit,
    onTableLayoutClick: () -> Unit,
    onConstraintLayout1Click: () -> Unit,
    onConstraintLayout2Click: () -> Unit,
    onStudyTaskClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
        ) {
            Text(
                text = "实验二",
                color = TaskRed,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Android 界面布局",
                color = TextPrimary,
                fontSize = 28.sp,
                lineHeight = 34.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "请选择要查看的实验模块",
                color = TextSecondary,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ModuleButton(
                    text = "线性布局",
                    onClick = onLinearLayoutClick,
                )
                ModuleButton(
                    text = "表格布局",
                    onClick = onTableLayoutClick,
                )
                ModuleButton(
                    text = "约束布局1",
                    onClick = onConstraintLayout1Click,
                )
                ModuleButton(
                    text = "约束布局2",
                    onClick = onConstraintLayout2Click,
                )
                ModuleButton(
                    text = "课程学习任务布局",
                    onClick = onStudyTaskClick,
                )
            }
        }
    }
}

@Composable
private fun ModuleButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = TaskRed,
            contentColor = Color.White,
        ),
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}
