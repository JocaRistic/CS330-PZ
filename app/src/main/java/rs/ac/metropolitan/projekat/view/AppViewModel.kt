package rs.ac.metropolitan.projekat.view

<<<<<<< HEAD
import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
=======
import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
>>>>>>> 5c306112aad41453196195e8ca085da53ede2786
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import rs.ac.metropolitan.projekat.common.models.User
import rs.ac.metropolitan.projekat.db.BazaPodataka
import rs.ac.metropolitan.projekat.navigation.NavigationRoutes
import rs.ac.metropolitan.projekat.repository.RepositoryUsers

class AppViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var navController: NavHostController
    var granted = mutableStateOf(false)

    private val usersRepository: RepositoryUsers
<<<<<<< HEAD

    var registrovan = mutableStateOf(false)


    init {
        val userDao = BazaPodataka.getDatabase(application).userDao()
        usersRepository = RepositoryUsers(userDao)
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.loadUsers()
        }
    }

    fun registerUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
//            usersRepository.loadUsers()
            val user = usersRepository.getUserFromDB(user.username)
            if(user != null){
                Log.d("test", "${user.username}")
            } else{
                usersRepository.submitUser(user)
                registrovan.value = true
            }
        }
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = usersRepository.loginUserFromDB(username, password)
            if (user != null){

                Log.d("test", "ulogovan je ${user.username} ${user.role}")
            } else {
                Log.d("test", "GRESKA ")

            }
        }
=======
    var registrovan = mutableStateOf(false)

    init {
        val userDao = BazaPodataka.getDatabase(application).userDao()
        usersRepository = RepositoryUsers(userDao)
//        viewModelScope.launch(Dispatchers.IO) {
//            usersRepository.loadUsers()
//        }
    }


    fun registerUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.loadUsers()
            val korisnik = usersRepository.getUserFromDB(user.username)

                if(korisnik != null){
                    Log.d("test", "${korisnik.username}")
                } else{
                    usersRepository.submitUser(user)
                    registrovan.value = true
                }


        }


>>>>>>> 5c306112aad41453196195e8ca085da53ede2786
    }



    fun navigateToRegistration() {
        navController.navigate(NavigationRoutes.Registration.route)
    }


}