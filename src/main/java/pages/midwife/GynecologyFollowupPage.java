
package pages.midwife;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.doctor.FollowupPage;

import drivers.DriverManager;
import java.util.List;

/**
 * GynecologyFollowupPage - רחיסה מ-FollowupPage לעמוד פולואפ מיילדותי מורחב
 * מכיל אלמנטים נוספים ספציפיים לפולואפ המיילדותי בחטיבת מיון נשים
 * 
 * אלמנטים נוספים:
 * - מעקב רפואי סיעודי (Medical Nursing Tracking)
 * - טבים: רפואי וסיעודי (Medical/Nursing tabs)
 * - כפתורים נוספים: Monitor Note, Metrics, Lab, Imaging, Consultations
 * - סימנים יולדותיים (Obstetric Indicators):
 *   🔴 מוניטור (Monitor Circle)
 *   💧 ירידת מים (Water Circle)
 *   💉 אפידורל (Epidural Circle)
 *   PT פיטוצין (Pitocin)
 *   ⚡ זירוז (Zeruz)
 */
@Slf4j
public class GynecologyFollowupPage extends FollowupPage {

    public GynecologyFollowupPage() {
        super();
     //   UIActions.waitForSpinnerToDisappear();
    }

    // ============ Element Locators ============
    
    private final By medicalNursingHeadingBy = By.xpath("//heading[contains(text(), 'מעקב רפואי סיעודי')]");
    private final By monitorNoteButtonBy = By.xpath("//button[contains(text(), 'הערה למוניטור')]");
   // private final By monitorNotesButtonBy = By.xpath("//button[contains(text(), 'הערות למוניטור')]");

    // Wrapper to open monitor notes modal
    public void openMonitorNotesModal() {
        UIActions.click(monitorNoteButtonBy);
        UIActions.waitForSpinnerToDisappear();
        log.info("✅ Opened Monitor Notes modal");
    }
    
    // ============ PV Section Elements (אלמנטי בדיקת PV) ============
    
    // Tabs & Buttons
    private final By freeReportTabBy = By.xpath("//a[.//span[contains(text(), 'דיווח חופשי')]]");
    private final By pvTabBy = By.xpath("//a[.//span[contains(text(), 'בדיקת PV')]]");
    private final By monitoringTabBy = By.xpath("//a[.//span[contains(text(), 'ניטור')]]");

    // Accessors for tests
    public By getPvTabBy() { return pvTabBy; }
    public By getMonitoringTabBy() { return monitoringTabBy; }
    public By getWaterDropDateSpanBy() { return waterDropDateSpanBy; }
    private final By copyLastPVButtonBy = By.xpath("//button[contains(text(), 'העתקת PV אחרון')]");
    private final By clearPVButtonBy = By.xpath("//button[contains(text(), 'ניקוי')]");
    
    // Location (מיקום) - Dropdown - רשימה בודדת
    private final By locationLabelBy = By.xpath("//label[contains(text(), 'מיקום')]");
    private final By locationDropdownToggleBy = By.xpath("//label[contains(text(), 'מיקום')]/ancestor::div[@class='form-group']//button[@class='dropdown-toggle form-control']");
    private final By locationOptionsBy = By.xpath("//label[contains(text(), 'מיקום')]/ancestor::div[@class='form-group']//button[@class='dropdown-item ng-star-inserted']");
    
    // Fetal Position (תנוחת עובר) - Dropdown - רשימה בודדת
    private final By fetalPositionLabelBy = By.xpath("//label[contains(text(), 'תנוחת עובר')]");
    private final By fetalPositionDropdownToggleBy = By.xpath("//label[contains(text(), 'תנוחת עובר')]/ancestor::div[@class='form-group']//button[@class='dropdown-toggle form-control']");
    private final By fetalPositionOptionsBy = By.xpath("//label[contains(text(), 'תנוחת עובר')]/ancestor::div[@class='form-group']//button[@class='dropdown-item ng-star-inserted']");
    
    // Head Height (גובה חלק מקדים) - Dropdown - רשימה בודדת
    private final By headHeightLabelBy = By.xpath("//label[contains(text(), 'גובה חלק מקדים')]");
    private final By headHeightDropdownToggleBy = By.xpath("//label[contains(text(), 'גובה חלק מקדים')]/ancestor::div[@class='form-group']//button[@class='dropdown-toggle form-control']");
    private final By headHeightOptionsBy = By.xpath("//label[contains(text(), 'גובה חלק מקדים')]/ancestor::div[@class='form-group']//button[@class='dropdown-item ng-star-inserted']");
    
