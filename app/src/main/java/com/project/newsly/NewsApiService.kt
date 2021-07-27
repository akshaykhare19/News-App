package com.project.newsly

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=in&apiKey=236fd80af9c24ab5b18dd50d7f4d6d7d
const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "236fd80af9c24ab5b18dd50d7f4d6d7d"

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): News

}


object NewsService {
    val newsInstance: NewsApiService

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsApiService::class.java)

    }
}