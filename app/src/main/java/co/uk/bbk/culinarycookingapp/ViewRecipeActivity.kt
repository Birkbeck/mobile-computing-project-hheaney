package co.uk.bbk.culinarycookingapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycookingapp.databinding.ViewRecipeBinding
import java.io.File

class ViewRecipeActivity : AppCompatActivity() {

    private lateinit var binding : ViewRecipeBinding
    private val viewModel: RecipesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            val systemBars = insets.getInsets(android.view.WindowInsets.Type.systemBars())
            view.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        val dao = RecipesDatabase.getInstance(applicationContext).recipesDao()
        viewModel.recipesDao = dao

        val recipeId = intent.getLongExtra("id", -1L)
        Log.d("ViewRecipeActivity", "Received recipe ID: $recipeId")
        if (recipeId != -1L) {
            viewModel.getRecipeById(recipeId).observe(this) { recipe ->
                Log.d("ViewRecipeActivity", "Observed recipe: $recipe")
                recipe?.let {
                    binding.recipeTitle.text = recipe.name
                    binding.recipeName.text = recipe.name
                    binding.recipeIngredients.text = recipe.ingredients
                    binding.recipeInstructions.text = recipe.instructions
                    if(!recipe.imageUri.isNullOrEmpty()) {
                        var file = File(recipe.imageUri)
                        if(file.exists()) {
                            binding.recipeImage.setImageURI(Uri.fromFile(file))
                        }
                    }
                }
            }
        }

        binding.editButton.setOnClickListener {
            val recipeId = intent.getLongExtra("id", -1L)
            val intent = Intent(this, EditRecipeActivity::class.java)
            intent.putExtra("id", recipeId)
            startActivity(intent)
        }

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}