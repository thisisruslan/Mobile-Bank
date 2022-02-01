package uz.gita.newmobilebanking.domain.usecase.impl.auth

import android.annotation.SuppressLint
import uz.gita.newmobilebanking.data.local.enums.StartAppEnum
import uz.gita.newmobilebanking.domain.repository.interfaces.AppRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.SplashScreenUC
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class SplashScreenUCImpl @Inject constructor(private val appRepository: AppRepository) :
    SplashScreenUC {
    override fun startApp(): StartAppEnum = appRepository.startApp()
}
