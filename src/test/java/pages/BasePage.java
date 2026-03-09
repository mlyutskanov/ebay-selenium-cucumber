package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // ── Waits ─────────────────────────────────────────────────────────────────

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(locator));
    }

    protected WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions
            .visibilityOf(element));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions
            .elementToBeClickable(locator));
    }

    protected WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions
            .elementToBeClickable(element));
    }

    // ── Common interactions ───────────────────────────────────────────────────

    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement el = waitForVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForVisible(locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ── JavaScript helpers ────────────────────────────────────────────────────

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", element);
    }

    protected String jsGetText(WebElement element) {
        return (String) ((JavascriptExecutor) driver)
            .executeScript("return arguments[0].textContent;", element);
    }

    // ── Window handling ───────────────────────────────────────────────────────

    protected void switchToNewWindow(String originalWindow) {
        wait.until(d -> d.getWindowHandles().size() > 1);

        driver.getWindowHandles()
              .stream()
              .filter(w -> !w.equals(originalWindow))
              .findFirst()
              .ifPresent(w -> driver.switchTo().window(w));

        wait.until(d -> !d.getCurrentUrl().equals("about:blank")
                     && !d.getCurrentUrl().isEmpty());

        System.out.println("Switched to: " + driver.getCurrentUrl());
    }
}