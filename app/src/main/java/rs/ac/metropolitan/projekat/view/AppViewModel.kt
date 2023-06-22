package rs.ac.metropolitan.projekat.view


import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import rs.ac.metropolitan.projekat.common.models.Movie
import rs.ac.metropolitan.projekat.common.models.User
import rs.ac.metropolitan.projekat.db.BazaPodataka
import rs.ac.metropolitan.projekat.db.LoggedInUser
import rs.ac.metropolitan.projekat.navigation.NavigationRoutes
import rs.ac.metropolitan.projekat.repository.RepositoryMovies
import rs.ac.metropolitan.projekat.repository.RepositoryUsers


class AppViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var navController: NavHostController
    var granted = mutableStateOf(false)

    private val usersRepository: RepositoryUsers
    private val moviesRepository: RepositoryMovies

    var registrovan = mutableStateOf(false)
    var ulogovan = mutableStateOf(false)

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies


    init {
        val userDao = BazaPodataka.getDatabase(application).userDao()
        usersRepository = RepositoryUsers(userDao)

        val movieDao = BazaPodataka.getDatabase(application).movieDao()
        moviesRepository = RepositoryMovies(movieDao)

        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.loadUsers()
            moviesRepository.loadMovies()
        }
    }

    fun registerUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
//            usersRepository.loadUsers()
            val postojeciUser = usersRepository.getUserFromDB(user.username)
            if (postojeciUser != null) {
                registrovan.value = false
                //Log.d("test", "vec postoji ${user.username}")
            } else {
                usersRepository.submitUser(user)
                registrovan.value = true
                viewModelScope.launch(Dispatchers.Main){
                    navigateToLogin()
                }

            }
        }
    }

    fun loginUser(context: Context, scope: CoroutineScope, username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = usersRepository.loginUserFromDB(username, password)
            if (user != null) {
                val loggedInUserStore = LoggedInUser(context)
                scope.launch {
                    loggedInUserStore.saveLoggedInUser(user)
                    ulogovan.value = true
                    Toast.makeText(context, "Uspesan login", Toast.LENGTH_LONG).show()
                    viewModelScope.launch(Dispatchers.Main){
                        navigateToMoviesList()
                    }
                }
                Log.d("test", "ulogovan je ${user.username} ${user.role}")
            } else {
                ulogovan.value = false
                Log.d("test", "POGRESNI PODACI ZA LOGIN")
            }
        }
    }

    fun loadMoviesfromDB() {
        //moviesRepository.getAllMoviesFromDB()

        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.getAllMoviesFromDB()
            viewModelScope.launch(Dispatchers.Main) {
                moviesRepository.moviesFlow.collect() {
                    _movies.value = it
                }
            }
        }
    }

    fun getMovie(id: String): Movie? {
        return _movies.value?.find { it.id == id }
    }


    fun navigateToRegistration() {
        navController.navigate(NavigationRoutes.Registration.route)
    }

    fun navigateToLogin() {
        navController.navigate(NavigationRoutes.Login.route)
    }

    fun navigateToMoviesList() {
        navController.navigate(NavigationRoutes.MoviesList.route)
    }

    fun navigateToDetailMovie(id: String) {
        navController.navigate(NavigationRoutes.MovieDetailScreen.createRoute(id))
    }

    fun goBack() {
        navController.popBackStack()
    }

}