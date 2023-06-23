package rs.ac.metropolitan.projekat.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import rs.ac.metropolitan.projekat.view.AppViewModel
import rs.ac.metropolitan.projekat.view.screens.AddMovieScreen
import rs.ac.metropolitan.projekat.view.screens.HomeScreen
import rs.ac.metropolitan.projekat.view.screens.LoginScreen
import rs.ac.metropolitan.projekat.view.screens.MovieDetailScreen
import rs.ac.metropolitan.projekat.view.screens.MoviesListScreen
import rs.ac.metropolitan.projekat.view.screens.RegistrationScreen

@Composable
fun NavSetup(navController: NavHostController) {
    val vm: AppViewModel = viewModel()
    val paddingValues = PaddingValues()
    vm.navController = navController

    NavHost(navController = navController, startDestination = NavigationRoutes.Home.route) {
        composable(route = NavigationRoutes.Home.route){
            HomeScreen(vm, paddingValues = paddingValues)
        }
        composable(route = NavigationRoutes.Registration.route){
            RegistrationScreen(vm, paddingValues)
        }
        composable(route = NavigationRoutes.Login.route){
            LoginScreen(vm, paddingValues)
        }
        composable(route = NavigationRoutes.MoviesList.route){
            MoviesListScreen(vm, paddingValues)
        }
        composable(route = NavigationRoutes.AddMovie.route){
            AddMovieScreen(vm = vm, paddingValues = paddingValues)
        }
        composable(route = NavigationRoutes.MovieDetailScreen.route) { navBackStackEntry ->
            var movieId = navBackStackEntry.arguments?.getString("movieId")
            if (movieId != null){
                MovieDetailScreen(vm = vm, movieId = movieId, paddingValues = paddingValues)
            } else {
                Toast.makeText(navController.context, "Error, elementId is required!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}