package com.chiasmera.makeablechat_udfordring.Views.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.chiasmera.makeablechat_udfordring.R

@Composable
fun ErrorView(
    message: String
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(message, modifier = Modifier.align(Alignment.TopCenter))
        Icon(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.baseline_error_outline_24),
            contentDescription = "Error!"
        )
    }
}

@Preview
@Composable
fun ErrorPreview() {
    ErrorView(message = "Ups")
}