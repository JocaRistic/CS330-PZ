package rs.ac.metropolitan.projekat.view.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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

    val context = LocalContext.current

    Column {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
<<<<<<< HEAD
=======
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Email
//            ),
//            isError = !isEmailValid(email),
//            visualTransformation = VisualTransformation.None
>>>>>>> 5c306112aad41453196195e8ca085da53ede2786
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
                    val user = User(email = email, password = password, username = username, role = "user", id = UUID.randomUUID().toString())
                    vm.registerUser(user)
<<<<<<< HEAD
                    if (!vm.registrovan.value){
                        Toast.makeText(context, "Uneti username je zauzet.", Toast.LENGTH_LONG).show()
                    }
=======
>>>>>>> 5c306112aad41453196195e8ca085da53ede2786
                }

            }
        ) {
            Text("Register")
        }

        Text(
            text = registrationStatus,
            color = Color.Red,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}



