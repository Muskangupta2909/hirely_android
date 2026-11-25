package com.example.hirely.presentation.ui.screen.joblisting

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hirely.domain.model.Job
import com.example.hirely.presentation.viewmodel.JobListingViewModel
import kotlinx.coroutines.launch


// =================================================
// HIRELY COLORS
// =================================================

private val HirelyBlue = Color(0xFF243EAA)

private val LightBlue = Color(0xFFE8ECFA)

private val DarkText = Color(0xFF333333)

private val GrayText = Color(0xFF6B7280)


// =================================================
// JOB LISTING SCREEN
// =================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListingScreen(
    viewModel: JobListingViewModel = viewModel()
) {

    // =================================================
    // SELECTED JOB
    // =================================================

    val selectedJob = remember {
        mutableStateOf<Job?>(null)
    }


    // =================================================
    // APPLY DIALOG
    // =================================================

    val showApplyDialog = remember {
        mutableStateOf(false)
    }

    // Calendar ko show/hide karne ke liye
    val showCalendar = remember {
        mutableStateOf(false)
    }

    // =================================================
    // SNACKBAR
    // =================================================

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()


    // =================================================
    // CALENDAR
    // =================================================

    // Calendar BottomSheet show/hide
    val showDatePicker = remember {
        mutableStateOf(false)
    }

    // Material DatePicker state
    val datePickerState = rememberDatePickerState()


    // =================================================
    // JOB DETAILS BOTTOM SHEET
    // =================================================

    if (selectedJob.value != null) {

        ModalBottomSheet(

            onDismissRequest = {
                selectedJob.value = null
            },

            containerColor = Color.White

        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {

                // Job title
                Text(
                    text = selectedJob.value!!.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = HirelyBlue
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )


                // Company
                Text(
                    text = selectedJob.value!!.company,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkText
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )


                // Location
                Text(
                    text = "📍 ${selectedJob.value!!.location}",
                    fontSize = 14.sp,
                    color = GrayText
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )


                // Salary
                Text(
                    text = selectedJob.value!!.salary,
                    fontSize = 14.sp,
                    color = GrayText
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )


                // =================================================
                // APPLY BUTTON
                // =================================================

                Button(

                    onClick = {
                        showApplyDialog.value = true
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),

                    shape = RoundedCornerShape(10.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = HirelyBlue,
                        contentColor = Color.White
                    )

                ) {

                    Text(
                        text = "Apply Now",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }


    // =================================================
    // CUSTOM APPLY DIALOG
    // =================================================

    if (showApplyDialog.value) {

        AlertDialog(

            onDismissRequest = {
                showApplyDialog.value = false
            },

            title = {

                Text(
                    text = "Apply for Job",
                    color = HirelyBlue,
                    fontWeight = FontWeight.Bold
                )
            },

            text = {

                Text(
                    text = "Are you sure you want to apply for " +
                            "${selectedJob.value?.title}?",
                    color = DarkText
                )
            },


            // =================================================
            // APPLY
            // =================================================

            confirmButton = {

                Button(

                    onClick = {

                        // Dialog close
                        showApplyDialog.value = false

                        // Job BottomSheet close
                        selectedJob.value = null


                        // Snackbar show
                        scope.launch {

                            snackbarHostState.showSnackbar(
                                "Application submitted successfully!"
                            )
                        }
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = HirelyBlue,
                        contentColor = Color.White
                    ),

                    shape = RoundedCornerShape(10.dp)

                ) {

                    Text(
                        text = "Apply",
                        fontWeight = FontWeight.Bold
                    )
                }
            },


            // =================================================
            // CANCEL
            // =================================================

            dismissButton = {

                Button(

                    onClick = {
                        showApplyDialog.value = false
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightBlue,
                        contentColor = HirelyBlue
                    ),

                    shape = RoundedCornerShape(10.dp)

                ) {

                    Text("Cancel")
                }
            },

            containerColor = Color.White,

            shape = RoundedCornerShape(20.dp)
        )
    }


    // =================================================
    // CALENDAR BOTTOM SHEET
    // =================================================

    if (showDatePicker.value) {

        ModalBottomSheet(

            onDismissRequest = {
                showDatePicker.value = false
            },

            containerColor = Color.White

        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)

            ) {

                // Calendar heading
                Text(
                    text = "Select Date",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = HirelyBlue
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // =================================================
                // MATERIAL DATE PICKER
                // =================================================

                DatePicker(
                    state = datePickerState
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // =================================================
                // SELECT DATE BUTTON
                // =================================================

                Button(

                    onClick = {

                        // Calendar BottomSheet close
                        showDatePicker.value = false


                        // Snackbar
                        scope.launch {

                            snackbarHostState.showSnackbar(
                                "Date selected successfully!"
                            )
                        }
                    },

                    modifier = Modifier.fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = HirelyBlue,
                        contentColor = Color.White
                    ),

                    shape = RoundedCornerShape(10.dp)

                ) {

                    Text(
                        text = "Select Date",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }


    // =================================================
    // MAIN JOB LISTING SCREEN
    // =================================================

    Scaffold(

        // =================================================
        // CUSTOM SNACKBAR
        // =================================================

        snackbarHost = {

            SnackbarHost(
                hostState = snackbarHostState
            ) { snackbarData ->

                Snackbar(

                    snackbarData = snackbarData,

                    // Hirely blue
                    containerColor = HirelyBlue,

                    // White text
                    contentColor = Color.White,

                    // Rounded snackbar
                    shape = RoundedCornerShape(10.dp),

                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    ) { paddingValues ->


        // =================================================
        // MAIN COLUMN
        // =================================================

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)

        ) {


            // =================================================
            // FIXED HEADER
            // =================================================
            // Ye part LazyColumn ke bahar hai.
            // Isliye scroll karne par heading upar nahi jayegi.

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    )

            ) {


                // =================================================
                // HEADING
                // =================================================

                Text(
                    text = "Find Jobs",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = HirelyBlue
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )


                // =================================================
                // SUBTITLE
                // =================================================

                Text(
                    text = "Find the right opportunity for you",
                    fontSize = 14.sp,
                    color = GrayText
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )


                // =================================================
                // SEARCH BAR
                // =================================================

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
                        Text(
                            "Search by job title or company"
                        )
                    },

                    singleLine = true,

                    shape = RoundedCornerShape(10.dp),

                    colors = OutlinedTextFieldDefaults.colors(

                        focusedBorderColor = HirelyBlue,

                        unfocusedBorderColor = Color.LightGray,

                        focusedLabelColor = HirelyBlue,

                        unfocusedLabelColor = GrayText,

                        cursorColor = HirelyBlue,

                        focusedTextColor = DarkText,

                        unfocusedTextColor = DarkText
                    )
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // =================================================
                // FILTER + CALENDAR ROW
                // =================================================

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(
                            rememberScrollState()
                        ),

                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {


                    // =================================================
                    // ALL
                    // =================================================

                    FilterChip(

                        selected = true,

                        onClick = {},

                        label = {
                            Text("All")
                        },

                        colors =
                            FilterChipDefaults.filterChipColors(

                                selectedContainerColor =
                                    HirelyBlue,

                                selectedLabelColor =
                                    Color.White
                            )
                    )


                    // =================================================
                    // REMOTE
                    // =================================================

                    FilterChip(

                        selected = false,

                        onClick = {},

                        label = {
                            Text("Remote")
                        },

                        colors =
                            FilterChipDefaults.filterChipColors(

                                labelColor =
                                    DarkText
                            )
                    )


                    // =================================================
                    // ON-SITE
                    // =================================================

                    FilterChip(

                        selected = false,

                        onClick = {},

                        label = {
                            Text("On-site")
                        },

                        colors =
                            FilterChipDefaults.filterChipColors(

                                labelColor =
                                    DarkText
                            )
                    )


                    // =================================================
                    // CALENDAR BUTTON
                    // =================================================

                    Button(

                        onClick = {

                            // Calendar BottomSheet open
                            showDatePicker.value = true
                        },

                        modifier = Modifier.height(40.dp),

                        shape = RoundedCornerShape(10.dp),

                        colors = ButtonDefaults.buttonColors(

                            containerColor =
                                LightBlue,

                            contentColor =
                                HirelyBlue
                        )

                    ) {

                        // Icons use nahi kiya
                        // Isliye icons wala error nahi aayega

                        Text(
                            text = "📅",
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }


            // =================================================
            // SCROLLABLE JOB LIST
            // =================================================
            // Sirf ye part scroll hoga.

            LazyColumn(

                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        horizontal = 20.dp
                    ),

                verticalArrangement =
                    Arrangement.spacedBy(14.dp)
            ) {


                // =================================================
                // RECOMMENDED JOBS
                // =================================================

                item {

                    Text(
                        text = "Recommended Jobs",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                }


                // =================================================
                // JOB CARDS
                // =================================================

                items(
                    viewModel.filteredJobs
                ) { job ->

                    JobCard(

                        title = job.title,

                        company = job.company,

                        location = job.location,

                        salary = job.salary,

                        onClick = {

                            // Job details BottomSheet open
                            selectedJob.value = job
                        }
                    )
                }


                // =================================================
                // BOTTOM SPACE
                // =================================================

                item {

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }
            }
        }
    }
}