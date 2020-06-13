package soup.animation.sample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.coroutines.delay
import soup.animation.sample.databinding.ViewerActivityBinding
import soup.animation.sample.utils.NotificationChannels
import soup.animation.sample.utils.viewBindings

class ViewerActivity : AppCompatActivity() {

    private val binding by viewBindings(ViewerActivityBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: Shared Elements
        //window.sharedElementEnterTransition = TransitionSet().apply {
        //    interpolator = OvershootInterpolator(0.7f)
        //    ordering = TransitionSet.ORDERING_TOGETHER
        //    addTransition(ChangeBounds().apply { pathMotion = ArcMotion() })
        //    addTransition(ChangeTransform())
        //    addTransition(ChangeClipBounds())
        //    addTransition(ChangeImageTransform())
        //}
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.photo.setImageResource(intent.getIntExtra("resId", 0))
        binding.foldableDeviceButton.apply {
            setOnClickListener { toggle() }
            setOnCheckedChangeListener { _, isChecked ->
                updateUi(editing = binding.photoModeButton.isChecked, fold = isChecked)
            }
        }
        binding.photoModeButton.apply {
            setOnClickListener { toggle() }
            setOnCheckedChangeListener { _, isChecked ->
                updateUi(editing = isChecked, fold = binding.foldableDeviceButton.isChecked)
            }
        }
        binding.photoSaveButton.setOnClickListener {
            WorkManager.getInstance(it.context).enqueueUniqueWork(
                WORK_NAME_DOWNLOAD,
                ExistingWorkPolicy.KEEP,
                OneTimeWorkRequest.from(DownloadWorker::class.java)
            )
        }

        WorkManager.getInstance(applicationContext)
            .getWorkInfosForUniqueWorkLiveData(WORK_NAME_DOWNLOAD)
            .observe(this, Observer { works ->
                binding.photoSaveButton.isEnabled = works.all { it.state != WorkInfo.State.RUNNING }
            })

        updateUi(
            editing = binding.photoModeButton.isChecked,
            fold = binding.foldableDeviceButton.isChecked
        )
    }

    private fun updateUi(editing: Boolean, fold: Boolean) {
        val layout = binding.root
        val constraintSet = ConstraintSet().apply {
            clone(layout)
            if (editing) {
                val inset = resources.getDimensionPixelSize(R.dimen.fake_inset_size)
                setGuidelineBegin(R.id.inset_top_guideline, inset)
                setGuidelineBegin(R.id.inset_left_guideline, inset)
                setGuidelineEnd(R.id.inset_right_guideline, inset)
                setGuidelineEnd(R.id.inset_bottom_guideline, inset)
            } else {
                setGuidelineBegin(R.id.inset_top_guideline, 0)
                setGuidelineBegin(R.id.inset_left_guideline, 0)
                setGuidelineEnd(R.id.inset_right_guideline, 0)
                setGuidelineEnd(R.id.inset_bottom_guideline, 0)
            }
            if (fold) {
                setGuidelinePercent(R.id.fold_guideline, 0.5f)
            } else {
                setGuidelinePercent(R.id.fold_guideline, 1f)
            }
        }
        // TODO: Transition
        //TransitionManager.beginDelayedTransition(layout)
        constraintSet.applyTo(layout)
    }

    companion object {
        private const val WORK_NAME_DOWNLOAD = "download"
    }
}

class DownloadWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        //TODO: animation-list
        val notification = NotificationCompat
            .Builder(applicationContext, NotificationChannels.NOTIFICATION)
            .setContentTitle("Download")
            .setContentText("Animated icon will be shown!")
            .setSmallIcon(R.drawable.stat_sys_download_anim0)
            .setProgress(0, 0, true)
            .build()
        setForeground(ForegroundInfo(1, notification))
        delay(3000)
        return Result.success()
    }
}
