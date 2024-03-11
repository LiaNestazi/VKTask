package com.example.vktask.view

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.vktask.ui.navigation.SetupNavGraph
import com.example.vktask.viewmodel.MainViewModel

@Composable
fun MainPage(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    Box() {
        SetupNavGraph(navController = navController, mainViewModel)
        navController.navigate("ProductList", navOptions = null)
    }
}