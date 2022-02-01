package uz.gita.newmobilebanking.domain.repository.impl

import uz.gita.newmobilebanking.data.local.enums.StartAppEnum
import uz.gita.newmobilebanking.data.local.sharepref.SharePref
import uz.gita.newmobilebanking.domain.repository.interfaces.AppRepository
import javax.inject.Inject


class AppRepositoryImpl @Inject constructor(private val pref: SharePref) : AppRepository {
    override fun startApp(): StartAppEnum = pref.startScreen

}
