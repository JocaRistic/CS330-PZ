package rs.ac.metropolitan.projekat.common.models

data class User(
    val email: String,
    val password: String,
    val username: String,
    val role: String
)