package rs.ac.metropolitan.projekat.view.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import rs.ac.metropolitan.projekat.common.models.Movie
import rs.ac.metropolitan.projekat.view.AppViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMovieScreen(vm: AppViewModel, paddingValues: PaddingValues) {

    //context
    val context = LocalContext.current

    var title by remember { mutableStateOf(TextFieldValue("")) }
    var genre by remember { mutableStateOf(TextFieldValue("")) }
    var photo by remember { mutableStateOf(TextFieldValue("")) }
    var duration by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var mainActor by remember { mutableStateOf(TextFieldValue("")) }
    var ticketPrice by remember { mutableStateOf(TextFieldValue("")) }
    var numberOfTickets by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    var calendarState = rememberSheetState()
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { datum ->
            date = datum.toString()
        }
    )

    var timeState = rememberSheetState()
    ClockDialog(
        state = timeState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            time = "$hours:${minutes}"
        }
    )

    var error by remember { mutableStateOf(false) }


    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
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
                        text = "Novi film",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            item {
                TextField(
                    value = title,
                    onValueChange = { newText ->
                        title = newText
                    },
                    label = { Text("Naziv") },
                    placeholder = { Text("Unesite naziv filma") },
                )
            }
            item {
                TextField(
                    value = genre,
                    onValueChange = { newText ->
                        genre = newText
                    },
                    label = { Text("Zanr") },
                    placeholder = { Text("Unesite zanr filma") },
                )
            }
            item {
                TextField(
                    value = photo,
                    onValueChange = {
                        photo = it
                    },
                    label = { Text(text = "Slika ") },
                    placeholder = { Text(text = "Unesite link slike") },
                )
            }
            item {
                TextField(
                    value = duration,
                    onValueChange = {
                        duration = it
                    },
                    label = { Text(text = "Trajanje filma") },
                    placeholder = { Text(text = "Unesite trajanje filma") },
                )
            }
            item {
                TextField(
                    value = mainActor,
                    onValueChange = {
                        mainActor = it
                    },
                    label = { Text(text = "Glavni glumac") },
                    placeholder = { Text(text = "Unesite glavnog glumca") },
                )
            }
            item {
                TextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    label = { Text(text = "Opis filma") },
                    placeholder = { Text(text = "Unesite opis filma") },
                )
            }
            item {
                Button(onClick = {
                    calendarState.show()
                }) {
                    Text(text = "Izaberi datum prikazivanja")
                }
            }
            item {
                Button(onClick = {
                    timeState.show()
                }) {
                    Text(text = "Izaberi vreme prikazivanja")
                }
            }
            item {
                TextField(
                    value = ticketPrice,
                    onValueChange = {
                        ticketPrice = it
                    },
                    label = { Text(text = "Cena karte") },
                    placeholder = { Text(text = "Unesite cenu karte") },
                )
            }
            item {
                TextField(
                    value = numberOfTickets,
                    onValueChange = {
                        numberOfTickets = it
                    },
                    label = { Text(text = "Broj karata") },
                    placeholder = { Text(text = "Unesite broj karata") },
                )
            }

            if (error){
                item {
                    Text(
                        text = "Niste uneli sve podatke",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            item {
                Button(onClick = {
                    if (title.text != "" && genre.text != "" && photo.text != "" && duration.text != "" && mainActor.text != "" && description.text != "" && date != "" && time != "" && ticketPrice.text != "" && numberOfTickets.text != ""){
                        vm.addMovie(context,
                            Movie(
                                title = title.text,
                                genre = genre.text,
                                photo = photo.text,
                                duration = duration.text,
                                main_actor = mainActor.text,
                                description = description.text,
                                date = date,
                                time = time,
                                id = UUID.randomUUID().toString(),
                                cena_karte = ticketPrice.text,
                                broj_karata = numberOfTickets.text
                            )
                        )
                    } else {
                        error = true
                    }
                }) {
                    Text(text = "Dodaj")
                }
            }
        }
    }
}