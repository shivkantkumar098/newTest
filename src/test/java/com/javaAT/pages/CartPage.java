package com.javaAT.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(pageTitle, "Your Cart"));
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

    public CheckoutInformationPage checkout() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton)));
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        return new CheckoutInformationPage(driver);
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
