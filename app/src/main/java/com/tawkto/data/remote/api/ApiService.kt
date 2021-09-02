package com.greenlight.data.remote.api

import com.tawkto.data.local.entities.UserDetails
import com.tawkto.data.remote.models.GithubProjectListDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUsersResponse(@Query("since") count: Int): Call<GithubProjectListDataResponse>

    @GET("users/{login}")
    fun getUserDetailsResponse(@Path("login") login: String): Call<UserDetails>

}