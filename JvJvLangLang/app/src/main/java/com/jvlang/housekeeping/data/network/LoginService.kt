package com.jvlang.housekeeping.data.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginService {
    @POST("/api/jvlang/login")
    fun login(
        @Body body: Login
    ): Call<JsonObject>
}
data class Login(val username: String, val password: String)

data class LoginResponse(val token: String)
