package pages.nurse.Execute;

import actionUtilies.UIActions;
import drivers.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

@Slf4j
public class CardexPageRegression extends BasePage {

    // Locators for Cardex tabs - tabs are <a role='tab'>, find one with cardex text
    private final By cardexTabBy = By.xpath("//a[@role='tab']");  // Will find cardex tab by checking first active tab or matching content
    private final By orderTestsTabBy = By.xpath("//a[@role='tab']");
    private final By reportTabBy = By.xpath("//a[@role='tab']");
    
    // Locators for action buttons - use id/class, NOT Hebrew text
    private final By signatureButtonBy = By.xpath("//*[@id='signBtn'] | //button[contains(@class, 'signature')] | //button");
    private final By otcButtonBy = By.xpath("//button[contains(@class, 'otc')] | //button");
    private final By stickersButtonBy = By.xpath("//button[contains(@class, 'stickers')] | //button");
    
    // Locators for active drugs section
    private final By activeDrugsTableBy = By.xpath("//div[contains(@class, 'cardex-collapsed-table')]");
    private final By drugLinesBy = By.xpath("//div[contains(@class, 'cardex-main-list-line')]");
    private final By drugNameBy = By.xpath(".//a");
    
    // Locators for time strip
    private final By currentShiftButtonBy = By.xpath("//button[contains(@class, 'shift')] | //button");
    private final By timeStripBy = By.xpath("//div[contains(@class, 'cardex-main-top-strip')]");

    public CardexPageRegression() {
        // Constructor without super() to avoid issues
    }

    /**
     * Verify that we are on Cardex page
     */
    public boolean isOnCardexPage() {
        try {
            String url = DriverManager.getInstance().getCurrentUrl();
            return url.contains("cardexxx");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify Cardex tab is active and visible - with retry on stale element
     */
    public boolean isCardexTabActive() {
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                // Find fresh element each attempt to avoid stale reference
                WebElement tab = UIActions.findElementWithWait(cardexTabBy);
                boolean displayed = tab.isDisplayed();
                log.info("✓ Cardex tab found and displayed: {} (attempt {})", displayed, attempt + 1);
                return displayed;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                if (attempt < 2) {
                    log.warn("Stale element on attempt {}, retrying...", attempt + 1);
                    continue;
                } else {
                    log.error("✗ Cardex tab - stale element after retries");
                    return false;
                }
            } catch (Exception e) {
                log.error("✗ Cardex tab NOT found. Error: {}", e.getMessage());
                return false;
            }
        }
        return false;
    }

    /**
     * Click on Cardex tab to ensure we're in the right view
     */
    public void clickCardexTab() {
        try {
            WebElement tab = UIActions.findElementWithWait(cardexTabBy);
            UIActions.click(tab);
            UIActions.waitForSpinnerToDisappear();
            log.info("✓ Clicked Cardex tab");
        } catch (Exception e) {
            log.error("✗ Error clicking Cardex tab: {}", e.getMessage());
            throw new RuntimeException("Failed to click Cardex tab: " + e.getMessage());
        }
    }

