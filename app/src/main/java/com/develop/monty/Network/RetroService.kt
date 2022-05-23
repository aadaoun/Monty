package com.develop.monty.Network

import com.develop.monty.models.Repo
import retrofit2.http.*
import java.util.*

interface RetroService {
    @Headers("Authorization: token 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")
    @GET("repos")
    suspend fun getDataFromAPI(@Query("page") page:String): List<Repo>
}
