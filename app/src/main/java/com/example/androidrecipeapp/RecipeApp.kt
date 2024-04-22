package com.example.androidrecipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecipeApp(navController: NavHostController) {
    val recipeViewModel: MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState


    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route) {

        composable(route = Screen.RecipeScreen.route) {

            RecipeScreen(viewState = viewState, navigateToDetail = {
                // set the category in the back stack so next screen can get it.
                navController.currentBackStackEntry?.savedStateHandle?.set("cat", it)

                // finally use navigate to move to that screen.
                navController.navigate(Screen.DetailScreen.route)
            })

        }

        composable(route = Screen.DetailScreen.route) {

            // retrieve the categroy from the state
            val category =
                navController.previousBackStackEntry?.savedStateHandle?.get<Category>("cat")
                    ?: Category("", "", "", "")

            // and show this screen/component
            CategoryDetailScreen(category = category)
        }
    }
}