    // Effacement (מחיקה) - Input Number
    private final By effacementLabelBy = By.xpath("//label[contains(text(), 'מחיקה')]");
    private final By effacementInputBy = By.id("effacementPV");
    
    // Texture (מרקם) - Dropdown - רשימה בודדת
    private final By textureLabelBy = By.xpath("//label[contains(text(), 'מרקם')]");
    private final By textureDropdownToggleBy = By.xpath("//label[contains(text(), 'מרקם')]/ancestor::div[@class='form-group']//button[@class='dropdown-toggle form-control']");
    private final By textureOptionsBy = By.xpath("//label[contains(text(), 'מרקם')]/ancestor::div[@class='form-group']//button[@class='dropdown-item ng-star-inserted']");
    
    // Weight Assessment (הערכת משקל)
    private final By weightAssessmentLabelBy = By.xpath("//span[contains(text(), 'הערכת משקל')]");
    private final By weightAssessmentClinicalLabelBy = By.xpath("//span[contains(text(), 'קליני')]");
    private final By weightAssessmentClinicalInputBy = By.xpath("//span[contains(text(), 'קליני')]/ancestor::div[@class='form-group']//input");
    private final By weightAssessmentUSLabelBy = By.xpath("//span[contains(text(), 'US')]");
    private final By weightAssessmentUSInputBy = By.xpath("//span[contains(text(), 'US')]/ancestor::div[@class='form-group']//input");
    
    // Presentation (מצג) - Dropdown - רשימה בודדת
    private final By presentationLabelBy = By.xpath("//label[contains(text(), 'מצג')]");
    private final By presentationDropdownToggleBy = By.xpath("//label[contains(text(), 'מצג')]/ancestor::div[@class='form-group']//button[@class='dropdown-toggle form-control']");
    private final By presentationOptionsBy = By.xpath("//label[contains(text(), 'מצג')]/ancestor::div[@class='form-group']//button[@class='dropdown-item ng-star-inserted']");
    
    // Dilation/Opening (פתיחה) - Input Number
    private final By dilationLabelBy = By.xpath("//label[contains(text(), 'פתיחה')]");
    private final By dilationInputBy = By.xpath("//label[contains(text(), 'פתיחה')]/ancestor::div[@class='form-group']//input");
    
    // Execution Time (שעת ביצוע)
    private final By executionTimeLabelBy = By.xpath("//label[contains(text(), 'שעת ביצוע')]");
    private final By executionTimeHoursInputBy = By.xpath("//input[@placeholder='HH']");
    private final By executionTimeMinutesInputBy = By.xpath("//input[@placeholder='MM']");
    
    // ============ Monitoring Section Elements (אלמנטי ניטור) ============
    
    // BL (Baseline) - Input Number
    private final By blLabelBy = By.xpath("//label[contains(text(), 'BL')]");
    private final By blInputBy = By.id("BL");
    
    // Decelerations (האטות) - Dropdown - רשימה בודדת
    private final By decelerationsLabelBy = By.xpath("//label[contains(text(), 'האטות')]");
    private final By decelerationsDropdownToggleBy = By.xpath("//label[contains(text(), 'האטות')]/ancestor::div[@class='form-group']//button[@class='dropdown-toggle form-control']");
    private final By decelerationOptionsBy = By.xpath("//label[contains(text(), 'האטות')]/ancestor::div[@class='form-group']//button[@class='dropdown-item ng-star-inserted']");
    
    // Category (קטגוריה) - Dropdown - רשימה בודדת
    private final By categoryLabelBy = By.xpath("//label[contains(text(), 'קטגוריה')]");
    private final By categoryDropdownToggleBy = By.xpath("//label[contains(text(), 'קטגוריה')]/ancestor::div[@class='form-group']//button[@class='dropdown-toggle form-control']");
    private final By categoryOptionsBy = By.xpath("//label[contains(text(), 'קטגוריה')]/ancestor::div[@class='form-group']//button[@class='dropdown-item ng-star-inserted']");
    
    // Variability (וריאביליות) - Dropdown - רשימה בודדת
    private final By variabilityLabelBy = By.xpath("//label[contains(text(), 'וריאביליות')]");
    private final By variabilityDropdownToggleBy = By.xpath("//label[contains(text(), 'וריאביליות')]/ancestor::div[@class='form-group']/div/button");
    private final By variabilityOptionsBy = By.xpath("//label[contains(text(), 'וריאביליות')]/ancestor::div[@class='form-group']//div/button[contains(@class,'dropdown-item')]");
    
