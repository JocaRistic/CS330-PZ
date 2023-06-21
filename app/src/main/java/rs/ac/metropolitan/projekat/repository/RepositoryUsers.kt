package rs.ac.metropolitan.projekat.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import rs.ac.metropolitan.projekat.api.ApiService
import rs.ac.metropolitan.projekat.api.RetrofitHelper
import rs.ac.metropolitan.projekat.common.models.User
import rs.ac.metropolitan.projekat.db.BazaPodataka
import rs.ac.metropolitan.projekat.db.UserDao

class RepositoryUsers(private val userDao: UserDao) {

    suspend fun loadUsers(){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val result = apiService.getUsers()
        if (result != null){
            userDao.addAllUsers(result)
        }
    }

    suspend fun submitUser(user: User){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val result = apiService.addUser(user)
        userDao.addUser(user)
    }

    suspend fun deleteUser(id: String){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val result = apiService.deleteUser(id)
        userDao.deleteUserById(id)
    }

    suspend fun getUserFromDB(username: String): User{
        return userDao.getUserByUsername(username)
    }

    fun loginUserFromDB(username: String, password: String): User{
        return userDao.getUserByUsernameAndPass(username, password)
    }
}