package com.example.netlib.data.remote

import com.example.netlib.domain.model.ApplicantStatuses.ApplicantStatusesCreate
import com.example.netlib.domain.model.ApplicantStatuses.ApplicantStatusesRecord
import com.example.netlib.domain.model.ApplicantStatuses.ApplicantStatusesUpdate
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
import com.example.netlib.domain.model.CandidateStatuses.CandidateStatusesCreate
import com.example.netlib.domain.model.CandidateStatuses.CandidateStatusesListResponse
import com.example.netlib.domain.model.CandidateStatuses.CandidateStatusesRecord
import com.example.netlib.domain.model.CandidateStatuses.CandidateStatusesUpdate
import com.example.netlib.domain.model.Cities.CitiesCreate
import com.example.netlib.domain.model.Cities.CitiesListResponse
import com.example.netlib.domain.model.Cities.CitiesRecord
import com.example.netlib.domain.model.Cities.CitiesUpdate
import com.example.netlib.domain.model.Department.DepartmentsCreate
import com.example.netlib.domain.model.Department.DepartmentsListResponse
import com.example.netlib.domain.model.Department.DepartmentsRecord
import com.example.netlib.domain.model.Department.DepartmentsUpdate
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
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PBApi(
     val client: HttpClient,
) {

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


    //department
     suspend fun getDepartments(filter: String?): DepartmentsListResponse =
        get("collections/departments/records", filter)

     suspend fun postDepartments(data: DepartmentsCreate): DepartmentsRecord =
        post("collections/departments/records", data)

     suspend fun getDepartment(id: String): DepartmentsRecord =
        get("collections/departments/records/$id")


     suspend fun patchDepartments(id: String, data: DepartmentsUpdate): DepartmentsRecord =
        patch("collections/departments/records/$id", data)

     suspend fun deleteDepartments(id: String) {
        client.delete("collections/departments/records/$id")
    }

    //cities

     suspend fun getCities(filter: String?): CitiesListResponse =
        get("collections/cities/records", filter)

     suspend fun postCities(data: CitiesCreate): CitiesRecord =
        post("collections/cities/records", data)

     suspend fun getCity(id: String): CitiesRecord =
        get("collections/cities/records/$id")

     suspend fun patchCities(id: String, data: CitiesUpdate): CitiesRecord =
        patch("collections/cities/records/$id", data)

     suspend fun deleteCities(id: String) {
        client.delete("collections/cities/records/$id")
    }

    //applicant_statuses

    suspend fun getAppsStatus(filter: String?): ApplicantsStatusesListResponse =
        get("collections/applicant_statuses/records", filter)

     suspend fun postAppStatus(data: ApplicantStatusesCreate): ApplicantStatusesRecord =
        post("collections/applicant_statuses/records", data)

     suspend fun getAppStatus(id: String): ApplicantStatusesRecord =
        get("collections/applicant_statuses/records/$id")

     suspend fun patchAppStatus(
        id: String,
        data: ApplicantStatusesUpdate
    ): ApplicantStatusesRecord =
        patch("collections/applicant_statuses/records/$id", data)

     suspend fun deleteAppStatus(id: String) {
        client.delete("collections/applicant_statuses/records/$id")
    }


    //candidate_statuses

     suspend fun getCandidatesStatus(filter: String?): CandidateStatusesListResponse =
        get("collections/candidate_statuses/records", filter)

     suspend fun postCandidateStatus(data: CandidateStatusesCreate): CandidateStatusesRecord =
        post("collections/candidate_statuses/records", data)

     suspend fun getCandidateStatus(id: String): CandidateStatusesRecord =
        get("collections/candidate_statuses/records/$id")

     suspend fun patchCandidateStatus(
        id: String,
        data: CandidateStatusesUpdate
    ): CandidateStatusesRecord =
        patch("collections/candidate_statuses/records/$id", data)

     suspend fun deleteCandidateStatus(id: String) {
        client.delete("collections/candidate_statuses/records/$id")
    }

    //vacancies

     suspend fun getVacancies(filter: String?): VacanciesListResponse =
        get("collections/vacancies/records", filter)

     suspend fun postVacancies(data: VacanciesCreate): VacanciesRecord =
        post("collections/vacancies/records", data)

     suspend fun getVacancy(id: String): VacanciesRecord =
        get("collections/vacancies/records/$id")

     suspend fun patchVacancies(id: String, data: VacanciesUpdate): VacanciesRecord =
        patch("collections/vacancies/records/$id", data)

     suspend fun deleteVacancies(id: String) {
        client.delete("collections/vacancies/records/$id")
    }

    //applicants

     suspend fun getApplicants(filter: String?): ApplicantsListResponse =
        get("collections/applicants/records", filter)

     suspend fun postApplicants(data: ApplicantsCreate): ApplicantsRecord =
        post("collections/applicants/records", data)

     suspend fun getApplicant(id: String): ApplicantsRecord =
        get("collections/applicants/records/$id")

     suspend fun patchApplicants(id: String, data: ApplicantsUpdate): ApplicantsRecord =
        patch("collections/applicants/records/$id", data)

     suspend fun deleteApplicants(id: String) {
        client.delete("collections/applicants/records/$id")
    }

    //candidate_cards

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

     suspend fun deleteCandidateCards(id: String) {
        client.delete("collections/candidate_cards/records/$id")
    }

    //candidate_cards

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

     suspend fun deleteCandidateCardsCom(id: String) {
        client.delete("collections/candidate_card_comments/records/$id")
    }

    //user

     suspend fun getUsers(filter: String?): UsersListResponse =
        get("collections/users/records", filter)

     suspend fun postUsers(data: UsersCreate): UsersRecord =
        post("collections/users/records", data)

     suspend fun getUser(id: String): UsersRecord =
        get("collections/users/records/$id")

     suspend fun patchUsers(id: String, data: UsersUpdate): UsersRecord =
        patch("collections/users/records/$id", data)

     suspend fun deleteUsers(id: String) {
        client.delete("collections/users/records/$id")
    }

    //auth
     suspend fun authPassword(data: AuthWithPasswordRequest): UserAuthResponse =
        post("collections/users/records", data)

}