package com.example.netlib.domain.repository

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
import com.example.netlib.domain.model.NetworkResult
import com.example.netlib.domain.model.User.UsersCreate
import com.example.netlib.domain.model.User.UsersListResponse
import com.example.netlib.domain.model.User.UsersRecord
import com.example.netlib.domain.model.User.UsersUpdate
import com.example.netlib.domain.model.Vacancies.VacanciesCreate
import com.example.netlib.domain.model.Vacancies.VacanciesListResponse
import com.example.netlib.domain.model.Vacancies.VacanciesRecord
import com.example.netlib.domain.model.Vacancies.VacanciesUpdate

interface Repository {

        //department
        suspend fun getDepartments(filter: String?): NetworkResult<DepartmentsListResponse>

        suspend fun postDepartments(data: DepartmentsCreate): NetworkResult<DepartmentsRecord>

        suspend fun getDepartment(id: String): NetworkResult<DepartmentsRecord>


        suspend fun patchDepartments(id: String, data: DepartmentsUpdate): NetworkResult<DepartmentsRecord>

        suspend fun deleteDepartments(id: String): NetworkResult<Unit>

        //cities

        suspend fun getCities(filter: String?): NetworkResult<CitiesListResponse>

        suspend fun postCities(data: CitiesCreate): NetworkResult<CitiesRecord>

        suspend fun getCity(id: String): NetworkResult<CitiesRecord>

        suspend fun patchCities(id: String, data: CitiesUpdate): NetworkResult<CitiesRecord>

        suspend fun deleteCities(id: String): NetworkResult<Unit>

        //applicant_statuses

        suspend fun getAppsStatus(filter: String?): NetworkResult<ApplicantsStatusesListResponse>

        suspend fun postAppStatus(data: ApplicantStatusesCreate): NetworkResult<ApplicantStatusesRecord>

        suspend fun getAppStatus(id: String): NetworkResult<ApplicantStatusesRecord>

        suspend fun patchAppStatus(
            id: String,
            data: ApplicantStatusesUpdate
        ): NetworkResult<ApplicantStatusesRecord>

        suspend fun deleteAppStatus(id: String): NetworkResult<Unit>


        //candidate_statuses

        suspend fun getCandidatesStatus(filter: String?): NetworkResult<CandidateStatusesListResponse>

        suspend fun postCandidateStatus(data: CandidateStatusesCreate): NetworkResult<CandidateStatusesRecord>

        suspend fun getCandidateStatus(id: String): NetworkResult<CandidateStatusesRecord>

        suspend fun patchCandidateStatus(
            id: String,
            data: CandidateStatusesUpdate
        ): NetworkResult<CandidateStatusesRecord>

        suspend fun deleteCandidateStatus(id: String): NetworkResult<Unit>

        //vacancies

        suspend fun getVacancies(filter: String?): NetworkResult<VacanciesListResponse>

        suspend fun postVacancies(data: VacanciesCreate): NetworkResult<VacanciesRecord>

        suspend fun getVacancy(id: String): NetworkResult<VacanciesRecord>

        suspend fun patchVacancies(id: String, data: VacanciesUpdate): NetworkResult<VacanciesRecord>

        suspend fun deleteVacancies(id: String) : NetworkResult<Unit>

        //applicants

        suspend fun getApplicants(filter: String?): NetworkResult<ApplicantsListResponse>

        suspend fun postApplicants(data: ApplicantsCreate): NetworkResult<ApplicantsRecord>

        suspend fun getApplicant(id: String): NetworkResult<ApplicantsRecord>

        suspend fun patchApplicants(id: String, data: ApplicantsUpdate): NetworkResult<ApplicantsRecord>

        suspend fun deleteApplicants(id: String): NetworkResult<Unit>

        //candidate_cards

        suspend fun getCandidateCards(filter: String?): NetworkResult<CandidateCardsListResponse>

        suspend fun postCandidateCards(data: CandidateCardsCreate): NetworkResult<CandidateCardsRecord>

        suspend fun getCandidateCard(id: String): NetworkResult<CandidateCardsRecord>

        suspend fun patchCandidateCards(
            id: String,
            data: CandidateCardsUpdate
        ): NetworkResult<CandidateCardsRecord>

        suspend fun deleteCandidateCards(id: String): NetworkResult<Unit>

        //candidate_cards

        suspend fun getCandidateCardsCom(filter: String?): NetworkResult<CandidateCardCommentsListResponse>

        suspend fun postCandidateCardsCom(data: CandidateCardCommentsCreate): NetworkResult<CandidateCardCommentsRecord>

         suspend fun getCandidateCardCom(id: String): NetworkResult<CandidateCardCommentsRecord>

         suspend fun patchCandidateCardsCom(
            id: String,
            data: CandidateCardCommentsUpdate
        ): NetworkResult<CandidateCardCommentsRecord>

         suspend fun deleteCandidateCardsCom(id: String): NetworkResult<Unit>

        //user

        suspend fun getUsers(filter: String?): NetworkResult<UsersListResponse>

        suspend fun postUsers(data: UsersCreate): NetworkResult<UsersRecord>

        suspend fun getUser(id: String): NetworkResult<UsersRecord>

        suspend fun patchUsers(id: String, data: UsersUpdate): NetworkResult<UsersRecord>

        suspend fun deleteUsers(id: String) : NetworkResult<Unit>

        //auth

        suspend fun authPassword(data: AuthWithPasswordRequest): NetworkResult<UserAuthResponse>


}