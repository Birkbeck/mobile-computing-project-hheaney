package co.uk.bbk.culinarycookingapp


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycookingapp.databinding.ItemRecipeBinding
import android.util.Log

class RecipeListAdapter(
    private val viewRecipeClick: (Recipe) -> Unit,
    private val editRecipeClick: (Recipe) -> Unit
) :
    ListAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        Log.d("BBK", "Binding recipe $recipe")
        holder.bind(recipe, viewRecipeClick, editRecipeClick)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ViewRecipeActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.id)
            context.startActivity(intent)
        }
    }

    class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe,
                 viewRecipeClick: (Recipe) -> Unit,
                 editRecipeClick: (Recipe) -> Unit) {
            binding.recipeItemTitle.text = recipe.name
            recipe.imageUri?.let {
                binding.recipeItemImage.setImageResource(it)
            }
            binding.recipeItemViewButton.setOnClickListener {
                Log.d("BBK", "View button clicked for recipe: ${recipe.name}")
                viewRecipeClick(recipe)
            }
            binding.recipeItemEditButton.setOnClickListener {
                Log.d("BBK", "Edit button clicked for recipe: ${recipe.name}")
                editRecipeClick(recipe)
            }
        }
    }
}

class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }
}







/*
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.net.Uri
import android.view.View
import android.widget.TextView
import co.uk.bbk.culinarycookingapp.databinding.ItemRecipeBinding

class RecipeListAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.recipe_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = recipes[position].name
    }

    override fun getItemCount() = recipes.size
}


class RecipeListAdapter(
    private val recipes: List<Recipe>
): ListAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(RowItemDiffCallback()) {

    override fun getItemCount() = recipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ViewRecipeActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.id)
            context.startActivity(intent)
        }
    }

    class RecipeViewHolder private constructor(private val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): RecipeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecipeBinding.inflate(layoutInflater, parent, false)
                return RecipeViewHolder(binding)
            }
        }

        fun bind(recipe: Recipe) {
            binding.recipeItemTitle.text = recipe.name
            recipe.imageUri?.let {

                binding.recipeItemImage.setImageResource(it)
            }
        }
    }
}

class RowItemDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
}*/
