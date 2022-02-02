package com.vlad.tutu

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun Fragment.toast(@StringRes stringRes: Int) {
    Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
}

fun Throwable.isNetworkError(): Boolean {
    return this is UnknownHostException || this is ConnectException || this is SocketTimeoutException
}