    // Rhythm (צירים) - Radio Buttons
    private final By rhythmLabelBy = By.xpath("//label[contains(text(), 'צירים')]");
    private final By rhythmIrregularRadioBy = By.xpath("//label[contains(text(), 'צירים')]/ancestor::div[@class='form-group']//label[text() = 'לא סדיר']");
    private final By rhythmRegularRadioBy = By.xpath("//label[contains(text(), 'צירים')]/ancestor::div[@class='form-group']//label[text() = 'סדיר']");
    private final By rhythmNoneRadioBy = By.xpath("//label[contains(text(), 'צירים')]/ancestor::div[@class='form-group']//label[text() = 'ללא']");
    
    // Accelerations (האצות) - Radio Buttons
    private final By accelerationsLabelBy = By.xpath("//label[contains(text(), 'האצות')]");
    private final By accelerationsNoRadioBy = By.xpath("//label[contains(text(), 'האצות')]/ancestor::div[@class='form-group']//label[contains(text(), 'לא')]");
    private final By accelerationsYesRadioBy = By.xpath("//label[contains(text(), 'האצות')]/ancestor::div[@class='form-group']//label[contains(text(), 'כן')]");
    
    // Frequency (תדירות 10 דק) - Input Number
    private final By frequencyLabelBy = By.xpath("//label[contains(text(), 'תדירות')]");
    private final By frequencyInputBy = By.id("frequencyMonitoring");
    
    // Monitoring Array Inputs (laborMonitoring-0, laborMonitoring-1, laborMonitoring-2) - רשימה בודדת
    private final By laborMonitoringInputsBy = By.xpath("//input[contains(@id, 'laborMonitoring-')]");
    
    // Acceleration Monitoring Array Inputs (accelerationMonitoringID-0, accelerationMonitoringID-1) - רשימה בודדת
    private final By accelerationMonitoringInputsBy = By.xpath("//input[contains(@id, 'accelerationMonitoringID-')]");
    
    // ============ Obstetric Indicators (סימנים יולדותיים) ============
    private final By monitorCircleButtonBy = By.xpath("//button[contains(@class, 'monitor_circle')]");
    private final By waterDropDateSpanBy = By.xpath("//button[contains(@title, 'ירידת מים')]//span[2]");
    private final By waterCircleButtonBy = By.xpath("//button[contains(@title, 'ירידת מים')]");
    private final By epiduralCircleButtonBy = By.xpath("//button[contains(@class, 'epidural-circle')]");
    private final By pitociuButtonBy = By.xpath("//button[contains(@class, 'pt-button')]");
    private final By zeruzButtonBy = By.xpath("//button[contains(@class, 'zeruz')]");

    // ============ Water Drop Modal Elements (פופ-אפ ירידת מים) ============
    //private final By waterDropModalBy = By.xpath("//form[@class='water-drop-form']");
    private final By waterDropModalTitleBy = By.xpath("//ngb-modal-window//h5[contains(text(), 'ירידת מים')]");
    private final By waterDropCloseButtonBy = By.xpath("//ngb-modal-window//button[@aria-label='Close']");
    private final By waterDropSubmitButtonBy = By.xpath("//form[@class='water-drop-form']//button[@type='submit']");

    // ============ Medical Nursing Tracking Section (מעקב רפואי סיעודי) ============
    
    /**
     * בדוק אם כותרת "מעקב רפואי סיעודי" גלוי
     */
    public boolean isMedicalNursingTrackingVisible() {
        try {
            return UIActions.isElementPresentAndVisible(medicalNursingHeadingBy);
        } catch (Exception e) {
            log.warn("❌ Error checking medical nursing tracking visibility: {}", e.getMessage());
            return false;
        }
    }
    // ============ Water Drop Modal Methods (פונקציות פופ-אפ ירידת מים) ============
    
