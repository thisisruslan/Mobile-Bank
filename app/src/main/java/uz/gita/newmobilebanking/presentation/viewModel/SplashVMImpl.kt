package uz.gita.newmobilebanking.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.newmobilebanking.data.local.enums.StartAppEnum
import uz.gita.newmobilebanking.domain.repository.impl.AuthRepositoryImpl
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.SplashScreenUC
import javax.inject.Inject

@HiltViewModel
class SplashVMImpl @Inject constructor(private val splashUseCase: SplashScreenUC) : SplashVM,
    ViewModel() {

    override val openMainScreenLiveData = MutableLiveData<Unit>()
    override val openLoginScreenLiveData = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            delay(2500)
            Log.v("TTT", "splashVM->" + splashUseCase.startApp().toString())
            if (splashUseCase.startApp() == StartAppEnum.MAIN)
                openMainScreenLiveData.value = Unit
            else openLoginScreenLiveData.value = Unit
        }
    }

}