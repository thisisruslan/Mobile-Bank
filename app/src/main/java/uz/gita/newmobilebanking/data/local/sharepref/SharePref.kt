package uz.gita.newmobilebanking.data.local.sharepref

import android.content.Context
import uz.gita.newmobilebanking.data.local.enums.StartAppEnum
import uz.gita.newmobilebanking.utils.StringPreference
import uz.gita.newmobilebanking.utils.startScreen
import javax.inject.Inject

class SharePref @Inject constructor(private val context: Context){

    private val pref = context.getSharedPreferences("NewMobileBanking", Context.MODE_PRIVATE)

    var firstName: String by StringPreference(pref, "No firstName")

    var secondName: String by StringPreference(pref, "No secondName")
    var password : String by StringPreference(pref, "No password")
    var phoneNumber : String by StringPreference(pref, "No phone number")
    var pinCode : String by StringPreference(pref, "No pin code")
    var isPinCodeConfirmed : String by StringPreference(pref, "false")
    var accessToken : String by StringPreference(pref, "")
    var refreshToken : String by StringPreference(pref, "")

    var startScreen: StartAppEnum
        set(value) = pref.edit().putString("startScreen", value.name).apply()
        get() = pref.getString("startScreen", StartAppEnum.LOGIN.name)!!.startScreen()

}