package com.example.hirely.presentation.ui.screen.createjob

data class CreateJobUiState(

    val jobTitle: String = "",

    val company: String = "",

    val location: String = "",

    val salary: String = "",

    val description: String = "",

    val isLoading: Boolean = false,

    val errorMessage: String? = null,

    val isJobCreated: Boolean = false
)