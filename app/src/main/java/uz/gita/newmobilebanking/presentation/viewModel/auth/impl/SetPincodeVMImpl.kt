package uz.gita.newmobilebanking.presentation.viewModel.auth.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.newmobilebanking.data.local.sharepref.SharePref
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.SetPincodeVM
import javax.inject.Inject

@HiltViewModel
class SetPincodeVMImpl @Inject constructor(private val pref: SharePref) : ViewModel(),
    SetPincodeVM {
    private var codeChecker: String = ""

    override val confirmPinCodeLiveData = MutableLiveData<Unit>()
    override val isCorrectPinCodeLiveData = MutableLiveData<Boolean>()

    override fun setPincode(pinCode: String) {
        when {
            pref.isPinCodeConfirmed == "false" -> {
                confirmPinCodeLiveData.value = Unit
                pref.isPinCodeConfirmed = "true"
                codeChecker = pinCode
                Log.v("YYY", "1")
            }
            codeChecker == pinCode -> {
                pref.pinCode = pinCode
                isCorrectPinCodeLiveData.value = true
                Log.v("YYY", "2")
            }
            else -> {
                isCorrectPinCodeLiveData.value = false
                Log.v("YYY", "3")
            }
        }
    }
}