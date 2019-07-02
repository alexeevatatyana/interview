import io.qameta.allure.Description
import io.qameta.allure.Feature
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import page.MainPage
import utils.DataProvider
import utils.TestUtils
import java.util.concurrent.TimeUnit

@TestInstance(Lifecycle.PER_CLASS)
class InterviewTest {

    private val driver: WebDriver = TestUtils().configureFirefoxWebDriver(
        TestUtils().getCurrentWorkingDirectory())
    private val paramsFile = DataProvider().loadFileAsString(
        DataProvider().getCurrentWorkingDirectory()
            .resolve("src/test/resources/params.json"))

    @BeforeAll
    fun openPage() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver.manage().window().maximize()

        val page = MainPage(driver)
        page.openPage()
        page.countryAlert()
    }

    @DisplayName("Check the following links are displayed in the top menu")
    @Description("Check the following links are displayed in the top menu")
    @Feature("Links")
    @Test
    fun checkLinks() {
        val page = MainPage(driver)
        assertThat(page.whoWeServeButton.text,`is`( "WHO WE SERVE"))
        assertThat(page.subjectsButton.text,`is`( "SUBJECTS"))
        assertThat(page.aboutButton.text,`is`( "ABOUT"))
    }

    @AfterAll
    fun driverClose() {
        driver.close()
    }
}