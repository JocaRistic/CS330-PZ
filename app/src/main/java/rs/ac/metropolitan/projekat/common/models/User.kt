package rs.ac.metropolitan.projekat.common.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val email: String,
    val password: String,
    val username: String,
    val role: String
)