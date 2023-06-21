package rs.ac.metropolitan.projekat.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import rs.ac.metropolitan.projekat.view.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(vm: AppViewModel, paddingValues: PaddingValues){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        // Login Button
        Button(
            onClick = {
                if (username.length < 3) {
                    loginError = true
                } else if(password.length <= 6){
                    loginError = true
                } else {
                    vm.loginUser(username, password)
                }

            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Login")
        }

        if (loginError) {
            Text(
                text = "Invalid username or password",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Text(
            text = "Don't have an account? Register",
            color = Color.Blue,
            modifier = Modifier
                .clickable { vm.navigateToRegistration() }
                .padding(top = 16.dp)
        )
    }
}