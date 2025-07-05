package co.uk.bbk.culinarycookingapp
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.mockito.Mockito.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

/**
 * Unit tests for the RecipesViewModel class.
 * These tests use Mockito to mock the RecipesDao and verify that the ViewModel interacts with it correctly.
 * The tests cover adding, reading, editing, and deleting recipes, as well as retrieving a recipe by ID.
 * 6 of 6 tests passed.
 */


@OptIn(ExperimentalCoroutinesApi::class)
class RecipesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // This rule allows LiveData to execute synchronously in tests.

    private val testDispatcher = StandardTestDispatcher() // StandardTestDispatcher is used to control the execution of coroutines in tests.
    private val testScope = TestScope(testDispatcher) // TestScope is used to run tests in a controlled coroutine environment.

    private lateinit var viewModel: RecipesViewModel // ViewModel under test
    private lateinit var mockDao: RecipesDao // Mocked DAO to simulate database operations

    // This method is called before each test to set up the environment.
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockDao = mock(RecipesDao::class.java)
        viewModel = RecipesViewModel()
        viewModel.recipesDao = mockDao
        reset(mockDao)
    }

    // This method is called after each test to clean up the environment.
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // This test verifies that the ViewModel's addRecipe method calls the DAO's insertRecipe method with the correct parameters.
    @Test
    fun addRecipe_callsInsertRecipeOnDao() = testScope.runTest {
        viewModel.addRecipe("Cheesburger", "Buns, Cheese Slices, Meat", "Fry meat then add cheese", "uri", "Dinner")
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockDao).insertRecipe(any())
    }

    // This test verifies that the ViewModel's readAllRecipes method calls the DAO's getAllRecipes method.
    @Test
    fun readAllRecipes_callsGetAllRecipesOnDao() = testScope.runTest {
        viewModel.readAllRecipes()
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockDao, times(2)).getAllRecipes()
    }

    // This test verifies that the ViewModel's readAllRecipes method updates the recipes LiveData with the data returned by the DAO.
    @Test
    fun editRecipe_callsUpdateRecipeOnDao() = testScope.runTest {
        val recipe = Recipe(1L,"Cheeseburger", "Buns, Cheese Slices, Meat", "Fry meat then add cheese", "uri", "Dinner")
        viewModel.editRecipe(recipe)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockDao).updateRecipe(recipe)
    }

    // This test verifies that the ViewModel's deleteRecipe method calls the DAO's deleteRecipe method with the correct parameters.
    @Test
    fun deleteRecipe_callsDeleteRecipeOnDao() = testScope.runTest {
        val recipe = Recipe(1L,"Cheeseburger", "Buns, Cheese Slices, Meat", "Fry meat then add cheese", "uri", "Dinner")
        viewModel.deleteRecipe(recipe)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockDao).deleteRecipe(recipe)
    }

    // This test verifies that the ViewModel's getRecipeById method returns LiveData from the DAO.
    @Test
    fun getRecipeById_returnsLiveData() {
        val recipeId = 1L
        val liveData = MutableLiveData<Recipe?>()
        `when`(mockDao.getRecipeById(recipeId)).thenReturn(liveData)
        val result = viewModel.getRecipeById(recipeId)
        Assert.assertEquals(liveData, result)
    }

    // This test verifies that the ViewModel's updateRecipes method calls the DAO's updateRecipe method with the correct parameters.
    @Test
    fun updateRecipes_callsUpdateRecipeOnDao() = testScope.runTest {
        val recipe = Recipe(1L,"Cheeseburger", "Buns, Cheese Slices, Meat", "Fry meat then add cheese", "uri", "Dinner")
        viewModel.editRecipe(recipe)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockDao).updateRecipe(recipe)
    }
}