package com.example.netlib.domain.repository

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

        //cities

        suspend fun getCities(filter: String?): NetworkResult<CitiesListResponse>

        //applicant_statuses

        suspend fun getAppsStatus(filter: String?): NetworkResult<ApplicantsStatusesListResponse>

        //candidate_statuses
        suspend fun getCandidatesStatus(filter: String?): NetworkResult<CandidateStatusesListResponse>


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

        //candidate_cards
        suspend fun getCandidateCards(filter: String?): NetworkResult<CandidateCardsListResponse>

        suspend fun postCandidateCards(data: CandidateCardsCreate): NetworkResult<CandidateCardsRecord>

        suspend fun getCandidateCard(id: String): NetworkResult<CandidateCardsRecord>

        suspend fun patchCandidateCards(
            id: String,
            data: CandidateCardsUpdate
        ): NetworkResult<CandidateCardsRecord>


        //candidate_cards

        suspend fun getCandidateCardsCom(filter: String?): NetworkResult<CandidateCardCommentsListResponse>
        suspend fun postCandidateCardsCom(data: CandidateCardCommentsCreate): NetworkResult<CandidateCardCommentsRecord>
         suspend fun getCandidateCardCom(id: String): NetworkResult<CandidateCardCommentsRecord>
         suspend fun patchCandidateCardsCom(
            id: String,
            data: CandidateCardCommentsUpdate
        ): NetworkResult<CandidateCardCommentsRecord>

        //user

        suspend fun getUsers(filter: String?): NetworkResult<UsersListResponse>

        suspend fun postUsers(data: UsersCreate): NetworkResult<UsersRecord>

        suspend fun getUser(id: String): NetworkResult<UsersRecord>

        suspend fun patchUsers(id: String, data: UsersUpdate): NetworkResult<UsersRecord>

        //auth

        suspend fun authPassword(data: AuthWithPasswordRequest): NetworkResult<UserAuthResponse>
}