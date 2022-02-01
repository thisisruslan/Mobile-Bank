package uz.gita.newmobilebanking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.newmobilebanking.domain.repository.impl.*
import uz.gita.newmobilebanking.domain.repository.interfaces.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun getAppRepository(impl: AppRepositoryImpl): AppRepository

    @Binds
    @Singleton
    fun getAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @[Binds Singleton]
    fun getTransferRepository(impl: TransferRepositoryImpl) : TransferRepository

    @[Binds Singleton]
    fun getCardsRepository(impl: CardRepositoryImpl) : CardRepository

    @[Binds Singleton]
    fun getProfileRepository(impl: ProfileRepositoryImpl) : ProfileRepository
}
