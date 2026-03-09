package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.ItemDetailPage;

public class ItemDetailSteps {

    private final ItemDetailPage detailPage = new ItemDetailPage();
    private final ScenarioContext context;

    public ItemDetailSteps(ScenarioContext context) {
        this.context = context;
    }

    @Then("the detail page title should contain {string}")
    public void the_detail_page_title_should_contain(String expected) {
        String actual = detailPage.getItemTitle();
        Assert.assertTrue(
            "Expected detail title to contain: " + expected 
            + " but got: " + actual,
            actual.toLowerCase().contains(expected.toLowerCase())
        );
    }

    @Then("the detail page price should match the search results price")
    public void the_detail_page_price_should_match() {
        String detailPrice  = detailPage.getItemPrice();
        String resultsPrice = context.getSearchResultsPrice();

        String normDetail  = detailPrice.replaceAll("[^0-9.,]", "");
        String normResults = resultsPrice.replaceAll("[^0-9.,]", "");

        System.out.println("Detail page price:    " + detailPrice);
        System.out.println("Search results price: " + resultsPrice);

        Assert.assertTrue(
            "Prices do not match. Detail: " + detailPrice 
            + " | Results: " + resultsPrice,
            normDetail.contains(normResults) || 
            normResults.contains(normDetail)
        );
    }

    @When("I click the See details link")
    public void i_click_the_see_details_link() {
        detailPage.clickSeeDetailsLink();
    }

    @Then("the shipping popup should confirm shipping to {string}")
    public void the_shipping_popup_should_confirm_shipping_to(String country) {
        String popupText = detailPage.getShippingPopupText();

        boolean mentionsCountry = popupText.toLowerCase()
                                           .contains(country.toLowerCase());
        boolean isWorldwide     = popupText.toLowerCase().contains("worldwide");

        System.out.println("Mentions Bulgaria: " + mentionsCountry);
        System.out.println("Ships worldwide:   " + isWorldwide);

        Assert.assertTrue(
            "Shipping popup does not mention '" + country 
            + "' or 'Worldwide'. Actual: " + popupText,
            mentionsCountry || isWorldwide
        );
    }

    @Then("I close the shipping popup")
    public void i_close_the_shipping_popup() {
        detailPage.closeShippingPopup();
    }

    @When("I select quantity {string}")
    public void i_select_quantity(String quantity) {
        detailPage.selectQuantity(quantity);
    }

    @When("I click Add to cart")
    public void i_click_add_to_cart() {
        detailPage.clickAddToCart();
    }
}