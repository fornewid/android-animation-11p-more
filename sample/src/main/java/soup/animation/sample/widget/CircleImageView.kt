package soup.animation.sample.widget

import android.content.Context
import android.graphics.Outline
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    private val outlineRect = RectF()

    init {
        clipToOutline = true
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val bounds = Rect()
                outlineRect.roundOut(bounds)
                outline.setRoundRect(bounds, bounds.width() / 2f)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateOutline()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        updateOutline()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start, top, end, bottom)
        updateOutline()
    }

    private fun updateOutline() {
        val w = width - paddingLeft - paddingRight
        val h = height - paddingTop - paddingBottom
        val size = min(w, h)
        val left = paddingLeft + (w - size) / 2f
        val top = paddingTop + (h - size) / 2f
        outlineRect.set(left, top, left + size, top + size)
    }
}
