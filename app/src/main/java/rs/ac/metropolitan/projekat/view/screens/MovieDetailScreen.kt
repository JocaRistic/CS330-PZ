package rs.ac.metropolitan.projekat.view.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import coil.compose.AsyncImage
import rs.ac.metropolitan.projekat.R
import rs.ac.metropolitan.projekat.common.models.Movie
import rs.ac.metropolitan.projekat.common.models.User
import rs.ac.metropolitan.projekat.db.LoggedInUser
import rs.ac.metropolitan.projekat.view.AppViewModel

@Composable
fun MovieDetailScreen(vm: AppViewModel, movieId: String, paddingValues: PaddingValues) {
    //context
    val context = LocalContext.current
    MovieDetail(
        vm = vm,
        context = context,
        movie = vm.getMovieById(movieId),
        goBack = { vm.goBack() },
        delete = { vm.deleteMovieById(context, movieId) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetail(
    vm: AppViewModel,
    context: Context,
    movie: Movie?,
    goBack: () -> Unit,
    delete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    //check admin logged in
    val loggedInUserStore = LoggedInUser(context)
    val adminLoggedIn = loggedInUserStore.adminLoggedIn.collectAsState(initial = false)

    //get logged in user
    val userLoggedIn = loggedInUserStore.getLoggedInUser.collectAsState(initial = "")

    //true ukoliko treba da se prikaze dialog
    var isReservationDialogOpen by remember { mutableStateOf(false) }

    //broj karata koje je korisnik uneo za rezervaciju
    var brojPotrebnihKarata by remember { mutableStateOf("") }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                modifier = Modifier
                    .background(Color.Transparent)
                    .scale(1.5f),
                onClick = { goBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(text = "Pregled filma",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
            if (adminLoggedIn.value) {
                IconButton(
                    modifier = Modifier
                        .scale(1.5f)
                        .align(Alignment.BottomEnd),
                    onClick = { delete() }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
        movie?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                AsyncImage(
                    model = it.photo,
                    contentDescription = null,
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "${it.title}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "${it.genre}",
                    color = Color.Gray,
                    modifier = Modifier.padding(4.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Trajanje:",
                                color = Color.Gray
                            )
                            Text(
                                text = "${it.duration}"
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Glavna uloga:",
                                color = Color.Gray
                            )
                            Text(
                                text = "${it.main_actor}"
                            )
                        }
                    }
                }
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 5.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                            .padding(16.dp)
                            .clip(MaterialTheme.shapes.small)
                    ) {
                        Text(
                            text = "Opis filma (klik za vise): \n${it.description}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .animateContentSize(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioLowBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                                .clickable { expanded = !expanded },
                            maxLines = if (!expanded) 2 else Int.MAX_VALUE
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Termin prikazivanja",
                                textAlign = TextAlign.Center,
                                color = Color.Gray
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.calendar),
                                contentDescription = "Date",
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            Text(
                                text = "${it.date}",
                                Modifier.padding(start = 10.dp)
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.time),
                                contentDescription = "Time",
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            Text(
                                text = "${it.time}",
                                Modifier.padding(start = 10.dp)
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Info o rezervaciji",
                                textAlign = TextAlign.Center,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Broj dostupnih karata:",
                                color = Color.Gray
                            )
                            Text(
                                text = "${it.broj_karata}",
                                Modifier.padding(start = 10.dp)
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Cena karte:",
                                color = Color.Gray
                            )
                            Text(
                                text = "${it.cena_karte}",
                                Modifier.padding(start = 10.dp)
                            )
                        }
                    }
                }
                Button(
                    onClick = {
                        isReservationDialogOpen = true
                    },
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(text = "Rezervisi karte")
                }
            }

            //ako je isReservatioDialogOpen == true prikazuje dialog
            if (isReservationDialogOpen) {
                Dialog(
                    onDismissRequest = { isReservationDialogOpen = false },
                    properties = DialogProperties(dismissOnClickOutside = false)
                ) {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            TextField(
                                value = brojPotrebnihKarata,
                                onValueChange = {
                                    brojPotrebnihKarata = it
                                },
                                label = { Text(text = "Broj karata za rezervaciju") },
                                placeholder = { Text(text = "Unesite broj karata") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Button(
                                onClick = {
                                    if(brojPotrebnihKarata.isDigitsOnly()) {

                                        vm.reserveTickets(
                                            context,
                                            userLoggedIn.value as User,
                                            movie.id,
                                            brojPotrebnihKarata.toInt()
                                        )
                                        isReservationDialogOpen = false
                                    } else {
                                        Toast.makeText(context, "Unos nije validan", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(text = "Rezervisi")
                            }
                        }
                    }
                }
            }
        }
    }
}
