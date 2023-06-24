package rs.ac.metropolitan.projekat.view.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import rs.ac.metropolitan.projekat.common.Util.Companion.isEmailValid
import rs.ac.metropolitan.projekat.common.models.User
import rs.ac.metropolitan.projekat.view.AppViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(vm: AppViewModel, paddingValues: PaddingValues) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var registrationStatus by remember { mutableStateOf("") }
    var registrationError by remember { mutableStateOf(false) }

    //context
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registracija",
            style = MaterialTheme.typography.titleLarge
        )
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.padding(top = 16.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.padding(top = 16.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(top = 16.dp)
        )

        if (registrationError){
            Text(
                text = registrationStatus,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Button(
            onClick = {
                if (password.length <= 6) {
                    registrationStatus = "Sifra mora biti duza od 6 karaktera"
                    registrationError = true
                } else if(!isEmailValid(email)){
                    registrationStatus = "Uneti email nije validan"
                    registrationError = true
                } else if(username.length < 3){
                    registrationStatus = "Username mora imati bar 3 karaktera"
                    registrationError = true
                } else {
                    val user = User(
                        email = email,
                        password = password,
                        username = username,
                        role = "user",
                        id = UUID.randomUUID().toString()
                    )
                    vm.registerUser(context, user)
                    if (!vm.registrovan.value){
                        registrationStatus = "Uneti username je zauzet."
                        registrationError = true
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Register")
        }

        Text(
            text = "You have an account? Login",
            color = Color.Blue,
            modifier = Modifier
                .clickable { vm.navigateToLogin() }
                .padding(top = 16.dp)
        )
    }
}



