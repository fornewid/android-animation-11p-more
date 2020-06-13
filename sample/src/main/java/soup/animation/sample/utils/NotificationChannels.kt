package soup.animation.sample.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService
import soup.animation.sample.utils.NotificationChannels.NOTIFICATION

object NotificationChannels {
    const val NOTIFICATION = "NOTIFICATION"
}

fun Context.createNotificationChannels() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
    getSystemService<NotificationManager>()?.createNotificationChannel(
        NotificationChannel(NOTIFICATION, "Notification", NotificationManager.IMPORTANCE_DEFAULT)
    )
}
