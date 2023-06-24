package rs.ac.metropolitan.projekat.navigation

sealed class NavigationRoutes(val route: String) {
    object Home : NavigationRoutes(route = "home")
    object Registration : NavigationRoutes(route = "registration")
    object Login : NavigationRoutes(route = "login")
    object MoviesList : NavigationRoutes(route = "moviesList")
    object MovieDetailScreen : NavigationRoutes(route = "detailMovie/{movieId}"){
        fun createRoute(movieId : String) = "detailMovie/$movieId"
    }
    object AddMovie : NavigationRoutes(route = "addMovie")

    object UserTicketsList : NavigationRoutes(route = "ticketsList")

}
