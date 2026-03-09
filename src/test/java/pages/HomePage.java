package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class HomePage extends BasePage {

    // ── Locators ────────────────────────────────────────────────────────────

    @FindBy(id = "gh-ac")
    private WebElement searchInput;

    @FindBy(id = "gh-cat")
    private WebElement categoryDropdown;

    private static final By EBAY_LOGO = 
    		By.id("gh-logo");

    // ── Actions ─────────────────────────────────────────────────────────────

    public boolean isPageLoaded() {
        boolean titleOk = driver.getTitle().toLowerCase().contains("ebay");
        boolean logoOk  = isDisplayed(EBAY_LOGO);
        return titleOk && logoOk;
    }

    public void selectCategory(String categoryName) {
        waitForVisible(categoryDropdown);
        Select select = new Select(categoryDropdown);
        select.selectByVisibleText(categoryName);
    }

    public void search(String keyword) {
        waitForVisible(searchInput);
        searchInput.clear();
        searchInput.sendKeys(keyword);
        searchInput.sendKeys(Keys.ENTER);
    }
}