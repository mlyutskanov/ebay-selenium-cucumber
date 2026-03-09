package steps;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import utils.DriverManager;

public class CommonSteps {

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        DriverManager.getDriver().get(url);
        Hooks.dismissLocationPopupIfPresent(DriverManager.getDriver());
    }
}