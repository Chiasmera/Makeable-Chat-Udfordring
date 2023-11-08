package com.chiasmera.makeablechat_udfordring.Views.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chiasmera.makeablechat_udfordring.R
import com.chiasmera.makeablechat_udfordring.Views.components.InputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMessageView(
    message : String,
    modifier: Modifier = Modifier,
    onclick : () -> Unit,
    onValueChange : (String) -> Unit
    ) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InputField(
            value = message,
            title = "Message",
            placeholder = "Message",
            onValueChange = onValueChange)

        FilledIconButton(onClick = onclick) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_send_24), contentDescription = stringResource(id = R.string.send_icon)
            )
        }
    }
}

@Preview
@Composable
fun SendMessagePreview() {
    SendMessageView(message = "", onclick = { }, onValueChange = {})
}