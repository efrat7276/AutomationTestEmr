package pages.menu;
import actionUtilies.UIActions;
import drivers.DriverManager;
import lombok.extern.slf4j.Slf4j;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import pages.BasePage;

@Slf4j
public class InnerMenuPage extends BasePage {

    private final String MENU_ITEM_XPATH_TEMPLATE =
            "//*[normalize-space(text())='%s']";

    /**
     * מקבלת את שם כניסת התפריט בעברית ולוחצת על הכניסה המתאימה.
     * * @param entryName שם הכניסה המדויק (למשל, "תרופות", "רשימת מטופלים").
     */
    public void navigateToMenuEntry(String entryName) {
        String dynamicXpath = String.format(MENU_ITEM_XPATH_TEMPLATE, entryName);
        By targetLocator = By.xpath(dynamicXpath);
        log.info("Navigating to menu entry: " + entryName );
        try {
            UIActions.waitForSpinnerToDisappear();
            UIActions.waitForElementClickable(targetLocator);
            UIActions.click(targetLocator);
        }
        catch (Exception e) {
            log.error("❌ Failed to navigate to menu entry '" + entryName + "'. Ensure the name is correct. Error: " + e.getMessage());
            throw new RuntimeException("Failed to navigate in the inner menu", e);
        }
        UIActions.waitForSpinnerToDisappear();
        assertTrue(isMenuEntryDisplayed(entryName), "Failed to navigate to menu entry: " + entryName);
        log.info("Successfully navigated to menu entry: " + entryName);
    }

    public boolean isMenuEntryDisplayed(String entryName) {
        String dynamicXpath = String.format(MENU_ITEM_XPATH_TEMPLATE, entryName);
        By targetLocator = By.xpath(dynamicXpath);
        try {
            return DriverManager.getInstance().findElement(targetLocator).isDisplayed();
        }
         catch (Exception e) {
            return false;
        }
    }

     private By getTitleSpanLocator() {
        return By.xpath("(//span[contains(@class,'lan-title-ng-view-Hierarchy-a')])[1]");
    }
     public boolean isTitleDisplayed(String expectedTitle) {
        String actualText = UIActions.getText(getTitleSpanLocator()).trim();
        log.debug("DEBUG: actualText = '{}' | expected = '{}'", actualText, expectedTitle);
        return actualText.contains(expectedTitle);
    }
}
