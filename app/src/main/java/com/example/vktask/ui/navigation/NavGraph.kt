package com.example.vktask.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vktask.ui.composables.ProductList
import com.example.vktask.view.ProductInfo
import com.example.vktask.viewmodel.MainViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    NavHost(navController = navController, startDestination = "ProductList") {
        composable("ProductList") {
            ProductList(navController = navController, mainViewModel = mainViewModel)
        }
        composable(
            route = "ProductInfo/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                },
            )
        ){
            ProductInfo(
                navController = navController,
                mainViewModel = mainViewModel,
                itemId = it.arguments?.getInt("id")

            )
        }
    }
}