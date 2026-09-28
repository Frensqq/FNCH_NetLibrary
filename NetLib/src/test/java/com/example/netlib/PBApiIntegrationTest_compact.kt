package com.example.netlib

import com.example.netlib.data.remote.PBApi
import com.example.netlib.data.remote.PBApiService
import com.example.netlib.domain.model.Applicants.ApplicantsCreate
import com.example.netlib.domain.model.Applicants.ApplicantsUpdate
import com.example.netlib.domain.model.Auth.AuthWithPasswordRequest
import com.example.netlib.domain.model.CandidateCards.CandidateCardsCreate
import com.example.netlib.domain.model.CandidateCards.CandidateCardsRecord
import com.example.netlib.domain.model.CandidateCards.CandidateCardsUpdate
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsCreate
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsRecord
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsUpdate
import com.example.netlib.domain.model.UploadFile
import com.example.netlib.domain.model.User.UsersCreate
import com.example.netlib.domain.model.User.UsersUpdate
import com.example.netlib.domain.model.Vacancies.VacanciesCreate
import com.example.netlib.domain.model.Vacancies.VacanciesUpdate
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test

class PBApiIntegrationTestV2 {

    private val api: PBApi
        get() = PBApiService.instance

    @Test
    fun getDepartments() = runBlocking {
        assertTrue(api.getDepartments(null).page >= 1)
    }

    @Test
    fun getCities() = runBlocking {
        assertTrue(api.getCities(null).page >= 1)
    }

    @Test
    fun getApplicantStatuses() = runBlocking {
        assertTrue(api.getAppsStatus(null).page >= 1)
    }

    @Test
    fun getCandidateStatuses() = runBlocking {
        assertTrue(api.getCandidatesStatus(null).page >= 1)
    }

    @Test
    fun getPositions() = runBlocking {
        assertTrue(api.getPositions(null).page >= 1)
    }

    @Test
    fun getVacancies() = runBlocking {
        assertTrue(api.getVacancies(null).page >= 1)
    }

    @Test
    fun postVacancies() = runBlocking {
        val result = api.postVacancies(
            vacancyCreate(),
            listOf(textFile("one.txt"), textFile("two.txt"))
        )
        assertEquals(2, result.files.size)
    }

    @Test
    fun getVacancy() = runBlocking {
        val vacancy = createVacancy()
        assertEquals(vacancy.id, api.getVacancy(vacancy.id).id)
    }

    @Test
    fun patchVacancies() = runBlocking {
        val vacancy = createVacancy()
        val result = api.patchVacancies(
            vacancy.id,
            vacancyUpdate("Updated vacancy"),
            listOf(textFile("update.txt"))
        )
        assertEquals("Updated vacancy", result.title)
    }

    @Test
    fun deleteVacancies() = runBlocking {
        api.deleteVacancies(createVacancy().id)
    }

    @Test
    fun getApplicants() = runBlocking {
        assertTrue(api.getApplicants(null).page >= 1)
    }

    @Test
    fun postApplicants() = runBlocking {
        val result = api.postApplicants(
            applicantCreate(),
            imageFile(),
            textFile("resume.txt")
        )
        assertTrue(result.avatar.isNotBlank() && result.resume.isNotBlank())
    }

    @Test
    fun getApplicant() = runBlocking {
        val applicant = createApplicant()
        assertEquals(applicant.id, api.getApplicant(applicant.id).id)
    }

    @Test
    fun patchApplicants() = runBlocking {
        val applicant = createApplicant()
        val result = api.patchApplicants(
            applicant.id,
            applicantUpdate("Петров"),
            imageFile(),
            textFile("resume.txt")
        )
        assertEquals("Петров", result.lastName)
    }

    @Test
    fun getCandidateCards() = runBlocking {
        assertTrue(api.getCandidateCards(null).page >= 1)
    }

