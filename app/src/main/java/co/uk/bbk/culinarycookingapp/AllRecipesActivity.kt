package co.uk.bbk.culinarycookingapp

import android.os.Bundle
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

        adapter = RecipeListAdapter()
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipesRecyclerView.adapter = adapter

        viewModel.recipes.observe(this) { recipes ->
            adapter.submitList(recipes)
        }
    }
}




