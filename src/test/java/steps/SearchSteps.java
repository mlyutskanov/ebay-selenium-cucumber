package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.HomePage;
import pages.SearchResultsPage;

public class SearchSteps {

    private final HomePage          homePage    = new HomePage();
    private final SearchResultsPage resultsPage = new SearchResultsPage();
    private final ScenarioContext   context;

    public SearchSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("I select {string} from the category dropdown")
    public void i_select_from_the_category_dropdown(String category) {
        homePage.selectCategory(category);
    }

    @When("I search for {string}")
    public void i_search_for(String keyword) {
        homePage.search(keyword);
    }

    @Then("the first result title should contain {string}")
    public void the_first_result_title_should_contain(String expected) {
        String actual = resultsPage.getFirstItemTitle();
        Assert.assertTrue(
            "Expected title to contain: " + expected + " but got: " + actual,
            actual.toLowerCase().contains(expected.toLowerCase())
        );
    }

    @Then("the first result should show shipping to {string}")
    public void the_first_result_should_show_shipping_to(String country) {
        String shipping = resultsPage.getFirstItemShipping();
        Assert.assertTrue(
            "Expected shipping to mention: " + country 
            + " but got: " + shipping,
            shipping.toLowerCase().contains(country.toLowerCase())
        );
    }

    @Then("the first result should display a price")
    public void the_first_result_should_display_a_price() {
        String price = resultsPage.getFirstItemPrice();
        Assert.assertFalse(
            "First result does not display a price",
            price.isBlank()
        );
        context.setSearchResultsPrice(price);
        System.out.println("Price captured from results page: " + price);
    }

    @When("I click on the first result")
    public void i_click_on_the_first_result() {
        resultsPage.clickFirstItem();

        if (context.getSearchResultsPrice() == null) {
            String captured = resultsPage.getCapturedPrice();
            if (!captured.isBlank()) {
                context.setSearchResultsPrice(captured);
                System.out.println("Price saved to context from click: " + captured);
            }
        }
    }
}