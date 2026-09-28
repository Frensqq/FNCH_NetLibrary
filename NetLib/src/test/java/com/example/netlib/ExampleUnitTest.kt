package com.example.netlib

import android.telephony.CellIdentity
import com.example.netlib.data.remote.PBApi
import com.example.netlib.data.remote.PBApiService
import com.example.netlib.data.repository.RepositoryImpl
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
import com.example.netlib.domain.model.Department.DepartmentsListResponse
import com.example.netlib.domain.model.NetworkResult
import com.example.netlib.domain.model.User.UsersCreate
import com.example.netlib.domain.model.User.UsersRecord
import com.example.netlib.domain.model.User.UsersUpdate
import com.example.netlib.domain.model.Vacancies.VacanciesCreate
import com.example.netlib.domain.model.Vacancies.VacanciesListResponse
import com.example.netlib.domain.model.Vacancies.VacanciesRecord
import com.example.netlib.domain.model.Vacancies.VacanciesUpdate
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass

class ExampleUnitTest {

    private val api: PBApi = PBApiService.instance

    private var idVac = ""

    //dictionary

    @Test
    fun getDepartments() = runBlocking{
        val departments = api.getDepartments(null)
        assertTrue(departments.totalItems > 1)
    }

    @Test
    fun getDepartmentsFilter() = runBlocking{
        val departments = api.getDepartments("name='Финансы'")
        assertEquals(departments.items[0].name, "Финансы")
        assertTrue(departments.totalItems == 1)
    }

    @Test
    fun getCities()= runBlocking{
        val cities = api.getCities(null)
        assertTrue(cities.totalItems == 6)
    }
    @Test
    fun getCitiesFilter()= runBlocking{
        val cities = api.getCities("name='Минск'")
        assertEquals(cities.items[0].name, "Минск")
        assertTrue(cities.totalItems == 1)
    }


    @Test
    fun getPosition()= runBlocking{
        val cities = api.getPositions(null)
        assertTrue(cities.totalItems > 1)
    }
    @Test
    fun getPositionFilter()= runBlocking{
        val position = api.getPositions("name='Маркетолог'")
        assertEquals(position.items[0].name, "Маркетолог")
        assertTrue(position.totalItems == 1)
    }

    @Test
    fun getApplicantsStatuses() = runBlocking{
        val appStatus = api.getAppsStatus(null)
        assertTrue(appStatus.totalItems > 1)
    }
    @Test
    fun getApplicantsStatusesFilter()= runBlocking{
        val appStatus = api.getAppsStatus("name='Отказ'")
        assertEquals(appStatus.items[0].name, "Отказ")
        assertEquals(appStatus.items[0].sort.toInt(), 5)
        assertTrue(appStatus.totalItems == 1)
    }

    @Test
    fun getCandidateStatuses() = runBlocking{
        val candStatus = api.getCandidatesStatus(null)
        assertTrue(candStatus.totalItems > 1)
    }

    @Test
    fun getCandidateStatusesFilter()= runBlocking{
        val candStatus = api.getCandidatesStatus("name='Новые заявки'")
        assertEquals(candStatus.items[0].name, "Новые заявки")
        assertEquals(candStatus.items[0].sort.toInt(), 1)
        assertTrue(candStatus.totalItems == 1)
    }

    @Test
    fun createUser() = runBlocking {
        val userData = register(uniqueEmail(), "Unit1234")
        assertEquals(userData.firstName , "Иван")
        assertEquals(userData.lastName , "Иванов")
        assertEquals(userData.patronymic , "Иванович")
        assertEquals(userData.role , "user")
    }

    @Test
    fun authUser() = runBlocking {
        val user = loginIn(uniqueEmail, "Unit1234")
        assertTrue(user.token != "")
        assertEquals(user.record.lastName , "Иванов")
        assertEquals(user.record.patronymic , "Иванович")
        assertEquals(user.record.role , "user")
    }

