package com.example.news.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchAppBar(
    modifier: Modifier= Modifier,
    value: String,
    onValueChange:(String)->Unit,
    oncloseiconclicked:()->Unit,
    onsearchiconclicked:()->Unit
) {
    androidx.compose.material3.TextField(
        modifier = modifier.fillMaxWidth(),
        value=value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        leadingIcon = {
         Icon(
             imageVector = Icons.Filled.Search, contentDescription = "",
             tint = Color.White.copy(alpha = 0.7f)
         )
        },
        placeholder = {
           Text(
                text = "Search...",
                style = TextStyle(color = Color.White.copy(alpha = 0.7f))
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                if (value.isNotEmpty()) onValueChange("")
                else oncloseiconclicked()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close, contentDescription = "",
                    tint = Color.White
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
       keyboardActions = KeyboardActions (onSearch = {onsearchiconclicked()}),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.White
        )
    )

}
