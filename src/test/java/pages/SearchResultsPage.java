package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class SearchResultsPage extends BasePage {

    // ── Locators ─────────────────────────────────────────────────────────────

    private static final By RESULT_ITEMS =
        By.cssSelector("li.s-card");

    private static final By ITEM_TITLE =
        By.cssSelector("div.s-card__title span.su-styled-text");

    private static final By ITEM_PRICE =
        By.cssSelector("span.s-card__price");

    private static final By ITEM_LINK =
        By.cssSelector("a.s-card__link");

    private String capturedPrice = "";

    // ── Helpers ──────────────────────────────────────────────────────────────

    private WebElement getFirstRealResult() {
        wait.until(d -> d.findElements(RESULT_ITEMS).size() > 1);

        List<WebElement> items = driver.findElements(RESULT_ITEMS);
        for (WebElement item : items) {
            List<WebElement> titles = item.findElements(ITEM_TITLE);
            if (titles.isEmpty()) continue;

            String titleText  = titles.get(0).getText().trim();
            String titleLower = titleText.toLowerCase();

            if (titleLower.contains("monopoly")
                    && titleLower.contains("board game")
                    && !titleLower.contains("digital")
                    && !titleLower.contains("go ")
                    && !titleLower.contains("card")) {
                System.out.println("First valid result: " + titleText);
                return item;
            }
        }
        throw new RuntimeException(
            "No valid Monopoly Board Game result found on page.");
    }

    // ── Popup handler ─────────────────────────────────────────────────────────

    public void handleWhereAreYouShippingToPopup() {
        try {
            WebDriverWait shortWait =
                new WebDriverWait(driver, Duration.ofSeconds(6));

            WebElement confirmBtn = shortWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector(
                        "button.btn.submit-button.btn--primary.btn--fluid")));

            confirmBtn.click();
            System.out.println(
                "'Where are you shipping to?' popup — clicked Confirm (Bulgaria pre-selected)");

            shortWait.until(ExpectedConditions.stalenessOf(confirmBtn));
            wait.until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
            System.out.println(
                "'Where are you shipping to?' popup — page reloaded after confirmation");

        } catch (Exception e) {
            try {
                driver.findElement(By.tagName("body"))
                      .sendKeys(org.openqa.selenium.Keys.ESCAPE);
                System.out.println(
                    "'Where are you shipping to?' popup — dismissed with Escape (no reload)");
            } catch (Exception esc) {
                System.out.println(
                    "'Where are you shipping to?' popup — did not appear, continuing");
            }
        }
    }

    // ── Actions ──────────────────────────────────────────────────────────────

    public String getFirstItemTitle() {
        return getFirstRealResult()
                .findElement(ITEM_TITLE)
                .getText().trim();
    }

    public String getFirstItemPrice() {
        return getFirstRealResult()
                .findElement(ITEM_PRICE)
                .getText().trim();
    }
    
    public String getFirstItemShipping() {
        WebElement item = getFirstRealResult();

        String cardText = item.getText();
        System.out.println("Card text for shipping check:\n" + cardText);
        return cardText;
    }

    public String getCapturedPrice() {
        return capturedPrice;
    }

    public void clickFirstItem() {
    	handleWhereAreYouShippingToPopup();

        WebElement item  = getFirstRealResult();

        try {
            capturedPrice = item.findElement(ITEM_PRICE).getText().trim();
            System.out.println("Price captured before click: " + capturedPrice);
        } catch (Exception e) {
            System.out.println("Could not capture price before click: "
                + e.getMessage());
        }

        WebElement link = item.findElement(ITEM_LINK);
        scrollIntoView(link);

        String originalWindow = driver.getWindowHandle();
        jsClick(link);
        switchToNewWindow(originalWindow);
    }
}