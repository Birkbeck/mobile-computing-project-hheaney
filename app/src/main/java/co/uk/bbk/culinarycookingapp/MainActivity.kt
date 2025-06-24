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
        }
}

