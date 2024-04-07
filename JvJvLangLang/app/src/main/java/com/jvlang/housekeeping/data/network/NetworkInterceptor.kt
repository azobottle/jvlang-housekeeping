package com.jvlang.housekeeping.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // TODO 从本地存储带上token
        val response = chain.proceed(request)

        // 在这里可以访问 response 的 header 信息
        val headers = response.headers

        // 可以在这里做进一步处理，例如打印 header 信息等


        return response
    }
}
