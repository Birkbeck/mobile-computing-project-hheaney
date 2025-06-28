package co.uk.bbk.culinarycookingapp

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Recipe::class], version = 3)
abstract class RecipesDatabase : RoomDatabase(){

    abstract fun recipesDao(): RecipesDao

    companion object {
       @Volatile
        private var INSTANCE: RecipesDatabase? = null
        fun getInstance(context: Context): RecipesDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecipesDatabase::class.java,
                    "recipes_database"
                )
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }

            }
        }
    }
}
