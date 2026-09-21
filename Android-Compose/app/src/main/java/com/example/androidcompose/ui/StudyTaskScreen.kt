package com.example.androidcompose.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcompose.model.StudyTask
import com.example.androidcompose.model.StudyTaskState
import com.example.androidcompose.ui.theme.CardBorder
import com.example.androidcompose.ui.theme.FieldBorder
import com.example.androidcompose.ui.theme.TaskRed
import com.example.androidcompose.ui.theme.TaskRedPressed
import com.example.androidcompose.ui.theme.TextPrimary
import com.example.androidcompose.ui.theme.TextSecondary

private val TaskListSaver = listSaver<List<StudyTask>, Any>(
    save = { tasks ->
        tasks.flatMap { task -> listOf(task.id, task.title, task.completed) }
    },
    restore = { values ->
        values.chunked(3).map { (id, title, completed) ->
            StudyTask(
                id = id as Int,
                title = title as String,
                completed = completed as Boolean,
            )
        }
    },
)

@Composable
fun StudyTaskScreen() {
    var tasks by rememberSaveable(stateSaver = TaskListSaver) {
        mutableStateOf(StudyTaskState.initialTasks())
    }
    var draft by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 18.dp),
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "课程学习任务",
                color = TaskRed,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 34.sp,
            )

            Spacer(modifier = Modifier.height(18.dp))

            AddTaskRow(
                draft = draft,
                onDraftChange = { draft = it },
                focusManager = focusManager,
                onAdd = {
                    tasks = StudyTaskState.addTask(tasks, draft)
                    draft = ""
                    focusManager.clearFocus()
                },
            )

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = "已完成: ${StudyTaskState.completedCount(tasks)} / ${tasks.size}",
                color = TextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
            ) {
                items(
                    items = tasks,
                    key = StudyTask::id,
                ) { task ->
                    TaskRow(
                        task = task,
                        onToggle = {
                            tasks = StudyTaskState.toggleTask(tasks, task.id)
                        },
                        onDelete = {
                            tasks = StudyTaskState.deleteTask(tasks, task.id)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun AddTaskRow(
    draft: String,
    onDraftChange: (String) -> Unit,
    focusManager: FocusManager,
    onAdd: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            value = draft,
            onValueChange = onDraftChange,
            modifier = Modifier
                .weight(1f)
                .height(42.dp)
                .border(
                    width = 1.dp,
                    color = FieldBorder,
                    shape = RoundedCornerShape(6.dp),
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            singleLine = true,
            textStyle = TextStyle(
                color = TextPrimary,
                fontSize = 13.sp,
                lineHeight = 18.sp,
            ),
            cursorBrush = SolidColor(TaskRed),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    onAdd()
                    focusManager.clearFocus()
                },
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (draft.isEmpty()) {
                        Text(
                            text = "请输入学习任务",
                            color = TextSecondary,
                            fontSize = 12.sp,
                        )
                    }
                    innerTextField()
                }
            },
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .width(72.dp)
                .height(42.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (draft.isNotBlank()) TaskRed else TaskRed.copy(alpha = 0.55f))
                .clickable(enabled = draft.isNotBlank(), onClick = onAdd)
                .semantics { contentDescription = "添加任务" },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "添加",
                color = androidx.compose.ui.graphics.Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun TaskRow(
    task: StudyTask,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(4.dp),
                clip = false,
            )
            .background(
                color = androidx.compose.ui.graphics.Color.White,
                shape = RoundedCornerShape(4.dp),
            )
            .border(
                width = 1.dp,
                color = CardBorder,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TaskCheckbox(
            checked = task.completed,
            onCheckedChange = onToggle,
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = task.title,
            modifier = Modifier.weight(1f),
            color = if (task.completed) TextSecondary else TextPrimary,
            fontSize = 13.sp,
            textDecoration = if (task.completed) {
                TextDecoration.LineThrough
            } else {
                TextDecoration.None
            },
            maxLines = 1,
        )

        Box(
            modifier = Modifier
                .width(40.dp)
                .height(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .clickable(onClick = onDelete)
                .semantics { contentDescription = "删除任务" },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "删除",
                color = TaskRed,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun TaskCheckbox(
    checked: Boolean,
    onCheckedChange: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .size(22.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(if (checked) TaskRed else androidx.compose.ui.graphics.Color.White)
            .border(
                width = 1.dp,
                color = if (checked) TaskRed else TextSecondary,
                shape = RoundedCornerShape(3.dp),
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onCheckedChange,
            )
            .semantics {
                role = Role.Checkbox
                toggleableState = if (checked) ToggleableState.On else ToggleableState.Off
                contentDescription = "任务完成状态"
            },
        contentAlignment = Alignment.Center,
    ) {
        if (checked) {
            Canvas(modifier = Modifier.size(14.dp)) {
                val stroke = 2.dp.toPx()
                drawLine(
                    color = androidx.compose.ui.graphics.Color.White,
                    start = Offset(size.width * 0.08f, size.height * 0.52f),
                    end = Offset(size.width * 0.38f, size.height * 0.78f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round,
                )
                drawLine(
                    color = androidx.compose.ui.graphics.Color.White,
                    start = Offset(size.width * 0.38f, size.height * 0.78f),
                    end = Offset(size.width * 0.92f, size.height * 0.18f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round,
                )
            }
        }
    }
}

