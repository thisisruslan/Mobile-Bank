package uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces

import androidx.lifecycle.LiveData

interface PinCodeVM {
    val isCorrectPinCodeLiveData : LiveData<Boolean>
    fun checkPinCode(pinCode: String)
}