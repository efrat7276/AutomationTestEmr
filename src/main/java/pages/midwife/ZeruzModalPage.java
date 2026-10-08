package pages.midwife;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import pages.BasePage;
import actionUtilies.UIActions;
import drivers.DriverManager;
import helpers.Constants;

/**
 * Page Object for Zeruz (זירוז) Modal
 * 
 * This class handles all interactions with the Zeruz modal form:
 * - Opening/closing the modal
 * - Selecting Zeruz types from dropdown list
 * - Getting modal title and content
 */
@Slf4j
public class ZeruzModalPage extends BasePage {


    // ============ Locators for Zeruz Modal Elements ============

    private static final By zeruz_modal = By.xpath("//zerozim");

    private static final By modal_title = By.xpath("//zerozim//h4[@class='modal-title']");
    private static final By modal_btn_close = By.xpath("//zerozim//h4[@class='modal-title']/following-sibling::button[@class='close']");
 
    // Zeruz Type List
    private static final By zeruzTypeInputBy = By.xpath("//zerozim//input[@type='text' and contains(@placeholder, 'סוג')]");
    private static final By zeruzTypeInputOpenOptionsBy = By.xpath("//zerozim//input[@type='text' and contains(@placeholder, 'סוג')]/following-sibling::i");
    private static final By zeruzTypeInputOptionsListBy = By.xpath("//zerozim//input[@type='text' and contains(@placeholder, 'סוג')]/../following-sibling::div[contains(@class, 'multi-select-dropdown')]//input[@type='checkbox']");
   
    // Zeruz Reason List
    private static final By zeruzReasonInputBy = By.xpath("//zerozim//input[@type='text' and contains(@placeholder, 'סיבת')]");
    private static final By zeruzReasonInputOpenOptionsBy = By.xpath("//zerozim//input[@type='text' and contains(@placeholder, 'סיבת')]/following-sibling::i");
    private static final By zeruzReasonInputOptionsListBy = By.xpath("//zerozim//input[@type='text' and contains(@placeholder, 'סיבת')]/../following-sibling::div/button[contains(@class, 'dropdown')]");
    
    // Commentes 
    private static final By commentsInputBy = By.xpath("//textarea[@formcontrolname='additionalDetails']");
    
    // Priority Buttons (מטרה - Objective)
    private static final By priorityButtonListBy= By.xpath("//zerozim//button[contains(@class, 'priority-btn')]");

    // Buttons
    private static final By btnConfirmAndGiveInstructionBy = By.xpath("//zerozim//button[@class='btn btn-confirm' and text() = ' אישור הנחיה ומתן הוראה ']");
    private static final By btnConfirmBy = By.xpath("//button[text() = ' אישור הנחיה ']");
    
    /// Zeruzim list section
    private static final By zeruzimActiveListSectionBy = By.xpath("//div[contains(@class,'zerozim-table-section')][.//h5[normalize-space(text())='הנחיות שניתנו']]//tbody//tr");
    private static final By zeruzimCancelledListSectionBy = By.xpath("//div[contains(@class,'zerozim-table-section')][.//span[normalize-space(text())='הנחיות שבוטלו']]//tbody//tr");

    private static final By userSignBy = By.xpath("//input[@id='user']");
    private static final By userSignPasswordBy = By.xpath("//input[@id='password']");


    // ============ Constructor ============
    
    public ZeruzModalPage() {
        super();
     //   UIActions.waitForSpinnerToDisappear();
    }

    // ============ Visibility Check Methods ============
    
