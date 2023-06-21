package rs.ac.metropolitan.projekat.common.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    val id: String,
    val email: String,
    val password: String,
    @PrimaryKey(autoGenerate = false)
    val username: String,
    val role: String
)