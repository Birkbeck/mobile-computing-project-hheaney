package co.uk.bbk.culinarycookingapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipesViewModel: ViewModel() {

    private val _recipes = MutableLiveData(listOf<Recipe>())
    val recipes: LiveData<List<Recipe>> = _recipes

    var recipesDao: RecipesDao? = null

    fun readAllRecipes() {
        viewModelScope.launch {
            recipesDao?.let {
                val recipes = it.getAllRecipes()
                _recipes.value = it.getAllRecipes()
            }
        }
    }

    fun addRecipe(
        name: String,
        ingredients: String,
        instructions: String,
        imageUri: String,
        category: String
    ) {
        viewModelScope.launch {
            recipesDao?.let {
                val recipe = Recipe(
                    name = name,
                    ingredients = ingredients,
                    instructions = instructions,
                    imageUri = imageUri,
                    category = category
                )
                it.insertRecipe(recipe)

                readAllRecipes()
            }
        }

    }

    fun editRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipesDao?.let {
                it.updateRecipe(recipe)

                readAllRecipes()
            }
        }

    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipesDao?.let {
                it.deleteRecipe(recipe)

                readAllRecipes()
            }
        }

    }

    fun getRecipeById(id: Long): LiveData<Recipe?> {
        val dao = recipesDao
        requireNotNull(dao) { "RecipesDao is not initialized" }
        return dao.getRecipeById(id)
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipesDao?.let {
                it.updateRecipe(recipe)
                readAllRecipes()
            }
        }
    }

}



