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


class MainPage(val driver: WebDriver) {

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
    @FindBy(css = ".glyphicon-search")
    lateinit var searchButton: WebElement
    @FindBy(xpath = "/html//input[@id='js-site-search-input']")
    lateinit var searchInput: WebElement


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
            ElementsTexts.add(it.getAttribute("innerHTML")
                .replace("\n        ", "")
                .replace("  ", ""))
        }
        return ElementsTexts
    }

    fun studentsLink (): WebElement? {
      return driver.findElement(By.cssSelector("[data-ps-id] [href='\\/en-us\\/students']"))
    }

    fun suggestions(): WebElement? {
        return driver.findElement(By.cssSelector("[name] aside"))
    }

    fun inputGroup(): WebElement? {
        return driver.findElement(By.cssSelector(".input-group"))
    }

    fun suggestionsResult(): List<WebElement>? {
      return driver.findElements(By.cssSelector("[name] aside .search-list .search-highlight"))
    }
    fun productResult(): List<WebElement>? {
        return driver.findElements(By.cssSelector("[name] aside .related-content-products .search-highlight"))
    }

}