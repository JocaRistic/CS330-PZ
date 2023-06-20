package rs.ac.metropolitan.projekat.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import rs.ac.metropolitan.projekat.api.ApiService
import rs.ac.metropolitan.projekat.api.RetrofitHelper
import rs.ac.metropolitan.projekat.common.models.User

class RepositoryUsers {
    var usersFlow: Flow<List<User>> = flowOf(listOf())

    suspend fun loadUsers(){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val result = apiService.getUsers()
        if (result != null){
            usersFlow = flowOf(result)
        }
    }

    suspend fun submitUser(user: User){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val result = apiService.addUser(user)
    }

    suspend fun deleteUser(username: String){
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val result = apiService.deleteUser(username)
    }
}