    @Test
    fun getUser() = runBlocking {
        PBApiService.token = loginIn(uniqueEmail, "Unit1234").token
        val user = api.getUser(userId)
        assertEquals(user.id, userId)
    }
    @Test
    fun patchUser() = runBlocking {

        val data = loginIn(uniqueEmail, "Unit1234")
        PBApiService.token = data.token
        val user = api.patchUsers(data.record.id, data = UsersUpdate(
            email = uniqueEmail,
            emailVisibility = true,
            firstName = "Павел",
            lastName = "Иванов",
            patronymic = "Иванович",
            phone = "",
            department = "",
            role = "user",
            position = "",
        ))
        assertEquals(user.id, userId)
    }

    @Test
    fun getListVacancies() = runBlocking {
        val data = getVacancies()

        assertTrue(data.totalItems > 1)
    }

    @Test
    fun getVacancy() = runBlocking {
        val dataList = getVacancies()
        val data = api.getVacancy(dataList.items[0].id)

        assertEquals(data.status,"open" )
    }

    @Test
    fun postVacancy() = runBlocking {
        val data = postVacancies()
        assertEquals(data.title, "testVacancy")
        assertEquals(data.conditions, "No")
    }

    @Test
    fun patchVacancy() = runBlocking {
        val dataPost = postVacancies()
        val data = api.patchVacancies(dataPost.id,
            data = VacanciesUpdate(
                title = "testVacancyRedact",
                description ="This is test data",
                requirements = "No",
                responsibilities = "No",
                conditions = "Yes",
                department = "",
                position = "",
                city = "",
                status = "open",
                salaryFrom = 100000,
                salaryTo = 200000,
                author = "",
            )
        )

        assertEquals(data.title, "testVacancyRedact")
        assertEquals(data.conditions, "Yes")
    }

    @Test
    fun deleteVacancy() = runBlocking {
        val dataPost = postVacancies()
        api.deleteVacancies(dataPost.id)
        val test = getVacancies("id='${dataPost.id}'").totalItems
        assertEquals(test,0 )
    }


    @Test
    fun getListApplicants() = runBlocking {
        val data = getApplicants()
        assertTrue(data.totalItems > 1)
    }

    @Test
    fun getApplicant() = runBlocking {
        val dataList = getApplicants()
        val data = api.getApplicant(dataList.items[0].id)
        assertEquals(data.id,dataList.items[0].id)
    }

    @Test
    fun postApplicant() = runBlocking {
        val status = api.getAppsStatus(filter = "name='Новый'").items[0].id
        val data = postApplicants(status)
        assertEquals(data.firstName, "Testfirst")
        assertEquals(data.status, status)
    }

    @Test
    fun patchApplicant() = runBlocking {
        val status = api.getAppsStatus(filter = "name='Отказ'").items[0].id
        val dataPost = postApplicants(status)
        val data = api.patchApplicants(dataPost.id,
            data = ApplicantsUpdate(
                lastName = "TestLast",
                firstName = "TestFiresRed",
                patronymic = "TestPatronimyc",
                phone = "",
                email = "",
                city = "",
                status = status,
                vacancy = "",
                resume = "",
                avatar = "",
                source ="",
                comment = "No Comment",
            )
        )

        assertEquals(data.firstName, "TestFiresRed")
        assertEquals(data.status, status)
    }


    @Test
    fun getListCandidateCards() = runBlocking {
        val data = getCandidateCards()
        assertTrue(data.totalItems > 1)
    }

    @Test
    fun getCandidateCard() = runBlocking {
        val dataList = getCandidateCards()
        val data = api.getCandidateCard(dataList.items[0].id)
        assertEquals(data.id,dataList.items[0].id)
    }

    @Test
    fun postCandidateCard() = runBlocking {
        val data = postCandidateCards()
        assertEquals(data.sort, 3)
        assertEquals(data.isDeleted, false)
    }

    @Test
    fun patchCandidateCard() = runBlocking {
        val dataPost = postCandidateCards()
        val data = api.patchCandidateCards(dataPost.id,
            data = CandidateCardsUpdate(
                vacancy = getVacancies().items[0].id,
                applicant = getApplicants().items[0].id,
                status = api.getCandidatesStatus(null).items[0].id,
                hrResponsible = "",
                sort = 1,
                isDeleted = true
            )
        )

        assertEquals(data.sort, 1)
        assertEquals(data.isDeleted, true)
    }

