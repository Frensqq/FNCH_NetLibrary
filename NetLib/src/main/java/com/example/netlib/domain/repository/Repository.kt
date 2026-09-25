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
        suspend fun getDepartments(filter: String?): DepartmentsListResponse

        suspend fun postDepartments(data: DepartmentsCreate): DepartmentsRecord

        suspend fun getDepartment(id: String): DepartmentsRecord


        suspend fun patchDepartments(id: String, data: DepartmentsUpdate): DepartmentsRecord

        suspend fun deleteDepartments(id: String)

        //cities

        suspend fun getCities(filter: String?): CitiesListResponse

        suspend fun postCities(data: CitiesCreate): CitiesRecord

        suspend fun getCity(id: String): CitiesRecord

        suspend fun patchCities(id: String, data: CitiesUpdate): CitiesRecord

        suspend fun deleteCities(id: String)

        //applicant_statuses

        suspend fun getAppsStatus(filter: String?): ApplicantsStatusesListResponse

        suspend fun postAppStatus(data: ApplicantStatusesCreate): ApplicantStatusesRecord

        suspend fun getAppStatus(id: String): ApplicantStatusesRecord

        suspend fun patchAppStatus(
            id: String,
            data: ApplicantStatusesUpdate
        ): ApplicantStatusesRecord

        suspend fun deleteAppStatus(id: String)


        //candidate_statuses

        suspend fun getCandidatesStatus(filter: String?): CandidateStatusesListResponse

        suspend fun postCandidateStatus(data: CandidateStatusesCreate): CandidateStatusesRecord

        suspend fun getCandidateStatus(id: String): CandidateStatusesRecord

        suspend fun patchCandidateStatus(
            id: String,
            data: CandidateStatusesUpdate
        ): CandidateStatusesRecord

        suspend fun deleteCandidateStatus(id: String)

        //vacancies

        suspend fun getVacancies(filter: String?): VacanciesListResponse

        suspend fun postVacancies(data: VacanciesCreate): VacanciesRecord

        suspend fun getVacancy(id: String): VacanciesRecord

        suspend fun patchVacancies(id: String, data: VacanciesUpdate): VacanciesRecord

        suspend fun deleteVacancies(id: String)

        //applicants

        suspend fun getApplicants(filter: String?): ApplicantsListResponse

        suspend fun postApplicants(data: ApplicantsCreate): ApplicantsRecord

        suspend fun getApplicant(id: String): ApplicantsRecord

        suspend fun patchApplicants(id: String, data: ApplicantsUpdate): ApplicantsRecord

        suspend fun deleteApplicants(id: String)

        //candidate_cards

        suspend fun getCandidateCards(filter: String?): CandidateCardsListResponse

        suspend fun postCandidateCards(data: CandidateCardsCreate): CandidateCardsRecord

        suspend fun getCandidateCard(id: String): CandidateCardsRecord

        suspend fun patchCandidateCards(
            id: String,
            data: CandidateCardsUpdate
        ): CandidateCardsRecord

        suspend fun deleteCandidateCards(id: String)

        //candidate_cards

        suspend fun getCandidateCardsCom(filter: String?): CandidateCardCommentsListResponse

        suspend fun postCandidateCardsCom(data: CandidateCardCommentsCreate): CandidateCardCommentsRecord

         suspend fun getCandidateCardCom(id: String): CandidateCardCommentsRecord

         suspend fun patchCandidateCardsCom(
            id: String,
            data: CandidateCardCommentsUpdate
        ): CandidateCardCommentsRecord

         suspend fun deleteCandidateCardsCom(id: String)

        //user

        suspend fun getUsers(filter: String?): UsersListResponse

        suspend fun postUsers(data: UsersCreate): UsersRecord

        suspend fun getUser(id: String): UsersRecord

        suspend fun patchUsers(id: String, data: UsersUpdate): UsersRecord

        suspend fun deleteUsers(id: String)

        //auth

        suspend fun authPassword(data: AuthWithPasswordRequest): UserAuthResponse


}