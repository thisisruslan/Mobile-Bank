package uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces

import androidx.lifecycle.LiveData

interface RegisterVM {

    val openRegister2ScreenLiveData : LiveData<Unit>
    fun openRegister2Screen()
}