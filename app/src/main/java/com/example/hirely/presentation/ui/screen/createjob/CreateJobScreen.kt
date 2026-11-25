package com.example.hirely.presentation.ui.screen.createjob

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hirely.presentation.viewmodel.CreateJobViewModel

private val HirelyBlue = Color(0xFF243EAA)

@Composable
fun CreateJobScreen(
    onJobCreated: () -> Unit,
    viewModel: CreateJobViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(
                rememberScrollState()
            )
            .padding(20.dp),

        verticalArrangement = Arrangement.Top
    ) {

        // -----------------------------------------
        // TITLE
        // -----------------------------------------

        Text(
            text = "Create Job",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = HirelyBlue
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Add a new job opportunity",
            fontSize = 14.sp,
            color = Color(0xFF6B7280)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // -----------------------------------------
        // JOB TITLE
        // -----------------------------------------

        OutlinedTextField(
            value = uiState.jobTitle,

            onValueChange = { value ->
                viewModel.onJobTitleChange(value)
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Job Title")
            },

            placeholder = {
                Text("e.g. Android Developer")
            },

            singleLine = true,

            shape = RoundedCornerShape(10.dp)
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // -----------------------------------------
        // COMPANY
        // -----------------------------------------

        OutlinedTextField(
            value = uiState.company,

            onValueChange = { value ->
                viewModel.onCompanyChange(value)
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Company")
            },

            placeholder = {
                Text("Enter company name")
            },

            singleLine = true,

            shape = RoundedCornerShape(10.dp)
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // -----------------------------------------
        // LOCATION
        // -----------------------------------------

        OutlinedTextField(
            value = uiState.location,

            onValueChange = { value ->
                viewModel.onLocationChange(value)
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Location")
            },

            placeholder = {
                Text("e.g. Bhopal / Remote")
            },

            singleLine = true,

            shape = RoundedCornerShape(10.dp)
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // -----------------------------------------
        // SALARY
        // -----------------------------------------

        OutlinedTextField(
            value = uiState.salary,

            onValueChange = { value ->
                viewModel.onSalaryChange(value)
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Salary")
            },

            placeholder = {
                Text("e.g. 4-6 LPA")
            },

            singleLine = true,

            shape = RoundedCornerShape(10.dp)
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // -----------------------------------------
        // DESCRIPTION
        // -----------------------------------------

        OutlinedTextField(
            value = uiState.description,

            onValueChange = { value ->
                viewModel.onDescriptionChange(value)
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),

            label = {
                Text("Job Description")
            },

            placeholder = {
                Text("Enter job description")
            },

            maxLines = 5,

            shape = RoundedCornerShape(10.dp)
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // -----------------------------------------
        // ERROR
        // -----------------------------------------

        if (uiState.errorMessage != null) {

            Text(
                text = uiState.errorMessage!!,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(
                    bottom = 12.dp
                )
            )
        }


        // -----------------------------------------
        // CREATE BUTTON
        // -----------------------------------------

        Button(
            onClick = {

                viewModel.createJob {
                    onJobCreated()
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            shape = RoundedCornerShape(10.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = HirelyBlue,
                contentColor = Color.White
            ),

            enabled = !uiState.isLoading
        ) {

            Text(
                text = "Create Job",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}