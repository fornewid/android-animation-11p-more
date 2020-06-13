package soup.animation.sample

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.transition.platform.MaterialSharedAxis
import soup.animation.sample.databinding.DetailActivityBinding
import soup.animation.sample.utils.viewBindings

class DetailActivity : AppCompatActivity() {

    private val binding by viewBindings(DetailActivityBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
            addTarget(R.id.root)
        }
        window.allowEnterTransitionOverlap = true
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.thumbnailImage.setImageResource(intent.getIntExtra("resId", 0))
        binding.favoriteFab.setOnClickListener {
            it.isSelected = it.isSelected.not()
            if (it.isSelected) {
                binding.animatedView.execute()
            }
        }
    }
}
