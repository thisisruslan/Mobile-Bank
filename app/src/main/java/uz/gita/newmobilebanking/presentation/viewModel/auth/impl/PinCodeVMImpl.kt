package uz.gita.newmobilebanking.presentation.viewModel.auth.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import uz.gita.newmobilebanking.data.local.sharepref.SharePref
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.PinCodeVM
import javax.inject.Inject

@HiltViewModel
class PinCodeVMImpl @Inject constructor(private val pref: SharePref) : ViewModel(), PinCodeVM {
    override val isCorrectPinCodeLiveData = MutableLiveData<Boolean>()

    override fun checkPinCode(pinCode: String) {
        Timber.tag("TTT").v("pref -> " + pref.pinCode)
        Timber.v("user input -> " + pinCode)
        isCorrectPinCodeLiveData.value = pref.pinCode == pinCode

    }

}