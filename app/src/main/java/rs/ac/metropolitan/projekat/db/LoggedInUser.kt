package rs.ac.metropolitan.projekat.db

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import rs.ac.metropolitan.projekat.common.models.User
import kotlinx.coroutines.flow.Flow

class LoggedInUser(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("loggedInUser")
        val ID = stringPreferencesKey("id")
        val USERNAME = stringPreferencesKey("username")
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = stringPreferencesKey("password")
        val ROLE = stringPreferencesKey("role")
    }

    val getLoggedInUser: Flow<User?> = context.dataStore.data
        .map { preferences ->
            val id = preferences[ID] ?: return@map null
            val username = preferences[USERNAME] ?: return@map null
            val email = preferences[EMAIL] ?: return@map null
            val password = preferences[PASSWORD] ?: return@map null
            val role = preferences[ROLE] ?: return@map null
            User(id = id, username = username, email = email, password = password, role = role)
        }

    suspend fun saveLoggedInUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[ID] = user.id
            preferences[USERNAME] = user.username
            preferences[EMAIL] = user.email
            preferences[PASSWORD] = user.password
            preferences[ROLE] = user.role
        }
    }

    suspend fun clearLoggedInUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            val id = preferences[ID] ?: return@map false
            val username = preferences[USERNAME] ?: return@map false
            val email = preferences[EMAIL] ?: return@map false
            val password = preferences[PASSWORD] ?: return@map false
            val role = preferences[ROLE] ?: return@map false
            id.isNotBlank() && username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && role.isNotBlank()
        }

}