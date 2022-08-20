package com.smartestmedia.task.api


import com.smartestmedia.task.model.ResultApi
import com.smartestmedia.task.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): Response<ResultApi>
}