package co.uk.bbk.culinarycookingapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycookingapp.databinding.ActivitySettingsBinding

class SettingsActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            val systemBars = insets.getInsets(android.view.WindowInsets.Type.systemBars())
            view.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        val description = intent.getStringExtra("description")
        val pageTitle = intent.getStringExtra("title") ?: getString(R.string.settings_title)

        binding.settingsDescription.text = description ?: getString(R.string.settings_description)
        binding.settingsTitle.text = pageTitle

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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
                intent.putExtra("description", "Profile page selected. $disclaimerText")
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