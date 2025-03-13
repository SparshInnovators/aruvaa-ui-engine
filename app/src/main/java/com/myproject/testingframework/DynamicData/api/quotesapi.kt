package com.myproject.testingframework.DynamicData.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface quotesApi{

    @GET("/v1/quotes")
    @Headers("X-Api-Key: JMSGNalxBMdm2wrvos4iIQ==QOh3SzdEx7peckI6")
    suspend fun getSingleQuote(): Response<List<Map<String,Any>>>
}