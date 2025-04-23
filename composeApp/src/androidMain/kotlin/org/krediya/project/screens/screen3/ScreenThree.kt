package org.krediya.project.screens.screen3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScreenThree(
    viewModel: ScreenThreeViewModel = koinViewModel()
) {

    Text(text = "Screen Three")

}
