package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ItemDetailPage extends BasePage {

    // ── Locators ──────────────────────────────────────────────────────────────

    private static final By ITEM_TITLE =
        By.cssSelector("h1.x-item-title__mainTitle span.ux-textspans");

    private static final By ITEM_PRICE =
        By.cssSelector(".x-price-primary span.ux-textspans");

    private static final By SEE_DETAILS_BUTTON =
        By.cssSelector(
            "div.ux-labels-values--shipping button[data-testid='ux-action']");

    private static final By ADD_TO_CART_BUTTON =
        By.cssSelector(
            "a[href*='cart'][class*='fake-btn--secondary'], a[id*='atcBtn']");

    private static final By SEE_IN_CART_BUTTON =
        By.cssSelector("a[href*='cart.ebay.com'][class*='fake-btn--primary']");
    
    private static final By SHIPS_TO_SECTION =
        By.cssSelector("div.ux-labels-values--shipsto");

    private static final By POPUP_CLOSE_BUTTON =
    	    By.cssSelector("button[aria-label='Close dialog']");

    private static final By SHIPPING_POPUP =
        By.cssSelector("div.lightbox-dialog__header");
    
    private static final By DELIVERY_TO_SELECT =
    	    By.id("shCountry");

    // ── Actions ──────────────────────────────────────────────────────────────

    public String getItemTitle() {
        return waitForVisible(ITEM_TITLE).getText().trim();
    }

    public String getItemPrice() {
        return waitForVisible(ITEM_PRICE).getText().trim();
    }

    public void clickSeeDetailsLink() {
        WebElement btn = waitForVisible(SEE_DETAILS_BUTTON);
        scrollIntoView(btn);
        jsClick(btn);
        System.out.println("Clicked 'See details' link — waiting for 'Shipping, returns, and payments' popup");

        try {
            WebDriverWait shortWait =
                new WebDriverWait(driver, Duration.ofSeconds(8));
            shortWait.until(
                ExpectedConditions.visibilityOfElementLocated(SHIPPING_POPUP));
            System.out.println("'Shipping, returns, and payments' popup opened");
        } catch (Exception e) {
            System.out.println("'Shipping, returns, and payments' popup slow to appear — continuing");
        }
    }

    public String getShippingPopupText() {

        try {
            WebElement countrySelect = waitForVisible(DELIVERY_TO_SELECT);
            org.openqa.selenium.support.ui.Select select =
                new org.openqa.selenium.support.ui.Select(countrySelect);
            String selectedCountry =
                select.getFirstSelectedOption().getText().trim();
            System.out.println(
                "'Shipping, returns, and payments' popup — Delivery to: "
                + selectedCountry);
        } catch (Exception e) {
            System.out.println(
                "'Shipping, returns, and payments' popup — could not read Delivery to: "
                + e.getMessage());
        }

        WebElement shipsTo = waitForVisible(SHIPS_TO_SECTION);
        String shipsToText = jsGetText(shipsTo).trim();
        boolean bulgariaFound = shipsToText.toLowerCase().contains("bulgaria");
        System.out.println(
            "'Shipping, returns, and payments' popup — Bulgaria in Ships to list: "
            + bulgariaFound);

        closeShippingPopup();

        return shipsToText;
    }

    public void closeShippingPopup() {
        try {
            WebDriverWait shortWait =
                new WebDriverWait(driver, Duration.ofSeconds(8));
            WebElement closeBtn = shortWait.until(
                ExpectedConditions.presenceOfElementLocated(POPUP_CLOSE_BUTTON));
            scrollIntoView(closeBtn);
            jsClick(closeBtn);
            shortWait.until(
                ExpectedConditions.invisibilityOfElementLocated(SHIPPING_POPUP));
            System.out.println(
                "'Shipping, returns, and payments' popup — closed via X button");
        } catch (Exception e) {
            driver.findElement(By.tagName("body"))
                  .sendKeys(org.openqa.selenium.Keys.ESCAPE);
            System.out.println(
                "'Shipping, returns, and payments' popup — closed via Escape (fallback)");
        }
    }

    public void selectQuantity(String qty) {
        wait.until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
            .executeScript("return document.readyState").equals("complete"));

        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("window.scrollBy(0, 400)");

        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        WebElement input = waitForVisible(By.id("qtyTextBox"));
        scrollIntoView(input);
        input.clear();
        input.sendKeys(qty);
        System.out.println("Quantity set to: " + qty);
    }
    
    public void clickAddToCart() {
        WebElement btn = waitForClickable(ADD_TO_CART_BUTTON);
        scrollIntoView(btn);
        btn.click();
        System.out.println("Clicked Add to cart");

        WebElement seeInCart = waitForVisible(SEE_IN_CART_BUTTON);
        seeInCart.click();
        System.out.println("Clicked See in cart");
    }
}