package uz.gita.newmobilebanking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.newmobilebanking.domain.repository.impl.HistoryPageUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.MainPageUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.auth.LoginScreenUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.auth.RegisterScreenUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.auth.SplashScreenUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.auth.VerifyScreenUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.card.AddCardScreenUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.card.EditCardScreenUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.card.EditRemoveCardScreenUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.card.VerifyAddCardScreenUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.nav_draw.SecurityScreenUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.profile.ProfilePageUCImpl
import uz.gita.newmobilebanking.domain.usecase.impl.transfer.TransferPageUCImpl
import uz.gita.newmobilebanking.domain.usecase.interfaces.MainPageUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.LoginScreenUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.RegisterScreenUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.SplashScreenUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.VerifyScreenUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.AddCardScreenUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.EditCardScreenUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.EditRemoveCardScreenUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.VerifyAddCardScreenUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.nav_draw.SecurityScreenUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.profile.ProfilePageUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.transfer.HistoryPageUC
import uz.gita.newmobilebanking.domain.usecase.interfaces.transfer.TransferPageUC

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {


    //Auth UseCase
    @Binds
    fun getSplashUseCase(impl: SplashScreenUCImpl): SplashScreenUC

    @Binds
    fun getLoginUseCase(impl: LoginScreenUCImpl): LoginScreenUC

    @Binds
    fun getRegisterUseCase(impl: RegisterScreenUCImpl): RegisterScreenUC

    @Binds
    fun getVerifyUseCase(impl: VerifyScreenUCImpl): VerifyScreenUC

    @Binds
    fun getSecurityScreenUseCase(impl: SecurityScreenUCImpl): SecurityScreenUC


    //Transfer UseCase
    @Binds
    fun getTransferPageUseCase(impl: TransferPageUCImpl): TransferPageUC

    @Binds
    fun getTransferHistoryPaging(impl: HistoryPageUCImpl) : HistoryPageUC


    //Card UseCase
    @Binds
    fun getMainPageUseCase(impl: MainPageUCImpl): MainPageUC

    @Binds
    fun getAddCardScreenUseCase(impl: AddCardScreenUCImpl): AddCardScreenUC

    @Binds
    fun getVerifyAddCardScreenUseCase(impl: VerifyAddCardScreenUCImpl): VerifyAddCardScreenUC

    @Binds
    fun getEditRemoveCardScreenUseCase(impl: EditRemoveCardScreenUCImpl): EditRemoveCardScreenUC

    @Binds
    fun getEditCardScreenUseCase(impl: EditCardScreenUCImpl): EditCardScreenUC

    //profile use case
    @Binds
    fun getProfilePageUseCase(impl: ProfilePageUCImpl): ProfilePageUC


}