    @Test
    fun postCandidateCards() = runBlocking {
        assertTrue(createCandidateCard().id.isNotBlank())
    }

    @Test
    fun getCandidateCard() = runBlocking {
        val card = createCandidateCard()
        assertEquals(card.id, api.getCandidateCard(card.id).id)
    }

    @Test
    fun patchCandidateCards() = runBlocking {
        val card = createCandidateCard()
        val result = api.patchCandidateCards(
            card.id,
            candidateCardUpdate(
                card.vacancy,
                card.applicant,
                card.status,
                2
            )
        )
        assertEquals(card.id, result.id)
    }

    @Test
    fun getCandidateCardsComments() = runBlocking {
        assertTrue(api.getCandidateCardsCom(null).page >= 1)
    }

    @Test
    fun postCandidateCardsComment() = runBlocking {
        assertTrue(createComment().id.isNotBlank())
    }

    @Test
    fun getCandidateCardComment() = runBlocking {
        val comment = createComment()
        assertEquals(comment.id, api.getCandidateCardCom(comment.id).id)
    }

    @Test
    fun patchCandidateCardsComment() = runBlocking {
        val comment = createComment()
        val result = api.patchCandidateCardsCom(
            comment.id,
            commentUpdate(comment.card, "Updated comment")
        )
        assertEquals("Updated comment", result.text)
    }

    @Test
    fun getUsers() = runBlocking {
        assertTrue(api.getUsers(null).items.any { it.id == testUserId })
    }

    @Test
    fun postUsers() = runBlocking {
        val result = api.postUsers(
            userCreate(uniqueEmail()),
            imageFile()
        )
        assertTrue(result.avatar.isNotBlank())
    }

    @Test
    fun getUser() = runBlocking {
        assertEquals(testUserId, api.getUser(testUserId).id)
    }

    @Test
    fun patchUsers() = runBlocking {
        val email = uniqueEmail()

        api.postUsers(userCreate(email))
        val auth = api.authPassword(authRequest(email))
        PBApiService.token = auth.token

        val result = api.patchUsers(
            auth.record.id,
            userUpdate(auth.record.id, email),
            imageFile()
        )

        assertEquals("Updated", result.firstName)
        loginTestUser()
    }

    @Test
    fun authPassword() = runBlocking {
        assertTrue(api.authPassword(authRequest()).token.isNotBlank())
    }

    private suspend fun createVacancy() =
        api.postVacancies(vacancyCreate())

    private suspend fun createApplicant(vacancyId: String = "") =
        api.postApplicants(applicantCreate(vacancyId))

    private suspend fun createCandidateCard(): CandidateCardsRecord {
        val vacancy = createVacancy()
        val applicant = createApplicant(vacancy.id)
        val status = api.getCandidatesStatus(null).items.first()

        return api.postCandidateCards(
            candidateCardCreate(
                vacancy.id,
                applicant.id,
                status.id
            )
        )
    }

    private suspend fun createComment(): CandidateCardCommentsRecord {
        val card = createCandidateCard()

        return api.postCandidateCardsCom(
            commentCreate(card.id, "Test comment")
        )
    }

    private fun vacancyCreate() = VacanciesCreate(
        title = "Test vacancy ${System.nanoTime()}",
        description = "",
        requirements = "",
        responsibilities = "",
        conditions = "",
        department = "",
        position = "",
        city = "",
        status = "open",
        salaryFrom = 0,
        salaryTo = 0,
        author = testUserId
    )

    private fun vacancyUpdate(title: String) = VacanciesUpdate(
        title = title,
        description = "",
        requirements = "",
        responsibilities = "",
        conditions = "",
        department = "",
        position = "",
        city = "",
        status = "open",
        salaryFrom = 0,
        salaryTo = 0,
        author = testUserId
    )

