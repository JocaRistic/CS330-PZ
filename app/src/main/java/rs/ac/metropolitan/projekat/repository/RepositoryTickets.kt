package rs.ac.metropolitan.projekat.repository

import android.util.Log
import rs.ac.metropolitan.projekat.api.ApiService
import rs.ac.metropolitan.projekat.api.RetrofitHelper
import rs.ac.metropolitan.projekat.common.models.Ticket
import rs.ac.metropolitan.projekat.common.models.TicketDB
import rs.ac.metropolitan.projekat.db.TicketDao

class RepositoryTickets(private val ticketDao: TicketDao) {

    suspend fun submitTicket(ticket: TicketDB){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)

        apiService.addTicket(Ticket(id = ticket.ticket_number, movie_title = ticket.movie_title))
        ticketDao.addTicket(ticket)
    }

    suspend fun deleteAllTickets(){
        ticketDao.deleteAllTickets()
    }

//    suspend fun sendTicketsToServer(){
//
//        val tickets = ticketDao.getAllTickets()
//        Log.d("test", "TEST ${tickets.toString()}")
//        val listaTiketa = mutableListOf<Ticket>()
//
//        if (tickets != null && tickets.isNotEmpty()){
//
//            for (ticketDb in tickets){
//                listaTiketa.add(
//                    Ticket(
//                        id = ticketDb.ticket_number,
//                        movie_title = ticketDb.movie_title
//                    )
//                )
//            }
//
//            val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
//            apiService.addTickets(listaTiketa.toList())
//
//        }
//
//
//    }
}