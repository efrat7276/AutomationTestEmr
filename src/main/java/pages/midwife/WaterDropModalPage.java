package pages.midwife;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.log.Log;

import pages.BasePage;
import pages.common.DatePickerPage;
import drivers.DriverManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * WaterDropModalPage - עמוד ירידת מים (Water Drop Modal)
 * מנהל את הפורם של ירידת מים בעמוד הפולואפ המיילדותי
 * 
 * פונקציונליות:
 * - פתיחת מודל ירידת מים
 * - מילוי שדות הפורם
 * - שמירה ואישור
 * - סגירת מודל
 */
@Slf4j
public class WaterDropModalPage extends BasePage {

    public WaterDropModalPage() {
        super();
        UIActions.waitForSpinnerToDisappear();
        log.info("✅ WaterDropModalPage initialized");
    }

    // ============ Modal Container Elements ============
    
    private final By waterDropModalContainerBy = By.xpath("//ngb-modal-window");
    private final By waterDropModalTitleBy = By.xpath("//ngb-modal-window//h5[contains(text(), 'ירידת מים')]");
    private final By waterDropCloseButtonBy = By.xpath("//ngb-modal-window//button[@aria-label='Close']");
    private final By waterDropFormBy = By.xpath("//form[@class='water-drop-form']");
     // ============ Modal Input ============

     private final By userSignBy = By.xpath("//input[@id='user']");
     private final By userSignPasswordBy = By.xpath("//input[@id='password']");

    // ============ Modal Buttons ============

    private final By waterDropSubmitButtonBy = By.xpath("//ngb-modal-window//button[contains(text(), 'חתימה')]");
    private final By waterDropCancelButtonBy = By.xpath("//ngb-modal-window//button[contains(text(), 'ביטול')]");

    // ============ Form Fields (שדות הפורם) ============
    
    // Date Field (תאריך ירידת מים)
    private final By dateFieldLabelBy = By.xpath("//label[contains(text(), 'תאריך ירידת מים')]");
    private final By dateFieldInputBy = By.xpath("//emr-datepicker");
   
    // Water Color/Type Dropdown (צבע נוזל)
    private final By waterColorLabelBy = By.xpath("//label[contains(text(), 'צבע הנוזל')]");
    private final By waterColorDropdownToggleBy = By.xpath("//label[contains(text(), 'צבע הנוזל')]/ancestor::div[@class='form-group']//button[@class='dropdown-toggle form-control']");
    private final By waterColorOptionsBy = By.xpath("//label[contains(text(), 'צבע הנוזל')]/ancestor::div[@class='form-group']//button[@class='dropdown-item ng-star-inserted']");
    
    // Water Flow Type Dropdown (אופן ירידת המים)
    private final By waterFlowTypeLabelBy = By.xpath("//label[contains(text(), 'אופן ירידת המים')]");
    private final By waterFlowTypeDropdownToggleBy = By.xpath("//label[contains(text(), 'אופן ירידת המים')]/ancestor::div[@class='form-group']//button[@class='dropdown-toggle form-control']");
    private final By waterFlowTypeOptionsBy = By.xpath("//label[contains(text(), 'אופן ירידת המים')]/ancestor::div[@class='form-group']//button[@class='dropdown-item ng-star-inserted']");
    // ============ Modal Verification Methods ============
    
    /**
     * בדוק אם מודל ירידת מים פתוח וגלוי
     * @return true if modal is visible, false otherwise
     */
    public boolean isWaterDropModalVisible() {
        try {
            return UIActions.isElementPresentAndVisible(waterDropModalTitleBy);
        } catch (Exception e) {
            log.warn("❌ Error checking Water Drop modal visibility: {}", e.getMessage());
            return false;
        }
    }

    // ============ Modal Control Methods ============
    
    /**
     * סגור את מודל ירידת מים בלחיצה על כפתור X (Close)
     */
    public void closeWaterDropModal() {
        try {
            log.info("🔌 Closing Water Drop modal...");
            UIActions.click(waterDropCloseButtonBy);
            UIActions.waitForSpinnerToDisappear();
            log.info("✅ Water Drop modal closed");
        } catch (Exception e) {
            log.error("❌ Error closing Water Drop modal: {}", e.getMessage());
        }
    }