    /**
     * לחץ על כפתור "ירידת מים" ובדוק שהפופ-אפ נפתח
     * @return true if modal opened successfully, false otherwise
     */
    public boolean openAndVerifyWaterDropModal() {
        try {
            log.info("💧 [DEBUG] Starting Water Drop modal opening sequence...");
            
            // Step 1: Click the water circle button
            log.info("💧 [DEBUG] About to click waterCircleButtonBy: {}", waterCircleButtonBy);
            UIActions.click(waterCircleButtonBy);
            log.info("💧 [DEBUG] Click executed, waiting for spinner to disappear...");
            
            UIActions.waitForSpinnerToDisappear();
            log.info("💧 [DEBUG] Spinner disappeared");
            
            // Small delay for modal animation
            Thread.sleep(1000);
            log.info("💧 [DEBUG] After 1s sleep, about to check if modal is visible...");
            
            // Step 2: Verify modal is visible
            //log.info("💧 [DEBUG] Checking modal visibility with xpath: {}", waterDropModalBy);
            boolean isModalVisible = UIActions.isElementPresentAndVisible(waterDropModalTitleBy);
            
            log.info("💧 [DEBUG] Modal visibility check result: {}", isModalVisible);
            
            if (isModalVisible) {
                log.info("✅ Water Drop modal opened successfully - VERIFIED");
                return true;
            } else {
                log.error("❌ Water Drop modal did not open - isElementPresentAndVisible returned FALSE");
                log.error("❌ [DEBUG] Checking alternative: trying to find modal by different XPath...");
                
                // Try alternative check: just see if form exists
                try {
                    boolean formExists = UIActions.isElementPresentAndVisible(By.xpath("//form[@class='water-drop-form']"));
                    log.error("❌ [DEBUG] Alternative check for form: {}", formExists);
                } catch (Exception altE) {
                    log.error("❌ [DEBUG] Alternative check also failed: {}", altE.getMessage());
                }
                
                return false;
            }
        } catch (InterruptedException ie) {
            log.error("❌ [DEBUG] InterruptedException: {}", ie.getMessage());
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            log.error("❌ Error opening Water Drop modal: {}", e.getMessage());
            log.error("❌ [DEBUG] Exception type: {}", e.getClass().getName());
            log.error("❌ [DEBUG] Full stack trace:", e);
            return false;
        }
    }
    
    /**
     * סגור את פופ-אפ "ירידת מים" בלחיצה על כפתור הביטול
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
    
    /**
     * בדוק אם כותרת הפופ-אפ של ירידת מים גלויה
     * @return true if modal title is visible, false otherwise
     */
    public boolean isWaterDropModalTitleVisible() {
        try {
            return UIActions.isElementPresentAndVisible(waterDropModalTitleBy);
        } catch (Exception e) {
            log.warn("❌ Error checking Water Drop modal title visibility: {}", e.getMessage());
            return false;
        }
    }

    // ============ Utility Methods ============
    
    /**
     * ממלא פולואפ מיילדותי **מלא**:
     * 1. כל שדות SOAP (Subjective, Objective, Assessment, Plan)
     * 2. כל שדות ה-PV עם ערכים ברירת מחדל
     * 3. לוחץ על כפתור אישור (Save)
     * 
     * @param subjective - טקסט SOAP Subjective
     * @param objective - טקסט SOAP Objective
     * @param assessment - טקסט SOAP Assessment
     * @param plan - טקסט SOAP Plan
     * @return true if all steps completed successfully, false otherwise
     */
    public boolean fillAndSaveGynecologyFollowupWithPV(String subjective, String objective, String assessment, String plan, String username, String password) {
            log.info("🔄 Starting Complete Gynecology Followup Fill Process...");
            
            // Step 1: Fill SOAP Fields (from parent FollowupPage)
       //     log.info("📋 Step 1: Filling SOAP Fields...");
            fillSOAPFields(subjective, objective, assessment, plan);

            
            // Step 2: Fill PV Section
            log.info("🔍 Step 2: Filling PV Section...");
            boolean pvFilled = fillPVSection();
            
            if (!pvFilled) {
                log.error("❌ PV Section filling FAILED - aborting save");
                return false;
            }
            
            // Step 3: Fill Monitoring Section
            log.info("📊 Step 3: Filling Monitoring Section...");
            boolean monitoringFilled = fillMonitoringSection();
            
            if (!monitoringFilled) {
                log.error("❌ Monitoring Section filling FAILED - aborting save");
                return false;
            }
            
            saveAndApproveFollowup(username, password);
            log.info("✅ Complete Gynecology Followup with PV and Monitoring filled and saved successfully!");
            return true;
       
    }
    
    /**
     * Alternative method: ממלא פולואפ מלא עם ערכים ברירת מחדל עבור SOAP fields
     * @return true if all steps completed successfully, false otherwise
     */
    public boolean fillCompleteFollowupWithDefaults() {
        try {
            log.info("🔄 Starting Complete Gynecology Followup with defaults...");
            
            // Step 1: Fill SOAP Fields with default values
            String defaultSubjective = "מטופלת בהריון תקין, ללא תלונות, מצב כללי טוב";
            String defaultObjective = "סימנים חיוניים תקינים, בדיקה פיזיקלית רגילה";
            String defaultAssessment = "הריון תקין בשבוע המתאים, ללא סימנים של סיבוכים";
            String defaultPlan = "המשך המעקב השגרתי, בקרה בשבועות הקרובים";
            
            fillSubjective(defaultSubjective);
            fillObjective(defaultObjective);
            fillAssessment(defaultAssessment);
            fillPlan(defaultPlan);
            
            // Step 2: Fill PV Section
            boolean pvFilled = fillPVSection();
            
            if (!pvFilled) {
                log.error("❌ PV Section filling FAILED - aborting save");
                return false;
            }
            
            // Step 3: Fill Monitoring Section
            log.info("📊 Step 3: Filling Monitoring Section...");
            boolean monitoringFilled = fillMonitoringSection();
            
            if (!monitoringFilled) {
                log.error("❌ Monitoring Section filling FAILED - aborting save");
                return false;
            }
           
           // Step 4: Save the Followup
            log.info("💾 Step 4: Saving Followup...");
            saveAndApproveFollowup("midwife", "password"); // Replace with actual credentials or pass them as parameters
            
            log.info("✅ Complete Gynecology Followup with defaults filled and saved successfully!");
            return true;
        } catch (Exception e) {
            log.error("❌ Error filling complete followup with defaults: {}", e.getMessage());
            return false;
        }
    }

