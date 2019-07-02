import io.qameta.allure.Description
import io.qameta.allure.Feature
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
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
    }

    @DisplayName("Баги")
    @Description("Заполнение форм и результаты")
    @Feature("Треугольник")
    @ParameterizedTest
    @MethodSource("utils.Provider#getBugs")
    fun checkBugs() {
    }

    @AfterAll
    fun driverClose() {
        driver.close()
    }
}