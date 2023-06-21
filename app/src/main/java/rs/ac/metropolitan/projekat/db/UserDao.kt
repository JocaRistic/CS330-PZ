package rs.ac.metropolitan.projekat.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import rs.ac.metropolitan.projekat.common.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllUsers(users: List<User>)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE username == :username")
    suspend fun getUserByUsername(username: String): LiveData<User>

    @Query("DELETE FROM user_table WHERE username == :username")
    suspend fun deleteUserByUsername(username: String)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Transaction
    suspend fun deleteAndInsertAllUsers(users: List<User>) {
        deleteAllUsers()
        addAllUsers(users)
    }

}