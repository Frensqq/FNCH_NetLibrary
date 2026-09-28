package com.example.netlib.data.remote

import com.example.netlib.domain.model.ApplicantStatuses.ApplicantsStatusesListResponse
import com.example.netlib.domain.model.Applicants.ApplicantsCreate
import com.example.netlib.domain.model.Applicants.ApplicantsListResponse
import com.example.netlib.domain.model.Applicants.ApplicantsRecord
import com.example.netlib.domain.model.Applicants.ApplicantsUpdate
import com.example.netlib.domain.model.Auth.AuthWithPasswordRequest
import com.example.netlib.domain.model.Auth.UserAuthResponse
import com.example.netlib.domain.model.CandidateCards.CandidateCardsCreate
import com.example.netlib.domain.model.CandidateCards.CandidateCardsListResponse
import com.example.netlib.domain.model.CandidateCards.CandidateCardsRecord
import com.example.netlib.domain.model.CandidateCards.CandidateCardsUpdate
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsCreate
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsListResponse
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsRecord
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsUpdate
import com.example.netlib.domain.model.CandidateStatuses.CandidateStatusesListResponse
import com.example.netlib.domain.model.Cities.CitiesListResponse
import com.example.netlib.domain.model.Department.DepartmentsListResponse
import com.example.netlib.domain.model.Position.PositionsListResponse
import com.example.netlib.domain.model.UploadFile
import com.example.netlib.domain.model.User.UsersCreate
import com.example.netlib.domain.model.User.UsersListResponse
import com.example.netlib.domain.model.User.UsersRecord
import com.example.netlib.domain.model.User.UsersUpdate
import com.example.netlib.domain.model.Vacancies.VacanciesCreate
import com.example.netlib.domain.model.Vacancies.VacanciesListResponse
import com.example.netlib.domain.model.Vacancies.VacanciesRecord
import com.example.netlib.domain.model.Vacancies.VacanciesUpdate
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

