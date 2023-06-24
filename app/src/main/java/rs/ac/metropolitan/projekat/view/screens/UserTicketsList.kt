package rs.ac.metropolitan.projekat.view.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import rs.ac.metropolitan.projekat.common.models.TicketDB
import rs.ac.metropolitan.projekat.common.models.User
import rs.ac.metropolitan.projekat.db.LoggedInUser
import rs.ac.metropolitan.projekat.view.AppViewModel
import kotlin.math.log

@Composable
fun TicketsListPage(vm: AppViewModel, paddingValues: PaddingValues) {
    val tickets = vm.tickets.observeAsState()

    //context
    val context = LocalContext.current

    //get logged in user
    val loggedInUserStore = LoggedInUser(context)
    val userLoggedIn = loggedInUserStore.getLoggedInUser.collectAsState(initial = "")

    var username by remember { mutableStateOf("") }

    if (userLoggedIn.value != ""){
        val user = userLoggedIn.value as User
        username = user.username

        LaunchedEffect(vm.loadAllTicketsForUser(context, username)) {
        }
    }



    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .scale(1.5f),
                        onClick = { vm.goBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "Rezervacije",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            tickets.value?.let {
                items(it) { ticket ->
                    TicketView(
                        ticket = ticket,
                        goBack = { vm.goBack() },
                        vm = vm,
                        context = context
                    )
                }
            }
        }
    }
}

@Composable
fun TicketView(ticket: TicketDB, vm: AppViewModel, context: Context, goBack: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 10.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "${ticket.movie_title}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Center)
            )
            IconButton(
                modifier = Modifier
                    .scale(1.5f)
                    .align(Alignment.BottomEnd),
                onClick = { vm.deleteTicketById(context, ticket.ticket_number) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }

        }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .width(250.dp),
            ) {
                Text(
                    text = "Serijski broj karte",
                    color = Color.Gray,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "${ticket.ticket_number}",
                    modifier = Modifier.padding(4.dp)
                )
            }

    }

}