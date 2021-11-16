package com.clivewatts.pawpawscroll.network

import com.clivewatts.pawpawscroll.BuildConfig
import com.google.gson.Gson
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.java.KoinJavaComponent.inject

class RetrofitInstance {
    companion object {
        private val gson : Gson by inject(Gson::class.java);
        fun retrofit(): Retrofit {
            val httpClient = OkHttpClient.Builder();
            httpClient.addInterceptor(HttpLoggingInterceptor())
            return Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}