package androidx.recyclerview.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.DecelerateInterpolator

class HomeItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        return super.animateAdd(holder).apply {
            holder.itemView.translationY =
                (holder.itemView.height * holder.bindingAdapterPosition).toFloat()
        }
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        val view = holder.itemView
        val animation = view.animate()
        mAddAnimations.add(holder)
        animation
            .setInterpolator(DecelerateInterpolator(3f))
            .alpha(1f)
            .translationY(0f)
            .setDuration(addDuration * holder.bindingAdapterPosition)
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
}
