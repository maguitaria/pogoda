package com.example.myapplication.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiConfig {


    companion object {

      //  val API_KEY : String? = System.getenv("API_KEY")
        fun getApiService() : APIInterface {
            // Api responce interceptor
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
                // Client
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
                // Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(APIInterface::class.java)

        }
       const  val API_KEY = "af0393288db44404bfe95148240702"
    }

}


