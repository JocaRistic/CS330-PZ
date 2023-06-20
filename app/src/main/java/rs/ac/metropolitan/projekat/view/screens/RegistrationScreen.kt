package rs.ac.metropolitan.projekat.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import rs.ac.metropolitan.projekat.common.models.User
import rs.ac.metropolitan.projekat.view.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(vm: AppViewModel, paddingValues: PaddingValues) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var registrationStatus by remember { mutableStateOf("") }

    Column {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = !isEmailValid(email),
            visualTransformation = VisualTransformation.None
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                if (password.length <= 6) {
                    registrationStatus = "Sifra mora biti duza od 6 karaktera"
                } else if(!isEmailValid(email)){
                    registrationStatus = "Uneti email nije validan"
                } else if(username.length < 3){
                    registrationStatus = "Username mora imati bar 3 karaktera"
                } else {
                    val user = User(email = email, password = password, username = username, role = "user")
//                    registrationStatus = vm.registerUser(user)
                }

            }
        ) {
            Text("Register")
        }

        Text(registrationStatus)
    }
}

private fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    return emailRegex.matches(email)
}


