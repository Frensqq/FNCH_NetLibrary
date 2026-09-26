package com.example.netlib.data.repository

import android.content.Context
import com.example.netlib.data.remote.PBApi
import com.example.netlib.domain.model.ApplicantStatuses.ApplicantStatusesCreate
import com.example.netlib.domain.model.ApplicantStatuses.ApplicantStatusesUpdate
import com.example.netlib.domain.model.Applicants.ApplicantsCreate
import com.example.netlib.domain.model.Applicants.ApplicantsUpdate
import com.example.netlib.domain.model.Auth.AuthWithPasswordRequest
import com.example.netlib.domain.model.Auth.ErrorResponse
import com.example.netlib.domain.model.CandidateCards.CandidateCardsCreate
import com.example.netlib.domain.model.CandidateCards.CandidateCardsUpdate
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsCreate
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsUpdate
import com.example.netlib.domain.model.CandidateStatuses.CandidateStatusesCreate
import com.example.netlib.domain.model.CandidateStatuses.CandidateStatusesUpdate
import com.example.netlib.domain.model.Cities.CitiesCreate
import com.example.netlib.domain.model.Cities.CitiesUpdate
import com.example.netlib.domain.model.Department.DepartmentsCreate
import com.example.netlib.domain.model.Department.DepartmentsUpdate
import com.example.netlib.domain.model.NetworkResult
import com.example.netlib.domain.model.User.UsersCreate
import com.example.netlib.domain.model.User.UsersUpdate
import com.example.netlib.domain.model.Vacancies.VacanciesCreate
import com.example.netlib.domain.model.Vacancies.VacanciesUpdate
import com.example.netlib.domain.network.NetworkMonitor
import com.example.netlib.domain.repository.Repository
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.utils.io.errors.IOException

