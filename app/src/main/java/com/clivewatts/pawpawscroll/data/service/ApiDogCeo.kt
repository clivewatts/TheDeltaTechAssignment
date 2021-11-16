package com.clivewatts.pawpawscroll.data.service

import com.clivewatts.pawpawscroll.data.models.response.ResponseGoodBoys
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiDogCeo {

    @GET("api/breeds/image/random/{pageSize}")
    suspend fun fetchGoodBoys(@Path("pageSize") size : Int) : ResponseGoodBoys

    @GET
    suspend fun downloadDogImage(@Url url : System)

}