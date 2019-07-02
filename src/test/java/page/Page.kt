package page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.util.concurrent.TimeUnit

class Page(driver: WebDriver) {

    init {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES)
        PageFactory.initElements(driver, this)
    }

    @FindBy(css = ".js_a")
    lateinit var inputA: WebElement

}