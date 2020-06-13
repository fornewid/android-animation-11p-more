package soup.animation.sample

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import soup.animation.sample.databinding.ProfileActivityBinding
import soup.animation.sample.databinding.ProfileItemPhotoBinding
import soup.animation.sample.utils.Interpolators
import soup.animation.sample.utils.NoDiffCallback
import soup.animation.sample.utils.viewBindings

class ProfileActivity : AppCompatActivity() {

    private val binding by viewBindings(ProfileActivityBinding::inflate)
    private val viewModel: ProfileViewModel by viewModels()

    private val listener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        val progress = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
        // TODO: MotionLayout
        // binding.header.root.progress = progress
        binding.header.root.alpha = 1f - progress
        binding.collapse.alpha = progress
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: Shared Elements
        //window.sharedElementEnterTransition = TransitionSet().apply {
        //    ordering = ORDERING_TOGETHER
        //    addTransition(ChangeBounds().apply { pathMotion = ArcMotion() })
        //    addTransition(ChangeTransform())
        //    addTransition(ChangeClipBounds())
        //    addTransition(ChangeImageTransform())
        //    doOnEnd {
        //        binding.header.profileNickname.animateSlideUp(100)
        //        binding.header.profileDescription.animateSlideUp(200)
        //    }
        //}
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = Adapter { view, resId ->
            val intent = Intent(this, ViewerActivity::class.java)
            intent.putExtra("resId", resId)
            // TODO: Shared Elements
            //val options = ActivityOptionsCompat
            //    .makeSceneTransitionAnimation(this, view, view.transitionName)
            //ActivityCompat.startActivity(this, intent, options.toBundle())
            startActivity(intent)
        }
        binding.recyclerView.let { view ->
            //TODO: ItemAnimator
            //val spanCount = (view.layoutManager as? GridLayoutManager)?.spanCount ?: 1
            //view.itemAnimator = GridItemAnimator(spanCount = spanCount)
            view.adapter = adapter
        }
        viewModel.uiModel.observe(this, Observer {
            adapter.submitList(it.items)
            binding.loadingView.isVisible = it.loading

            binding.header.profileNickname.isGone = it.loading
            binding.header.profileDescription.isGone = it.loading
        })
    }

    override fun onResume() {
        super.onResume()
        binding.appBar.addOnOffsetChangedListener(listener)
    }

    override fun onPause() {
        binding.appBar.removeOnOffsetChangedListener(listener)
        super.onPause()
    }

    private fun View.animateSlideUp(delay: Long) {
        animate().cancel()
        translationY = 40f
        visibility = View.VISIBLE
        animate()
            .setStartDelay(delay)
            .translationY(0f)
            .setDuration(800)
            .setInterpolator(Interpolators.EASE_OUT_QUINT)
            .withLayer()
            .withEndAction(null)
    }

    class Adapter(
        private val onItemClick: (View, Int) -> Unit
    ) : ListAdapter<Int, ViewHolder>(NoDiffCallback()) {

        init {
            stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProfileItemPhotoBinding.inflate(inflater, parent, false)
            return ViewHolder(binding) { view, position ->
                onItemClick(view, getItem(position))
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }

    class ViewHolder(
        private val binding: ProfileItemPhotoBinding,
        private val onItemClick: (View, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.thumbnailImage.setOnClickListener {
                onItemClick(it, bindingAdapterPosition)
            }
        }

        fun bind(@DrawableRes resId: Int) {
            binding.thumbnailImage.setImageResource(resId)
        }
    }
}
