package co.uk.bbk.culinarycookingapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycookingapp.databinding.ActivityViewRecipeBinding
import java.io.File

class ViewRecipeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewRecipeBinding
    private val viewModel: RecipesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewRecipeBinding.inflate(layoutInflater)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val disclaimerText = "This functionality has intentionally not been implemented. It is not directly specified in my wireframes and is to demonstrate as proof of concept."
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("description", "Settings page selected. $disclaimerText")
                intent.putExtra("title", "Settings")
                startActivity(intent)
                return true
            }
            R.id.about -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("description", "About page selected. $disclaimerText")
                intent.putExtra("title", "About")
                startActivity(intent)
                return true
            }
            R.id.helpandsupport -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("description", "Help & Support page selected. $disclaimerText")
                intent.putExtra("title", "Help & Support")
                startActivity(intent)
                return true
            }
            R.id.profile -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("description", "Help & Support page selected. $disclaimerText")
                intent.putExtra("title", "Profile")
                startActivity(intent)
                return true
            }
            R.id.toggletheme -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("description", "Toggle Theme page selected. $disclaimerText")
                intent.putExtra("title", "Toggle Theme")
                startActivity(intent)
                return true
            }
            R.id.logout -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("description", "Logut page selected. $disclaimerText")
                intent.putExtra("title", "Logout")
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}