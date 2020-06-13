package soup.animation.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import soup.animation.sample.utils.EventLiveData
import soup.animation.sample.utils.MutableEventLiveData

class LoginViewModel : ViewModel() {

    private val _loginUiModel = MutableLiveData(LoginUiModel(loading = false, enabled = true))
    val loginUiModel: LiveData<LoginUiModel>
        get() = _loginUiModel

    private val _uiEvent = MutableEventLiveData<Boolean>()
    val uiEvent: EventLiveData<Boolean>
        get() = _uiEvent

    fun login() {
        viewModelScope.launch {
            _loginUiModel.value = LoginUiModel(loading = true, enabled = false)
            withContext(Dispatchers.IO) {
                delay(1000)
                if (GlobalData.isLoginFailed) {
                    GlobalData.isLoginFailed = false
                    _loginUiModel.postValue(LoginUiModel(loading = false, enabled = true))
                    _uiEvent.postEvent(false)
                } else {
                    GlobalData.isLogin = true
                    _uiEvent.postEvent(true)
                }
            }
        }
    }
}

data class LoginUiModel(
    val loading: Boolean,
    val enabled: Boolean
)
