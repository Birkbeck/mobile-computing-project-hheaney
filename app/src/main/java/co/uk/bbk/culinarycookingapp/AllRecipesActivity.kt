package co.uk.bbk.culinarycookingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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

        // co pilot
        val spacing = (8 * resources.displayMetrics.density).toInt()
        binding.recipesRecyclerView.addItemDecoration(object : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: android.graphics.Rect,
                view: android.view.View,
                parent: androidx.recyclerview.widget.RecyclerView,
                state: androidx.recyclerview.widget.RecyclerView.State
            ) {
                outRect.bottom = spacing
            }
        })

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




