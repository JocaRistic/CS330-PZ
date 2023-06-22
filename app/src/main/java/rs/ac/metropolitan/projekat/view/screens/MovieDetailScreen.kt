package rs.ac.metropolitan.projekat.view.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import rs.ac.metropolitan.projekat.view.AppViewModel

@Composable
fun MovieDetailScreen(vm: AppViewModel, movieId: String, paddingValues: PaddingValues) {
    Text(text = movieId)
}