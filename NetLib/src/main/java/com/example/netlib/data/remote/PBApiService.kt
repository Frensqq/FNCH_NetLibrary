package com.example.netlib.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object PBApiService {

    const val BASE_URL = "http://frens-project.tech:8099/api/"

    var token: String? = null

    val instance: PBApi by lazy {
        PBApi(
            client = HttpClient(OkHttp){
                expectSuccess = true

                install(ContentNegotiation){
                    json(
                        Json{
                            ignoreUnknownKeys = true
                            isLenient = true
                            encodeDefaults = true
                        }
                    )
                }

                install(Logging){
                    level = LogLevel.ALL
                }

                defaultRequest {
                    url(BASE_URL)
                    contentType(ContentType.Application.Json)

                    token?.let {
                        header("Authorization", it)
                    }
                }

            }
        )
    }

}