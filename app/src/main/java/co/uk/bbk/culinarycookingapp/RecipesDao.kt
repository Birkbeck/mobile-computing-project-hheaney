package co.uk.bbk.culinarycookingapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipesDao {
    @Query("SELECT * FROM Recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM Recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}