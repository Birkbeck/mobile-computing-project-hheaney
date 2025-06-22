package co.uk.bbk.culinarycookingapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// This file defines the Recipe data class which is used to represent a recipe in the app.
// It includes properties for the recipe's ID, name, ingredients, instructions, and an optional
// image URI.
// The class is annotated with @Entity to indicate that it is a Room database entity, and the
// @PrimaryKey annotation is used to specify that the 'id' field is the primary key for the entity.
// A user may add one or more details for the recipe.
@Entity(tableName = "Recipes")
data class Recipe (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String? = null,
    val ingredients: String? = null,
    val instructions: String? = null,
    val imageUri: Int? = null,
    val category: String? = null
) : Serializable