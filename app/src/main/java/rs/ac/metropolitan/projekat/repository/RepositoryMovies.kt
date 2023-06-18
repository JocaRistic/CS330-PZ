package rs.ac.metropolitan.projekat.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import rs.ac.metropolitan.projekat.api.ApiService
import rs.ac.metropolitan.projekat.api.RetrofitHelper
import rs.ac.metropolitan.projekat.common.models.Movie

class RepositoryMovies {
    var moviesFlow: Flow<List<Movie>> = flowOf(listOf())

    suspend fun loadMovies(){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val result = apiService.getMovies()
        if (result != null){
            moviesFlow = flowOf(result)
        }
    }

    suspend fun submitMovie(movie: Movie){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val result = apiService.addMovie(movie)
    }

    suspend fun deleteMovie(id: String){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val result = apiService.deleteMovie(id)
    }

}