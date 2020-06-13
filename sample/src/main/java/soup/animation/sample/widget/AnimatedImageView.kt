package soup.animation.sample.widget

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import soup.animation.sample.R

class AnimatedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    private var anim: Animatable? = null
    private var attached: Boolean = false
    private var allowAnimation = false

    private var drawableId: Int = 0

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedImageView, 0, 0)
        try {
            allowAnimation = a.getBoolean(R.styleable.AnimatedImageView_allowAnimation, true)
        } finally {
            a.recycle()
        }
    }

    fun setAllowAnimation(allowAnimation: Boolean) {
        if (this.allowAnimation != allowAnimation) {
            this.allowAnimation = allowAnimation
            updateAnim()
            if (!allowAnimation && anim != null) {
                (anim as? Drawable)?.setVisible(visibility == View.VISIBLE, true /* restart */)
            }
        }
    }

    private fun updateAnim() {
        if (attached) {
            anim?.stop()
        }
        val drawable = drawable
        if (drawable is Animatable) {
            anim = drawable
            if (isShown && allowAnimation) {
                drawable.start()
            }
        } else {
            anim = null
        }
    }

    override fun setImageDrawable(drawable: Drawable?) {
        if (drawable != null) {
            if (drawableId == drawable.hashCode()) return
            drawableId = drawable.hashCode()
        } else {
            drawableId = 0
        }
        super.setImageDrawable(drawable)
        updateAnim()
    }

    override fun setImageResource(resid: Int) {
        if (drawableId == resid) return
        drawableId = resid
        super.setImageResource(resid)
        updateAnim()
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attached = true
        updateAnim()
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        anim?.stop()
        attached = false
    }

    override fun onVisibilityChanged(changedView: View, vis: Int) {
        super.onVisibilityChanged(changedView, vis)
        if (isShown && allowAnimation) {
            anim?.start()
        } else {
            anim?.stop()
        }
    }
}
