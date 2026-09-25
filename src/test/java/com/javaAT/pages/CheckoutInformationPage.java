package com.javaAT.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutInformationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");

    public CheckoutInformationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(pageTitle, "Checkout: Your Information"));
    }

    public CheckoutOverviewPage fillInformation(String firstName, String lastName, String postalCode) {
        setInputValue(this.firstName, firstName);
        setInputValue(this.lastName, lastName);
        setInputValue(this.postalCode, postalCode);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                driver.findElement(continueButton));
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        return new CheckoutOverviewPage(driver);
    }

    private void setInputValue(By input, String value) {
        ((JavascriptExecutor) driver).executeScript(
                "const input = arguments[0];"
                        + "const setter = Object.getOwnPropertyDescriptor("
                        + "window.HTMLInputElement.prototype, 'value').set;"
                        + "setter.call(input, arguments[1]);"
                        + "input.dispatchEvent(new Event('input', {bubbles: true}));"
                        + "input.dispatchEvent(new Event('change', {bubbles: true}));",
                wait.until(ExpectedConditions.visibilityOfElementLocated(input)), value);
    }
}