    private fun applicantCreate(vacancyId: String = "") = ApplicantsCreate(
        lastName = "Иванов",
        firstName = "Иван",
        patronymic = "",
        phone = "",
        email = "applicant${System.nanoTime()}@test.ru",
        city = "",
        status = "",
        vacancy = vacancyId,
        resume = "",
        avatar = "",
        source = "",
        comment = ""
    )

    private fun applicantUpdate(lastName: String) = ApplicantsUpdate(
        lastName = lastName,
        firstName = "Иван",
        patronymic = "",
        phone = "",
        email = "",
        city = "",
        status = "",
        vacancy = "",
        resume = "",
        avatar = "",
        source = "",
        comment = ""
    )

    private fun candidateCardCreate(
        vacancy: String,
        applicant: String,
        status: String
    ) = CandidateCardsCreate(
        vacancy = vacancy,
        applicant = applicant,
        status = status,
        hrResponsible = "",
        sort = 1,
        isDeleted = false
    )

    private fun candidateCardUpdate(
        vacancy: String,
        applicant: String,
        status: String,
        sort: Int
    ) = CandidateCardsUpdate(
        vacancy = vacancy,
        applicant = applicant,
        status = status,
        hrResponsible = "",
        sort = sort,
        isDeleted = false
    )

    private fun commentCreate(
        card: String,
        text: String
    ) = CandidateCardCommentsCreate(
        card = card,
        text = text,
        author = testUserId
    )

    private fun commentUpdate(
        card: String,
        text: String
    ) = CandidateCardCommentsUpdate(
        card = card,
        text = text,
        author = testUserId
    )

    private fun authRequest(
        email: String = TEST_EMAIL
    ) = AuthWithPasswordRequest(
        identity = email,
        password = TEST_PASSWORD,
        identityField = "email"
    )

    private fun userCreate(email: String) = UsersCreate(
        email = email,
        emailVisibility = true,
        verified = false,
        firstName = "Test",
        lastName = "User",
        patronymic = "",
        phone = "",
        department = "",
        position = "",
        role = "hr",
        password = TEST_PASSWORD,
        passwordConfirm = TEST_PASSWORD
    )

    private fun userUpdate(
        id: String,
        email: String
    ) = UsersUpdate(
        email = email,
        emailVisibility =true,
        firstName = "Updated",
        lastName = "User",
        patronymic = "",
        phone = "",
        department = "",
        position = "",
        role = "hr",
    )

    private fun textFile(name: String = "test.txt") =
        UploadFile(
            "test".encodeToByteArray(),
            name,
            "text/plain"
        )

    private fun imageFile() =
        UploadFile(
            TEST_PNG,
            "test.png",
            "image/png"
        )

    companion object {

        private const val TEST_EMAIL = "netlib.integration@test.ru"
        private const val TEST_PASSWORD = "12345678"

        private lateinit var testUserId: String

        private val TEST_PNG = java.util.Base64.getDecoder().decode(
            "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAusB9WlZswAAAABJRU5ErkJggg=="
        )

        @JvmStatic
        @BeforeClass
        fun prepare() = runBlocking {
            runCatching {
                PBApiService.instance.postUsers(
                    UsersCreate(
                        email = TEST_EMAIL,
                        emailVisibility = true,
                        verified = false,
                        firstName = "Integration",
                        lastName = "Test",
                        patronymic = "",
                        phone = "",
                        department = "",
                        position = "",
                        role = "hr",
                        password = TEST_PASSWORD,
                        passwordConfirm = TEST_PASSWORD
                    )
                )
            }

            loginTestUser()
        }

        private suspend fun loginTestUser() {
            val auth = PBApiService.instance.authPassword(
                AuthWithPasswordRequest(
                    identity = TEST_EMAIL,
                    password = TEST_PASSWORD,
                    identityField = "email"
                )
            )

            PBApiService.token = auth.token
            testUserId = auth.record.id
        }

        private fun uniqueEmail() =
            "test${System.nanoTime()}@test.ru"
    }
}
