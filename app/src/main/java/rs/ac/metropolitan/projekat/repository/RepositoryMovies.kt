package rs.ac.metropolitan.projekat.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import rs.ac.metropolitan.projekat.api.ApiService
import rs.ac.metropolitan.projekat.api.RetrofitHelper
import rs.ac.metropolitan.projekat.common.models.Movie
import rs.ac.metropolitan.projekat.db.MovieDao

class RepositoryMovies(private val movieDao: MovieDao) {
    var moviesFlow: Flow<List<Movie>> = flowOf(listOf())

    suspend fun loadMovies(){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val result = apiService.getMovies()
        if (result != null){
            movieDao.deleteAndInsertAllMovies(result)
        }
    }

    suspend fun submitMovie(movie: Movie){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        apiService.addMovie(movie)
        movieDao.addMovie(movie)
    }

    suspend fun deleteMovie(id: String){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        apiService.deleteMovie(id)
        movieDao.deleteMovieById(id)
    }

    fun getAllMoviesFromDB() {
        val result = movieDao.getAllMovies()
        if(result != null){
            moviesFlow = flowOf(result)
        }
    }

}