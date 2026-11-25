package com.example.hirely.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hirely.data.DataStoreManager
import kotlinx.coroutines.launch

class OnboardingViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val dataStoreManager =
        DataStoreManager(getApplication())

    fun completeOnboarding(
        onComplete: () -> Unit
    ) {

        viewModelScope.launch {

            dataStoreManager.completeOnboarding()

            onComplete()
        }
    }
}