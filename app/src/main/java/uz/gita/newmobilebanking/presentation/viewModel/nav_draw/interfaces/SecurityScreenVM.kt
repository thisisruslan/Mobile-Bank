package uz.gita.newmobilebanking.presentation.viewModel.nav_draw.interfaces

import androidx.lifecycle.LiveData

interface SecurityScreenVM {
    val visibilityProgressLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<String>
    val clickableLogoutBtnLiveData : LiveData<Boolean>
    val logoutSuccessLiveData: LiveData<String>
    fun userLogout()
}