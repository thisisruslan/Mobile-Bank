package uz.gita.newmobilebanking.domain.usecase.impl.auth

import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.SetPinScreenUC
import uz.gita.newmobilebanking.domain.repository.interfaces.AuthRepository
import javax.inject.Inject

class SetPinScreenUCImpl @Inject constructor(private val repository: AuthRepository) : SetPinScreenUC {

}