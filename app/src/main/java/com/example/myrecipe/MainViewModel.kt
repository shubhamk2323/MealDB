package com.example.myrecipe

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    init{
        fetchCategories()
    }

    private val _categorystate= mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categorystate

    private fun fetchCategories(){
        viewModelScope.launch {

            try{
                val response= recipeService.getCategories()
                _categorystate.value= _categorystate.value.copy(
                    list= response.categories,
                    loading= false,
                    error= null
                )
            }
            catch(e: Exception){
                _categorystate.value= _categorystate.value.copy(
                    loading= false,
                    error= "Error fetching categories ${e.message}"
                )
            }

        }
    }

    data class RecipeState(
        val loading: Boolean= true,
        val list: List<Category> = emptyList(),
        val error: String?= null
    )

}