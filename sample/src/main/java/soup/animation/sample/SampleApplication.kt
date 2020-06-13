package soup.animation.sample

import android.app.Application
import soup.animation.sample.utils.createNotificationChannels

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }
}

object GlobalData {
    var showOnBoarding: Boolean = true
    var isLoginFailed: Boolean = true
    var isLogin: Boolean = false

    val assets = arrayOf(
        R.drawable.img_01,
        R.drawable.img_02,
        R.drawable.img_03,
        R.drawable.img_04,
        R.drawable.img_05,
        R.drawable.img_06
    )
}
