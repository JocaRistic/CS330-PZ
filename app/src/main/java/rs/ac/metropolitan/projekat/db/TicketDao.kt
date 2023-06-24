package rs.ac.metropolitan.projekat.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rs.ac.metropolitan.projekat.common.models.TicketDB

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllTickets(tickets: List<TicketDB>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTicket(ticket: TicketDB)

    @Query("SELECT * FROM ticket_table")
    fun getAllTickets(): List<TicketDB>

    @Query("SELECT * FROM ticket_table WHERE user_username == :username")
    fun getTicketsByUsername(username: String): List<TicketDB>

    @Query("SELECT * FROM ticket_table WHERE ticket_number == :ticketNumber")
    fun getTicketByTicketNumber(ticketNumber: String): TicketDB

    @Query("DELETE FROM ticket_table WHERE ticket_number == :id")
    suspend fun deleteTicketById(id: String)

    @Query("DELETE FROM ticket_table")
    suspend fun deleteAllTickets()


}