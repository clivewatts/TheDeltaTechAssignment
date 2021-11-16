package com.clivewatts.pawpawscroll.data.service

import retrofit2.Retrofit

fun getDogCeoClient(retrofit: Retrofit): ApiDogCeo {
    return retrofit.create(ApiDogCeo::class.java)
}