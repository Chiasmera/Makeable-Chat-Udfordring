package com.chiasmera.makeablechat_udfordring.Views.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chiasmera.makeablechat_udfordring.Views.components.InputField

@Composable
fun SignUpView(
    onNavigateToLogIn: () -> Unit,
    onSignUp: (email: String, password: String, userName: String) -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            InputField(
                value = userName,
                onValueChange = { userName = it },
                title = "User Name",
                placeholder = "ChatMeister69"
            )
            InputField(
                value = email,
                onValueChange = { email = it },
                title = "Email",
                placeholder = "example@gmail.com"
            )
            InputField(
                value = password,
                onValueChange = { password = it },
                title = "Password",
                placeholder = "p/\\5Sw0rD314",
                secret = true
            )
            InputField(
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                title = "Repeat Password",
                placeholder = "p/\\5Sw0rD314",
                secret = true
            )
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilledTonalButton(
                onClick = { onSignUp(email, password, userName) },
                enabled = password == repeatPassword
                        && !userName.isBlank()
                        && !email.isBlank()
                        && !password.isBlank()
            ) {
                Text("Sign Up")
            }
            TextButton(
                onClick = onNavigateToLogIn,
                modifier = Modifier.padding(top = 0.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 0.dp),
                    text = "Already have a user?",
                    style = MaterialTheme.typography.labelSmall

                )
            }
        }
    }

}

@Preview
@Composable
fun SignUpPreview() {
    SignUpView({}, { _, _, _ -> })
}