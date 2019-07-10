package page

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.util.concurrent.TimeUnit

class Students (val driver: WebDriver) {

    init {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)
        PageFactory.initElements(driver, this)
    }

    @FindBy(css = ".sg-title-h1")
    lateinit var studentsHeader: WebElement

    //TODO
    @FindAll(FindBy(xpath = ("//*[contains(text(), 'Learn More')]")))
    lateinit var learnMore: List<WebElement>


    @FindBy(linkText = "SUBJECTS")
    lateinit var subjectsButton: WebElement

    @FindBy(css = "[href='\\/en-us\\/']")
    lateinit var logoButton: WebElement

    fun subjectsDropDown(): WebElement {
        return (driver as JavascriptExecutor).executeScript(
            "return arguments[0].parentNode;", subjectsButton
        ) as WebElement
    }

    fun mouseOverSubjects() {
        Actions(driver).moveToElement(subjectsDropDown()).build().perform()
    }

    fun findSubEducation(): WebElement? {
        return subjectsDropDown()
            .findElement(By.linkText("Education"))
    }
    fun mouseOverEducation() {
        Actions(driver).moveToElement(findSubEducation()).build().perform()
    }

    fun subMenuEducation(): List<WebElement>? {
        return findSubEducation()!!
            .findElement(By.xpath("./.."))
            .findElement(By.cssSelector(".sub-list"))
            .findElements(By.cssSelector(".dropdown-item"))
    }


}