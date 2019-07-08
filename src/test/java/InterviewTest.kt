import io.qameta.allure.Description
import io.qameta.allure.Feature
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import page.MainPage
import page.Students
import utils.DataProvider
import utils.TestUtils
import java.util.concurrent.TimeUnit

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class InterviewTest {

    private val driver: WebDriver = TestUtils().configureFirefoxWebDriver(
        TestUtils().getCurrentWorkingDirectory()
    )
    private val paramsFile = DataProvider().loadFileAsString(
        DataProvider().getCurrentWorkingDirectory()
            .resolve("src/test/resources/params.json")
    )

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
    @Order(1)
    fun checkLinks() {
        val page = MainPage(driver)
        assertThat(page.whoWeServeButton.text, `is`("WHO WE SERVE"))
        assertThat(page.subjectsButton.text, `is`("SUBJECTS"))
        assertThat(page.aboutButton.text, `is`("ABOUT"))
    }

    @DisplayName("Check items under Who We Serve")
    @Description("Check items under Who We Serve")
    @Feature("Links")
    @Test
    @Order(2)
    fun checkItems() {
        val page = MainPage(driver)
        page.mouseOverWhoWeServe()
        page.findSubElements()
        val elementsTexts = page.findSubElements()

        assertThat(
            elementsTexts, containsInAnyOrder(
                "Students",
                "Instructors",
                "Book Authors",
                "Professionals",
                "Researchers",
                "Institutions",
                "Librarians",
                "Corporations",
                "Societies",
                "Journal Editors",
                "Bookstores",
                "Government"
            )
        )

        assertThat(
            elementsTexts.size, `is`(12)
        )
    }

    @Test
    @Order(3)
    fun checkStudentsLink() {
        val mainPage = MainPage(driver)
        mainPage.studentsLink()!!.click()
        assertThat(
            driver.currentUrl, `is`("https://www.wiley.com/en-us/students")
        )
        val studentsPage = Students(driver)
        assertThat(
            studentsPage.studentsHeader.isDisplayed, `is`(true)
        )

        //TODO
        driver.findElements(By.xpath("//*[contains(text(), 'Learn More')]")).forEach {
            assertThat(it.isDisplayed, `is`(true))
            assertThat(it.findElement(
                By.xpath("../..")).getAttribute("href"), containsString("www.wileyplus.com"))
        }

        @Test
        @Order(4)
        fun checkSubjectsLink() {
            val studentsPage = Students(driver)
            studentsPage.mouseOverSubjects()
            studentsPage.mouseOverEducation()

        }




    }

    @AfterAll
    fun driverClose() {
        driver.close()
    }
}