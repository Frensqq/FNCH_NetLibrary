package com.example.netlib

import com.example.netlib.data.remote.PBApi
import com.example.netlib.data.remote.PBApiService
import com.example.netlib.domain.model.Applicants.ApplicantsCreate
import com.example.netlib.domain.model.Applicants.ApplicantsUpdate
import com.example.netlib.domain.model.Auth.AuthWithPasswordRequest
import com.example.netlib.domain.model.CandidateCards.CandidateCardsCreate
import com.example.netlib.domain.model.CandidateCards.CandidateCardsUpdate
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsCreate
import com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsUpdate
import com.example.netlib.domain.model.UploadFile
import com.example.netlib.domain.model.User.UsersCreate
import com.example.netlib.domain.model.User.UsersUpdate
import com.example.netlib.domain.model.Vacancies.VacanciesCreate
import com.example.netlib.domain.model.Vacancies.VacanciesUpdate
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import java.util.Base64

/**
 * Интеграционные тесты.
 *
 * Никаких MockEngine / Mockito / MockK:
 * каждый тест реально обращается к PocketBase из PBApiService.BASE_URL.
 *
 * Для base-коллекций API Rules в PocketBase должны разрешать работу
 * авторизованному HR-пользователю.
 */
class PBApiIntegrationTest {

    private val api: PBApi
        get() = PBApiService.instance

    // ---------------- DICTIONARIES ----------------

    @Test
    fun getDepartments() = runBlocking {
        val result = api.getDepartments(null)
        assertTrue(result.page >= 1)
    }

    @Test
    fun getCities() = runBlocking {
        val result = api.getCities(null)
        assertTrue(result.page >= 1)
    }

    @Test
    fun getApplicantStatuses() = runBlocking {
        val result = api.getAppsStatus(null)
        assertTrue(result.page >= 1)
    }

    @Test
    fun getCandidateStatuses() = runBlocking {
        val result = api.getCandidatesStatus(null)
        assertTrue(result.page >= 1)
    }

    @Test
    fun getPositions() = runBlocking {
        val result = api.getPositions(null)
        assertTrue(result.page >= 1)
    }

    // ---------------- VACANCIES ----------------

    @Test
    fun getVacancies() = runBlocking {
        val result = api.getVacancies(null)
        assertTrue(result.page >= 1)
    }

    @Test
    fun postVacancies() = runBlocking {
        val result = api.postVacancies(
            data = vacancyCreate(),
            files = listOf(
                textFile("one.txt", "ONE"),
                textFile("two.txt", "TWO")
            )
        )

        assertTrue(result.id.isNotBlank())
        assertEquals(2, result.files.size)
    }

    @Test
    fun getVacancy() = runBlocking {
        val created = createVacancy()

        val result = api.getVacancy(created.id)

        assertEquals(created.id, result.id)
    }

    @Test
    fun patchVacancies() = runBlocking {
        val created = createVacancy()

        val result = api.patchVacancies(
            id = created.id,
            data = vacancyUpdate("Updated vacancy"),
            files = listOf(textFile("update.txt", "UPDATED"))
        )

        assertEquals("Updated vacancy", result.title)
        assertTrue(result.files.isNotEmpty())
    }

    @Test
    fun deleteVacancies() = runBlocking {
        val created = createVacancy()

        api.deleteVacancies(created.id)

        // Если DELETE вернул ошибку, тест упадёт до этой строки.
        assertTrue(true)
    }

    // ---------------- APPLICANTS ----------------

    @Test
    fun getApplicants() = runBlocking {
        val result = api.getApplicants(null)
        assertTrue(result.page >= 1)
    }

    @Test
    fun postApplicants() = runBlocking {
        val result = api.postApplicants(
            data = applicantCreate(),
            avatar = imageFile("avatar.png"),
            resume = textFile("resume.txt", "TEST RESUME")
        )

        assertTrue(result.id.isNotBlank())
        assertTrue(result.avatar.isNotBlank())
        assertTrue(result.resume.isNotBlank())
    }

    @Test
    fun getApplicant() = runBlocking {
        val created = createApplicant()

        val result = api.getApplicant(created.id)

        assertEquals(created.id, result.id)
    }

    @Test
    fun patchApplicants() = runBlocking {
        val created = createApplicant()

        val result = api.patchApplicants(
            id = created.id,
            data = applicantUpdate("Петров"),
            avatar = imageFile("new_avatar.png"),
            resume = textFile("new_resume.txt", "NEW RESUME")
        )

        assertEquals("Петров", result.lastName)
        assertTrue(result.avatar.isNotBlank())
        assertTrue(result.resume.isNotBlank())
    }

    // ---------------- CANDIDATE CARDS ----------------

    @Test
    fun getCandidateCards() = runBlocking {
        val result = api.getCandidateCards(null)
        assertTrue(result.page >= 1)
    }

    @Test
    fun postCandidateCards() = runBlocking {
        val result = createCandidateCard()

        assertTrue(result.id.isNotBlank())
    }

    @Test
    fun getCandidateCard() = runBlocking {
        val created = createCandidateCard()

        val result = api.getCandidateCard(created.id)

        assertEquals(created.id, result.id)
    }

