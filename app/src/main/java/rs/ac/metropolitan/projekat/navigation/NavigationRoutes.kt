package rs.ac.metropolitan.projekat.navigation

sealed class NavigationRoutes(val route: String) {
    object Home : NavigationRoutes(route = "home")
    object Registration : NavigationRoutes(route = "registration")
    object Login : NavigationRoutes(route = "login")
    object MoviesList : NavigationRoutes(route = "moviesList")


}
