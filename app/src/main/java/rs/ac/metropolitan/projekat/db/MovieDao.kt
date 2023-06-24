package rs.ac.metropolitan.projekat.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import rs.ac.metropolitan.projekat.common.models.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: Movie)

    @Query("SELECT * FROM movie_table")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie_table WHERE id == :id")
    fun getMovieById(id: String): LiveData<Movie>

    @Query("DELETE FROM movie_table WHERE id == :id")
    suspend fun deleteMovieById(id: String)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAllMovies()

    @Update
    suspend fun updateMovie(movie: Movie)

    @Transaction
    suspend fun deleteAndInsertAllMovies(movies: List<Movie>) {
        deleteAllMovies()
        addAllMovies(movies)
    }

}