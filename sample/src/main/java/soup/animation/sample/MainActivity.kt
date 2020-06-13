package soup.animation.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import soup.animation.sample.databinding.MainActivityBinding
import soup.animation.sample.utils.FragmentNavigator
import soup.animation.sample.utils.viewBindings

class MainActivity : AppCompatActivity() {

    private val binding by viewBindings(MainActivityBinding::inflate)

    private val navigator by lazy {
        FragmentNavigator(supportFragmentManager, R.id.container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tab_home ->
                    navigator.navigate(
                        "home",
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    ) { HomeFragment() }
                R.id.tab_settings ->
                    navigator.navigate(
                        "settings",
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    ) { SettingsFragment() }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.tab_home
        } else {
            navigator.onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        navigator.onSaveInstanceState(outState)
    }
}