    /**
     * ממלא ושומר פולואפ עם SOAP + Monitoring בערכים ברירת מחדל
     * בלי PV Section
     * @param username המשתמש לאישור
     * @param password הסיסמה לאישור
     * @return true if all steps completed successfully, false otherwise
     */
    public boolean fillAndSaveGynecologyFollowupAndMonitoring(String username, String password) {
        try {
            log.info("═══════════════════════════════════════════════════");
            log.info("🔄 Starting SOAP + Monitoring Fill and Save Process (without PV)...");
            log.info("═══════════════════════════════════════════════════");
            
            // Step 1: Fill SOAP Fields with default values
            log.info("📋 Step 1: Filling SOAP Fields...");
            String defaultSubjective = "מטופלת בהריון תקין, ללא תלונות, מצב כללי טוב";
            String defaultObjective = "סימנים חיוניים תקינים, בדיקה פיזיקלית רגילה";
            String defaultAssessment = "הריון תקין בשבוע המתאים, ללא סימנים של סיבוכים";
            String defaultPlan = "המשך המעקב השגרתי, בקרה בשבועות הקרובים";
            
            fillSubjective(defaultSubjective);
            fillObjective(defaultObjective);
            fillAssessment(defaultAssessment);
            fillPlan(defaultPlan);
            log.info("✅ SOAP Fields filled successfully");
            
            // Step 2: Fill Monitoring Section
            log.info("📊 Step 2: Filling Monitoring Section...");
            boolean monitoringFilled = fillMonitoringSection();
            
            if (!monitoringFilled) {
                log.error("❌ Monitoring Section filling FAILED - aborting save");
                return false;
            }
            log.info("✅ Monitoring Section filled successfully");
            
            // Step 3: Save and Approve Followup
            log.info("💾 Step 3: Saving and Approving Followup...");
            saveAndApproveFollowup(username, password);
            
            log.info("═══════════════════════════════════════════════════");
            log.info("✅ SOAP + Monitoring filled and saved successfully!");
            log.info("═══════════════════════════════════════════════════");
            return true;
            
        } catch (Exception e) {
            log.error("❌ Error in SOAP + Monitoring fill and save: {}", e.getMessage());
            return false;
        }
    }

