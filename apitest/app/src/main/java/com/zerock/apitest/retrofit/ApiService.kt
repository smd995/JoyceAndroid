package com.zerock.apitest.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/api/users")
    fun getUsers(): Call<List<User>>

    @POST("/api/users")
    fun addUser(@Body user: User): Call<User>

    @DELETE("/api/users/{id}")
    fun deleteUser(@Path("id") id: Long): Call<Void>

    @PUT("/api/users/{id}")
    fun updateUser(@Path("id") id: Long, @Body user: User): Call<User>
}