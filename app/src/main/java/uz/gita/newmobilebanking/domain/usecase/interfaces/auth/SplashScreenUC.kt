package uz.gita.newmobilebanking.domain.usecase.interfaces.auth

import android.annotation.SuppressLint
import uz.gita.newmobilebanking.data.local.enums.StartAppEnum

@SuppressLint("CustomSplashScreen")
interface SplashScreenUC {
    fun startApp(): StartAppEnum
}