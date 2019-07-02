package page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.util.concurrent.TimeUnit

class MainPage(driver: WebDriver) {

    val driver = driver

    init {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)
        PageFactory.initElements(driver, this)
    }
    fun countryAlert() {
        try{
            driver.findElement(By.cssSelector(".changeLocationConfirmBtn")).click()
        } catch(e: Exception) {
        }
    }

    @FindBy(linkText = "WHO WE SERVE")
    lateinit var whoWeServeButton:WebElement
    @FindBy(linkText = "SUBJECTS")
    lateinit var subjectsButton:WebElement
    @FindBy(linkText = "ABOUT")
    lateinit var aboutButton:WebElement


    fun openPage() {
        driver.get("https://www.wiley.com/en-us")
    }

}