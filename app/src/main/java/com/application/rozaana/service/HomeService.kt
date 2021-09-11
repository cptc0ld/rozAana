package com.application.rozaana.service

import com.application.rozaana.model.PexelData
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {

    @GET("/v1/collections/{collectionId}")
    suspend fun getMedia(
        @HeaderMap headers: Map<String, String>,
        @Path("collectionId") collectionId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 3,
    ): PexelData
}
