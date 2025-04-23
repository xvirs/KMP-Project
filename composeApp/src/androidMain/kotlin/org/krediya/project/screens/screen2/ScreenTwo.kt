package org.krediya.project.screens.screen2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScreenTwo(
    viewModel: ScreenTwoViewModel = koinViewModel()
) {

    Text(text = "Screen Two")

}
