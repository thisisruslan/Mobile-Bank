package uz.gita.newmobilebanking.presentation.viewModel.auth.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.RegisterVM
import javax.inject.Inject

@HiltViewModel
class RegisterVMImpl @Inject constructor() : ViewModel(), RegisterVM {

    override val openRegister2ScreenLiveData = MutableLiveData<Unit>()
    override fun openRegister2Screen() {
        openRegister2ScreenLiveData.value = Unit
    }

}