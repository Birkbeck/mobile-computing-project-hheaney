package co.uk.bbk.culinarycookingapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycookingapp.databinding.EditRecipeBinding

class EditRecipeActivity: AppCompatActivity() {

    private lateinit var binding: EditRecipeBinding
    private val viewModel: RecipesViewModel by viewModels()

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        binding = EditRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}