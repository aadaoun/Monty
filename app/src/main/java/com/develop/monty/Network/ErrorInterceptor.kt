package com.develop.monty.Network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        Log.d("@@@", "intercept: "+response)
        return response
    }
}
