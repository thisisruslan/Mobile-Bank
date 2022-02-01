package uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces

import androidx.lifecycle.LiveData

interface SetPincodeVM {
    val confirmPinCodeLiveData : LiveData<Unit>
    val isCorrectPinCodeLiveData : LiveData<Boolean>
    fun setPincode(pinCode : String)
}