package co.uk.bbk.culinarycookingapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycookingapp.databinding.EditRecipeBinding
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class EditRecipeActivity: AppCompatActivity() {

    companion object {
        private const val REQUEST_IMAGE_PICK = 1001
    }

    private lateinit var binding: EditRecipeBinding
    private val viewModel: RecipesViewModel by viewModels()
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("EditRecipeActivity", "onCreate called")
        binding = EditRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // used co pilot to help
        val categories = resources.getStringArray(R.array.recipe_categories)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editRecipeCategorySpinner.adapter = adapter


        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            val systemBars = insets.getInsets(android.view.WindowInsets.Type.systemBars())
            view.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        val dao = RecipesDatabase.getInstance(applicationContext).recipesDao()
        viewModel.recipesDao = dao

        val recipeId = intent.getIntExtra("id", -1)
        Log.d("EditRecipeActivity", "Received recipe ID: $recipeId")
        if(recipeId == -1) {
            finish()
            return
        }

        viewModel.getRecipeById(recipeId).observe(this) { recipe ->
            Log.d("EditRecipeActivity", "Observed recipe: $recipe")
            if(recipe != null) {
                binding.editRecipeNameInput.setText(recipe.name)
                binding.editRecipeIngredientsInput.setText(recipe.ingredients)
                binding.editRecipeInstructionsInput.setText(recipe.instructions)
                binding.editRecipeCategorySpinner.setSelection(
                    resources.getStringArray(R.array.recipe_categories).indexOf(recipe.category)
                )

                if(!recipe.imageUri.isNullOrEmpty()) {
                    val file = File(recipe.imageUri)
                    if(file.exists()) {
                        val uri = Uri.fromFile(file)
                        Log.d("EditRecipeActivity", "Setting image URI: $uri")
                        binding.editRecipeImage.setImageURI(uri)
                        selectedImageUri = uri
                    }
                    else {
                        Log.d("EditRecipeActivity", "Image file does not exist: ${recipe.imageUri}")
                        binding.editRecipeImage.setImageResource(R.drawable.chefskiss)
                        selectedImageUri = null
                    }
                } else {
                    Log.d("EditRecipeActivity", "No image URI found, setting default image")
                    binding.editRecipeImage.setImageResource(R.drawable.chefskiss)
                    selectedImageUri = null
                }

                binding.saveButton.setOnClickListener {
                    val selectedCategory = binding.editRecipeCategorySpinner.selectedItem.toString()
                    val updatedRecipe = recipe.copy(
                        name = binding.editRecipeNameInput.text.toString(),
                        ingredients = binding.editRecipeIngredientsInput.text.toString(),
                        instructions = binding.editRecipeInstructionsInput.text.toString(),
                        category = selectedCategory,
                        imageUri = selectedImageUri?.path ?: recipe.imageUri
                    )
                    viewModel.updateRecipe(updatedRecipe)
                    finish()
                }

                binding.editImageButton.setOnClickListener {
                    // Launch image picker
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                    intent.type = "image/*"
                    startActivityForResult(intent, REQUEST_IMAGE_PICK)
                }
            }
        }

        binding.deleteButton.setOnClickListener {
            viewModel.getRecipeById(recipeId).observe(this) { recipe ->
                if (recipe != null) {
                    viewModel.deleteRecipe(recipe)
                    finish()
                }
            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("EditRecipeActivity", "onActivityResult called with requestCode: $requestCode, resultCode: $resultCode")
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                val copiedPath = copyImageToInternalStorage(imageUri)
                if (copiedPath != null) {
                    selectedImageUri = Uri.fromFile(File(copiedPath))
                    binding.editRecipeImage.setImageURI(selectedImageUri)
                }
            }
        }
    }

    // co pilot

    private fun copyImageToInternalStorage(uri: Uri): String? {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val fileName = "recipe_image_${System.currentTimeMillis()}.jpg"
            val file = File(filesDir, fileName)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file.absolutePath
        } catch (e: Exception) {
            Log.e("EditRecipeActivity", "Error copying image: ${e.message}")
            null
        }
    }
}