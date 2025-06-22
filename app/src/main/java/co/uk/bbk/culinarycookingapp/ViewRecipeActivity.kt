package co.uk.bbk.culinarycookingapp

import android.app.Activity
import android.os.Bundle

class ViewRecipeActivity : Activity() {
    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.view_recipe)
    }
}