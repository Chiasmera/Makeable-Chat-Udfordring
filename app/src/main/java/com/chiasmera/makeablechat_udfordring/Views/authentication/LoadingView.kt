package com.chiasmera.makeablechat_udfordring.Views.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
fun LoadingView(
    message: String
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(message)
            Icon(
                painter = painterResource(R.drawable.baseline_access_time_24),
                contentDescription = "Error!"
            )
        }

    }

}

@Preview
@Composable
fun LoadingPreview() {
    LoadingView(message = "Loading")
}