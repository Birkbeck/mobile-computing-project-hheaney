package co.uk.bbk.culinarycookingapp

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import co.uk.bbk.culinarycookingapp.databinding.ActivityAddRecipeBinding
import java.io.File

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding
    private lateinit var recipesDb: RecipesDatabase
    private var imageUri: Uri? = null
    private var copiedImagePath: String? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        binding.recipeImageView.setImageURI(uri)
        uri?.let {
            copiedImagePath = copyImageToInternalStorage(this, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            val systemBars = insets.getInsets(android.view.WindowInsets.Type.systemBars())
            view.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        binding.uploadImageButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        /*ArrayAdapter.createFromResource(
            this,
            R.array.recipe_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.recipeCategorySpinner.adapter = adapter
        }*/

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.recipe_categories,
            R.layout.spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.recipeCategorySpinner.adapter = adapter

        recipesDb = RecipesDatabase.getInstance(this)

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.saveButton.setOnClickListener {
            val name = binding.recipeNameInput.text.toString()
            val ingredients = binding.recipeIngredientsInput.text.toString()
            val instructions = binding.recipeInstructionsInput.text.toString()
            val category = binding.recipeCategorySpinner.selectedItem.toString()


            val recipe = Recipe(
                name = name,
                ingredients = ingredients,
                instructions = instructions,
                imageUri = copiedImagePath,
                category = category
            )
            lifecycleScope.launch {
                recipesDb.recipesDao().insertRecipe(recipe)
                finish()
            }
        }
    }

    // Disclaimer copilot

    private fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val fileName = "recipe_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            file.outputStream().use { output ->
                inputStream.copyTo(output)
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
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


