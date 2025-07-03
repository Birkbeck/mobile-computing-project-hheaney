package co.uk.bbk.culinarycookingapp

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.uk.bbk.culinarycookingapp.databinding.AddRecipeBinding
import kotlinx.coroutines.launch
import android.content.Context
import java.io.File

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: AddRecipeBinding
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
        binding = AddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            val systemBars = insets.getInsets(android.view.WindowInsets.Type.systemBars())
            view.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        binding.uploadImageButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.recipe_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.recipeCategorySpinner.adapter = adapter
        }

        recipesDb = RecipesDatabase.getInstance(this)

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
}


