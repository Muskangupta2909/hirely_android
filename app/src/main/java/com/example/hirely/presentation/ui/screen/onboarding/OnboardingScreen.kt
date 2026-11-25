package com.example.hirely.presentation.ui.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String
)

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit
) {

    val pages = listOf(
        _root_ide_package_.com.example.hirely.presentation.ui.screen.onboarding.OnboardingPage(
            title = "Register Your Account",
            description = "Find the workers you want or need from all the workers listed."
        ),
        _root_ide_package_.com.example.hirely.presentation.ui.screen.onboarding.OnboardingPage(
            title = "Hire The Workers",
            description = "Choose the best workers that you want to work with."
        ),
        _root_ide_package_.com.example.hirely.presentation.ui.screen.onboarding.OnboardingPage(
            title = "Let's Work Together",
            description = "Enjoy the job you choose and achieve the best success."
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )

    val scope = rememberCoroutineScope()

    val currentPage = pagerState.currentPage
    val isLastPage = currentPage == pages.lastIndex


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFC))
            .padding(
                horizontal = 24.dp,
                vertical = 24.dp
            )
    ) {

        // -------------------------------------------------
        // Skip
        // -------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            TextButton(
                onClick = {
                    onOnboardingComplete()
                }
            ) {

                Text(
                    text = "Skip",
                    color = Color(0xFF243EAA),
                    fontSize = 14.sp
                )
            }
        }


        // -------------------------------------------------
        // Pager
        // -------------------------------------------------

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // -------------------------------------------------
                // Illustration
                // -------------------------------------------------

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color(0xFFE5F5FF)),
                    contentAlignment = Alignment.Center
                ) {

                    when (page) {

                        0 -> {
                            Text(
                                text = "👤",
                                fontSize = 90.sp
                            )
                        }

                        1 -> {
                            Text(
                                text = "💼",
                                fontSize = 90.sp
                            )
                        }

                        2 -> {
                            Text(
                                text = "🤝",
                                fontSize = 90.sp
                            )
                        }
                    }
                }


                Spacer(
                    modifier = Modifier.height(32.dp)
                )


                // -------------------------------------------------
                // Title
                // -------------------------------------------------

                Text(
                    text = pages[page].title,
                    color = Color(0xFF202124),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )


                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // -------------------------------------------------
                // Description
                // -------------------------------------------------

                Text(
                    text = pages[page].description,
                    color = Color(0xFF6B7280),
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
            }
        }


        // -------------------------------------------------
        // Page indicators
        // -------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            pages.indices.forEach { index ->

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(
                            if (index == currentPage) 10.dp else 8.dp
                        )
                        .clip(CircleShape)
                        .background(
                            if (index == currentPage) {
                                Color(0xFF243EAA)
                            } else {
                                Color(0xFFD9DCE5)
                            }
                        )
                )
            }
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // -------------------------------------------------
        // Bottom buttons
        // -------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Back
            if (currentPage > 0) {

                TextButton(
                    onClick = {

                        scope.launch {
                            pagerState.animateScrollToPage(
                                currentPage - 1
                            )
                        }
                    }
                ) {

                    Text(
                        text = "Back",
                        color = Color(0xFF243EAA),
                        fontSize = 14.sp
                    )
                }

            } else {

                Spacer(
                    modifier = Modifier.size(1.dp)
                )
            }


            Spacer(
                modifier = Modifier.weight(1f)
            )


            // Next / Start
            Button(
                onClick = {

                    if (isLastPage) {

                        onOnboardingComplete()

                    } else {

                        scope.launch {
                            pagerState.animateScrollToPage(
                                currentPage + 1
                            )
                        }
                    }
                },

                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 8.dp),

                shape = RoundedCornerShape(25.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF243EAA),
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = if (isLastPage) {
                        "Start"
                    } else {
                        "Next"
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}