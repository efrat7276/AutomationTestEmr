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
public class DocumentsPage extends BasePage {

    private final InnerMenuPage innerMenuPage = new InnerMenuPage();
    
    // Locators for documents list
    private final By documentRowsBy = By.xpath("//tr[contains(@class, 'docuRow')]");
    private final By documentIframeBy = By.xpath("//iframe");

    public DocumentsPage() {
        // Constructor without super() to avoid issues with other class structures
    }

    /**
     * Navigate to the Documents section from the Patient Manager menu
     */
    public void navigateToDocuments() {
        try {
            // Use innerMenuPage to navigate to "מנהל חולים" menu
            innerMenuPage.navigateToMenuEntry("מנהל חולים",false);
            
            // Use innerMenuPage to navigate to "מסמכים" submenu
            innerMenuPage.navigateToMenuEntry("מסמכים",true);
            
            log.info("✓ Successfully navigated to Documents section");
        } catch (Exception e) {
            log.error("✗ Error while navigating to Documents: {}", e.getMessage());
            throw new RuntimeException("Failed to navigate to Documents section: " + e.getMessage());
        }
    }

    /**
     * Get all available documents in the current view
     * @return List of WebElements representing document rows
     */
    public List<WebElement> getDocumentsList() {
        return UIActions.findElementsWithWait(documentRowsBy);
    }

    /**
     * Get the count of documents available
     * @return number of documents
     */
    public int getDocumentsCount() {
        List<WebElement> documents = getDocumentsList();
        return documents.size();
    }

    /**
     * Open the first document in the list
     */
    public void openFirstDocument() {
        try {
            List<WebElement> documents = getDocumentsList();
            
            if (documents.isEmpty()) {
                throw new RuntimeException("No documents available in the list");
            }
            
            WebElement firstDocument = documents.get(0);
            
            // Scroll into view
            ((JavascriptExecutor) DriverManager.getInstance()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", 
                firstDocument
            );
            
            // Click on first document
            UIActions.click(firstDocument);
            
            // Wait for document to load
            Thread.sleep(2000);
            UIActions.waitForSpinnerToDisappear();
            
            log.info("✓ Document opened successfully");
        } catch (Exception e) {
            log.error("✗ Error while opening document: {}", e.getMessage());
            throw new RuntimeException("Failed to open document: " + e.getMessage());
        }
    }

    /**
     * Open a specific document by index
     * @param documentIndex the index of the document to open (0-based)
     */
    public void openDocumentByIndex(int documentIndex) {
        try {
            List<WebElement> documents = getDocumentsList();
            
            if (documentIndex >= documents.size() || documentIndex < 0) {
                throw new RuntimeException("Document index out of range. Total documents: " + documents.size());
            }
            
            WebElement document = documents.get(documentIndex);
            
            // Scroll into view
            ((JavascriptExecutor) DriverManager.getInstance()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", 
                document
            );
            
            // Click on document
            UIActions.click(document);
            
            // Wait for document to load
            Thread.sleep(2000);
            UIActions.waitForSpinnerToDisappear();
            
            log.info("✓ Document at index {} opened successfully", documentIndex);
        } catch (Exception e) {
            log.error("✗ Error while opening document at index {}: {}", documentIndex, e.getMessage());
            throw new RuntimeException("Failed to open document: " + e.getMessage());
        }
    }

    /**
     * Verify that a document viewer has opened (check for iframe)
     * @return true if document viewer is displayed, false otherwise
     */
    public boolean isDocumentViewerOpened() {
        try {
            List<WebElement> iframes = DriverManager.getInstance().findElements(documentIframeBy);
            return !iframes.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get current URL to verify document context
     * @return current URL as String
     */
    public String getCurrentUrl() {
        return DriverManager.getInstance().getCurrentUrl();
    }

    /**
     * Verify that we are on the documents page
     * @return true if URL contains 'documents', false otherwise
     */
    public boolean isOnDocumentsPage() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("documents");
    }
}
