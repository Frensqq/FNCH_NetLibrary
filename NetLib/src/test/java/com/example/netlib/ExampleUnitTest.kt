package com.example.netlib

import com.example.netlib.data.remote.PBApi
import com.example.netlib.data.remote.PBApiService
import com.example.netlib.data.repository.RepositoryImpl
import com.example.netlib.domain.model.User.UsersCreate
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun registrationUser() = runBlocking{

        val api: PBApi = PBApiService.instance

        val user = api.postUsers(
            data =
                UsersCreate(
                    "test@mail.com",
                    true,
                    verified = false,
                    firstName = "FirstTest",
                    lastName = "FirstTest",
                    patronymic = "FirstTest",
                    phone = "+78887776655",
                    department = " ",
                    position = " ",
                    role =" ",
                    password = "12345678",
                    confirmPassword = "12345678"
                    )
        )
    }
}