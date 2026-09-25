package com.javaAT.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutOverviewPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By itemTotal = By.cssSelector("[data-test='subtotal-label']");
    private final By tax = By.cssSelector("[data-test='tax-label']");
    private final By total = By.cssSelector("[data-test='total-label']");
    private final By finishButton = By.id("finish");
    private final By confirmation = By.cssSelector("[data-test='complete-header']");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(pageTitle, "Checkout: Overview"));
    }

    public String getItemQuantity(String itemName) {
        return itemContainer(itemName).findElement(By.className("cart_quantity")).getText();
    }

    public String getItemDescription(String itemName) {
        return itemContainer(itemName).findElement(By.className("inventory_item_desc")).getText();
    }

    public String getItemPrice(String itemName) {
        return itemContainer(itemName).findElement(By.className("inventory_item_price")).getText();
    }

    public String getItemTotal() {
        return driver.findElement(itemTotal).getText();
    }

    public String getTax() {
        return driver.findElement(tax).getText();
    }

    public String getTotal() {
        return driver.findElement(total).getText();
    }

    public CheckoutOverviewPage finish() {
        driver.findElement(finishButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmation));
        return this;
    }

    public String getConfirmationMessage() {
        return driver.findElement(confirmation).getText();
    }

    private org.openqa.selenium.WebElement itemContainer(String itemName) {
        return driver.findElement(By.xpath(
                "//div[contains(@class,'cart_item') and .//div[contains(@class,'inventory_item_name') and normalize-space()="
                        + xpathLiteral(itemName) + "]]"));
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "', \"'\", '") + "')";
    }
}
