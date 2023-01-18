package com.theo.kanyequotes

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface kanyeApi {

    @GET(".")
    fun getQoute() : Call<quote>

}