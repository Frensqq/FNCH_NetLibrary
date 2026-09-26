package com.example.netlib

import com.example.netlib.data.remote.PBApi
import com.example.netlib.data.remote.PBApiService
import com.example.netlib.data.repository.RepositoryImpl
import com.example.netlib.domain.model.Department.DepartmentsListResponse
import com.example.netlib.domain.model.User.UsersCreate
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val api: PBApi = PBApiService.instance



    @Test
    fun getDepartments() = runBlocking {
        val departments = api.getDepartments(null)

        Assert.assertEquals(5, departments.totalItems)
        Assert.assertNotEquals(10, departments.totalItems)
    }

   

    @Test
    fun registrationUser() = runBlocking{


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
                    role ="admin",
                    password = "12345678",
                    passwordConfirm = "12345678"
                    )
        )
    }
}