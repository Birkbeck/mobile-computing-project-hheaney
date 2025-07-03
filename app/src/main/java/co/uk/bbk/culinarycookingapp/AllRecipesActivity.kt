package co.uk.bbk.culinarycookingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycookingapp.databinding.ActivityAllRecipesBinding

class AllRecipesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllRecipesBinding
    private val viewModel: RecipesViewModel by viewModels()
    private lateinit var adapter: RecipeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            val systemBars = insets.getInsets(android.view.WindowInsets.Type.systemBars())
            view.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        adapter = RecipeListAdapter()
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipesRecyclerView.adapter = adapter

        // sample data - test.  to be deleted
        val dao = RecipesDatabase.getInstance(applicationContext).recipesDao()
        viewModel.recipesDao = dao
        viewModel.readAllRecipes()

        viewModel.recipes.observe(this) { recipes ->
            Log.d("BBK", "Observed recipes: $recipes")
            val category = intent.getStringExtra("category")
            binding.allRecipesTitle.text = category ?: "All Recipes"
            val filteredRecipes = if (category != null) {
                recipes.filter { it.category == category }
            } else {
                recipes
            }
            adapter.submitList(filteredRecipes)
        }

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val category = intent.getStringExtra("category")
    }

    // To ensure up to date data is displayed after any changes
    override fun onResume() {
        super.onResume()
        viewModel.readAllRecipes()
    }
}




