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
import java.io.IOException
import kotlin.jvm.Throws
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Instrumented test for the RecipesDatabase.  This file tests the basic CRUD operations of the
 * database such as; create, retrieve, update and delete.
 */


@RunWith(AndroidJUnit4::class)
class RecipesDatabaseTest {

    private lateinit var dao: RecipesDao // DAO for accessing the database
    private lateinit var db: RecipesDatabase // Database instance for testing

    // Sample recipes for testing from each food category
    private val omlette = Recipe(
        name = "Omlette",
        ingredients = "Eggs, Salt, Pepper, Cheese",
        instructions = "Beat eggs, pour into pan, cook until set, add cheese.",
        category = "Breakfast"
    )
    private val salad = Recipe(
        name = "Salad",
        ingredients = "Lettuce, Tomato, Cucumber, Dressing",
        instructions = "Chop vegetables, mix with dressing.",
        category = "Lunch"
    )
    private val pizza = Recipe(
        name = "Pizza",
        ingredients = "Dough, Tomato Sauce, Cheese, Toppings",
        instructions = "Prepare dough, add sauce and toppings, bake in oven.",
        category = "Dinner"
    )
    private val pancakes = Recipe(
        name = "Pancakes",
        ingredients = "Flour, Milk, Eggs, Sugar, Baking Powder",
        instructions = "Mix ingredients, pour batter onto skillet, cook until golden.",
        category = "Brunch"
    )
    private val applePie = Recipe(
        name = "Apple Pie",
        ingredients = "Apples, Sugar, Cinnamon, Pie Crust",
        instructions = "Prepare filling, place in crust, bake until golden.",
        category = "Dessert"
    )
    private val shake = Recipe(
        name = "Fruit Shake",
        ingredients = "Banana, Milk, Honey, Ice",
        instructions = "Blend ingredients until smooth.",
        category = "Other"
    )

    // Create an in-memory database before each test
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java).allowMainThreadQueries().build()
        dao = db.recipesDao()
    }

    // Close the database after each test
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * Test to insert a recipe from each food category and retrieve it.  Confirming that the
     * expected number of recipes is returned.
     */
    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveAll() = runBlocking {
        dao.insertRecipe(omlette)
        dao.insertRecipe(salad)
        dao.insertRecipe(pizza)
        dao.insertRecipe(pancakes)
        dao.insertRecipe(applePie)
        dao.insertRecipe(shake)
        val allRecipes = dao.getAllRecipes()
        Assert.assertEquals(6, allRecipes.size)
    }

    /**
     * Test to insert recipes from each food category and confirm deletion of a recipe.
     * This test inserts multiple recipes, deletes one, and then checks that the
     * correct recipe is deleted.  The Insert method returns a Long which is how the
     * recipe is deleted.
     */
    @Test
    @Throws(Exception::class)
    fun insertAndRemove() = runBlocking {
        dao.insertRecipe(omlette)
        dao.insertRecipe(salad)
        dao.insertRecipe(pizza)
        dao.insertRecipe(pancakes)
        val applePieId = dao.insertRecipe(applePie)
        dao.insertRecipe(shake)

        val applePieWithId = applePie.copy(id = applePieId)
        dao.deleteRecipe(applePieWithId)

        val allRecipes = dao.getAllRecipes()
        Assert.assertEquals(5, allRecipes.size)
        Assert.assertFalse(allRecipes.any { it.name == applePie.name })
    }

    /**
     * Test to insert a recipe and then update the recipe details.
     */
    @Test
    @Throws(Exception::class)
    fun insertAndUpdate() = runBlocking {
        val omletteId = dao.insertRecipe(omlette)
        val updatedOmlette = omlette.copy(id = omletteId, ingredients = "Eggs, Salt, Pepper, Cheese, Herbs")
        dao.updateRecipe(updatedOmlette)
        val allRecipes = dao.getAllRecipes()
        Assert.assertEquals(1, allRecipes.size)
        Assert.assertTrue(allRecipes.contains(updatedOmlette))
    }

}