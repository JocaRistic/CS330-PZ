package rs.ac.metropolitan.projekat.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import rs.ac.metropolitan.projekat.api.ApiService
import rs.ac.metropolitan.projekat.api.RetrofitHelper
import rs.ac.metropolitan.projekat.common.models.Ticket
import rs.ac.metropolitan.projekat.common.models.TicketDB
import rs.ac.metropolitan.projekat.db.TicketDao

class RepositoryTickets(private val ticketDao: TicketDao) {

    var ticketsFlow: Flow<List<TicketDB>> = flowOf(listOf())

    suspend fun submitTicket(ticket: TicketDB){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)

        apiService.addTicket(Ticket(id = ticket.ticket_number, movie_title = ticket.movie_title))
        ticketDao.addTicket(ticket)
    }

    fun getAllTicketsByUsername(username: String) {
        val result = ticketDao.getTicketsByUsername(username)
        if(result != null){
            ticketsFlow = flowOf(result)
        }
    }

    suspend fun deleteTicketById(id: String){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        apiService.deleteTicket(id)
        ticketDao.deleteTicketById(id)
    }

    suspend fun deleteAllTickets(){
        ticketDao.deleteAllTickets()
    }


}