package com.example.androidrecipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecipeApp(navHostController: NavHostController) {
    // our own custom viewModel
    val recipeViewModel: MainViewModel = viewModel()

    // a reference to our category state
    val viewState by recipeViewModel.categoriesState


    NavHost(navController = navHostController, startDestination = Screen.RecipeScreen.route) {

        composable(route = Screen.RecipeScreen.route) {

            RecipeScreen(viewState = viewState, navigateToDetail = { category ->
                // set the category in the back stack so next screen can get it.
                navHostController.currentBackStackEntry?.savedStateHandle?.set("cat", category)

                // finally use navigate to move to that screen.
                navHostController.navigate(Screen.DetailScreen.route)
            })

        }

        composable(route = Screen.DetailScreen.route) {

            // retrieve the Category from the state, that was pushed by the screen above.
            var category =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<Category>("cat")

            // if for some reason the category isn't found.
            if(category == null){
                // give a "empty" blank category.
                category = Category("", "", "", "")
            }

            // and show this screen/component
            CategoryDetailScreen(category = category)
        }
    }
}