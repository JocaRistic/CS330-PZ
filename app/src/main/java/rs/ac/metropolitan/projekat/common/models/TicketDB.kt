package rs.ac.metropolitan.projekat.common.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticket_table")
data class TicketDB(
    @PrimaryKey(autoGenerate = false)
    val ticket_number: String,
    val user_username: String,
    val movie_title: String
)