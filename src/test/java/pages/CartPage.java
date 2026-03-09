package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {

    // ── Locators ──────────────────────────────────────────────────────────────

    private static final By ITEMS_LABEL =
        By.xpath("//span[contains(text(),'Items (')]");

    private static final By ITEM_TOTAL_PRICE =
        By.cssSelector("[data-test-id='ITEM_TOTAL'] span");

    // ── Actions ──────────────────────────────────────────────────────────────

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getCartQuantity() {
        WebElement label = waitForVisible(ITEMS_LABEL);
        String fullText = label.getText().trim();
        String qty = fullText.replaceAll(".*\\((\\d+)\\).*", "$1");
        System.out.println("Cart items label: '" + fullText
            + "' — extracted quantity: " + qty);
        return qty;
    }

    public boolean isPriceDisplayed() {
        try {
            WebElement priceEl = waitForVisible(ITEM_TOTAL_PRICE);
            String priceText = priceEl.getText().trim();
            System.out.println("Cart item total price: " + priceText);
            return priceText.matches(".*\\d+[.,]\\d{2}.*");
        } catch (Exception e) {
            System.out.println("Item total price not found: " + e.getMessage());
            return false;
        }
    }
}