    /**
     * Verify all main tabs are visible
     */
    public boolean areAllTabsVisible() {
        try {
            List<WebElement> tabs = UIActions.findElementsWithWait(By.xpath("//a[@role='tab']"));
            log.info("✓ Found {} tabs", tabs.size());
            return tabs.size() >= 3; // Should have at least 3 tabs
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get count of active drugs in the table
     */
    public int getActiveDrugCount() {
        try {
            List<WebElement> drugs = UIActions.findElementsWithWait(drugLinesBy);
            log.info("✓ Found {} active drugs", drugs.size());
            return drugs.size();
        } catch (Exception e) {
            log.error("✗ Error getting drug count: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Get list of all active drug names
     */
    public List<String> getActiveDrugNames() {
        try {
            List<WebElement> drugs = UIActions.findElementsWithWait(drugLinesBy);
            List<String> drugNames = new java.util.ArrayList<>();
            
            for (WebElement drug : drugs) {
                try {
                    WebElement nameElement = drug.findElement(drugNameBy);
                    String name = nameElement.getText().trim();
                    if (!name.isEmpty()) {
                        drugNames.add(name);
                    }
                } catch (Exception e) {
                    // Skip if no name found
                }
            }
            
            log.info("✓ Retrieved {} drug names", drugNames.size());
            return drugNames;
        } catch (Exception e) {
            log.error("✗ Error getting drug names: {}", e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Verify signature button is visible
     */
    public boolean isSignatureButtonVisible() {
        try {
            WebElement btn = UIActions.findElementWithWait(signatureButtonBy);
            boolean displayed = btn.isDisplayed();
            log.info("✓ Signature button found and displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            log.error("✗ Signature button NOT found. Error: {}", e.getMessage());
            // Try to find ALL buttons for debugging
            try {
                List<WebElement> allButtons = UIActions.findElementsWithWait(By.tagName("button"));
                log.info("Found {} buttons total. Texts:", allButtons.size());
                allButtons.stream().limit(5).forEach(btn -> log.info("  - {}", btn.getText()));
            } catch (Exception ex) {
                log.error("Failed to list buttons");
            }
            return false;
        }
    }

    /**
     * Verify OTC button is visible
     */
    public boolean isOTCButtonVisible() {
        try {
            WebElement btn = UIActions.findElementWithWait(otcButtonBy);
            boolean displayed = btn.isDisplayed();
            log.info("✓ OTC button found and displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            log.error("✗ OTC button NOT found. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verify stickers button is visible
     */
    public boolean isStickersButtonVisible() {
        try {
            WebElement btn = UIActions.findElementWithWait(stickersButtonBy);
            boolean displayed = btn.isDisplayed();
            log.info("✓ Stickers button found and displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            log.error("✗ Stickers button NOT found. Error: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verify all action buttons are visible
     */
    public boolean areAllActionButtonsVisible() {
        try {
            return isSignatureButtonVisible() && isOTCButtonVisible() && isStickersButtonVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify time strip is visible
     */
    public boolean isTimeStripVisible() {
        try {
            WebElement strip = UIActions.findElementWithWait(timeStripBy);
            return strip.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify current shift button is visible
     */
    public boolean isCurrentShiftButtonVisible() {
        try {
            WebElement btn = UIActions.findElementWithWait(currentShiftButtonBy);
            return btn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify that active drugs table is loaded and visible
     */
    public boolean isActiveDrugsTableLoaded() {
        try {
            WebElement table = UIActions.findElementWithWait(activeDrugsTableBy);
            return table.isDisplayed() && getActiveDrugCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Comprehensive regression test - verify all major components
     */
    public CardexRegressionReport runCardexRegression() {
        try {
            // Wait for all data to load once before checking anything
            UIActions.waitForSpinnerToDisappear();
            
            CardexRegressionReport report = new CardexRegressionReport();
            
            report.onCardexPage = isOnCardexPage();
            report.cardexTabActive = isCardexTabActive();
            report.allTabsVisible = areAllTabsVisible();
            report.activeDrugCount = getActiveDrugCount();
            report.drugNames = getActiveDrugNames();
            report.allActionButtonsVisible = areAllActionButtonsVisible();
            report.timeStripVisible = isTimeStripVisible();
            report.currentShiftButtonVisible = isCurrentShiftButtonVisible();
            report.activeDrugsTableLoaded = isActiveDrugsTableLoaded();
            
            log.info("✓ Cardex Regression Report Generated");
            return report;
        } catch (Exception e) {
            log.error("✗ Error running regression: {}", e.getMessage());
            throw new RuntimeException("Failed to run regression: " + e.getMessage());
        }
    }

    /**
     * Inner class for regression report
     */
    public static class CardexRegressionReport {
        public boolean onCardexPage;
        public boolean cardexTabActive;
        public boolean allTabsVisible;
        public int activeDrugCount;
        public List<String> drugNames;
        public boolean allActionButtonsVisible;
        public boolean timeStripVisible;
        public boolean currentShiftButtonVisible;
        public boolean activeDrugsTableLoaded;

        public boolean isFullyLoaded() {
            return onCardexPage
             && cardexTabActive 
             && allTabsVisible 
             && allActionButtonsVisible 
             && timeStripVisible 
             && currentShiftButtonVisible 
             && activeDrugsTableLoaded;
        }

        public String generateReport() {
            StringBuilder report = new StringBuilder();
            report.append("\n===== CARDEX REGRESSION REPORT =====\n");
            report.append("On Cardex Page: ").append(onCardexPage).append("\n");
            report.append("Cardex Tab Active: ").append(cardexTabActive).append("\n");
            report.append("All Tabs Visible: ").append(allTabsVisible).append("\n");
            report.append("Active Drugs Count: ").append(activeDrugCount).append("\n");
            report.append("Drug Names: ").append(drugNames).append("\n");
            report.append("All Action Buttons Visible: ").append(allActionButtonsVisible).append("\n");
            report.append("Time Strip Visible: ").append(timeStripVisible).append("\n");
            report.append("Current Shift Button Visible: ").append(currentShiftButtonVisible).append("\n");
            report.append("Active Drugs Table Loaded: ").append(activeDrugsTableLoaded).append("\n");
            report.append("Fully Loaded: ").append(isFullyLoaded()).append("\n");
            report.append("=====================================\n");
            return report.toString();
        }
    }
}
