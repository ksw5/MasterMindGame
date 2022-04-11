package com.example.mastermindgame.model.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers


const val BASE_URL = "https://www.random.org/integers/"


val networkLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

val retrofit = Retrofit.Builder()
    .client(OkHttpClient.Builder().addInterceptor(networkLoggingInterceptor).build())
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface RandomNumberApiRequest {
    @Headers("Accept: text/plain")
    @GET("?num=4&min=0&max=7&col=1&base=10&format=plain&rnd=new")
        suspend fun getRandomNumbers() : String

}

object GameApiService {
    val retrofitService: RandomNumberApiRequest by lazy { retrofit.create(RandomNumberApiRequest::class.java) }

}