class PBApi(
    private val client: HttpClient,
) {

    private val json = Json {
        encodeDefaults = true
        explicitNulls = false
    }

    private suspend inline fun <reified T> get(
        path: String,
        filter: String? = null
    ): T = client.get(path) {
        filter?.let { parameter("filter", it) }
    }.body()

    private suspend inline fun <reified T> post(
        path: String,
        data: Any
    ): T = client.post(path) {
        contentType(ContentType.Application.Json)
        setBody(data)
    }.body()

    private suspend inline fun <reified T> patch(
        path: String,
        data: Any
    ): T = client.patch(path) {
        contentType(ContentType.Application.Json)
        setBody(data)
    }.body()

    private suspend fun delete(path: String) {
        client.delete(path)
    }

    private fun files(
        vararg values: Pair<String, UploadFile?>
    ) = values
        .filter { it.second != null }
        .associate { it.first to listOf(it.second!!) }


    private inline fun <reified D> fields(data: D): Map<String, String> {
        val objectData = json.encodeToJsonElement(data).jsonObject

        return objectData.mapNotNull { (key, value) ->
            if (value == JsonNull) {
                null
            } else {
                key to if (value is JsonPrimitive) value.content else value.toString()
            }
        }.toMap()
    }

    private suspend inline fun <reified T, reified D> multipart(
        path: String,
        data: D,
        files: Map<String, List<UploadFile>>,
        patch: Boolean = false
    ): T {

        val body = MultiPartFormDataContent(
            formData {
                fields(data).forEach { (k, v) -> append(k, v) }

                files.forEach { (field, list) ->
                    list.forEach { file ->
                        append(
                            field,
                            file.bytes,
                            Headers.build {
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "filename=\"${file.name}\""
                                )
                                append(HttpHeaders.ContentType, file.mimeType)
                            }
                        )
                    }
                }
            }
        )
        return if (patch)
            client.patch(path) { setBody(body) }.body()
        else
            client.post(path) { setBody(body) }.body()
    }

    // dictionaries
    suspend fun getDepartments(filter: String?): DepartmentsListResponse =
        get("collections/departments/records", filter)

    suspend fun getCities(filter: String?): CitiesListResponse =
        get("collections/cities/records", filter)

    suspend fun getAppsStatus(filter: String?): ApplicantsStatusesListResponse =
        get("collections/applicant_statuses/records", filter)

    suspend fun getCandidatesStatus(filter: String?): CandidateStatusesListResponse =
        get("collections/candidate_statuses/records", filter)

    suspend fun getPositions(filter: String?): PositionsListResponse =
        get("collections/positions/records", filter)

    // vacancies
    suspend fun getVacancies(filter: String?): VacanciesListResponse =
        get("collections/vacancies/records", filter)

    suspend fun postVacancies(
        data: VacanciesCreate,
        files: List<UploadFile> = emptyList()
    ): VacanciesRecord =
        if (files.isEmpty())
            post("collections/vacancies/records", data)
        else
            multipart(
                "collections/vacancies/records",
                data,
                mapOf("files" to files)
            )

    suspend fun getVacancy(id: String): VacanciesRecord =
        get("collections/vacancies/records/$id")

    suspend fun patchVacancies(
        id: String,
        data: VacanciesUpdate,
        files: List<UploadFile> = emptyList()
    ): VacanciesRecord =
        if (files.isEmpty())
            patch("collections/vacancies/records/$id", data)
        else
            multipart(
                "collections/vacancies/records/$id",
                data,
                mapOf("files+" to files),
                patch = true
            )

    suspend fun deleteVacancies(id: String) {
        delete("collections/vacancies/records/$id")
    }

    // applicants
    suspend fun getApplicants(filter: String?): ApplicantsListResponse =
        get("collections/applicants/records", filter)

    suspend fun postApplicants(
        data: ApplicantsCreate,
        avatar: UploadFile? = null,
        resume: UploadFile? = null
    ): ApplicantsRecord {

        if (avatar == null && resume == null)
            return post("collections/applicants/records", data)

        return multipart(
            "collections/applicants/records",
            data,
            files(
                "avatar" to avatar,
                "resume" to resume
            )
        )
    }

    suspend fun getApplicant(id: String): ApplicantsRecord =
        get("collections/applicants/records/$id")

    suspend fun patchApplicants(
        id: String,
        data: ApplicantsUpdate,
        avatar: UploadFile? = null,
        resume: UploadFile? = null
    ): ApplicantsRecord {

        if (avatar == null && resume == null)
            return patch("collections/applicants/records/$id", data)

        return multipart(
            "collections/applicants/records/$id",
            data,
            files(
                "avatar" to avatar,
                "resume" to resume
            ),
            patch = true
        )
    }

    // candidate cards
    suspend fun getCandidateCards(filter: String?): CandidateCardsListResponse =
        get("collections/candidate_cards/records", filter)

    suspend fun postCandidateCards(data: CandidateCardsCreate): CandidateCardsRecord =
        post("collections/candidate_cards/records", data)

    suspend fun getCandidateCard(id: String): CandidateCardsRecord =
        get("collections/candidate_cards/records/$id")

    suspend fun patchCandidateCards(
        id: String,
        data: CandidateCardsUpdate
    ): CandidateCardsRecord =
        patch("collections/candidate_cards/records/$id", data)

    // candidate card comments
    suspend fun getCandidateCardsCom(filter: String?): CandidateCardCommentsListResponse =
        get("collections/candidate_card_comments/records", filter)

    suspend fun postCandidateCardsCom(data: CandidateCardCommentsCreate): CandidateCardCommentsRecord =
        post("collections/candidate_card_comments/records", data)

    suspend fun getCandidateCardCom(id: String): CandidateCardCommentsRecord =
        get("collections/candidate_card_comments/records/$id")

    suspend fun patchCandidateCardsCom(
        id: String,
        data: CandidateCardCommentsUpdate
    ): CandidateCardCommentsRecord =
        patch("collections/candidate_card_comments/records/$id", data)

    // users
    suspend fun getUsers(filter: String?): UsersListResponse =
        get("collections/users/records", filter)

    suspend fun postUsers(
        data: UsersCreate,
        avatar: UploadFile? = null
    ): UsersRecord {
        if (avatar == null) {
            return post("collections/users/records", data)
        }

        return multipart(
            path = "collections/users/records",
            data = data,
            files = mapOf("avatar" to listOf(avatar))
        )
    }

    suspend fun getUser(id: String): UsersRecord =
        get("collections/users/records/$id")

    suspend fun patchUsers(
        id: String,
        data: UsersUpdate,
        avatar: UploadFile? = null
    ): UsersRecord {
        if (avatar == null) {
            return patch("collections/users/records/$id", data)
        }

        return multipart(
            path = "collections/users/records/$id",
            data = data,
            files = mapOf("avatar" to listOf(avatar)),
            patch = true
        )
    }

    // auth
    suspend fun authPassword(data: AuthWithPasswordRequest): UserAuthResponse =
        post("collections/users/auth-with-password", data)
}