    // ============ Form Filling Methods ============
   
   // /**\n     * בחר תאריך ירידת מים בעזרת DatePickerPage\n     * @param day The day to select\n     * @param month The month to select\n     * @param year The year to select\n     * @return true if date was selected successfully, false otherwise\n     */\n    public boolean selectWaterDropDate(int day, int month, int year) {\n        log.info(\"attempting to select Water Drop date: {}/{}/{}\", day, month, year);\n        \n        // Click on date field to open datepicker\n        UIActions.click(dateFieldInputBy);\n        UIActions.waitForSpinnerToDisappear();\n        \n        // Create DatePickerPage and select date\n        DatePickerPage datePickerPage = new DatePickerPage();\n        datePickerPage.waitForDatePickerToAppear(5);\n        \n        if (!datePickerPage.isDatePickerOpen()) {\n            log.error(\"datepicker did not open after clicking date field\");\n            return false;\n        }\n        \n        boolean selected = datePickerPage.selectDate(day, month, year);\n        if (!selected) {\n            log.error(\"failed to select date via DatePickerPage\");\n            return false;\n        }\n        \n        log.info(\"water drop date selected: {}/{}/{}\", day, month, year);\n        return true;\n    }\n\n    /**\n     * בחר את תאריך היום בירידת מים\n     * @return true if today's date was selected, false otherwise\n     */\n    public boolean selectTodayForWaterDrop() {\n        LocalDate today = LocalDate.now();\n        return selectWaterDropDate(today.getDayOfMonth(), today.getMonthValue(), today.getYear());\n    }
   
    public void fillAndSaveWaterDropForm( String username, String password) {
            log.info("🔄 Starting to fill Water Drop form...");

            // מה לגבי תאריך ירידת מים - נשתמש בתאריך של היום
            // לבדוק איזה תאריך ניתן לשנות, אם בכלל, ואם יש צורך להוסיף בדיקה של תאריך עתידי או עבר

            String todayDate = getTodayDateFormatted();
        

            log.info("✅ Filling Water Color and Water Flow Type dropdowns");

            UIActions.click(waterColorDropdownToggleBy);
            List<WebElement> waterColorOptionsListElement = DriverManager.getInstance().findElements(waterColorOptionsBy);
            UIActions.selectFromListByIndex(waterColorOptionsListElement, 1);
            UIActions.click(waterFlowTypeDropdownToggleBy);
            List<WebElement> waterFlowTypeOptionsListElement = DriverManager.getInstance().findElements(waterFlowTypeOptionsBy);
            UIActions.selectFromListByIndex(waterFlowTypeOptionsListElement, 1);
            
            saveWaterDropForm(username, password);

    }

    private void saveWaterDropForm(String username, String password) {
        
        UIActions.typeText(userSignBy, username);
        UIActions.typeText(userSignPasswordBy, password);
        UIActions.click(waterDropSubmitButtonBy);
      //  userSignModalPage.signModal(username, password);
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

    // /**
    //  * בחר אופציה מ-dropdown
    //  * @param dropdownToggle The By locator for dropdown toggle
    //  * @param options The By locator for all dropdown options
    //  * @param fieldName The name of the field (for logging)
    //  * @return true if option was selected successfully, false otherwise
    //  */
    // protected boolean selectDropdownOption(By dropdownToggle, By options, String fieldName) {
    //     try {
    //         UIActions.click(dropdownToggle);
    //         UIActions.waitForSpinnerToDisappear();
            
    //         var optionsList = DriverManager.getInstance().findElements(options);
            
    //         if (!optionsList.isEmpty()) {
    //             String optionText = optionsList.get(0).getText();
    //             UIActions.click(optionsList.get(0));
    //             UIActions.waitForSpinnerToDisappear();
    //             log.info("✅ Selected option '{}' for {} - SUCCESS", optionText, fieldName);
    //             return true;
    //         } else {
    //             log.error("❌ No options found for {} - FAILED", fieldName);
    //             return false;
    //         }
    //     } catch (Exception e) {
    //         log.error("❌ Error selecting dropdown option for {}: {}", fieldName, e.getMessage());
    //         return false;
    //     }
    // }
}
