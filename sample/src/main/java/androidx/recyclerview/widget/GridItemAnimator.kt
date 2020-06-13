package androidx.recyclerview.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.DecelerateInterpolator

class GridItemAnimator(private val spanCount: Int = 1) : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        return super.animateAdd(holder).apply {
            holder.itemView.translationX = (holder.itemView.width / 10).toFloat()
            holder.itemView.translationY = (holder.itemView.height / 10).toFloat()
        }
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        val view = holder.itemView
        val animation = view.animate()
        mAddAnimations.add(holder)
        animation
            .setInterpolator(DecelerateInterpolator())
            .setStartDelay(calculateAddDelay(holder))
            .alpha(1f)
            .translationX(0f)
            .translationY(0f)
            .setDuration(addDuration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animator: Animator) {
                    dispatchAddStarting(holder)
                }

                override fun onAnimationCancel(animator: Animator) {
                    view.alpha = 1f
                    view.translationY = 0f
                }

                override fun onAnimationEnd(animator: Animator) {
                    animation.setListener(null)
                    dispatchAddFinished(holder)
                    mAddAnimations.remove(holder)
                    dispatchFinishedWhenDone()
                }
            }).start()
    }

    private fun calculateAddDelay(holder: RecyclerView.ViewHolder): Long {
        val position = holder.bindingAdapterPosition
        val order = position / spanCount + position % spanCount
        return order * addDuration
    }
}
