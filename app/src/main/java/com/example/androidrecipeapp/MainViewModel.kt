package com.example.androidrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _categoryState = mutableStateOf(RecipeState())

    // this is the public variable that will be exposed to other classes.
    val categoriesState: State<RecipeState> = _categoryState
    init{
        fetchCategories()
    }
    private fun fetchCategories(){

        // called on startup. immediately launched
        viewModelScope.launch {
            try{
                // call the services and wait for response
                val response = recipeService.getCategories()

                // SUCCESS: update the view modal accordinly via
                // a "reducer" spread operation
                _categoryState.value = _categoryState.value.copy(
                    list = response.categories,
                    error = null
                )

            }catch(e:Exception){
                // FAILURE, update the view model accordinly via
                // a "reducer" spread operation
                _categoryState.value = _categoryState.value.copy(
                    error = "Error fetching Categories: ${e.message}"
                )
            }finally {
                // stop the spinner
                _categoryState.value  = _categoryState.value.copy(
                    loading = false
                )
            }
        }
    }

    data class RecipeState(
        var loading:Boolean = true,
        var list:List<Category> = emptyList(),
        var error: String? = null
    )
}