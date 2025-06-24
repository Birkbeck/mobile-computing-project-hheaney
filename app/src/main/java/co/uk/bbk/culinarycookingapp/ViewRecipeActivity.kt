package co.uk.bbk.culinarycookingapp

import android.app.Activity
import android.os.Bundle
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

        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId != -1) {
            viewModel.getRecipeById(recipeId).observe(this) { recipe ->
                recipe?.let {
                    binding.recipeTitle.text = it.name
                    binding.recipeIngredients.text = it.ingredients
                    binding.recipeInstructions.text = it.instructions
                    it.imageUri?.let { imgRes ->
                        binding.recipeImage.setImageResource(imgRes)
                    }
                }
            }
        }
    }
}