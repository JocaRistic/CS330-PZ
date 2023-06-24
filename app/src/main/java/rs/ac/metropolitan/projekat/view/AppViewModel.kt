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
import rs.ac.metropolitan.projekat.common.models.TicketDB
import rs.ac.metropolitan.projekat.common.models.User
import rs.ac.metropolitan.projekat.db.BazaPodataka
import rs.ac.metropolitan.projekat.db.LoggedInUser
import rs.ac.metropolitan.projekat.navigation.NavigationRoutes
import rs.ac.metropolitan.projekat.repository.RepositoryMovies
import rs.ac.metropolitan.projekat.repository.RepositoryTickets
import rs.ac.metropolitan.projekat.repository.RepositoryUsers


class AppViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var navController: NavHostController
    var granted = mutableStateOf(false)

    private val usersRepository: RepositoryUsers
    private val moviesRepository: RepositoryMovies
    private val ticketsRepository: RepositoryTickets

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

        val ticketDao = BazaPodataka.getDatabase(application).ticketDao()
        ticketsRepository = RepositoryTickets(ticketDao)

        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.loadUsers()
            moviesRepository.loadMovies()
//            ticketsRepository.sendTicketsToServer()
        }
    }

    fun registerUser(context: Context, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val postojeciUser = usersRepository.getUserFromDB(user.username)
            if (postojeciUser != null) {
                registrovan.value = false
            } else {
                usersRepository.submitUser(user)
                registrovan.value = true
                viewModelScope.launch(Dispatchers.Main){
                    Toast.makeText(context, "Uspesna registracija", Toast.LENGTH_LONG).show()
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
            } else {
                ulogovan.value = false
            }
        }
    }

    fun loadMoviesfromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.getAllMoviesFromDB()
            viewModelScope.launch(Dispatchers.Main) {
                moviesRepository.moviesFlow.collect() {
                    _movies.value = it
                }
            }
        }
    }

    fun getMovieById(id: String): Movie? {
        return _movies.value?.find { it.id == id }
    }

    fun addMovie(context: Context, movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.submitMovie(movie)
            viewModelScope.launch(Dispatchers.Main) {
                Toast.makeText(context, "Film je uspesno dodat", Toast.LENGTH_LONG).show()
                goBack()
            }
        }
    }

    fun deleteMovieById(context: Context, id: String){
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.deleteMovie(id)
            viewModelScope.launch(Dispatchers.Main) {
                Toast.makeText(context, "Film je uspesno obrisan", Toast.LENGTH_LONG).show()
                goBack()
            }
        }
    }

    fun reserveTickets(
        context: Context,
        user: User,
        movieId: String,
        numberOfTickets: Int
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            var movie = getMovieById(movieId)
            var stariBrojKarata = movie!!.broj_karata.toInt()

            //provera da li ima dovoljno karata za rezervaciju
            if (movie!!.broj_karata.toInt() >= numberOfTickets){

                //smanjuju se karte na stanju za film, od trenutnog broja karata
                //oduzima se broj rezervisanih karata
                var noviBrojKarata = movie!!.broj_karata.toInt() - numberOfTickets
                movie.broj_karata = noviBrojKarata.toString()
                val updatedMovie = moviesRepository.updateMovie(movieId, movie)

                //kreira se serijski broj karte i upisuje u bazu rezervisana karta
                var brojKarte = mutableStateOf(0)
                var serijskiBrojKarte = mutableStateOf("")
                for (i in 0 until numberOfTickets){
                    brojKarte.value = stariBrojKarata.toInt() - i
                    serijskiBrojKarte.value = "${movie.title}-TIC-NUM-${brojKarte.value}"
                    ticketsRepository.submitTicket(
                        TicketDB(
                            ticket_number = serijskiBrojKarte.value,
                            movie_title = movie.title,
                            user_username = user.username
                        )
                    )
                    viewModelScope.launch(Dispatchers.Main) {
                        Toast.makeText(context, "Uspesna rezervacija", Toast.LENGTH_LONG).show()
                    }
                }

            } else {
                viewModelScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Nema dovoljno karata!", Toast.LENGTH_LONG).show()
                }
            }
        }
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

    fun navigateToAddMovie() {
        navController.navigate(NavigationRoutes.AddMovie.route)
    }

    fun goBack() {
        navController.popBackStack()
    }

}