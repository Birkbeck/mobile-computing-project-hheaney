package co.uk.bbk.culinarycookingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycookingapp.databinding.ActivityMainBinding
import android.view.Menu
import android.view.MenuItem
import android.util.Log



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RecipesViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            //setContentView(R.layout.activity_main)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.root.setOnApplyWindowInsetsListener { view, insets ->
                val systemBars = insets.getInsets(android.view.WindowInsets.Type.systemBars())
                view.setPadding(0, systemBars.top, 0, systemBars.bottom)
                insets
            }

            val dao = RecipesDatabase.getInstance(applicationContext).recipesDao()
            viewModel.recipesDao = dao
            //viewModel.generateSampleData()
            viewModel.readAllRecipes()


            val allRecipesButton = binding.viewAllRecipesButton
            allRecipesButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                startActivity(intent)
            }

            val breakfastButton = binding.buttonBreakfast
            breakfastButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("category", "Breakfast")
                startActivity(intent)
            }

            val lunchButton = binding.buttonLunch
            lunchButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("category", "Lunch")
                startActivity(intent)
            }

            val dinnerButton = binding.buttonDinner
            dinnerButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("category", "Dinner")
                startActivity(intent)
            }

            val brunchButton = binding.buttonBrunch
           brunchButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("category", "Brunch")
                startActivity(intent)
            }

            val dessertButton = binding.buttonDessert
            dessertButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("category", "Dessert")
                startActivity(intent)
            }

            val otherButton = binding.buttonOther
            otherButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("category", "Other")
                startActivity(intent)
            }

            val addRecipe = binding.addRecipeButton
            addRecipe.setOnClickListener {
                val intent = Intent(this, AddRecipeActivity::class.java)
                startActivity(intent)
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