class RepositoryImpl(
    private val api: PBApi,
    private val networkMonitor: NetworkMonitor,
    private val context: Context,
    ): Repository {

    private suspend fun <T> safeApiCall(apiCall : suspend () ->T): NetworkResult<T>{
        if(!networkMonitor.isConnected()){
            return NetworkResult.NoInternet
        }

        return try{
            NetworkResult.Success(apiCall())
        }catch (e: IOException){
            NetworkResult.NoInternet
        }catch (e: ResponseException){
            NetworkResult.Error(
                errorResponse = ErrorResponse(
                    status = e.response.status.value,
                    message = e.message.toString(),
                    data = mapOf("raw" to runCatching{
                        e.response.body<String>()
                    }.getOrDefault(""))
                )
            )
        }catch (e: Exception){
            NetworkResult.Error(
                errorResponse = ErrorResponse(
                    status = -1,
                    message = e.message ?: "Unknown error"
                )
            )
        }
    }

    override suspend fun getDepartments(filter: String?) =
      safeApiCall {api.getDepartments(filter)}


    override suspend fun postDepartments(data: DepartmentsCreate) =
      safeApiCall {api.postDepartments(data)}

    override suspend fun getDepartment(id: String) =
      safeApiCall {api.getDepartment(id)}

    override suspend fun patchDepartments(
        id: String,
        data: DepartmentsUpdate
    ) =
      safeApiCall {api.patchDepartments(
      id,data
        )}

    override suspend fun deleteDepartments(id: String) =
      safeApiCall {api.deleteDepartments(id)}

    override suspend fun getCities(filter: String?) =
      safeApiCall {api.getCities(filter)}

    override suspend fun postCities(data: CitiesCreate) =
      safeApiCall {api.postCities(data)}

    override suspend fun getCity(id: String) =
      safeApiCall {api.getCity(id)}

    override suspend fun patchCities(
        id: String,
        data: CitiesUpdate
    ) =
      safeApiCall {api.patchCities(
            id,
            data
        )}

    override suspend fun deleteCities(id: String) =
      safeApiCall {api.deleteCities(id)}

    override suspend fun getAppsStatus(filter: String?) =
      safeApiCall {api.getAppsStatus(filter)}

    override suspend fun postAppStatus(data: ApplicantStatusesCreate) =
      safeApiCall {api.postAppStatus(data)}

    override suspend fun getAppStatus(id: String) =
      safeApiCall {api.getAppStatus(id)}

    override suspend fun patchAppStatus(
        id: String,
        data: ApplicantStatusesUpdate
    ) =
      safeApiCall {api.patchAppStatus(
      id, data
        )}

    override suspend fun deleteAppStatus(id: String) =
      safeApiCall {api.deleteAppStatus(id)}

    override suspend fun getCandidatesStatus(filter: String?) =
      safeApiCall {api.getCandidatesStatus(filter)}

    override suspend fun postCandidateStatus(data: CandidateStatusesCreate) =
      safeApiCall {api.postCandidateStatus(data)}

    override suspend fun getCandidateStatus(id: String) =
      safeApiCall {api.getCandidateStatus(id)}

    override suspend fun patchCandidateStatus(
        id: String,
        data: CandidateStatusesUpdate
    ) =
      safeApiCall {api.patchCandidateStatus(id, data)}

    override suspend fun deleteCandidateStatus(id: String) =
      safeApiCall {api.deleteCandidateStatus(id)}

    override suspend fun getVacancies(filter: String?) =
      safeApiCall {api.getVacancies(filter)}


    override suspend fun postVacancies(data: VacanciesCreate) =
      safeApiCall {api.postVacancies(data)}

    override suspend fun getVacancy(id: String) = safeApiCall { api.getVacancy(id)}

    override suspend fun patchVacancies(
        id: String,
        data: VacanciesUpdate
    ) = safeApiCall { api.patchVacancies(id,data)}

    override suspend fun deleteVacancies(id: String) = safeApiCall { api.deleteVacancies(id)}

    override suspend fun getApplicants(filter: String?) = safeApiCall { api.getApplicants(filter)}

    override suspend fun postApplicants(data: ApplicantsCreate) = safeApiCall { api.postApplicants(data)}

    override suspend fun getApplicant(id: String) = safeApiCall { api.getApplicant(id)}

    override suspend fun patchApplicants(
        id: String,
        data: ApplicantsUpdate
    ) = safeApiCall { api.patchApplicants(id,data)}

    override suspend fun deleteApplicants(id: String) = safeApiCall { api.deleteApplicants(id)}

    override suspend fun getCandidateCards(filter: String?) = safeApiCall { api.getCandidateCards(filter)}

    override suspend fun postCandidateCards(data: CandidateCardsCreate) = safeApiCall { api.postCandidateCards(data)}

    override suspend fun getCandidateCard(id: String) = safeApiCall { api.getCandidateCard(id)}

    override suspend fun patchCandidateCards(
        id: String,
        data: CandidateCardsUpdate
    ) = safeApiCall { api.patchCandidateCards(id,data)}

    override suspend fun deleteCandidateCards(id: String) = safeApiCall { api.deleteCandidateCards(id)}

    override suspend fun getCandidateCardsCom(filter: String?) = safeApiCall { api.getCandidateCardsCom(filter)}

    override suspend fun postCandidateCardsCom(data: CandidateCardCommentsCreate) = safeApiCall { api.postCandidateCardsCom(data)}

    override suspend fun getCandidateCardCom(id: String) = safeApiCall { api.getCandidateCardCom(id)}

    override suspend fun patchCandidateCardsCom(
        id: String,
        data: CandidateCardCommentsUpdate
    ) = safeApiCall { api.patchCandidateCardsCom(id,data)}

    override suspend fun deleteCandidateCardsCom(id: String) = safeApiCall { api.deleteCandidateCardsCom(id)}

    override suspend fun getUsers(filter: String?) = safeApiCall { api.getUsers(filter)}

    override suspend fun postUsers(data: UsersCreate) = safeApiCall { api.postUsers(data)}

    override suspend fun getUser(id: String) = safeApiCall { api.getUser(id)}

    override suspend fun patchUsers(
        id: String,
        data: UsersUpdate
    ) = safeApiCall { api.patchUsers(id,data)}

    override suspend fun deleteUsers(id: String) = safeApiCall { api.deleteUsers(id)}

    override suspend fun authPassword(data: AuthWithPasswordRequest) = safeApiCall { api.authPassword(data)}


}
