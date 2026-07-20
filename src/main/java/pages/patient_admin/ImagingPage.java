package pages.patient_admin;

import actionUtilies.UIActions;
import drivers.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.menu.InnerMenuPage;

import java.util.List;

@Slf4j
public class ImagingPage extends BasePage {

    private final InnerMenuPage innerMenuPage = new InnerMenuPage();
    
    // Locators for imaging list
    private final By imagingRowsBy = By.xpath("//tr[contains(@class, 'xrayRow')]");
    private final By pacsIconBy = By.xpath(".//img[contains(@src, 'pacs-icon')]");
    private final By pacsModalBy = By.xpath("//div[role='dialog']//iframe");

    public ImagingPage() {
        // Constructor without super() to avoid issues with other class structures
    }

    /**
     * Navigate to the Imaging section from the Patient Manager menu
     */
    public void navigateToImaging() {
        try {
            innerMenuPage.navigateToMenuEntry("מנהל חולים",false);
            innerMenuPage.navigateToMenuEntry("הדמיה",true);
            log.info("✓ Successfully navigated to Imaging section");
        } catch (Exception e) {
            log.error("✗ Error while navigating to Imaging: {}", e.getMessage());
            throw new RuntimeException("Failed to navigate to Imaging section: " + e.getMessage());
        }
    }

    /**
     * Get all available imaging studies in the current view
     * @return List of WebElements representing imaging rows
     */
    public List<WebElement> getImagingList() {
        return UIActions.findElementsWithWait(imagingRowsBy);
    }

    /**
     * Get the count of imaging studies available
     * @return number of imaging studies
     */
    public int getImagingCount() {
        List<WebElement> studies = getImagingList();
        return studies.size();
    }

    /**
     * Open the first imaging study in PACS viewer by clicking the PACS icon
     */
    public void openFirstImagingInPacs() {
        try {
            List<WebElement> studies = getImagingList();
            
            if (studies.isEmpty()) {
                throw new RuntimeException("No imaging studies available in the list");
            }
            
            WebElement firstStudy = studies.get(0);
            
            // Find PACS icon within the first row
            WebElement pacsIcon = firstStudy.findElement(pacsIconBy);
            
            // Scroll into view
            ((JavascriptExecutor) DriverManager.getInstance()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", 
                pacsIcon
            );
            
            // Click on PACS icon
            UIActions.click(pacsIcon);
            
            // Wait for modal to load
            Thread.sleep(2000);
            UIActions.waitForSpinnerToDisappear();
            
            log.info("✓ PACS viewer opened successfully");
        } catch (Exception e) {
            log.error("✗ Error while opening PACS viewer: {}", e.getMessage());
            throw new RuntimeException("Failed to open PACS viewer: " + e.getMessage());
        }
    }

    /**
     * Open a specific imaging study by index in PACS viewer
     * @param studyIndex the index of the imaging study to open (0-based)
     */
    public void openImagingByIndexInPacs(int studyIndex) {
        try {
            List<WebElement> studies = getImagingList();
            
            if (studyIndex >= studies.size() || studyIndex < 0) {
                throw new RuntimeException("Study index out of range. Total studies: " + studies.size());
            }
            
            WebElement study = studies.get(studyIndex);
            
            // Find PACS icon within the specified row
            WebElement pacsIcon = study.findElement(pacsIconBy);
            
            // Scroll into view
            ((JavascriptExecutor) DriverManager.getInstance()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", 
                pacsIcon
            );
            
            // Click on PACS icon
            UIActions.click(pacsIcon);
            
            // Wait for modal to load
            Thread.sleep(2000);
            UIActions.waitForSpinnerToDisappear();
            
            log.info("✓ PACS viewer opened for study at index {}", studyIndex);
        } catch (Exception e) {
            log.error("✗ Error while opening PACS viewer at index {}: {}", studyIndex, e.getMessage());
            throw new RuntimeException("Failed to open PACS viewer: " + e.getMessage());
        }
    }

    /**
     * Verify that PACS viewer has opened (check for iframe)
     * @return true if PACS viewer is displayed, false otherwise
     */
    public boolean isPacsViewerOpened() {
        try {
            // Retry logic to wait for iframe to load
            int maxRetries = 10;
            int retryCount = 0;
            
            while (retryCount < maxRetries) {
                try {
                    // Look for any iframe in the page
                    List<WebElement> iframes = DriverManager.getInstance().findElements(By.xpath("//iframe"));
                    if (!iframes.isEmpty()) {
                        log.info("✓ Found {} iframe(s) - PACS viewer is open", iframes.size());
                        return true;
                    }
                } catch (Exception e) {
                    // Continue to retry
                }
                
                Thread.sleep(500); // Wait 500ms before retry
                retryCount++;
            }
            
            log.info("✗ No iframe found after {} retries", maxRetries);
            return false;
        } catch (Exception e) {
            log.error("✗ Error checking PACS viewer: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get current URL to verify imaging context
     * @return current URL as String
     */
    public String getCurrentUrl() {
        return DriverManager.getInstance().getCurrentUrl();
    }

    /**
     * Verify that we are on the imaging page
     * @return true if URL contains 'xray', false otherwise
     */
    public boolean isOnImagingPage() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("xray");
    }
}
