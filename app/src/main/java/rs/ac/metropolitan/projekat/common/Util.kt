package rs.ac.metropolitan.projekat.common

class Util {
    companion object {
        fun isEmailValid(email: String): Boolean {
            val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
            return emailRegex.matches(email)
        }
    }
}