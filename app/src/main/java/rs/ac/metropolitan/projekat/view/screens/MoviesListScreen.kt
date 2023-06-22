package rs.ac.metropolitan.projekat.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import rs.ac.metropolitan.projekat.R
import rs.ac.metropolitan.projekat.common.models.Movie
import rs.ac.metropolitan.projekat.view.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListScreen(vm: AppViewModel, paddingValues: PaddingValues){
    val movies = vm.movies.observeAsState()
    LaunchedEffect(vm.loadMoviesfromDB()){
    }

//    LazyColumn {
//        movies.value?.let {
//            items(it) { movie ->
//                MovieItem(movie = movie){
//                    vm.navigateToDetailMovie(it)
//                }
//                Divider(color = Color.Gray, thickness = 1.dp)
//            }
//        }
//    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(8.dp)
    ){
        movies.value?.let {
            items(it) {movie ->
                MovieItem(movie = movie){
                    vm.navigateToDetailMovie(it)
                }
            }
        }
    }

}

@Composable
fun MovieItem(movie: Movie, onSelected: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable { onSelected(movie.id) }
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ) {
            AsyncImage(
                model = movie.photo,
                contentDescription = null,
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()

            )
            Text(
                text = movie.genre,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 5.dp)
            )
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.calendar),
                    contentDescription = "Time",
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = "${movie.date}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.time),
                    contentDescription = "Time",
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = "${movie.time}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}
