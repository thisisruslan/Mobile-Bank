package uz.gita.newmobilebanking.domain.repository.interfaces

import uz.gita.newmobilebanking.data.local.enums.StartAppEnum

interface AppRepository {
    fun startApp(): StartAppEnum
}