package pages.midwife;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pages.BasePage;
import drivers.DriverManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * EpiduralModalPage - עמוד אפידורל (Epidural Modal)
 * מנהל את הפורם של אפידורל בעמוד הפולואפ המיילדותי
 * 
 * פונקציונליות:
 * - פתיחת מודל אפידורל
 * - מילוי שדות הפורם
 * - שמירה ואישור
 * - סגירת מודל
 */
@Slf4j
public class EpiduralModalPage extends BasePage {

    public EpiduralModalPage() {
        super();
        UIActions.waitForSpinnerToDisappear();
        log.info("✅ EpiduralModalPage initialized");
    }

    // ============ Modal Container Elements ============
    
    private final By epiduralModalContainerBy = By.xpath("//ngb-modal-window");
    private final By epiduralModalTitleBy = By.xpath("//ngb-modal-window//h5[contains(text(), 'אפידורל')]");
    private final By epiduralCloseButtonBy = By.xpath("//ngb-modal-window//button[@aria-label='Close']");

    // ============ Modal Buttons ============

    private final By epiduralSubmitButtonBy = By.xpath("//ngb-modal-window//button[contains(text(), 'חתימה')]");
    private final By epiduralCancelButtonBy = By.xpath("//ngb-modal-window//button[contains(text(), 'ניקוי')]");

    // ============ Form Fields (שדות הפורם) ============
    
     //בחירה האם מעוניינת / לא מעוניינת באפידורל
    private final By epiduralChoiceYesCheckboxBy = By.xpath("//input[@type='radio' and @id='interested']");
    private final By epiduralChoiceNoCheckboxBy = By.xpath("//input[@type='radio' and @id='notInterested']");

    // בתנאי   שמעוניינת - בחירת שדות נוספים
    private final By fluidsCheckboxBy = By.xpath("//input[@type='checkbox' and @id='fluids']");
    private final By normalBloodCountCheckboxBy = By.xpath("//input[@type='checkbox' and @id='bloodCount']");

    //
    private final By userSignBy = By.xpath("//input[@id='user']");
    private final By userSignPasswordBy = By.xpath("//input[@id='password']");

    /**
     * בדוק אם מודל אפידורל פתוח וגלוי
     * @return true if modal is visible, false otherwise
     */
    public boolean isEpiduralModalVisible() {
        try {
            return UIActions.isElementPresentAndVisible(epiduralModalTitleBy);
        } catch (Exception e) {
            log.warn("❌ Error checking Epidural modal visibility: {}", e.getMessage());
            return false;
        }
    }

    // ============ Modal Control Methods ============
    
    /**
     * סגור את מודל אפידורל בלחיצה על כפתור X (Close)
     */
    public void closeEpiduralModal() {
        try {
            log.info("🔌 Closing Epidural modal...");
            UIActions.click(epiduralCloseButtonBy);
            UIActions.waitForSpinnerToDisappear();
            log.info("✅ Epidural modal closed");
        } catch (Exception e) {
            log.error("❌ Error closing Epidural modal: {}", e.getMessage());
        }
    }

    // ============ Form Filling Methods ============
   
    /**
     * מילוי טופס אפידורל ושמירה
     * @param username Username for signature
     * @param password Password for signature
     */
    public void fillAndSaveEpiduralForm(String username, String password) {
        try {
            log.info("🔄 Starting to fill Epidural form...");

            // Get today's date
            String todayDate = getTodayDateFormatted();
            log.info("📅 Using date: {}", todayDate);

            // Fill anesthesiologist dropdown
            log.info("✅ Filling Epidural form fields");

            UIActions.click(epiduralChoiceYesCheckboxBy);
           UIActions.click(fluidsCheckboxBy);
            UIActions.click(normalBloodCountCheckboxBy);
            
            // Click submit button
            //UIActions.click(epiduralSubmitButtonBy);
            saveEpiduralForm(username, password);
            
            // Sign the modal
            userSignModalPage.signModal(username, password);
            log.info("✅ Epidural form filled, submitted, and signed");

        } catch (Exception e) {
            log.error("❌ Error filling Epidural form: {}", e.getMessage());
            throw new RuntimeException("Failed to fill and save Epidural form", e);
        }
    }

    private void saveEpiduralForm(String username, String password) {
       
        UIActions.typeText(userSignBy, username);
        UIActions.typeText(userSignPasswordBy, password);
        UIActions.click(epiduralSubmitButtonBy);
       // userSignModalPage.signModal(username, password);
    }

    // ============ Utility Helper Methods ============
    
    /**
     * מקבל את התאריך של היום בפורמט DD/MM/YYYY
     * @return Today's date in DD/MM/YYYY format
     */
    private String getTodayDateFormatted() {
        try {
            java.time.LocalDate today = java.time.LocalDate.now();
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return today.format(formatter);
        } catch (Exception e) {
            log.warn("⚠️ Error getting today's date, using fallback format: {}", e.getMessage());
            return "01/01/2026"; // Fallback
        }
    }
    
    /**
     * ממלא שדה input בערך מסוים
     * @param fieldBy The By locator for the input field
     * @param value The value to fill
     * @param fieldName The name of the field (for logging)
     * @return true if field was filled successfully, false otherwise
     */
    protected boolean fillInputField(By fieldBy, String value, String fieldName) {
        try {
            WebElement field = DriverManager.getInstance().findElement(fieldBy);
            field.clear();
            field.sendKeys(value);
            
            String actualValue = field.getAttribute("value");
            if (actualValue != null && actualValue.equals(value)) {
                log.info("✅ Filled {} with value '{}' - VERIFIED", fieldName, value);
                return true;
            } else {
                log.warn("⚠️ Filled {} with value '{}' but actual value is '{}' - MISMATCH", fieldName, value, actualValue);
                return false;
            }
        } catch (Exception e) {
            log.error("❌ Error filling {} with value '{}': {}", fieldName, value, e.getMessage());
            return false;
        }
    }
}
