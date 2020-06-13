package soup.animation.sample.utils

import android.view.animation.Interpolator
import androidx.core.view.animation.PathInterpolatorCompat

// https://easings.net/
object Interpolators {

    val EASE_IN_SINE by cubicBezier(0.47f, 0f, 0.745f, 0.715f)
    val EASE_OUT_SINE by cubicBezier(0.39f, 0.575f, 0.565f, 1f)
    val EASE_IN_OUT_SINE by cubicBezier(0.445f, 0.05f, 0.55f, 0.95f)
    val EASE_IN_QUAD by cubicBezier(0.55f, 0.085f, 0.68f, 0.53f)
    val EASE_OUT_QUAD by cubicBezier(0.25f, 0.46f, 0.45f, 0.94f)
    val EASE_IN_OUT_QUAD by cubicBezier(0.455f, 0.03f, 0.515f, 0.955f)
    val EASE_IN_CUBIC by cubicBezier(0.55f, 0.055f, 0.675f, 0.19f)
    val EASE_OUT_CUBIC by cubicBezier(0.215f, 0.61f, 0.355f, 1f)
    val EASE_IN_OUT_CUBIC by cubicBezier(0.645f, 0.045f, 0.355f, 1f)
    val EASE_IN_QUART by cubicBezier(0.895f, 0.03f, 0.685f, 0.22f)
    val EASE_OUT_QUART by cubicBezier(0.165f, 0.84f, 0.44f, 1f)
    val EASE_IN_OUT_QUART by cubicBezier(0.77f, 0f, 0.175f, 1f)
    val EASE_IN_QUINT by cubicBezier(0.755f, 0.05f, 0.855f, 0.06f)
    val EASE_OUT_QUINT by cubicBezier(0.23f, 1f, 0.32f, 1f)
    val EASE_IN_OUT_QUINT by cubicBezier(0.86f, 0f, 0.07f, 1f)
    val EASE_IN_EXPO by cubicBezier(0.95f, 0.05f, 0.795f, 0.035f)
    val EASE_OUT_EXPO by cubicBezier(0.19f, 1f, 0.22f, 1f)
    val EASE_IN_OUT_EXPO by cubicBezier(1f, 0f, 0f, 1f)
    val EASE_IN_CIRC by cubicBezier(0.6f, 0.04f, 0.98f, 0.335f)
    val EASE_OUT_CIRC by cubicBezier(0.075f, 0.82f, 0.165f, 1f)
    val EASE_IN_OUT_CIRC by cubicBezier(0.785f, 0.135f, 0.15f, 0.86f)
    val EASE_IN_BACK by cubicBezier(0.6f, -0.28f, 0.735f, 0.045f)
    val EASE_OUT_BACK by cubicBezier(0.175f, 0.885f, 0.32f, 1.275f)
    val EASE_IN_OUT_BACK by cubicBezier(0.68f, -0.55f, 0.265f, 1.55f)

    private fun cubicBezier(x1: Float, y1: Float, x2: Float, y2: Float): Lazy<Interpolator> {
        return lazy { PathInterpolatorCompat.create(x1, y1, x2, y2) }
    }
}
