package com.example.pagin_with_api

import com.example.pagin_with_api.dataclass.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("users")
    fun getData(
        @Query("since") since: Int,
        @Query("per_page") per_page: Int?,
    ): Call<ArrayList<Data.DataItem>>

}