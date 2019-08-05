import io.qameta.allure.Description
import io.qameta.allure.Feature
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import page.MainPage
import page.Students
import utils.DataProvider
import utils.TestUtils
import java.util.ArrayList
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
            assertThat(
                it.findElement(
                    By.xpath("../..")
                ).getAttribute("href"), anyOf(
                    containsString("www.wileyplus.com"),
                    containsString("www.wiley.com"))
            )
        }
    }

    @Test
    @Order(4)
    fun checkSubjectsLink() {
        val studentsPage = Students(driver)

        val educationSubList = studentsPage
            .mouseOverSubjects()
            .mouseOverEducation()
            .subMenuEducation()

        var subListTexts = ArrayList<String>()
        educationSubList!!.forEach {
            subListTexts.add(it.findElement(By.tagName("a")).text)
        }
        assertThat(
            subListTexts, containsInAnyOrder(
                "Assessment, Evaluation Methods",
                "Classroom Management",
                "Conflict Resolution & Mediation",
                "Curriculum Tools",
                "Education & Public Policy",
                "Educational Research",
                "General Education",
                "Higher Education",
                "Information & Library Science",
                "Special Education",
                "Special Topics",
                "Vocational Technology"
            )
        )
    }

    @Test
    @Order(5)
    fun clickLogo() {
        val studentsPage = Students(driver)
        studentsPage.logoButton.click()
        assertThat(
            driver.currentUrl, `is`("https://www.wiley.com/en-us")
        )
    }

    @Test
    @Order(6)
    fun clickSearchButton() {
        val page = MainPage(driver)
        var pageBefore = driver.findElement(By.xpath("/html/body"))
        page.searchButton.click()
        var pageAfter = driver.findElement(By.xpath("/html/body"))
        assertThat(
            pageAfter.text, `is`(pageBefore.text)
        )
    }

    @Test
    @Order(7)
    fun inputFieldSuggestions() {
        val page = MainPage(driver)
        page.searchInput.sendKeys("Java")
        WebDriverWait(driver,10).until(ExpectedConditions.visibilityOf(page.suggestions()))

        assertThat(
           page.inputGroup()!!.location.x, `is` (page.suggestions()!!.location.x)
        )

        assertThat(
            page.inputGroup()!!.size.width, `is` (page.suggestions()!!.size.width)
        )

        page.suggestionsResult()!!.forEach {
            assertThat (
                it.text, startsWith("java"))
        }

        page.productResult()!!.forEach {
            assertThat (
                it.text, containsString("Java")
            )
        }

    }

    @AfterAll
    fun driverClose() {
        driver.quit()
    }
}