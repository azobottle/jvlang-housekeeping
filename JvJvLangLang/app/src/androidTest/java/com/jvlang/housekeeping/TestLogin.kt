package com.jvlang.housekeeping

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.jvlang.housekeeping.data.network.Login
import com.jvlang.housekeeping.data.network.LoginResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.jvlang.housekeeping.data.network.LoginService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@LargeTest
class MyRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // 在此处注入需要测试的 Repository 实例
    @Inject
    lateinit var loginService: LoginService

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testRepositoryMethod() {
        // 进行 Repository 方法的测试
        // 例如，调用 Repository 中的方法并验证其行为
//        val response = loginService.login(Login("devadmin","devadmin1145141919810"))
        val call = loginService.login(Login("devadmin", "devadmin1145141919810"))
        val execute = call.execute()
        val get = execute.headers().get("x-jvlang-set-auth")
        assert(get != null)
    }
}

