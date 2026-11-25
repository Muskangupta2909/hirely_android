package com.example.hirely.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hirely.data.DataStoreManager
import com.example.hirely.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val dataStoreManager =
        DataStoreManager(getApplication())

    private val _loggedInUser =
        MutableStateFlow<User?>(null)

    val loggedInUser: StateFlow<User?> =
        _loggedInUser.asStateFlow()


    init {
        loadLoggedInUser()
    }


    private fun loadLoggedInUser() {

        viewModelScope.launch {

            // Currently logged-in user ka email
            val loggedInEmail =
                dataStoreManager.loggedInEmail.first()

            if (loggedInEmail == null) {
                return@launch
            }

            // Saare registered users
            val users =
                dataStoreManager.users.first()

            // Current user find
            val user = users.find {

                it.email.equals(
                    loggedInEmail,
                    ignoreCase = true
                )
            }

            // Profile mein user set
            _loggedInUser.value = user
        }
    }
}