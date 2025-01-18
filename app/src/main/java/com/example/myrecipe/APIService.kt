package com.example.myrecipe

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



private val retrofit= Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create()).build()
//builds connections to Url


val recipeService= retrofit.create(APIService::class.java)
//creates the service
//we want to have this service that allows us to have categories files


// Overall it gets data from the API


interface APIService{
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse
//getting response and putting in the CategoriesResponse
}