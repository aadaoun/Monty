package com.develop.monty.Network
import com.google.android.gms.common.api.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


class RetroInstance {

    companion object {
        val baseURL = "https://api.github.com/orgs/google/"
        fun getRetroInstance(): Retrofit {
            val retrofit: Retrofit =Retrofit.Builder()
                .baseUrl(baseURL)
                .client(myHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
        fun myHttpClient(): OkHttpClient {

            val builder = OkHttpClient().newBuilder()
                .addNetworkInterceptor(CacheInterceptor())
                .addInterceptor(ErrorInterceptor())
            return builder.build()
        }


    }

}