    @Test
    fun getListCandidateCommCards() = runBlocking {
        val data = getCandidateCardsCom()
        assertTrue(data.totalItems > 1)
    }


    @Test
    fun postCandidateCommCard() = runBlocking {
        val data = postCandidateCardsCom()
        assertEquals(data.text, "testComments")
    }

    @Test
    fun patchCandidateCommCard() = runBlocking {
        val dataPost = postCandidateCardsCom()
        val data = api.patchCandidateCardsCom(dataPost.id,
            data =
                CandidateCardCommentsUpdate
                    (
                    card = getCandidateCards().items[0].id,
                    text = "testCommentsRedact",
                    author = "",
                )
        )

        assertEquals(data.text, "testCommentsRedact")
    }




    private suspend fun getCandidateCardsCom(filter: String? = null) : CandidateCardCommentsListResponse{
        return api.getCandidateCardsCom(filter)
    }
    private suspend fun postCandidateCardsCom() : CandidateCardCommentsRecord{
        return api.postCandidateCardsCom(data =
            CandidateCardCommentsCreate
                (
                card = getCandidateCards().items[0].id,
                text = "testComments",
                author = "",
            )
        )
    }


    private suspend fun getVacancies(filter: String? = null) : VacanciesListResponse{
        return api.getVacancies(filter)
    }
    private suspend fun postVacancies() : VacanciesRecord{
        return api.postVacancies(data =
            VacanciesCreate
                (
                title = "testVacancy",
                description ="This is test data",
                requirements = "No",
                responsibilities = "No",
                conditions = "No",
                department = "",
                position = "",
                city = "",
                status = "open",
                salaryFrom = 10000,
                salaryTo = 20000,
                author = "",
            ))
    }

    private suspend fun getApplicants(filter: String? = null) : ApplicantsListResponse{
        return api.getApplicants(filter)
    }
    private suspend fun postApplicants(status: String) : ApplicantsRecord{
        return api.postApplicants(data =
            ApplicantsCreate(
                lastName = "TestLast",
                firstName = "Testfirst",
                patronymic = "TestPatronimyc",
                phone = "",
                email = "",
                city = "",
                status = status,
                vacancy = "",
                resume = "",
                avatar = "",
                source ="",
                comment = "No Comment",
            )
        )
    }


    private suspend fun getCandidateCards(filter: String? = null) : CandidateCardsListResponse{
        return api.getCandidateCards(filter)
    }
    private suspend fun postCandidateCards() : CandidateCardsRecord{
        return api.postCandidateCards(data =
            CandidateCardsCreate(
                vacancy = getVacancies().items[0].id,
                applicant = getApplicants().items[0].id,
                status = api.getCandidatesStatus(null).items[0].id,
                hrResponsible = "",
                sort = 3,
                isDeleted = false
            )
        )
    }

    companion object {


        val api = PBApiService.instance



        private fun uniqueEmail() =
            "test${System.nanoTime()}@test.ru"

        val uniqueEmail = uniqueEmail()
        var userId = ""

            @JvmStatic
        @BeforeClass
        fun prepare(): Unit = runBlocking {
            register(uniqueEmail, "Unit1234")
            userId = loginIn(uniqueEmail, "Unit1234").record.id
        }

        private suspend fun loginIn(identity: String, password: String): UserAuthResponse{
            val logData = api.authPassword(
                AuthWithPasswordRequest(
                    identity = identity,
                    password = password,
                    ""
                )
            )
            return logData
        }
        private suspend fun register(identity: String, password: String): UsersRecord{
            return api.postUsers(
                data = UsersCreate(
                    identity,
                    true,
                    verified = false,
                    firstName = "Иван",
                    lastName = "Иванов",
                    patronymic = "Иванович",
                    phone = "",
                    department = "",
                    role = "user",
                    position = "",
                    password = password,
                    passwordConfirm = password
                )
            )
        }
    }





}