package steps;

import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import utils.DriverManager;
import java.time.Duration;

public class CartSteps {

    private final WebDriver driver = DriverManager.getDriver();
    private final CartPage cartPage = new CartPage();

    @Then("the URL should contain {string}")
    public void the_url_should_contain(String expected) {
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("cart"));

        String actual = driver.getCurrentUrl();
        System.out.println("Cart URL: " + actual);

        Assert.assertTrue(
            "Expected URL to contain: " + expected + " but was: " + actual,
            actual.contains(expected)
        );
    }

    @Then("the cart quantity should show {string}")
    public void the_cart_quantity_should_show(String expected) {
        String actual = cartPage.getCartQuantity();
        System.out.println("Expected quantity: " + expected
            + " | Actual: " + actual);
        Assert.assertEquals(
            "Expected cart quantity: " + expected + " but was: " + actual,
            expected, actual
        );
    }
    @Then("the cart should display the price for 2 items")
    public void the_cart_should_display_the_price_for_2_items() {
        Assert.assertTrue(
            "Cart does not display a price",
            cartPage.isPriceDisplayed()
        );
    }
}