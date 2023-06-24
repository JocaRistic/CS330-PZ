package rs.ac.metropolitan.projekat.api

import androidx.room.Update
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import rs.ac.metropolitan.projekat.common.Constants
import rs.ac.metropolitan.projekat.common.models.Movie
import rs.ac.metropolitan.projekat.common.models.Ticket
import rs.ac.metropolitan.projekat.common.models.User

interface ApiService {

    //Users
    @GET(Constants.USERS_URL)
    suspend fun getUsers(): List<User>

    @POST(Constants.USERS_URL)
    suspend fun addUser(@Body user: User)

    @DELETE(Constants.USERS_URL + "/{id}")
    suspend fun deleteUser(@Path ("id") id: String)

    //Movies
    @GET(Constants.MOVIES_URL)
    suspend fun getMovies(): List<Movie>

    @POST(Constants.MOVIES_URL)
    suspend fun addMovie(@Body movie: Movie)

    @DELETE(Constants.MOVIES_URL + "/{id}")
    suspend fun deleteMovie(@Path ("id") id: String)

    @PUT(Constants.MOVIES_URL + "/{id}")
    suspend fun updateMovie(@Path("id") id: String, @Body updatedMovie: Movie): Response<Movie>

    //Tickets
    @GET(Constants.TICKETS_URL)
    suspend fun getTickets(): List<Ticket>

    @POST(Constants.TICKETS_URL)
    suspend fun addTicket(@Body ticket: Ticket)

    @POST(Constants.TICKETS_URL)
    suspend fun addTickets(@Body tickets: List<Ticket>)

    @DELETE(Constants.TICKETS_URL + "/{id}")
    suspend fun deleteTicket(@Path ("id") id: String)

}