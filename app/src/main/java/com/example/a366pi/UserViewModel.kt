package com.example.a366pi

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getInstance(application).userDao()
    private val apiService = RetrofitInstance.api
    private val userRepository = UserRepository(userDao)

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        viewModelScope.launch {
            try {
                userRepository.fetchAndStoreUsers(apiService)
                _users.value = userRepository.getUsersFromDb()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "No internet connection"
                }
            }
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            val success = userRepository.addUser(apiService, user)
            if (success) {
                _users.value = userRepository.getUsersFromDb()
            } else {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to sync user with API"
                }
            }
        }
    }
}
