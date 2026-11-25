package com.example.hirely.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.hirely.domain.model.Job

class JobListingViewModel : ViewModel() {

    val jobs = listOf(
        Job(
            title = "Android Developer",
            company = "Tech Company",
            location = "Gurugram",
            salary = "₹4 - 8 LPA"
        ),
        Job(
            title = "Java Developer",
            company = "ABC Technologies",
            location = "Noida",
            salary = "₹5 - 9 LPA"
        ),
        Job(
            title = "Backend Developer",
            company = "XYZ Solutions",
            location = "Bangalore",
            salary = "₹6 - 10 LPA"
        ),
        Job(
            title = "Frontend Developer",
            company = "Web Solutions",
            location = "Indore",
            salary = "₹4 - 7 LPA"
        )
    )
    val searchText = mutableStateOf("")

    val filteredJobs
        get() = jobs.filter { job ->

            job.title.contains(
                searchText.value,
                ignoreCase = true
            ) ||
                    job.company.contains(
                        searchText.value,
                        ignoreCase = true
                    )
        }

    fun onSearchChange(text: String) {
        searchText.value = text
    }

}