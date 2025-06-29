package co.uk.bbk.culinarycookingapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycookingapp.databinding.ViewRecipeBinding

class ViewRecipeActivity : AppCompatActivity() {

    private lateinit var binding : ViewRecipeBinding
    private val viewModel: RecipesViewModel by viewModels()

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        binding = ViewRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = RecipesDatabase.getInstance(applicationContext).recipesDao()
        viewModel.recipesDao = dao

        val recipeId = intent.getIntExtra("id", -1)
        Log.d("ViewRecipeActivity", "Received recipe ID: $recipeId")
        if (recipeId != -1) {
            viewModel.getRecipeById(recipeId).observe(this) { recipe ->
                Log.d("ViewRecipeActivity", "Observed recipe: $recipe")
                recipe?.let {
                    binding.recipeTitle.text = recipe.name
                    binding.recipeName.text = recipe.name
                    binding.recipeIngredients.text = recipe.ingredients
                    binding.recipeInstructions.text = recipe.instructions
                    //it.imageUri?.let { imgRes ->
                      //  binding.recipeImage.setImageResource(imgRes)
                    //}
                }
            }
        }
    }
}