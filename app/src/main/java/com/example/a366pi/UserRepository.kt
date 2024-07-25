package com.example.a366pi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    // Fetching the users from Reqres and Storing into the local database
    suspend fun fetchAndStoreUsers(apiService: ApiService) {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getUsers()
                response.data?.let {
                    userDao.insertUsers(it)
                }
            } catch (e: Exception) {
                throw Exception("Error fetching and storing users")
            }
        }
    }

    // Getting all users from database
    suspend fun getUsersFromDb(): List<User> {
        return withContext(Dispatchers.IO) {
            userDao.getAllUsers()
        }
    }

    // This function combines the API call and database insertion
    suspend fun addUser(apiService: ApiService, user: User): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.createUser(CreateUserRequest(user.first_name, user.last_name, user.id, user.email))
                if (response.isSuccessful) {
                    userDao.insertUser(user)
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                false
            }
        }
    }
}

