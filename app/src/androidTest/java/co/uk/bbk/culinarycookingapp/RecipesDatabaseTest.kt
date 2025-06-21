package co.uk.bbk.culinarycookingapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipesDatabaseTest {

    private lateinit var db: RecipesDatabase
    private lateinit var dao: RecipesDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java).build()
        dao = db.recipesDao()
    }

    @After
    fun tearDown() {
        if(this::db.isInitialized) {
            db.close()
        }
    }

    @Test
    fun insertAndGetRecipe() = runBlocking {
        val recipe = Recipe(name = "Test Recipe", ingredients = "Test Ingredients", instructions = "Test Instructions", category = "Test Category")
        dao.insertRecipe(recipe)
        val allRecipes = dao.getAllRecipes()
        Assert.assertTrue(allRecipes.contains(recipe))
    }
}