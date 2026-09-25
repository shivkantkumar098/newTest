package com.javaAT.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By cartLink = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(pageTitle, "Products"));
    }

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    public InventoryPage addBoltTShirt() {
        addProduct(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(cartBadge, "1"));
        return this;
    }

    public InventoryPage addBikeLight() {
        addProduct(By.id("add-to-cart-sauce-labs-bike-light"));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(cartBadge, "2"));
        return this;
    }

    public CartPage openCart() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                wait.until(ExpectedConditions.visibilityOfElementLocated(cartLink)));
        wait.until(ExpectedConditions.urlContains("cart.html"));
        return new CartPage(driver);
    }

    private void addProduct(By productButton) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                wait.until(ExpectedConditions.visibilityOfElementLocated(productButton)));
    }
}
