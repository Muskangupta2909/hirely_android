package com.example.hirely.ui.screen.joblisting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hirely.data.model.Job
import com.example.hirely.viewmodel.JobListingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListingScreen(
    viewModel: JobListingViewModel = viewModel()
) {

    val selectedJob = remember {
        mutableStateOf<Job?>(null)
    }

    val showApplyDialog = remember {
        mutableStateOf(false)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    // Bottom Sheet
    if (selectedJob.value != null) {

        ModalBottomSheet(
            onDismissRequest = {
                selectedJob.value = null
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {

                Text(
                    text = selectedJob.value!!.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = selectedJob.value!!.company
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "📍 ${selectedJob.value!!.location}"
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = selectedJob.value!!.salary
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Button(
                    onClick = {
                        showApplyDialog.value = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Apply Now")
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }

    // Custom Dialog
    if (showApplyDialog.value) {

        AlertDialog(
            onDismissRequest = {
                showApplyDialog.value = false
            },
            title = {
                Text("Apply for Job")
            },
            text = {
                Text(
                    "Are you sure you want to apply for " +
                            "${selectedJob.value?.title}?"
                )
            },
            confirmButton = {

                Button(
                    onClick = {

                        showApplyDialog.value = false
                        selectedJob.value = null

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Application submitted successfully!"
                            )
                        }
                    }
                ) {
                    Text("Apply")
                }
            },
            dismissButton = {

                Button(
                    onClick = {
                        showApplyDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    // Job Listing + Snackbar
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Heading
            item {

                Text(
                    text = "Find Jobs",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Search Bar
            item {

                OutlinedTextField(
                    value = viewModel.searchText.value,
                    onValueChange = { newText ->
                        viewModel.onSearchChange(newText)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Search Jobs")
                    },
                    placeholder = {
                        Text("Search by job title or company")
                    },
                    singleLine = true
                )
            }

            // Filter Chips
            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    FilterChip(
                        selected = true,
                        onClick = {},
                        label = {
                            Text("All")
                        }
                    )

                    FilterChip(
                        selected = false,
                        onClick = {},
                        label = {
                            Text("Remote")
                        }
                    )

                    FilterChip(
                        selected = false,
                        onClick = {},
                        label = {
                            Text("On-site")
                        }
                    )
                }
            }

            // Recommended Jobs
            item {

                Text(
                    text = "Recommended Jobs",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Job List
            items(viewModel.filteredJobs) { job ->

                JobCard(
                    title = job.title,
                    company = job.company,
                    location = job.location,
                    salary = job.salary,
                    onClick = {
                        selectedJob.value = job
                    }
                )
            }
        }
    }
}