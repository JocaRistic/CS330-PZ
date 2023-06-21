package rs.ac.metropolitan.projekat.common.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class Movie(
    val date: String,
    val description: String,
    val duration: String,
    val genre: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val main_actor: String,
    val time: String,
    val title: String
)