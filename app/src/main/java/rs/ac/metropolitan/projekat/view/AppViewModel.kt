package rs.ac.metropolitan.projekat.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import rs.ac.metropolitan.projekat.common.models.User
import rs.ac.metropolitan.projekat.navigation.NavigationRoutes
import rs.ac.metropolitan.projekat.repository.RepositoryUsers

class AppViewModel : ViewModel() {
    lateinit var navController: NavHostController
    val repositoryUsers = RepositoryUsers()
    var granted = mutableStateOf(false)

//    private val _users = MutableLiveData<List<User>>()
//    val users: LiveData<List<User>>
//        get() = _users




    fun navigateToRegistration() {
        navController.navigate(NavigationRoutes.Registration.route)
    }


}