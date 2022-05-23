package com.develop.monty.Network

import com.develop.monty.models.Repo
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {
    @Headers("Authorization: token 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")
    @GET("repos")
    suspend fun getSearchAPI(@Query("page") page:String): HashSet<Repo>
}
