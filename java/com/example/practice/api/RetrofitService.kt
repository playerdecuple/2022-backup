package com.example.practice.api

import com.example.practice.api.model.MovieList
import com.example.practice.api.model.TagList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("movie/popular")
    fun getPopular(
        @Query("api_key") apiKey: String = "c7adde3568c6262b70a9082967992fc9",
        @Query("language") lang: String = "ko-KR",
        @Query("page") page: Int? = 1
    ): Call<MovieList>;

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey: String = "c7adde3568c6262b70a9082967992fc9",
        @Query("language") lang: String = "ko-KR",
        @Query("page") page: Int? = 1
    ): Call<MovieList>;


    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String = "c7adde3568c6262b70a9082967992fc9",
        @Query("language") lang: String = "ko-KR"
    ): Call<TagList>;

}