    @Test
    fun patchCandidateCards() = runBlocking {
        val created = createCandidateCard()

        val result = api.patchCandidateCards(
            id = created.id,
            data = candidateCardUpdate(
                vacancy = created.vacancy,
                applicant = created.applicant,
                status = created.status,
                sort = 2
            )
        )

        assertEquals(created.id, result.id)
    }

    // ---------------- CANDIDATE CARD COMMENTS ----------------

    @Test
    fun getCandidateCardsComments() = runBlocking {
        val result = api.getCandidateCardsCom(null)
        assertTrue(result.page >= 1)
    }

    @Test
    fun postCandidateCardsComment() = runBlocking {
        val result = createComment()

        assertTrue(result.id.isNotBlank())
        assertEquals("Test comment", result.text)
    }

    @Test
    fun getCandidateCardComment() = runBlocking {
        val created = createComment()

        val result = api.getCandidateCardCom(created.id)

        assertEquals(created.id, result.id)
    }

    @Test
    fun patchCandidateCardsComment() = runBlocking {
        val created = createComment()

        val result = api.patchCandidateCardsCom(
            id = created.id,
            data = commentUpdate(
                card = created.card,
                text = "Updated comment"
            )
        )

        assertEquals("Updated comment", result.text)
    }

    // ---------------- USERS ----------------

    @Test
    fun getUsers() = runBlocking {
        val result = api.getUsers(null)

        assertTrue(result.items.any { it.id == testUserId })
    }

    @Test
    fun postUsers() = runBlocking {
        val email = uniqueEmail()

        val result = api.postUsers(
            data = userCreate(email),
            avatar = imageFile("user_avatar.png")
        )

        assertTrue(result.id.isNotBlank())
        assertEquals(email, result.email)
        assertTrue(result.avatar.isNotBlank())
    }

    @Test
    fun getUser() = runBlocking {
        val result = api.getUser(testUserId)

        assertEquals(testUserId, result.id)
    }

    @Test
    fun patchUsers() = runBlocking {
        // Обновляем отдельного пользователя, чтобы не ломать общего test-user.
        val email = uniqueEmail()
        val password = TEST_PASSWORD

        api.postUsers(userCreate(email))

        val auth = api.authPassword(authRequest(email, password))
        PBApiService.token = auth.token

        val result = api.patchUsers(
            id = auth.record.id,
            data = userUpdate(
                id = auth.record.id,
                email = email,
                password = password
            ),
            avatar = imageFile("updated_user.png")
        )

        assertEquals("Updated", result.firstName)
        assertTrue(result.avatar.isNotBlank())

        // Возвращаем токен общего пользователя для остальных тестов.
        loginTestUser()
    }

    // ---------------- AUTH ----------------

    @Test
    fun authPassword() = runBlocking {
        val result = api.authPassword(
            authRequest(TEST_EMAIL, TEST_PASSWORD)
        )

        assertTrue(result.token.isNotBlank())
        assertEquals(TEST_EMAIL, result.record.email)
    }

    // ---------------- HELPERS ----------------

    private suspend fun createVacancy() =
        api.postVacancies(vacancyCreate())

    private suspend fun createApplicant(vacancyId: String = "") =
        api.postApplicants(
            data = applicantCreate(vacancyId)
        )

    private suspend fun createCandidateCard(): com.example.netlib.domain.model.CandidateCards.CandidateCardsRecord {
        val vacancy = createVacancy()
        val applicant = createApplicant(vacancy.id)

        val status = api.getCandidatesStatus(null).items.firstOrNull()
            ?: error("В candidate_statuses нет ни одной записи")

        return api.postCandidateCards(
            candidateCardCreate(
                vacancy = vacancy.id,
                applicant = applicant.id,
                status = status.id
            )
        )
    }

    private suspend fun createComment(): com.example.netlib.domain.model.CandidateCartComments.CandidateCardCommentsRecord {
        val card = createCandidateCard()

        return api.postCandidateCardsCom(
            commentCreate(
                card = card.id,
                text = "Test comment"
            )
        )
    }

    private fun vacancyCreate() = VacanciesCreate(
        title = "Test vacancy ${System.nanoTime()}",
        description = "Description",
        requirements = "Requirements",
        responsibilities = "Responsibilities",
        conditions = "Conditions",
        department = "",
        position = "",
        city = "",
        status = "open",
        salaryFrom = 100000,
        salaryTo = 150000,
        author = testUserId
    )

    private fun vacancyUpdate(title: String) = VacanciesUpdate(
        title = title,
        description = "Description",
        requirements = "Requirements",
        responsibilities = "Responsibilities",
        conditions = "Conditions",
        department = "",
        position = "",
        city = "",
        status = "open",
        salaryFrom = 110000,
        salaryTo = 160000,
        author = testUserId
    )

    private fun applicantCreate(vacancyId: String = "") = ApplicantsCreate(
        lastName = "Иванов",
        firstName = "Иван",
        patronymic = "Иванович",
        phone = "+79990000000",
        email = "applicant${System.nanoTime()}@test.ru",
        city = "",
        status = "",
        vacancy = vacancyId,
        resume = "",
        avatar = "",
        source = "test",
        comment = "test"
    )

