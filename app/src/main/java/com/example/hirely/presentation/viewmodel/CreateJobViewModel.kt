package com.example.hirely.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hirely.presentation.ui.screen.createjob.CreateJobUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateJobViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(CreateJobUiState())

    val uiState: StateFlow<CreateJobUiState> =
        _uiState.asStateFlow()

    fun onJobTitleChange(value: String) {
        _uiState.value = _uiState.value.copy(
            jobTitle = value,
            errorMessage = null
        )
    }

    fun onCompanyChange(value: String) {
        _uiState.value = _uiState.value.copy(
            company = value,
            errorMessage = null
        )
    }

    fun onLocationChange(value: String) {
        _uiState.value = _uiState.value.copy(
            location = value,
            errorMessage = null
        )
    }

    fun onSalaryChange(value: String) {
        _uiState.value = _uiState.value.copy(
            salary = value,
            errorMessage = null
        )
    }

    fun onDescriptionChange(value: String) {
        _uiState.value = _uiState.value.copy(
            description = value,
            errorMessage = null
        )
    }

    fun createJob(
        onSuccess: () -> Unit
    ) {

        val state = _uiState.value

        if (state.jobTitle.trim().isEmpty()) {
            _uiState.value = state.copy(
                errorMessage = "Job title is required"
            )
            return
        }

        if (state.company.trim().isEmpty()) {
            _uiState.value = state.copy(
                errorMessage = "Company name is required"
            )
            return
        }

        if (state.location.trim().isEmpty()) {
            _uiState.value = state.copy(
                errorMessage = "Location is required"
            )
            return
        }

        if (state.salary.trim().isEmpty()) {
            _uiState.value = state.copy(
                errorMessage = "Salary is required"
            )
            return
        }

        if (state.description.trim().isEmpty()) {
            _uiState.value = state.copy(
                errorMessage = "Job description is required"
            )
            return
        }

        _uiState.value = state.copy(
            isLoading = false,
            errorMessage = null,
            isJobCreated = true
        )

        onSuccess()
    }
}