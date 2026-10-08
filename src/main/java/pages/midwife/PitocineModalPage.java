package pages.midwife;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import pages.BasePage;
import actionUtilies.UIActions;

/**
 * Page Object for Pitocine Rate Update Modal
 * 
 * This class handles all interactions with the Pitocine modal form:
 * - Opening/closing the modal
 * - Selecting rate options (Steady, Pause, Intravenous)
 * - Adding comments
 * - Saving and canceling the form
 */
@Slf4j
public class PitocineModalPage extends BasePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ============ Locators for Pitocine Modal Elements ============
    
    // Modal Container
    private static final By PITOCINE_MODAL = By.cssSelector("app-pitocin-modal");
    
    // Modal Header
    private static final By MODAL_HEADER = By.cssSelector("app-pitocin-modal div.modal-header");
    private static final By MODAL_TITLE = By.cssSelector("app-pitocin-modal h5.modal-title");
    
    // Modal Body
    private static final By MODAL_BODY = By.cssSelector("app-pitocin-modal div.modal-body");
    
    // Form Element
    private static final By PITOCINE_FORM = By.cssSelector("form.pitocin-form");
    
    // Radio Options Group
    private static final By RADIO_OPTIONS = By.cssSelector("div.radio-options");
    
    // Radio Button - Steady
    private static final By RADIO_STEADY = By.cssSelector("input#steady[type='radio']");
    private static final By RADIO_STEADY_LABEL = By.cssSelector("label[for='steady']");
    
    // Radio Button - Pause
    private static final By radioPauseBy = By.cssSelector("input#pause[type='radio']");
    private static final By radioPauseLabel = By.cssSelector("label[for='pause']");
    
    // Radio Button - Intravenous
    private static final By radioIntravenousBy = By.cssSelector("input#intravenous[type='radio']");
    private static final By radioIntravenousLabel = By.cssSelector("label[for='intravenous']");
    
    // Comments Textarea
    private static final By commentsTextareaBy = By.cssSelector("textarea.form-control.comments-textarea");
    
    // Comments Group
    private static final By commentsGroupBy = By.cssSelector("div.comments-group");
  
    // Buttons
    private static final By btnCancelBy = By.cssSelector("button.btn.btn-cancel");
    private static final By btnSubmitBy = By.cssSelector("button.btn.btn-submit");
    
    // ============ Constructor ============
    
    public PitocineModalPage () {
          super();
        UIActions.waitForSpinnerToDisappear();
        log.info("✅ PitocineModalPage initialized");
    }

    // ============ Visibility Check Methods ============
    
    /**
     * Check if Pitocine modal is visible
     */
    public boolean isPitocineModalVisible() {
        try {
            WebElement modal = driver.findElement(PITOCINE_MODAL);
            return modal.isDisplayed();
        } catch (Exception e) {
            log.warn("Pitocine modal is not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get the modal title text
     */
    public String getModalTitle() {
        try {
            return driver.findElement(MODAL_TITLE).getText();
        } catch (Exception e) {
            log.warn("Could not get modal title: {}", e.getMessage());
            return "";
        }
    }

    // ============ Radio Button Selection Methods ============
    
    /**
     * Select "Steady" rate option
     */
    public void selectSteadyOption() {
        try {
            WebElement steadyRadio = driver.findElement(RADIO_STEADY);
            if (!steadyRadio.isSelected()) {
                UIActions.click(RADIO_STEADY_LABEL);
                log.info("✅ Selected 'Steady' option");
            } else {
                log.info("✓ 'Steady' option already selected");
            }
        } catch (Exception e) {
            log.error("❌ Error selecting Steady option: {}", e.getMessage());
            throw new AssertionError("Failed to select Steady option: " + e.getMessage());
        }
    }

    /**
     * Select "Pause" rate option
     */
    public void selectPauseOption() {
        try {
            WebElement pauseRadio = driver.findElement(radioPauseBy);
            if (!pauseRadio.isSelected()) {
                UIActions.click(radioPauseLabel);
                log.info("✅ Selected 'Pause' option");
            } else {
                log.info("✓ 'Pause' option already selected");
            }
        } catch (Exception e) {
            log.error("❌ Error selecting Pause option: {}", e.getMessage());
            throw new AssertionError("Failed to select Pause option: " + e.getMessage());
        }
    }

    /**
     * Select "Intravenous" rate option
     */
    public void selectIntravenousOption() {
        try {
            WebElement intravenousRadio = driver.findElement(radioIntravenousBy);
            if (!intravenousRadio.isSelected()) {
                UIActions.click(radioIntravenousLabel);
                log.info("✅ Selected 'Intravenous' option");
            } else {
                log.info("✓ 'Intravenous' option already selected");
            }
        } catch (Exception e) {
            log.error("❌ Error selecting Intravenous option: {}", e.getMessage());
            throw new AssertionError("Failed to select Intravenous option: " + e.getMessage());
        }
    }


    /**
     * Get the comments field value
     */
    public String getCommentsValue() {
        try {
            return driver.findElement(commentsTextareaBy).getAttribute("value");
        } catch (Exception e) {
            log.warn("Could not get comments value: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Clear the comments field
     */
    public void clearComments() {
        try {
            WebElement commentsField = driver.findElement(commentsTextareaBy);
            commentsField.clear();
            log.info("✅ Comments cleared");
        } catch (Exception e) {
            log.error("❌ Error clearing comments: {}", e.getMessage());
            throw new AssertionError("Failed to clear comments: " + e.getMessage());
        }
    }

    // ============ Form Submission Methods ============
    
    /**
     * Submit the Pitocine form (Click Submit button)
     */
    public void submitForm() {
        try {
            UIActions.click(btnSubmitBy);
            log.info("✅ Pitocine form submitted");
        } catch (Exception e) {
            log.error("❌ Error submitting form: {}", e.getMessage());
            throw new AssertionError("Failed to submit form: " + e.getMessage());
        }
    }

    /**
     * Cancel the Pitocine form (Click Cancel button)
     */
    public void cancelForm() {
        try {
            UIActions.click(btnCancelBy);
            log.info("✅ Pitocine form cancelled");
        } catch (Exception e) {
            log.error("❌ Error cancelling form: {}", e.getMessage());
            throw new AssertionError("Failed to cancel form: " + e.getMessage());
        }
    }

    // ============ Complete Fill and Save Methods ============
    
    /**
     * Fill and save Pitocine form with steady rate option
     */
    public void fillAndSavePitocineFormSteady(String comments) {
        try {
            log.info("═══════════════════════════════════════════════════");
            log.info("🔄 Filling Pitocine form with STEADY option...");
            log.info("═══════════════════════════════════════════════════");
            
            selectSteadyOption();
           // fillComments(comments);
            
            log.info("✓ Form filled - proceeding to submit...");
            submitForm();
            
            log.info("✅ Pitocine form saved successfully (Steady)");
        } catch (Exception e) {
            log.error("❌ Failed to fill and save Pitocine form: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Fill and save Pitocine form with pause rate option
     */
    public void fillAndSavePitocineFormPause(String comments) {
        try {
            log.info("═══════════════════════════════════════════════════");
            log.info("⏸️  Filling Pitocine form with PAUSE option...");
            log.info("═══════════════════════════════════════════════════");
            
            selectPauseOption();
           // fillComments(comments);
            
            log.info("✓ Form filled - proceeding to submit...");
            submitForm();
            
            log.info("✅ Pitocine form saved successfully (Pause)");
        } catch (Exception e) {
            log.error("❌ Failed to fill and save Pitocine form: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Fill and save Pitocine form with intravenous rate option
     */
    public void fillAndSavePitocineFormIntravenous(String comments) {
        try {
            log.info("═══════════════════════════════════════════════════");
            log.info("💊 Filling Pitocine form with INTRAVENOUS option...");
            log.info("═══════════════════════════════════════════════════");
            
            selectIntravenousOption();
          //  fillComments(comments);
            
            log.info("✓ Form filled - proceeding to submit...");
            submitForm();
            
            log.info("✅ Pitocine form saved successfully (Intravenous)");
        } catch (Exception e) {
            log.error("❌ Failed to fill and save Pitocine form: {}", e.getMessage());
            throw e;
        }
    }

    // ============ Utility Methods ============
    
    /**
     * Wait for modal to appear
     */
    public void waitForModalToAppear() {
        try {
            wait.until(d -> isPitocineModalVisible());
            log.info("✅ Pitocine modal appeared");
        } catch (Exception e) {
            log.error("❌ Pitocine modal did not appear: {}", e.getMessage());
            throw new AssertionError("Modal did not appear: " + e.getMessage());
        }
    }

    /**
     * Wait for modal to disappear
     */
    public void waitForModalToDisappear() {
        try {
            wait.until(d -> !isPitocineModalVisible());
            log.info("✅ Pitocine modal disappeared");
        } catch (Exception e) {
            log.error("❌ Pitocine modal did not disappear: {}", e.getMessage());
            throw new AssertionError("Modal did not disappear: " + e.getMessage());
        }
    }

    /**
     * Close modal (using Cancel button)
     */
    public void closeModal() {
        try {
            cancelForm();
            waitForModalToDisappear();
            log.info("✅ Pitocine modal closed");
        } catch (Exception e) {
            log.error("❌ Error closing modal: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Get Pitocine modal header locator
     */
    public By getPitocineModalHeaderBy() {
        return MODAL_HEADER;
    }

    /**
     * Get Pitocine modal body locator
     */
    public By getPitocineModalBodyBy() {
        return MODAL_BODY;
    }

    /**
     * Get Pitocine form locator
     */
    public By getPitocineFormBy() {
        return PITOCINE_FORM;
    }
}
