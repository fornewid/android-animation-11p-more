package soup.animation.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import soup.animation.sample.databinding.DetailActivityBinding
import soup.animation.sample.utils.viewBindings

class DetailActivity : AppCompatActivity() {

    private val binding by viewBindings(DetailActivityBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.thumbnailImage.setImageResource(intent.getIntExtra("resId", 0))
        binding.favoriteFab.setOnClickListener {
            it.isSelected = it.isSelected.not()
            if (it.isSelected) {
                // TODO: Custom
                //binding.animatedView.execute()
            }
        }
    }
}