    private fun applicantUpdate(lastName: String) = ApplicantsUpdate(
        lastName = lastName,
        firstName = "Иван",
        patronymic = "Иванович",
        phone = "+79990000000",
        email = "applicant@test.ru",
        city = "",
        status = "",
        vacancy = "",
        resume = "",
        avatar = "",
        source = "test",
        comment = "test"
    )

    /**
     * Эти модели пользователь не присылал в текущем сообщении,
     * поэтому создаём их через kotlinx.serialization.
     * Так тест не зависит от порядка параметров конструктора.
     */
    private fun candidateCardCreate(
        vacancy: String,
        applicant: String,
        status: String
    ): CandidateCardsCreate =
        json.decodeFromString(
            """
            {
              "vacancy":"$vacancy",
              "applicant":"$applicant",
              "status":"$status",
              "hrResponsible":"",
              "sort":1,
              "isDeleted":false
            }
            """.trimIndent()
        )

    private fun candidateCardUpdate(
        vacancy: String,
        applicant: String,
        status: String,
        sort: Int
    ): CandidateCardsUpdate =
        json.decodeFromString(
            """
            {
              "vacancy":"$vacancy",
              "applicant":"$applicant",
              "status":"$status",
              "hrResponsible":"",
              "sort":$sort,
              "isDeleted":false
            }
            """.trimIndent()
        )

    private fun commentCreate(
        card: String,
        text: String
    ): CandidateCardCommentsCreate =
        json.decodeFromString(
            """
            {
              "id":"${recordId()}",
              "card":"$card",
              "text":"$text",
              "author":"$testUserId"
            }
            """.trimIndent()
        )

    private fun commentUpdate(
        card: String,
        text: String
    ): CandidateCardCommentsUpdate =
        json.decodeFromString(
            """
            {
              "card":"$card",
              "text":"$text",
              "author":"$testUserId"
            }
            """.trimIndent()
        )

    private fun authRequest(
        email: String,
        password: String
    ): AuthWithPasswordRequest =
        json.decodeFromString(
            """
            {
              "identity":"$email",
              "password":"$password",
              "identityField":"email"
            }
            """.trimIndent()
        )

    private fun userCreate(email: String) = UsersCreate(
        email = email,
        emailVisibility = true,
        verified = false,
        firstName = "Test",
        lastName = "User",
        patronymic = "",
        phone = "+79990000000",
        department = "",
        position = "",
        role = "hr",
        password = TEST_PASSWORD,
        passwordConfirm = TEST_PASSWORD
    )

    /**
     * Соответствует текущему UsersUpdate.kt пользователя:
     * emailVisibility и verified пока String,
     * поле подтверждения называется confirmPassword.
     */
    private fun userUpdate(
        id: String,
        email: String,
        password: String
    ) = UsersUpdate(
        id = id,
        email = email,
        emailVisibility = "true",
        verified = "false",
        firstName = "Updated",
        lastName = "User",
        patronymic = "",
        phone = "+79990000000",
        department = "",
        position = "",
        role = "hr",
        oldPassword = password,
        password = password,
        passwordConfirm = password
    )

    private fun textFile(
        name: String,
        value: String
    ) = UploadFile(
        bytes = value.encodeToByteArray(),
        name = name,
        mimeType = "text/plain"
    )

    private fun imageFile(name: String) = UploadFile(
        bytes = PNG_1X1,
        name = name,
        mimeType = "image/png"
    )

    companion object {

        private const val TEST_EMAIL = "netlib.integration@test.ru"
        private const val TEST_PASSWORD = "12345678"

        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        private val PNG_1X1: ByteArray = Base64.getDecoder().decode(
            "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAusB9WlZswAAAABJRU5ErkJggg=="
        )

        private lateinit var testUserId: String

        @JvmStatic
        @BeforeClass
        fun prepare() = runBlocking {
            val api = PBApiService.instance

            // Создаём общего тестового пользователя один раз.
            // Если он уже существует — PocketBase вернёт ошибку, её просто игнорируем.
            runCatching {
                api.postUsers(
                    UsersCreate(
                        email = TEST_EMAIL,
                        emailVisibility = true,
                        verified = false,
                        firstName = "Integration",
                        lastName = "Test",
                        patronymic = "",
                        phone = "+79990000000",
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
            val api = PBApiService.instance

            val request: AuthWithPasswordRequest = json.decodeFromString(
                """
                {
                  "identity":"$TEST_EMAIL",
                  "password":"$TEST_PASSWORD",
                  "identityField":"email"
                }
                """.trimIndent()
            )

            val auth = api.authPassword(request)

            PBApiService.token = auth.token
            testUserId = auth.record.id
        }

        private fun uniqueEmail() =
            "test${System.nanoTime()}@test.ru"

        private fun recordId(): String {
            val value = "t" + System.nanoTime().toString(36)
            return value.take(15).padEnd(15, '0')
        }
    }
}
