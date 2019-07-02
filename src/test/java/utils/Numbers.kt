package utils

import io.qameta.allure.Step
import org.openqa.selenium.WebElement


class Numbers {
    @Step("Заполнение полей")
    fun fillForm(
        inputA: WebElement, sendA: String,
        inputB: WebElement, sendB: String,
        inputC: WebElement, sendC: String,
        submit: WebElement
    ) {
        //TODO добавить нормальное ожидание элементов
        inputA.clear()
        inputB.clear()
        inputC.clear()
        inputA.sendKeys(sendA)
        inputB.sendKeys(sendB)
        inputC.sendKeys(sendC)
        Thread.sleep(2000)
        submit.click()
    }
}