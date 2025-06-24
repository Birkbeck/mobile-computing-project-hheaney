package co.uk.bbk.culinarycookingapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipesViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<Recipe>>().apply {
        value = listOf(
            Recipe(
                id = 1,
                name = "Spaghetti Bolognese",
                imageUri = R.drawable.chicken_curry
            ),
            Recipe(
                id = 2,
                name = "Chicken Curry",
                imageUri = R.drawable.chicken_curry
            ),
            Recipe(
                id = 3,
                name = "Vegetable Stir Fry",
                imageUri = R.drawable.chicken_curry
            ),
            Recipe(
                id = 4,
                name = "Burger and Fries",
                imageUri = R.drawable.chicken_curry
            ),
            Recipe(
                id = 5,
                name = "Salmon Teriyaki",
                imageUri = R.drawable.chicken_curry
            ),
            Recipe(
                id = 6,
                name = "Hot Dog",
                imageUri = R.drawable.chicken_curry
            ),
            Recipe(
                id = 7,
                name = "BBQ Ribs",
                imageUri = R.drawable.chicken_curry
            ),
            Recipe(
                id = 8,
                name = "Curry and Liver",
                imageUri = R.drawable.chicken_curry
            )

        )
    }
    val recipes: MutableLiveData<List<Recipe>> = _recipes
}