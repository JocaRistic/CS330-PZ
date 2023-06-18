package rs.ac.metropolitan.projekat.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rs.ac.metropolitan.projekat.common.Constants
import rs.ac.metropolitan.projekat.common.models.Movie
import rs.ac.metropolitan.projekat.common.models.User

interface ApiService {

    @GET(Constants.USERS_URL)
    suspend fun getUsers(): List<User>

    @POST(Constants.USERS_URL)
    suspend fun addUser(@Body user: User)

    @DELETE(Constants.USERS_URL + "/{username}")
    suspend fun deleteUser(@Path ("username") username: String)

    @GET(Constants.MOVIES_URL)
    suspend fun getMovies(): List<Movie>

    @POST(Constants.MOVIES_URL)
    suspend fun addMovie(@Body movie: Movie)

    @DELETE(Constants.MOVIES_URL + "/{id}")
    suspend fun deleteMovie(@Path ("id") id: String)

}