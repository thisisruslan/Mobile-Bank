package uz.gita.newmobilebanking.di

import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import uz.gita.newmobilebanking.BuildConfig.BASE_URL
import uz.gita.newmobilebanking.data.local.sharepref.SharePref
import uz.gita.newmobilebanking.data.remote.api.AuthApi
import uz.gita.newmobilebanking.data.remote.api.CardApi
import uz.gita.newmobilebanking.data.remote.api.ProfileApi
import uz.gita.newmobilebanking.data.remote.api.TransferApi
import uz.gita.newmobilebanking.utils.addLoggingInterceptor
import uz.gita.newmobilebanking.utils.refreshInterceptor
import javax.inject.Singleton

//SingletonComponent appning xayot davrida ishlaydi
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @[Provides Singleton]
    fun getGsonObject(): Gson = Gson()

    @[Provides Singleton]
    fun getAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @[Provides Singleton]
    fun getTransferApi(retrofit: Retrofit): TransferApi = retrofit.create(TransferApi::class.java)

    @[Provides Singleton]
    fun getCardApi(retrofit: Retrofit) : CardApi = retrofit.create(CardApi::class.java)

    @[Provides Singleton]
    fun getProfileApi(retrofit: Retrofit) : ProfileApi = retrofit.create(ProfileApi::class.java)

    @[Provides Singleton]
    fun getRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()

    @[Provides Singleton]
    fun getOkHttpClient(pref: SharePref, @ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .addLoggingInterceptor(context)
//            .addInterceptor(addHeaderInterceptor(pref))
            .addInterceptor(refreshInterceptor(pref))
            .build()


}