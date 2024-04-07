package com.jvlang.housekeeping.data

import com.jvlang.housekeeping.data.network.LoginService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val loginService: LoginService
) {

    suspend fun login(userName: String, password: String) {
        // TODO 写进本地存储，更新viewmodel状态
    }
}