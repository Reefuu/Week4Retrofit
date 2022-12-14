package com.uc.week4retrofit.retrofit

import com.uc.week4retrofit.model.Genre
import com.uc.week4retrofit.model.MovieDetails
import com.uc.week4retrofit.model.NowPlaying
import com.uc.week4retrofit.view.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EndPointApi {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<NowPlaying>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")id:Int,
        @Query("api_key")apiKey: String
    ):Response<MovieDetails>


}