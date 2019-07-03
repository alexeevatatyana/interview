package page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.util.concurrent.TimeUnit
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions
import java.util.ArrayList


class MainPage(driver: WebDriver) {

    val driver = driver

    init {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)
        PageFactory.initElements(driver, this)
    }

    fun countryAlert() {
        try {
            driver.findElement(By.cssSelector(".changeLocationConfirmBtn")).click()
        } catch (e: Exception) {
        }
    }

    @FindBy(linkText = "WHO WE SERVE")
    lateinit var whoWeServeButton: WebElement
    @FindBy(linkText = "SUBJECTS")
    lateinit var subjectsButton: WebElement
    @FindBy(linkText = "ABOUT")
    lateinit var aboutButton: WebElement


    fun openPage() {
        driver.get("https://www.wiley.com/en-us")
    }

    fun whoWeServeDropDown(): WebElement {
        return (driver as JavascriptExecutor).executeScript(
            "return arguments[0].parentNode;", whoWeServeButton
        ) as WebElement
    }

    fun mouseOverWhoWeServe() {
        Actions(driver).moveToElement(whoWeServeDropDown()).build().perform()
    }

    fun findSubElements(): ArrayList<String> {
        val whoWeServeElements = whoWeServeDropDown()
            .findElement(By.cssSelector("[class= 'dropdown-items ps-container ps-theme-default']"))
            .findElements(By.tagName("a"))
        var ElementsTexts = ArrayList<String>()
        whoWeServeElements.forEach{
            ElementsTexts.add(it.getAttribute("innerHTML"))
        }
        return ElementsTexts
    }
}