package com.chiasmera.makeablechat_udfordring.Views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    value: String = "",
    title : String,
    onValueChange : (String) -> Unit = {},
    placeholder : String,
    secret : Boolean = false
) {
        OutlinedTextField(
            modifier = Modifier.padding(top = 0.dp),
            value = value ,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {Text(placeholder, style = MaterialTheme.typography.labelSmall)},
            label = { Text(title)},
            visualTransformation = if (secret) { PasswordVisualTransformation('*') } else {VisualTransformation.None}
        )
}


@Preview
@Composable
fun InputFieldPreview() {
    InputField(title = "Navn", placeholder = "Julius")
}