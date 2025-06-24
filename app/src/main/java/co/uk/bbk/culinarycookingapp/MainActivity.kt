package co.uk.bbk.culinarycookingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycookingapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RecipesViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            //setContentView(R.layout.activity_main)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val dao = RecipesDatabase.getInstance(applicationContext).recipesDao()
            viewModel.recipesDao = dao
            viewModel.generateSampleData()
            viewModel.readAllRecipes()


            val allRecipesButton = binding.viewAllRecipesButton
            allRecipesButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                startActivity(intent)
            }

            val breakfastButton = binding.buttonBreakfast
            breakfastButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Breakfast")
                startActivity(intent)
            }

            val lunchButton = binding.buttonLunch
            lunchButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Lunch")
                startActivity(intent)
            }

            val dinnerButton = binding.buttonDinner
            dinnerButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Dinner")
                startActivity(intent)
            }

            val brunchButton = binding.buttonBrunch
           brunchButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Brunch")
                startActivity(intent)
            }

            val dessertButton = binding.buttonDessert
            dessertButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Dessert")
                startActivity(intent)
            }

            val otherButton = binding.buttonOther
            otherButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Other")
                startActivity(intent)
            }
        }
}

