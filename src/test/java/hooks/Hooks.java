package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import java.time.Duration;

public class Hooks {

    private static final By LOCATION_POPUP_CONFIRM =
        By.cssSelector("button.submit-button.btn--primary");

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("\n========================================");
        System.out.println("STARTING: " + scenario.getName());
        System.out.println("TAGS:     " + scenario.getSourceTagNames());
        System.out.println("========================================");

        DriverManager.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("\n========================================");
        System.out.println("FINISHED: " + scenario.getName());
        System.out.println("STATUS:   " + scenario.getStatus());
        System.out.println("========================================\n");

        DriverManager.quitDriver();
    }

    // ── Shared utility: dismiss Bulgaria location popup ───────────────────────

    public static void dismissLocationPopupIfPresent(WebDriver driver) {
        try {
            WebDriverWait shortWait =
                new WebDriverWait(driver, Duration.ofSeconds(5));
            shortWait.until(ExpectedConditions
                .elementToBeClickable(LOCATION_POPUP_CONFIRM))
                .click();
            System.out.println("Location popup dismissed");

            shortWait.until(ExpectedConditions
                .invisibilityOfElementLocated(LOCATION_POPUP_CONFIRM));
        } catch (Exception e) {
            System.out.println("No location popup — continuing");
        }
    }
}