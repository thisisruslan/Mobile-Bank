package uz.gita.newmobilebanking.utils

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import uz.gita.newmobilebanking.data.local.enums.StartAppEnum
import java.io.File

fun Fragment.showToast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun View.visibility(visibility: Boolean): View {
    if (visibility) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
    return this
}

fun String.startScreen(): StartAppEnum {
    return if (this == StartAppEnum.LOGIN.name) StartAppEnum.LOGIN
    else StartAppEnum.MAIN
}

fun myLog(message: String, tag: String = "TTT") {
    Log.d(tag, message)
}

fun <T : ViewBinding> T.scope(block: T.() -> Unit) {
    block(this)
}

fun File.toRequestData(): MultipartBody.Part {
    val requestFile = this.asRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("avatar", name, requestFile)
}


fun timber(message: String, tag: String = "TTT") {
    Timber.tag(tag).d(message)
}

fun View.visible(bool: Boolean) {
    if (bool) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}




