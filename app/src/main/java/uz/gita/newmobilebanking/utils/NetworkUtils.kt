package uz.gita.newmobilebanking.utils

import android.content.Context
import com.google.gson.Gson
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import timber.log.Timber
import uz.gita.newmobilebanking.BuildConfig
import uz.gita.newmobilebanking.BuildConfig.BASE_URL
import uz.gita.newmobilebanking.data.local.sharepref.SharePref
import uz.gita.newmobilebanking.data.remote.response.auth.VerifyResponse

fun OkHttpClient.Builder.addLoggingInterceptor(context: Context): OkHttpClient.Builder {
    HttpLoggingInterceptor.Level.HEADERS
    val logging = HttpLoggingInterceptor.Logger { message -> Timber.tag("HTTP").d(message) }
    if (BuildConfig.LOGGING) {
        addInterceptor(ChuckInterceptor(context))
        addInterceptor(HttpLoggingInterceptor(logging))
    }
    return this
}

//fun addHeaderInterceptor(pref: SharePref) = Interceptor { chain ->
//    val request = chain.request()
//
//    return@Interceptor chain.proceed(request)
//}

fun refreshInterceptor(pref: SharePref) = Interceptor { chain ->
    val request = chain.request()
    val response = chain.proceed(request)
    if (response.code == 401) {
        response.close()
        val data = JSONObject()
        data.put("phone", pref.phoneNumber)
        val body = data.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val requestRefresh = request.newBuilder()
            .addHeader("refresh_token", pref.refreshToken)
            .method("POST", body)
            .url("${BASE_URL}auth/refresh")
            .build()

        val responseRefresh = chain.proceed(requestRefresh)
        timber(responseRefresh.message)
        if (responseRefresh.code == 401) {
            timber("login navigate")
            return@Interceptor responseRefresh// refresh token ham eskirdi login screen navigate
        }

        if (responseRefresh.code == 200) {
            responseRefresh.body?.let {
                val data = Gson().fromJson(it.string(), VerifyResponse::class.java)
                pref.accessToken = data.data.accessToken
                pref.refreshToken = data.data.refreshToken
            }

            responseRefresh.close()
            val requestTwo = request.newBuilder()
                .removeHeader("token")
                .addHeader("token", pref.accessToken)
                .build()
            return@Interceptor chain.proceed(requestTwo)
        }
    }

    return@Interceptor response
}

