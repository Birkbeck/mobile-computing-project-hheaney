package co.uk.bbk.culinarycookingapp

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.uk.bbk.culinarycookingapp.databinding.AddRecipeBinding
import kotlinx.coroutines.launch

class AddRecipeActivity : AppCompatActivity(){

    private lateinit var binding: AddRecipeBinding
    private lateinit var recipesDb: RecipesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ArrayAdapter.createFromResource(
            this,
            R.array.recipe_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.recipeCategorySpinner.adapter = adapter
        }

        recipesDb = RecipesDatabase.getInstance(this)

        binding.saveButton.setOnClickListener {
            val name = binding.recipeNameInput.text.toString()
            val ingredients = binding.recipeIngredientsInput.text.toString()
            val instructions = binding.recipeInstructionsInput.text.toString()
            val category = binding.recipeCategorySpinner.selectedItem.toString()

            val recipe = Recipe(
                name = name,
                ingredients = ingredients,
                instructions = instructions,
                imageUri = null,
                category = category
            )
            lifecycleScope.launch {
                recipesDb.recipesDao().insertRecipe(recipe)
                finish()
            }
        }

    }
}