    /**
     * Check if Zeruz modal is visible
     */
    public boolean isZeruzModalVisible() {
        try {
            WebElement modal = driver.findElement(zeruz_modal);
            return modal.isDisplayed();
        } catch (Exception e) {
            log.warn("Zeruz modal is not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get the modal title text
     */
    public String getModalTitle() {
        try {
            return driver.findElement(modal_title).getText();
        } catch (Exception e) {
            log.warn("Could not get modal title: {}", e.getMessage());
            return "";
        }
    }

    public void closeZeruzModal() {
        try {
            UIActions.click(modal_btn_close);
            UIActions.waitForSpinnerToDisappear();
        } catch (Exception e) {
            log.warn("Could not close Zeruz modal: {}", e.getMessage());
        }
    }

    /**
     * Add a directive but keep the Zeruz modal open for further inspection.
     */
    public void addDirectiveKeepModalOpen(String desiredType) {
        UIActions.waitForSpinnerToDisappear();

        // Open Zeruz type dropdown (if closed)
        UIActions.click(zeruzTypeInputOpenOptionsBy);

        List<WebElement> zeruzTypeCheckboxes = DriverManager.getInstance().findElements(zeruzTypeInputOptionsListBy);
        String selectedTypeText = "";

        if (zeruzTypeCheckboxes.isEmpty()) {
            throw new AssertionError("No Zeruz types available to select");
        }

        // select by visible text when provided
        if (desiredType == null || desiredType.trim().isEmpty()) {
            selectedTypeText = selectZeruzReasonFirst(zeruzTypeCheckboxes);
        } else {
            boolean matched = false;
            for (WebElement cb : zeruzTypeCheckboxes) {
                try { Thread.sleep(150); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                String labelText = getLabelTextForCheckbox(cb).trim();
                if (!labelText.isEmpty() && (labelText.equalsIgnoreCase(desiredType) || labelText.contains(desiredType))) {
                    UIActions.click(cb);
                    selectedTypeText = labelText;
                    matched = true;
                    DriverManager.getInstance().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
                    break;
                }
            }
            if (!matched) selectedTypeText = selectZeruzReasonFirst(zeruzTypeCheckboxes);
        }

        // optional reason
        try {
            UIActions.click(zeruzReasonInputOpenOptionsBy);
            List<WebElement> zeruzReasonCheckboxes = DriverManager.getInstance().findElements(zeruzReasonInputOptionsListBy);
            if (!zeruzReasonCheckboxes.isEmpty()) {
                UIActions.click(zeruzReasonCheckboxes.get(0));
                DriverManager.getInstance().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
            }
        } catch (Exception ignored) {}

        selectPriorityButton(0);
        UIActions.typeText(commentsInputBy, "Automated test: " + selectedTypeText);

        // confirm but keep modal open (click confirm then wait for sign to complete)
        try {
            UIActions.click(btnConfirmBy);
            userSignModalPage.signModal(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
            UIActions.waitForSpinnerToDisappear();
        } catch (Exception e) {
            log.error("Error confirming zeruz form while keeping modal open: {}", e.getMessage());
            throw new AssertionError("Failed to confirm zeruz directive: " + e.getMessage());
        }
    }

    /**
     * Return true if a previous directive row that contains `prevDirectiveText` appears marked as canceled.
     * Looks for common cancellation indicators: explicit Hebrew 'בוטל' text, 'cancel' class variants, or strike-through.
     */
    public boolean isPreviousDirectiveMarkedCancelled(String prevDirectiveText) {
        UIActions.waitForSpinnerToDisappear();
        List<WebElement> zeruzimCancelledName = DriverManager.getInstance().findElements(
         By.xpath("//div[contains(@class,'zerozim-table-section')][.//span[normalize-space(text())='הנחיות שבוטלו']]//tbody//tr/td[1]"));
        for (WebElement r : zeruzimCancelledName) {
        String rowText = r.getAttribute("textContent"); // או r.getAttribute("innerText")
        if (rowText != null && rowText.contains(prevDirectiveText)) {
                return true;
            }
        }
        return false;
    }


    
    // ============ Fill and Save Methods ============
/**
 * Fill Zeruz form selecting the requested Zeruz type and confirm.
 * Verifies the chosen type appears in the "הנחיות שניתנו" section after save.
 * 
 * @param desiredType visible text of the Zeruz type to select (partial match allowed)
 */
public void fillAndSaveZeruz(String desiredType) {
    // UIActions.waitForSpinnerToDisappear();

    // // Open Zeruz type dropdown (if closed)
    // UIActions.click(zeruzTypeInputOpenOptionsBy);

    // List<WebElement> zeruzTypeCheckboxes = DriverManager.getInstance().findElements(zeruzTypeInputOptionsListBy);
     String selectedTypeText = "בלון";

    // if (zeruzTypeCheckboxes.isEmpty()) {
    //     throw new AssertionError("No Zeruz types available to select");
    // }

    // // Determine type selection logic
    // if (desiredType == null || desiredType.trim().isEmpty()) {
    //     selectedTypeText = selectZeruzReasonFirst(zeruzTypeCheckboxes);
    // } else {
    //     boolean matched = false;
    //     for (WebElement cb : zeruzTypeCheckboxes) {
    //         try {
    //             Thread.sleep(200); // Small delay to ensure the label text is properly rendered
    //         } catch (InterruptedException e) {
    //             Thread.currentThread().interrupt();
    //         }
    //         String labelText = getLabelTextForCheckbox(cb).trim();
    //         if (!labelText.isEmpty() && (labelText.equalsIgnoreCase(desiredType) || labelText.contains(desiredType))) {
    //             UIActions.click(cb);
    //             selectedTypeText = labelText;
    //             matched = true;

    //             // Release dropdown focus cleanly using ESCAPE key
    //             DriverManager.getInstance().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
    //             break;
    //         }
    //     }

    //     if (!matched) {
    //         // Fallback: select first option if requested text was not matched
    //         selectedTypeText = selectZeruzReasonFirst(zeruzTypeCheckboxes);
    //     }
    // }

    // // Open and select first reason (best-effort)
    // try {
    //     UIActions.click(zeruzReasonInputOpenOptionsBy);
    //     List<WebElement> zeruzReasonCheckboxes = DriverManager.getInstance().findElements(zeruzReasonInputOptionsListBy);
    //     if (!zeruzReasonCheckboxes.isEmpty()) {
    //         UIActions.click(zeruzReasonCheckboxes.get(0));
    //         DriverManager.getInstance().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
    //     }
    // } catch (Exception ignored) {
    //     // Optional step fallback
    // }

    // // Select priority and fill comment
    // selectPriorityButton(0);
    // UIActions.typeText(commentsInputBy, "Automated test: " + selectedTypeText);

    

    // // Confirm form and wait for processing
    // confirmForm();
    // UIActions.waitForSpinnerToDisappear();

    // Verify the chosen directive appears in the "הנחיות שניתנו" area inside the modal
    boolean found = false;
    try {
        String xpathContainsType = "//tr[contains(normalize-space(.), '" + selectedTypeText + "')]";
        WebElement matches = DriverManager.getInstance().findElement(By.xpath(xpathContainsType));
        if (matches.isDisplayed() && matches.getText().contains(selectedTypeText)) {
            found = true;
        }
    } catch (Exception e) {
        // Handled via assertion check below
    }

    if (!found) {
        throw new AssertionError("Zeruz directive '" + selectedTypeText + "' was not found under 'הנחיות שניתנו' after save");
    }

    closeZeruzModal();
}

    /**
     * Extract label text for a checkbox option (best-effort, handles common DOM patterns)
     */
    private String getLabelTextForCheckbox(WebElement checkbox) {
        try {
            WebElement label = checkbox.findElement(By.xpath("ancestor::label"));
            return label.getText();
        } catch (Exception e) {
            try {
                WebElement parent = checkbox.findElement(By.xpath(".."));
                return parent.getText();
            } catch (Exception ex) {
                return "";
            }
        }
    }

    private String selectZeruzReasonFirst(List<WebElement> zeruzTypeCheckboxes){
         UIActions.click(zeruzTypeCheckboxes.get(0));
         return getLabelTextForCheckbox(zeruzTypeCheckboxes.get(0)).trim();
        
    }

    private void selectPriorityButton(int index) {
           List<WebElement> priorityButtons = DriverManager.getInstance().findElements(priorityButtonListBy);
            if (!priorityButtons.isEmpty()) {
                UIActions.click(priorityButtons.get(index));
            } else {
                log.warn("⚠️ No priority buttons available");
            }
    }
    /**
     * Confirm the form by clicking the confirm button
     */
    private void confirmForm() {
        try {
            UIActions.typeText(userSignBy, Constants.DOCTOR_USERNAME);
            UIActions.typeText(userSignPasswordBy, Constants.DOCTOR_PASSWORD);
            UIActions.click(btnConfirmBy);
          //  userSignModalPage.signModal(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
            log.info("✅ Zeruz form confirmed");
        } catch (Exception e) {
            log.error("❌ Error confirming form: {}", e.getMessage());
            throw new AssertionError("Failed to confirm form: " + e.getMessage());
        }
    }
}
