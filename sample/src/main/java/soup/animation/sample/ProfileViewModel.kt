package soup.animation.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

class ProfileViewModel : ViewModel() {

    val uiModel: LiveData<ProfileUiModel> = liveData {
        emit(ProfileUiModel(loading = true))
        delay(1000)
        emit(ProfileUiModel(items = (1..100).map { GlobalData.assets.random() }))
    }
}

data class ProfileUiModel(
    val loading: Boolean = false,
    val items: List<Int> = emptyList()
)
