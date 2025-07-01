package co.uk.bbk.culinarycookingapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
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

        // used co pilot to help
        val categories = resources.getStringArray(R.array.recipe_categories)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editRecipeCategorySpinner.adapter = adapter


        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            val systemBars = insets.getInsets(android.view.WindowInsets.Type.systemBars())
            view.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

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
                binding.editRecipeCategorySpinner.setSelection(
                    resources.getStringArray(R.array.recipe_categories).indexOf(recipe.category)
                )

                binding.saveButton.setOnClickListener {
                    val selectedCategory = binding.editRecipeCategorySpinner.selectedItem.toString()
                    val updatedRecipe = recipe.copy(
                        name = binding.editRecipeNameInput.text.toString(),
                        ingredients = binding.editRecipeIngredientsInput.text.toString(),
                        instructions = binding.editRecipeInstructionsInput.text.toString(),
                        category = selectedCategory
                    )
                    viewModel.updateRecipe(updatedRecipe)
                    finish()
                }

            }
        }

        binding.deleteButton.setOnClickListener {
            viewModel.getRecipeById(recipeId).observe(this) { recipe ->
                if (recipe != null) {
                    viewModel.deleteRecipe(recipe)
                    finish()
                }
            }
        }

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}