    /**
     * ממלא ושומר פולואפ מלא עם SOAP + PV + Monitoring
     * בוחר תמיד את האפשרות הראשונה בכל dropdown
     * @return true if all PV elements were filled successfully, false otherwise
     */
    public boolean fillPVSection() {
        try {
            log.info("🔄 Starting to fill PV Section with default (first) options...");
            
            // Step 1: Click on PV Tab (בדיקת PV)
            UIActions.click(pvTabBy);

            
            // Step 2: Fill Location dropdown
            boolean locationFilled = fillDropdownWithFirstOption(locationDropdownToggleBy, locationOptionsBy, "Location (מיקום)");
            
            // Step 3: Fill Fetal Position dropdown
            boolean fetalPositionFilled = fillDropdownWithFirstOption(fetalPositionDropdownToggleBy, fetalPositionOptionsBy, "Fetal Position (תנוחת עובר)");
            
            // Step 4: Fill Head Height dropdown
            boolean headHeightFilled = fillDropdownWithFirstOption(headHeightDropdownToggleBy, headHeightOptionsBy, "Head Height (גובה חלק מקדים)");
            
            // Step 5: Fill Texture dropdown
            boolean textureFilled = fillDropdownWithFirstOption(textureDropdownToggleBy, textureOptionsBy, "Texture (מרקם)");
            
            // Step 6: Fill Presentation dropdown
            boolean presentationFilled = fillDropdownWithFirstOption(presentationDropdownToggleBy, presentationOptionsBy, "Presentation (מצג)");
            
            // Step 7: Fill Input Fields (מחיקה, פתיחה, קליני, US)
            log.info("🔄 Filling PV input fields with default values...");
            
            // Fill Effacement (מחיקה) with value 5
            boolean effacementFilled = fillInputField(effacementInputBy, "5", "Effacement (מחיקה)");
            
            // Fill Dilation (פתיחה) with value 8
            boolean dilationFilled = fillInputField(dilationInputBy, "8", "Dilation (פתיחה)");
            
            // Fill Clinical Weight Assessment (קליני) with value 3.1
            boolean clinicalFilled = fillInputField(weightAssessmentClinicalInputBy, "3.1", "Clinical Weight Assessment (קליני)");
            
            // Fill US Weight Assessment with value 3.8
            boolean usFilled = fillInputField(weightAssessmentUSInputBy, "3.8", "US Weight Assessment (US)");
            
            boolean inputFieldsFilled = effacementFilled && dilationFilled && clinicalFilled && usFilled;
            
            boolean allFilled = locationFilled && fetalPositionFilled && headHeightFilled && textureFilled && presentationFilled && inputFieldsFilled;
            
            if (allFilled) {
                log.info("✅ Successfully filled all PV section elements");
            } else {
                log.error("❌ Some PV section elements were NOT filled successfully");
            }
            
            return allFilled;
        } catch (Exception e) {
            log.error("❌ Error filling PV section: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * ממלא את כל אלמנטי ניטור PV בערכים ברירת מחדל
     * בוחר תמיד את האפשרות הראשונה בכל dropdown ואפשרויות ספציפיות לרדיו
     * @return true if all Monitoring elements were filled successfully, false otherwise
     */
    public boolean fillMonitoringSection() {
        try {
            log.info("🔄 Starting to fill Monitoring Section with default values...");
            
            // Step 1: Click on Monitoring Tab (ניטור PV)
            UIActions.click(monitoringTabBy);
            UIActions.waitForSpinnerToDisappear();
            Thread.sleep(500);
            
            // Step 2: Fill Decelerations dropdown
            boolean decelerationsFilled = fillDropdownWithFirstOption(decelerationsDropdownToggleBy, decelerationOptionsBy, "Decelerations (האטות)");
            
            // Step 3: Fill Category dropdown
            boolean categoryFilled = fillDropdownWithFirstOption(categoryDropdownToggleBy, categoryOptionsBy, "Category (קטגוריה)");
            
            // Step 4: Fill Variability dropdown
            boolean variabilityFilled = fillDropdownWithFirstOption(variabilityDropdownToggleBy, variabilityOptionsBy, "Variability (וריאביליות)");
            
            // Step 5: Fill Input Fields (BL, Frequency)
            log.info("🔄 Filling Monitoring input fields with default values...");
            
            // Fill BL (Baseline) with value 140
            boolean blFilled = fillInputField(blInputBy, "140", "BL (Baseline)");
            
            // Fill Frequency (תדירות) with value 10
            boolean frequencyFilled = fillInputField(frequencyInputBy, "10", "Frequency (תדירות)");
            
            // Step 6: Select Rhythm Radio Button - Regular (סדיר)
            log.info("🔄 Selecting Rhythm radio button...");
            boolean rhythmFilled = selectRadioButton(rhythmRegularRadioBy, "Rhythm - Regular (סדיר)");
            
            // Step 7: Select Accelerations Radio Button - Yes (כן)
            log.info("🔄 Selecting Accelerations radio button...");
            boolean accelerationsFilled = selectRadioButton(accelerationsYesRadioBy, "Accelerations - Yes (כן)");
            
            boolean allFilled = decelerationsFilled && categoryFilled && variabilityFilled && 
                               blFilled && frequencyFilled && rhythmFilled && accelerationsFilled;
            
            if (allFilled) {
                log.info("✅ Successfully filled all Monitoring section elements");
            } else {
                log.error("❌ Some Monitoring section elements were NOT filled successfully");
            }
            
            return allFilled;
        } catch (Exception e) {
            log.error("❌ Error filling Monitoring section: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * בוחר רדיו בתון על ידי לחיצה על ה-label המשויך
     * @param radioBy The By locator for the radio button label
     * @param fieldName The name of the field (for logging)
     * @return true if radio button was selected successfully, false otherwise
     */
    private boolean selectRadioButton(By radioBy, String fieldName) {
        try {
            WebElement radioElement = DriverManager.getInstance().findElement(radioBy);
            UIActions.click(radioElement);
            UIActions.waitForSpinnerToDisappear();
            log.info("✅ Selected radio button for {} - SUCCESS", fieldName);
            return true;
        } catch (Exception e) {
            log.error("❌ Error selecting radio button for {}: {}", fieldName, e.getMessage());
            return false;
        }
    }
  
  
    /**
     * ממלא שדה input עם ערך מסוים
     * @param inputBy The By locator for the input field
     * @param value The value to fill
     * @param fieldName The name of the field (for logging)
     * @return true if the field was filled successfully, false otherwise
     */
    private boolean fillInputField(By inputBy, String value, String fieldName) {
        try {
            WebElement inputElement = DriverManager.getInstance().findElement(inputBy);
            inputElement.clear();
            inputElement.sendKeys(value);
            
            // Verify the value was actually set
            String actualValue = inputElement.getAttribute("value");
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
    
    /**
     * Selects the first option from a dropdown
     * @param dropdownToggle The By locator for dropdown toggle button
     * @param options The By locator for all dropdown options
     * @param fieldName The name of the field (for logging)
     * @return true if an option was selected successfully, false otherwise
     */
    private boolean fillDropdownWithFirstOption(By dropdownToggle, By options, String fieldName) {
        try {
            // Click dropdown toggle to open
            UIActions.click(dropdownToggle);
            UIActions.waitForSpinnerToDisappear();
            
            // Get all options
            List<WebElement> optionsList = DriverManager.getInstance().findElements(options);
            
            if (!optionsList.isEmpty()) {
                // Click first option
                String optionText = optionsList.get(0).getText();
                UIActions.click(optionsList.get(0));
                UIActions.waitForSpinnerToDisappear();
                log.info("✅ Selected first option '{}' for {} - SUCCESS", optionText, fieldName);
                return true;
            } else {
                log.error("❌ No options found for {} - FAILED", fieldName);
                return false;
            }
        } catch (Exception e) {
            log.error("❌ Error filling {} dropdown: {} - FAILED", fieldName, e.getMessage());
            return false;
        }
    }
    
    /**
     * בדוק אם כל האלמנטים בעמוד המיילדותי נמצאים על המסך
     * @return true אם כל האלמנטים נמצאים, false אחרת
     */
    public boolean getPageStatus() {
        UIActions.waitForSpinnerToDisappear();
        boolean medicalNursingTrackingVisible = isMedicalNursingTrackingVisible();
        boolean monitorCircleVisible = UIActions.isElementPresentAndVisible(monitorCircleButtonBy);
        boolean waterCircleVisible = UIActions.isElementPresentAndVisible(waterCircleButtonBy);
        boolean epiduralCircleVisible = UIActions.isElementPresentAndVisible(epiduralCircleButtonBy);
        boolean pitociuVisible = UIActions.isElementPresentAndVisible(pitociuButtonBy);
        boolean zeruzVisible = UIActions.isElementPresentAndVisible(zeruzButtonBy);
        
        boolean allElementsPresent = medicalNursingTrackingVisible && 
                                     monitorCircleVisible && 
                                     waterCircleVisible && 
                                     epiduralCircleVisible && 
                                     pitociuVisible && 
                                     zeruzVisible;
        
        if (allElementsPresent) {
            log.info("✅ All Gynecology Followup Page elements are visible on the screen");
        } else {
            log.warn("❌ Some elements are missing from Gynecology Followup Page:");
            if (!medicalNursingTrackingVisible) log.warn("   - Medical Nursing Tracking not visible");
            if (!monitorCircleVisible) log.warn("   - Monitor Circle not visible");
            if (!waterCircleVisible) log.warn("   - Water Circle not visible");
            if (!epiduralCircleVisible) log.warn("   - Epidural Circle not visible");
            if (!pitociuVisible) log.warn("   - Pitocin (PT) not visible");
            if (!zeruzVisible) log.warn("   - Zeruz not visible");
        }
        
        return allElementsPresent;
    }

    // ============ Obstetric Indicator Button Click Methods ============
    
    /**
     * לחץ על כפתור Water Circle (ירידת מים)
     */
    public void clickWaterCircle() {
        try {
            log.info("💧 Clicking Water Circle button...");
            UIActions.click(waterCircleButtonBy);
            UIActions.waitForSpinnerToDisappear();
            log.info("✅ Water Circle button clicked successfully");
        } catch (Exception e) {
            log.error("❌ Error clicking Water Circle button: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * לחץ על כפתור Monitor Circle (מוניטור)
     */
    public void clickMonitorCircle() {
        try {
            log.info("🔴 Clicking Monitor Circle button...");
            UIActions.click(monitorCircleButtonBy);
            UIActions.waitForSpinnerToDisappear();
            log.info("✅ Monitor Circle button clicked successfully");
        } catch (Exception e) {
            log.error("❌ Error clicking Monitor Circle button: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * לחץ על כפתור Epidural Circle (אפידורל)
     */
    public void clickEpiduralCircle() {
        try {
            log.info("💉 Clicking Epidural Circle button...");
            UIActions.click(epiduralCircleButtonBy);
            UIActions.waitForSpinnerToDisappear();
            log.info("✅ Epidural Circle button clicked successfully");
        } catch (Exception e) {
            log.error("❌ Error clicking Epidural Circle button: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * לחץ על כפתור Pitocin (פיטוצין)
     */
    public void clickPitocin() {
            log.info("💊 Clicking Pitocin (PT) button...");
            UIActions.click(pitociuButtonBy);
            UIActions.waitForSpinnerToDisappear();
            log.info("✅ Pitocin button clicked successfully");
    }

    /**
     * לחץ על כפתור Zeruz (זרוז)
     */
    public void clickZeruz() {
            log.info("⚡ Clicking Zeruz button...");
            UIActions.click(zeruzButtonBy);
            UIActions.waitForSpinnerToDisappear();
            log.info("✅ Zeruz button clicked successfully");
    }

    // ============ PV Getters for Verification ============

    public String getLocationSelectedLabel() {
        try {
            return DriverManager.getInstance().findElement(locationDropdownToggleBy).getText().trim();
        } catch (Exception e) {
            log.warn("Could not read Location selected label: {}", e.getMessage());
            return "";
        }
    }

    public String getFetalPositionSelectedLabel() {
        try {
            return DriverManager.getInstance().findElement(fetalPositionDropdownToggleBy).getText().trim();
        } catch (Exception e) {
            log.warn("Could not read Fetal Position selected label: {}", e.getMessage());
            return "";
        }
    }

    public String getEffacementValue() {
        try {
            return DriverManager.getInstance().findElement(effacementInputBy).getAttribute("value");
        } catch (Exception e) {
            log.warn("Could not read Effacement value: {}", e.getMessage());
            return "";
        }
    }

    public String getDilationValue() {
        try {
            return DriverManager.getInstance().findElement(dilationInputBy).getAttribute("value");
        } catch (Exception e) {
            log.warn("Could not read Dilation value: {}", e.getMessage());
            return "";
        }
    }

    public String getWeightClinicalValue() {
        try {
            return DriverManager.getInstance().findElement(weightAssessmentClinicalInputBy).getAttribute("value");
        } catch (Exception e) {
            log.warn("Could not read Clinical weight value: {}", e.getMessage());
            return "";
        }
    }

    public String getWeightUSValue() {
        try {
            return DriverManager.getInstance().findElement(weightAssessmentUSInputBy).getAttribute("value");
        } catch (Exception e) {
            log.warn("Could not read US weight value: {}", e.getMessage());
            return "";
        }
    }

    // ============ Indicator Visual State Helpers ============

    /**
     * Get CSS class of Water indicator button to verify visual state change
     * @return CSS class string or empty if not found
     */
    public String getWaterIndicatorClass() {
        try {
            WebElement waterIndicator = DriverManager.getInstance().findElement(waterCircleButtonBy);
            return waterIndicator.getAttribute("class");
        } catch (Exception e) {
            log.warn("Could not get Water indicator CSS class: {}", e.getMessage());
            return "";
        }
    }

    /**
    
    /**
     * Get CSS class of Epidural indicator button to verify visual state change
     * @return CSS class string or empty if not found
     */
    public String getEpiduralIndicatorClass() {
        try {
            WebElement epiduralIndicator = DriverManager.getInstance().findElement(epiduralCircleButtonBy);
            return epiduralIndicator.getAttribute("class");
        } catch (Exception e) {
            log.warn("Could not get Epidural indicator CSS class: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get CSS class of Pitocin indicator button to verify visual state change
     * @return CSS class string or empty if not found
     */
    public String getPitocinIndicatorClass() {
        try {
            WebElement pitocinIndicator = DriverManager.getInstance().findElement(pitociuButtonBy);
            return pitocinIndicator.getAttribute("class");
        } catch (Exception e) {
            log.warn("Could not get Pitocin indicator CSS class: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get date text from span inside Water indicator button (verification after water drop save)
     * @return date text from button span (e.g., "18/09/2026" or similar format), or empty if not found
     */
    public String getWaterDropDateFromButton() {
        try {
            WebElement dateSpan = DriverManager.getInstance().findElement(waterDropDateSpanBy);
            String dateText = dateSpan.getText().trim();
            return dateText;
        } catch (Exception e) {
            log.warn("Could not get Water Drop date from button span: {}", e.getMessage());
            return "";
        }
    }
}
