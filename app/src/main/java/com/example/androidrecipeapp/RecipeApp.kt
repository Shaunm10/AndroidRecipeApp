package com.example.androidrecipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RecipeApp(navController: NavHostController) {
    val recipeViewModel : MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState


    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route){

        composable(route = Screen.RecipeScreen.route){

            RecipeScreen( viewState = viewState, navigateToDetail ={
                // set the category in the back stack so next screen can get it.
                navController.currentBackStackEntry?.savedStateHandle?.set("cat", it)

                // finally use navigate to move to that screen.
                navController.navigate(Screen.DetailScreen.route)
            } )

        }

        composable(route = Screen.DetailScreen.route)
    }
}