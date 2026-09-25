package com.javaAT;

import com.javaAT.pages.CartPage;
import com.javaAT.pages.CheckoutOverviewPage;
import com.javaAT.pages.InventoryPage;
import com.javaAT.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SauceDemoTest {

    private WebDriver driver;
    private static final String BOLT_T_SHIRT = "Sauce Labs Bolt T-Shirt";
    private static final String BIKE_LIGHT = "Sauce Labs Bike Light";
    private static final String BOLT_DESCRIPTION =
            "Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.";
    private static final String BIKE_DESCRIPTION =
            "A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void userCanPurchaseTwoProductsFromSauceDemo() {
        InventoryPage inventoryPage = new LoginPage(driver)
                .loginAs("standard_user", "secret_sauce");

        Assert.assertEquals(inventoryPage.getPageTitle(), "Products");

        CartPage cartPage = inventoryPage
                .addBoltTShirt()
                .addBikeLight()
                .openCart();

        assertCartItem(cartPage, BOLT_T_SHIRT, "1", BOLT_DESCRIPTION, "$15.99");
        assertCartItem(cartPage, BIKE_LIGHT, "1", BIKE_DESCRIPTION, "$9.99");

        CheckoutOverviewPage overviewPage = cartPage
                .checkout()
                .fillInformation("Golu", "Tester", "12345");

        assertOverviewItem(overviewPage, BOLT_T_SHIRT, "1", BOLT_DESCRIPTION, "$15.99");
        assertOverviewItem(overviewPage, BIKE_LIGHT, "1", BIKE_DESCRIPTION, "$9.99");
        Assert.assertEquals(overviewPage.getItemTotal(), "Item total: $25.98");
        Assert.assertEquals(overviewPage.getTax(), "Tax: $2.08");
        Assert.assertEquals(overviewPage.getTotal(), "Total: $28.06");

        overviewPage.finish();
        Assert.assertEquals(overviewPage.getConfirmationMessage(), "Thank you for your order!");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    private void assertCartItem(CartPage cartPage, String itemName, String quantity,
                                String description, String price) {
        Assert.assertEquals(cartPage.getItemQuantity(itemName), quantity);
        Assert.assertEquals(cartPage.getItemDescription(itemName), description);
        Assert.assertEquals(cartPage.getItemPrice(itemName), price);
    }

    private void assertOverviewItem(CheckoutOverviewPage overviewPage, String itemName, String quantity,
                                    String description, String price) {
        Assert.assertEquals(overviewPage.getItemQuantity(itemName), quantity);
        Assert.assertEquals(overviewPage.getItemDescription(itemName), description);
        Assert.assertEquals(overviewPage.getItemPrice(itemName), price);
    }
}
