package uz.gita.newmobilebanking.presentation.viewModel

import androidx.lifecycle.LiveData

interface SplashVM {
    val openMainScreenLiveData : LiveData<Unit>
    val openLoginScreenLiveData : LiveData<Unit>
}