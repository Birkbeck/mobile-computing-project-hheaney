package co.uk.bbk.culinarycookingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycookingapp.databinding.ActivityAllRecipesBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllRecipesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipes = listOf(
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
            )
        )

        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipesRecyclerView.adapter = RecipeListAdapter(recipes)
    }
}