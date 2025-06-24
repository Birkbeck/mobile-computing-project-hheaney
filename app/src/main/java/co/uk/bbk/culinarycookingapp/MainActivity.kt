package co.uk.bbk.culinarycookingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val allRecipesButton = findViewById<Button>(R.id.view_all_recipes_button)
            allRecipesButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                startActivity(intent)
            }

            val breakfastButton = findViewById<Button>(R.id.button_breakfast)
            breakfastButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Breakfast")
                startActivity(intent)
            }

            val lunchButton = findViewById<Button>(R.id.button_lunch)
            lunchButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Lunch")
                startActivity(intent)
            }

            val dinnerButton = findViewById<Button>(R.id.button_dinner)
            dinnerButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Dinner")
                startActivity(intent)
            }

            val brunchButton = findViewById<Button>(R.id.button_brunch)
           brunchButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Brunch")
                startActivity(intent)
            }

            val dessertButton = findViewById<Button>(R.id.button_dessert)
            dessertButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Dessert")
                startActivity(intent)
            }

            val otherButton = findViewById<Button>(R.id.button_other)
            otherButton.setOnClickListener {
                val intent = Intent(this, AllRecipesActivity::class.java)
                intent.putExtra("CATEGORY", "Other")
                startActivity(intent)
            }
        }
}

