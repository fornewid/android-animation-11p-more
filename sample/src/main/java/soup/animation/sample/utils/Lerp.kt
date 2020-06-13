package soup.animation.sample.utils

fun lerp(from: Int, to: Int, progress: Float): Float {
    return (1 - progress) * from + to * progress
}

fun lerp(from: Float, to: Float, progress: Float): Float {
    return (1 - progress) * from + to * progress
}
