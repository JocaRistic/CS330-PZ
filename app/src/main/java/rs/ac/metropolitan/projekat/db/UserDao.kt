package rs.ac.metropolitan.projekat.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import rs.ac.metropolitan.projekat.common.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllUsers(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE username == :username")
    fun getUserByUsername(username: String): User

    @Query("DELETE FROM user_table WHERE id == :id")
    suspend fun deleteUserById(id: String)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Transaction
    suspend fun deleteAndInsertAllUsers(users: List<User>) {
        deleteAllUsers()
        addAllUsers(users)
    }

}