package soup.animation.sample.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.graphics.withTranslation
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import soup.animation.sample.R
import soup.animation.sample.utils.lerp
import kotlin.math.max
import kotlin.random.Random

class HeartAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private class Heart(
        var targetX: Float = 0f,
        var targetY: Float = 0f,
        var x: Float = 0f,
        var y: Float = 0f,
        var alpha: Float = 0f
    )

    private val drawable: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_heart)
        ?: throw IllegalArgumentException()
    private val drawableSize = max(drawable.intrinsicWidth, drawable.intrinsicHeight) / 2

    private val random = Random(1337)
    private val hearts = (1..10).map { Heart() }

    private val moveInterpolator = FastOutSlowInInterpolator()
    private val alphaInterpolator = AccelerateInterpolator()
    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1000
        interpolator = LinearInterpolator()
        addUpdateListener {
            if (isLaidOut) {
                val moveProgress = moveInterpolator.getInterpolation(it.animatedFraction)
                val alphaProgress = alphaInterpolator.getInterpolation(it.animatedFraction)
                val startX = width / 2f
                val startY = (height - drawableSize).toFloat()
                hearts.forEach { heart ->
                    heart.x = lerp(startX, heart.targetX, moveProgress)
                    heart.y = lerp(startY, heart.targetY, moveProgress)
                    heart.alpha = lerp(1f, 0f, alphaProgress)
                }
                invalidate()
            }
        }
    }

    init {
        drawable.bounds = Rect(-drawableSize, -drawableSize, drawableSize, drawableSize)
    }

    override fun onDraw(canvas: Canvas) {
        hearts.forEach { heart ->
            canvas.withTranslation(heart.x, heart.y) {
                drawable.alpha = (255 * heart.alpha).toInt()
                drawable.draw(canvas)
            }
        }
    }

    fun execute() {
        if (animator.isRunning.not()) {
            hearts.forEach { heart ->
                heart.targetX = lerp(drawableSize, width - drawableSize, random.nextFloat())
                heart.targetY = lerp(drawableSize, height / 2, random.nextFloat())
            }
            animator.start()
        }
    }
}