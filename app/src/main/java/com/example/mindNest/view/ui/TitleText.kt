package com.example.mindNest.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TextTitle(title: String, onTitleChange: (String) -> Unit) {
    var errorMessage by remember { mutableStateOf("") }


    val isTitleEmpty = title.isEmpty()
    errorMessage = if (isTitleEmpty) {
        "Title can't be empty"
    } else {
        ""
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = title,
            onValueChange = {
                if (it.length <= 8) {
                    onTitleChange(it)
                }
            },
            label = { Text(text = "Title", color = MaterialTheme.colorScheme.primary) },
            shape = CutCornerShape(topEnd = 16.dp),
            textStyle = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            ),

            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            isError = isTitleEmpty
        )


        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(start = 24.dp)
            )
        }
    }
}

@Composable
fun TextDescription(description: String, onDescriptionChange: (String) -> Unit) {
    TextField(
        value = description,
        onValueChange = onDescriptionChange,
        label = { Text(text = "Description", color = Color.Gray) },
        shape = CutCornerShape(topEnd = 16.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 2.sp

        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)

    )
}
