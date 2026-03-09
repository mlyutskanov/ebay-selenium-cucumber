package steps;

import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.HomePage;

public class HomePageSteps {

    private final HomePage homePage = new HomePage();

    @Then("the eBay homepage should be loaded correctly")
    public void the_ebay_homepage_should_be_loaded_correctly() {
        Assert.assertTrue(
            "eBay homepage did not load correctly",
            homePage.isPageLoaded()
        );
    }
}