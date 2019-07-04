package page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
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

    @FindAll(FindBy(xpath = ("//*[contains(text(), 'Be Your Best')]")))
    lateinit var learnMore: List<WebElement>

}