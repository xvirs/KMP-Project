package org.krediya.project.screens.screen2

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScreenTwo(
    viewModel: ScreenTwoViewModel = koinViewModel()
) {

    Column {
        Button(onClick = {
            viewModel.testCrashlytics()
        }) {
            Text(text = "Crash")
        }

        Button(onClick = {
            viewModel.testAnalytics()
        }) {
            Text(text = "Analytics")
        }
    }


}
