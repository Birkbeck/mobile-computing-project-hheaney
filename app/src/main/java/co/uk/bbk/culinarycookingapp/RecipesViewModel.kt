package co.uk.bbk.culinarycookingapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipesViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<Recipe>>().apply {
        value = listOf(
            Recipe(
                id = 1,
                name = "Spaghetti Bolognese",
                ingredients = "Spaghetti, minced beef, tomato sauce, onions, garlic, herbs",
                instructions ="1. Cook spaghetti according to package instructions.\n2. In a pan, sauté onions and garlic until soft.\n3. Add minced beef and cook until browned.\n4. Stir in tomato sauce and herbs, simmer for 20 minutes.\n5. Serve sauce over spaghetti.",
                imageUri = R.drawable.chicken_curry,
                category = "Dinner"
            ),
            Recipe(
                id = 2,
                name = "Chicken Curry",
                ingredients ="Chicken, curry powder, coconut milk, onions, garlic, ginger, vegetables",
                instructions = "1. Sauté onions, garlic, and ginger in a pot.\n2. Add chicken pieces and cook until browned.\n3. Stir in curry powder and cook for 1 minute.\n4. Pour in coconut milk and add vegetables, simmer for 30 minutes.\n5. Serve with rice or naan.",
                imageUri = R.drawable.chicken_curry,
                category = "Dinner"
            ),
            Recipe(
                id = 3,
                name = "Milkshake",
                ingredients ="Milk, ice cream, chocolate syrup, whipped cream",
                instructions = "1. In a blender, combine milk, ice cream, and chocolate syrup.\n2. Blend until smooth.\n3. Pour into a glass and top with whipped cream.",
                imageUri = R.drawable.chicken_curry,
                category = "Other"
            ),
            Recipe(
                id = 4,
                name = "Pancakes",
                ingredients ="Flour, eggs, milk, sugar, baking powder, butter",
                instructions = "1. In a bowl, mix flour, sugar, and baking powder.\n2. In another bowl, whisk eggs and milk together.\n3. Combine wet and dry ingredients, stir until smooth.\n4. Heat a pan and pour batter to form pancakes, cook until golden brown on both sides.\n5. Serve with syrup or fruit.",
                imageUri = R.drawable.chicken_curry,
                category = "Brunch"
            ),
            Recipe(
                id = 5,
                name = "Full Engtlish Breakfast",
                ingredients = "Eggs, bacon, sausages, baked beans, toast, tomatoes, mushrooms",
                instructions = "1. Fry bacon and sausages in a pan until cooked.\n2. In the same pan, fry eggs to your liking.\n3. Toast bread and grill tomatoes and mushrooms.\n4. Serve everything on a plate with baked beans.",
                imageUri = R.drawable.chicken_curry,
                category = "Breakfast"
            ),
            Recipe(
                id = 6,
                name = "Club Sandwich",
                ingredients = "Bread, turkey, bacon, lettuce, tomato, mayonnaise",
                instructions = "1. Toast bread slices.\n2. Layer turkey, bacon, lettuce, and tomato between two slices of bread.\n3. Spread mayonnaise on the top slice and place another slice of bread on top.\n4. Cut into quarters and serve with chips.",
                imageUri = R.drawable.chicken_curry,
                category = "Lunch"
            ),
            Recipe(
                id = 7,
                name = "Apple Pie",
                ingredients = "Apples, sugar, cinnamon, pie crust",
                instructions = "1. Preheat oven to 180°C (350°F).\n2. Peel and slice apples, mix with sugar and cinnamon.\n3. Place apple mixture in pie crust, cover with another crust.\n4. Bake for 45 minutes or until crust is golden brown.\n5. Let cool before serving.",
                imageUri = R.drawable.chicken_curry,
                category = "Dessert"
            )

        )
    }
    val recipes: MutableLiveData<List<Recipe>> = _recipes
}