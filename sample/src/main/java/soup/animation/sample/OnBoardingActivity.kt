package soup.animation.sample

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import soup.animation.sample.databinding.OnBoardingActivityBinding
import soup.animation.sample.databinding.OnBoardingItemBinding
import soup.animation.sample.utils.Interpolators
import soup.animation.sample.utils.viewBindings

class OnBoardingActivity : AppCompatActivity() {

    private val binding by viewBindings(OnBoardingActivityBinding::inflate)

    private val callback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            binding.pagerDescription.run {
                text = "OnBoarding Description ${position + 1}"
                animateSlideUp()
            }
            binding.pagerIndicator01.isActivated = position == 0
            binding.pagerIndicator02.isActivated = position == 1
            binding.pagerIndicator03.isActivated = position == 2
            binding.startButton.isVisible = position == 2
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = Adapter()
        binding.viewPager2.adapter = adapter
        binding.nextButton.setOnClickListener {
            binding.viewPager2.currentItem += 1
        }
        binding.startButton.setOnClickListener {
            GlobalData.showOnBoarding = false
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.viewPager2.registerOnPageChangeCallback(callback)
    }

    override fun onPause() {
        super.onPause()
        binding.viewPager2.unregisterOnPageChangeCallback(callback)
    }

    override fun onBackPressed() {
        if (binding.viewPager2.currentItem > 0) {
            binding.viewPager2.currentItem -= 1
        } else {
            super.onBackPressed()
        }
    }

    private fun View.animateSlideUp() {
        animate().cancel()
        alpha = 0f
        translationY = 40f
        animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(800)
            .setInterpolator(Interpolators.EASE_OUT_QUINT)
            .withLayer()
            .withEndAction(null)
    }

    private class Adapter : RecyclerView.Adapter<ViewHolder>() {

        private val items: List<Int> = listOf(
            R.drawable.on_boarding_01,
            R.drawable.on_boarding_02,
            R.drawable.on_boarding_03
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ViewHolder(OnBoardingItemBinding.inflate(inflater, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size
    }

    private class ViewHolder(
        private val binding: OnBoardingItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(@DrawableRes resId: Int) {
            binding.image.setImageResource(resId)
        }
    }
}
