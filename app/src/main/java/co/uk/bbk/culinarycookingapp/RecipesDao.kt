package co.uk.bbk.culinarycookingapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipesDao {
    @Query("SELECT * FROM Recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM Recipes WHERE id = :id")
    fun getRecipeById(id: Int): LiveData<Recipe?>

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    // For sample data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}