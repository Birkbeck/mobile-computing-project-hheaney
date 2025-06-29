package co.uk.bbk.culinarycookingapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycookingapp.databinding.EditRecipeBinding

class EditRecipeActivity: AppCompatActivity() {

    private lateinit var binding: EditRecipeBinding
    private val viewModel: RecipesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = RecipesDatabase.getInstance(applicationContext).recipesDao()
        viewModel.recipesDao = dao

        val recipeId = intent.getIntExtra("id", -1)
        if(recipeId == -1) {
            finish()
            return
        }

        viewModel.getRecipeById(recipeId).observe(this) { recipe ->
            if(recipe != null) {
                binding.editRecipeNameInput.setText(recipe.name)
                binding.editRecipeIngredientsInput.setText(recipe.ingredients)
                binding.editRecipeInstructionsInput.setText(recipe.instructions)

                binding.saveButton.setOnClickListener {
                    val updatedRecipe = recipe.copy(
                        name = binding.editRecipeNameInput.text.toString(),
                        ingredients = binding.editRecipeIngredientsInput.text.toString(),
                        instructions = binding.editRecipeInstructionsInput.text.toString()
                    )
                    viewModel.updateRecipe(updatedRecipe)
                    finish()
                }

            }
        }
    }
}