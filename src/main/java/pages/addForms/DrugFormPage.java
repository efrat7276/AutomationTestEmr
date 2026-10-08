package pages.addForms;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BasePage;
import pages.DoctorInstructionPage;

import javax.annotation.Nullable;
import java.util.List;
import static org.testng.Assert.assertTrue;


@Slf4j
public class DrugFormPage extends BasePage {

    public DrugFormPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    // inputs
    private By inp_selectDrug = By.id("selectDrug");
    private By inp_selectDrugTopList = By.xpath("//div[@class='form-group code-list']//button");
    private By input_drugDosage = By.id("drugDosage");
    private By input_comments = By.id("drugComment");
    // ... (שאר האלמנטים)

    // dropdowns
    private By btn_unitMeasure = By.id("dropdownDrugUnitMeasure");
    private By unitMeasureList = By.xpath("//button[@id='dropdownDrugUnitMeasure']/following-sibling::ul/li");
    private By btn_dropdownRouteAdministration = By.id("dropdownRouteAdministrationID");
    private By routeAdministrationList = By.xpath("//button[@id='dropdownRouteAdministrationID']/following-sibling::ul/li");

    // buttons
    private By btn_add = By.id("btnAdd"); // כפתור הוסף;
    private By btn_addAndClose = By.id("btnAddAndClose"); // כפתור הוסף וסגור;

    // possibility (Radio Buttons)
    private By possibilityDaily = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Daily ']");
    private By possibilitySOS = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' SOS ']");
    private By possibilityOnceOnly = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Once Only ']");
    private By possibilityByHour = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' By Hour ']");
    private By possibilityWeekly = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Weekly ']");
    // ...
    //daily
    private By btn_numberOfTimesDaily = By.id("numberOfTimes_daily");
    private By numberOfTimesDaily = By.xpath("//ul[@aria-labelledby='numberOfTimes_daily']/li");

    // once-only
    private By hourList = By.xpath("//button[@id='btnHourToGive']/following-sibling::ul/li");
    private By btn_hour = By.id("btnHourToGive");

    //sos
    private By  btn_sosMaxTimesPerDay = By.xpath("//button[@id='sosMaxTimesPerDay']");
    private By btn_sosMinTimesPerDay = By.xpath("//button[@id='sosMinTimesPerDay']");
    private By sosMaxTimesPerDayList = By.xpath("//button[@id='sosMaxTimesPerDay']/following-sibling::ul/li");
    private By sosMinTimesPerDayList = By.xpath("//button[@id='sosMinTimesPerDay']/following-sibling::ul/li");

    //weekly

    private By weekNumberOfTimesList = By.xpath("//button[@id='WeekNumberOfTimes']/following-sibling::ul/li");

    private By btn_WeekNumberOfTimes = By.id("WeekNumberOfTimes");

    // by-hour

    private By everyXTimeList = By.xpath("//button[@id='everyXtimeGivingPossibiltyDetail']/following-sibling::ul/li");
    private By btn_everyXTime = By.xpath("//button[@id='everyXtimeGivingPossibiltyDetail']");

    // ============================================================================
    // FLUID SPECIFIC LOCATORS - לוקטורים ספציפיים לנוזלים
    // ============================================================================
    // Fluid frequency radio buttons
    private By possibilityContinuous = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Continuous ']");  // נוזל רציף
    private By possibilityTimeLimit = By.xpath("//input[contains(@id,'drugTimeGivingPossibilitiesID')]/following-sibling::label[text()=' Time Limit ']");  // נוזל בזמן מוגבל

    // Continuous fluid specific fields
    private By inp_flowRate = By.id("MinRate");  // קצב זרימה (מ"ל לשעה)

    // Time Limit fluid specific fields
    private By inp_startTime = By.id("startTime");  // שעת התחלה
    private By inp_endTime = By.id("endTime");  // שעת סיום
    private By btn_durationList = By.xpath("//button[@name='solutionDurationList']");  // כפתור בחירת משך הטיפול
    private By durationList = By.xpath("//button[@name='solutionDurationList']/following-sibling::ul/li");  // רשימת אפשרויות משך הטיפול

    private By duplicateInstructionModal = By.xpath("//duplicate-instruction-modal");
    private By duplicateInstructionModalConfirmButton = By.xpath("//duplicate-instruction-modal//button[@id='buttonImport']");
    // כפתור ביצוע בתוך טופס התרופה (מנוסח באופן גנרי לפי טקסט)
    private By btn_executeInForm = By.id("instructionWithExecution");


    //private WebDriver driver;
    private WebDriverWait wait;
    // ----------------------------------------------------------------------------------
    // Public Methods: תרופות לפי תדירויות שונות
    // ----------------------------------------------------------------------------------

    /**
     * Dispatcher method - קוראת לפונקציה הנכונה בהתאם לתדירות המבוקשת
     * שימושי כאשר התדירות נקבעת בזמן ריצה
     * 
     * @param nameMed שם התרופה/מוצר
     * @param frequency התדירות (Daily, Once Only, SOS, By Hour, Weekly)
     * @param dosage מינון
     * @param frequencyParam פרמטר ספציפי לתדירות (timesDaily, hourToGive, וכו')
     * @param comments הערות
     * @param alsoExecute ביצוע מיידי
     */
    public void addMedicineByFrequency(String nameMed, String frequency, String dosage, 
                                       String frequencyParam, @Nullable String comments, 
                                       boolean alsoExecute) {
        switch (frequency.toLowerCase().trim()) {
            case "daily":
                addDailyMedicine(nameMed, dosage, frequencyParam, comments, alsoExecute);
                break;
            case "once only":
                addOnceOnlyMedicine(nameMed, dosage, frequencyParam, comments, alsoExecute);
                break;
            case "sos":
                addSOSMedicine(nameMed, dosage, frequencyParam, null, comments);
                break;
            case "by hour":
                addByHourMedicine(nameMed, dosage, frequencyParam, comments, alsoExecute);
                break;
            case "weekly":
                addWeeklyMedicine(nameMed, dosage, frequencyParam, null, comments, alsoExecute);
                break;
            default:
                log.error("Unknown frequency: {}", frequency);
                throw new IllegalArgumentException("Unknown frequency: " + frequency);
        }
    }

    /**
     * הוספת תרופה יומית (Daily)
     * @param nameMed שם התרופה
     * @param dosage מינון התרופה
     * @param timesDaily מספר הפעמים ביום
     * @param comments הערות - אופציונלי
     * @param alsoExecute האם לבצע מיידית מתוך הטופס
     */
    public void addDailyMedicine(String nameMed, String dosage, String timesDaily, 
                                 @Nullable String comments, boolean alsoExecute) {
        log.info("Adding daily medicine: {} - {} times per day", nameMed, timesDaily);
        selectAndConfigureDrug(nameMed, "Daily", dosage, comments);
        handleDaily(timesDaily);
        finalizeMedicineAdd(alsoExecute, nameMed, "Daily", dosage);
    }

    /**
     * הוספת תרופה חד פעמית (Once Only)
     * @param nameMed שם התרופה
     * @param dosage מינון התרופה
     * @param hourToGive שעת מתן התרופה
     * @param comments הערות - אופציונלי
     * @param alsoExecute האם לבצע מיידית מתוך הטופס
     */
    public void addOnceOnlyMedicine(String nameMed, String dosage, String hourToGive,
                                    @Nullable String comments, boolean alsoExecute) {
        log.info("Adding once only medicine: {} - at {}", nameMed, hourToGive);
        selectAndConfigureDrug(nameMed, "Once Only", dosage, comments);
        //handleOnceOnly(hourToGive);
        finalizeMedicineAdd(alsoExecute, nameMed, "Once Only", dosage);
    }

    /**
     * הוספת תרופה SOS (במידת הצורך)
     * @param nameMed שם התרופה
     * @param dosage מינון התרופה
     * @param maxTimesPerDay מספר מקסימלי לפעמים ביום
     * @param minInterval מרווח מינימלי בין מנות - אופציונלי
     * @param comments הערות - אופציונלי
     */
    public void addSOSMedicine(String nameMed, String dosage, String maxTimesPerDay,
                               @Nullable String minInterval, @Nullable String comments) {
        log.info("Adding SOS medicine: {} - max {} times per day", nameMed, maxTimesPerDay);
        selectAndConfigureDrug(nameMed, "SOS", dosage, comments);
       
        handleSOS(maxTimesPerDay, minInterval);
        finalizeMedicineAdd(false, nameMed, "SOS", dosage);
    }

    /**
     * הוספת תרופה לפי שעה (By Hour)
     * @param nameMed שם התרופה
     * @param dosage מינון התרופה
     * @param everyXTime תדירות לפי שעות (למשל: "6 שעות")
     * @param comments הערות - אופציונלי
     * @param alsoExecute האם לבצע מיידית מתוך הטופס
     */
    public void addByHourMedicine(String nameMed, String dosage, String everyXTime,
                                  @Nullable String comments, boolean alsoExecute) {
        log.info("Adding by-hour medicine: {} - every {}", nameMed, everyXTime);
        selectAndConfigureDrug(nameMed, "By Hour", dosage, comments);
       
        handleByHour(everyXTime);
        finalizeMedicineAdd(alsoExecute, nameMed, "By Hour", dosage);
    }

    /**
     * הוספת תרופה שבועית (Weekly)
     * @param nameMed שם התרופה
     * @param dosage מינון התרופה
     * @param timesPerWeek מספר הפעמים בשבוע
     * @param daysOfWeek רשימת ימי השבוע - אופציונלי
     * @param comments הערות - אופציונלי
     * @param alsoExecute האם לבצע מיידית מתוך הטופס
     */
    public void addWeeklyMedicine(String nameMed, String dosage, String timesPerWeek,
                                  @Nullable List<String> daysOfWeek, @Nullable String comments,
                                  boolean alsoExecute) {
        log.info("Adding weekly medicine: {} - {} times per week", nameMed, timesPerWeek);
        selectAndConfigureDrug(nameMed, "Weekly", dosage, comments);
        handleWeekly(timesPerWeek, daysOfWeek);
        finalizeMedicineAdd(alsoExecute, nameMed, "Weekly", dosage);
    }

    // ----------------------------------------------------------------------------------
    // Private Helper Methods
    // ----------------------------------------------------------------------------------

    /**
     * בחירה של התרופה ובחירת התדירות
     */
    private void selectAndConfigureDrug(String nameMed, String possibility, 
                                       @Nullable String dosage, @Nullable String comments) {
        // 1. המתנה והזנת שם התרופה
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        UIActions.typeText(inp_selectDrug, nameMed);
        UIActions.click(inp_selectDrugTopList);
        
        // if(UIActions.isPopupAppeared(duplicateInstructionModal)) {
        //     UIActions.click(duplicateInstructionModalConfirmButton);
        //     log.info("Duplicate instruction modal appeared. Confirmed to import existing instruction.");
        // }
        
        // 2. בחירת התדירות
        By possibilityLocator = getDrugPossibilityLocator(possibility);
        if(!possibility.equals("Daily")) {
            UIActions.waitForElementClickable(possibilityLocator);
            log.info("Selecting possibility: {}", possibility);    
            UIActions.click(possibilityLocator);
        }
        
        // 3. מילוי פרטים כללים
        fillCommonFields(dosage, comments);
    }

    /**
     * סיום הוספת תרופה - ביצוע וסגירה
     */
    private void finalizeMedicineAdd(boolean alsoExecute, String nameMed, String possibility, String dosage) {
        // 1. ביצוע מיידי מתוך הטופס (אופציונלי)
        if (alsoExecute) {
            tryExecuteInForm();
        }
        
       
        
     //   log.info("Successfully added medicine '{}' with possibility '{}'. Dosage: {}.", nameMed, possibility, dosage);
    }
    public void editMedicine(@Nullable String possibility, @Nullable String dosage) {
        UIActions.waitForAnyText(inp_selectDrug);
         UIActions.waitForElementClickable(possibilityDaily);
        log.info("Editing medicine - Possibility: {}, Dosage: {}", possibility, dosage);
        if (possibility != null && !possibility.isEmpty()) {
            By possibilityLocator = getDrugPossibilityLocator(possibility);
            UIActions.waitForElementClickable(possibilityLocator);
            UIActions.click(possibilityLocator);
        }
        if (dosage != null && !dosage.isEmpty()) {
            fillCommonFields(dosage, null);
        }
        UIActions.click(btn_add);
        
    }

    private void tryExecuteInForm() {
        log.info("Attempting to click 'Execute in Form' button if available...");
        try {
            if (UIActions.isElementDisplayed(btn_executeInForm)) {
                UIActions.click(btn_executeInForm);
            }
        } catch (Exception e) {
            log.warn("Execute-in-form button not found or not visible: {}", e.getMessage());
        }
    }

    /**
     * מוסיפה נוזל/דילול למערכת בעזרת אותו טופס כמו תרופה.
     * טיפול ספציפי לנוזלים שיש להם תדירויות שונות: Continuous ו-Time Limit
     *
     * @param nameFluid שם הנוזל להזנה בשדה החיפוש.
     * @param possibility התדירות הנבחרת ("Continuous" או "Time Limit").
     * @param dosage מינון/כמות הנוזל - אופציונלי.
     * @param flowRateOrTimes קצב זרימה (Continuous) או מספר פעמים ביום (Time Limit) - אופציונלי.
     */
    public void addFluid(
            String nameFluid,
            String possibility,
            @Nullable String dosage,
            @Nullable String flowRateOrTimes,
            @Nullable String comments
    ) {
        // 1. המתנה והזנת שם הנוזל
        try {
            Thread.sleep(3000);  // המתנה קטנה יותר לנוזלים
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        UIActions.typeText(inp_selectDrug, nameFluid);
        UIActions.click(inp_selectDrugTopList);
        if(UIActions.isPopupAppeared(duplicateInstructionModal))
          UIActions.click(duplicateInstructionModalConfirmButton);
        
        By possibilityLocator = getFluidPossibilityLocator(possibility);
        UIActions.waitForElementClickable(possibilityLocator);
        UIActions.click(possibilityLocator);
        fillCommonFields(dosage, comments);
        handleFluidType(possibility, flowRateOrTimes);
        log.info("Fluid configuration complete. Awaiting button click from calling method.");
    }

    // ----------------------------------------------------------------------------------
    // פונקציות עזר גנריות (ללא פלט)
    // ----------------------------------------------------------------------------------

    private By getDrugPossibilityLocator(String possibility) {
        switch (possibility.toLowerCase()) {
            case "daily": return possibilityDaily;
            case "once only": return possibilityOnceOnly;
            case "sos": return possibilitySOS;
            case "by hour": return possibilityByHour;
            case "weekly": return possibilityWeekly;
            default:  log.error("Invalid possibility: {}", possibility);
                      throw new IllegalArgumentException("Invalid possibility: " + possibility);
        }
    }

    /**
     * מחזירה את ה-locator של התדירות עבור נוזלים בלבד.
     * נוזלים יש להם תדירויות שונות מתרופות: Continuous ו-Time Limit
     */
    private By getFluidPossibilityLocator(String possibility) {
        switch (possibility.toLowerCase()) {
            case "continuous":
                return possibilityContinuous;
            case "time limit":
                return possibilityTimeLimit;
            default:
                log.error("Invalid fluid possibility: {}", possibility);
                throw new IllegalArgumentException("Invalid fluid possibility: " + possibility + ". Use 'Continuous' or 'Time Limit'");
        }
    }

    /**
     * ממלאת שדות קלט שמשותפים לכלל התדירויות (מינון ודרך מתן).
     *
     * @param dosage מינון התרופה - אופציונלי.
     * @param routeAdministration דרך מתן התרופה - אופציונלי.
     */
    private void fillCommonFields(@Nullable String dosage, @Nullable String comments) {
        // 1. הזנת מינון
        if (dosage != null && !dosage.isEmpty()) {
           log.info("Filling field with dosage: {}", dosage);
            UIActions.clearText(input_drugDosage);
            UIActions.typeText(input_drugDosage, dosage);
            // TODO: לוגיקה לבחירת יחידות מידה
        }
        // 2. הזנת הערות
        if (comments != null && !comments.isEmpty()) {
           log.info("Filling field with comments: {}", comments);
            UIActions.clearText(input_comments);
            UIActions.typeText(input_comments, comments);
        }
        UIActions.waitForSpinnerToDisappear();
    }
    /**
     * מטפלת בלוגיקה הספציפית לתדירות "Daily" (יומי).
     *
     * @param timesDaily מספר הפעמים ביום לבחירה (כמחרוזת, לדוגמה "2") - אופציונלי.
     */
    private void handleDaily(@Nullable String timesDaily) {
        if (timesDaily != null && !timesDaily.isEmpty()) {
            UIActions.click(btn_numberOfTimesDaily);
            UIActions.selectFromList(numberOfTimesDaily, timesDaily);
        }
    }

    private void handleOnceOnly(@Nullable String hourToGive) {
        if (hourToGive != null && !hourToGive.isEmpty()) {
            UIActions.click(btn_hour);
            UIActions.selectFromList(hourList, hourToGive);
        }
    }
    /**
     * מטפלת בלוגיקה הספציפית לתדירות "SOS" (במידת הצורך).
     *
     * @param maxTimesPerDay מספר מקסימלי לפעמים ביום להזנה - אופציונלי.
     * @param minInterval מרווח מינימלי בין מנות לבחירה מהרשימה - אופציונלי.
     */
    private void handleSOS(
            @Nullable String maxTimesPerDay,
            @Nullable String minInterval
    ) {
        log.info("Handling SOS frequency with maxTimesPerDay: {} and minInterval: {}", maxTimesPerDay, minInterval);
        
        //  UIActions.waitForSpinnerToDisappear();
        // 1. הזנת מספר מקסימלי לפעמים ביום
        if (maxTimesPerDay != null) {
       // wait.until(ExpectedConditions.elementToBeClickable(btn_sosMaxTimesPerDay));

            UIActions.click(btn_sosMaxTimesPerDay);
            UIActions.selectFromList(sosMaxTimesPerDayList, maxTimesPerDay);
        }

//        // 2. בחירת מרווח מינימלי (מינימום פעמים ביום)
//        if (minInterval != null && !minInterval.isEmpty()) {
//            UIActions.click(btn_sosMinTimesPerDay);
//            UIActions.selectFromList(sosMinTimesPerDayList, minInterval);
//        }
    }
    /**
     * מטפלת בלוגיקה הספציפית לתדירות "Weekly" (שבועי).
     *
     * @param timesPerWeek מספר הפעמים בשבוע לבחירה מהרשימה - אופציונלי.
     * @param daysOfWeek רשימת ימי השבוע שיש לסמן (לדוגמה: ["Sunday", "Tuesday"]) - אופציונלי.
     */
    private void handleWeekly(
            @Nullable String timesPerWeek,
            @Nullable List<String> daysOfWeek
    ) {
        // 1. בחירת מספר הפעמים בשבוע
        if (timesPerWeek != null && !timesPerWeek.isEmpty()) {
            UIActions.click(btn_WeekNumberOfTimes);
            UIActions.selectFromList(weekNumberOfTimesList, timesPerWeek);
        }

        // 2. בחירת ימי השבוע
        if (daysOfWeek != null && !daysOfWeek.isEmpty()) {
            // TODO: נדרשים Locators עבור ימי השבוע. כאן יש לולאה וקריאה ל-UIActions.click
            // for (String day : daysOfWeek) { UIActions.click(getDayCheckbox(day)); }
        }
    }
    /**
     * מטפלת בלוגיקה הספציפית לתדירות "By Hour" (לפי שעה/מרווח).
     *
     * @param everyXTime תדירות לבחירה מהרשימה (לדוגמה: "6 שעות") - אופציונלי.
     */
    private void handleByHour(@Nullable String everyXTime) {
        log.info("Handling 'By Hour' frequency with everyXTime: {}", everyXTime);
        if (everyXTime != null && !everyXTime.isEmpty()) {
            UIActions.click(btn_everyXTime);
            UIActions.selectFromList(everyXTimeList, everyXTime);
        }
    }

    /**
     * מטפלת בלוגיקה הספציפית לסוגי נוזלים.
     *
     * @param possibility סוג הנוזל ("Continuous" או "Time Limit").
     * @param flowRateOrTimes קצב זרימה (Continuous) או מספר פעמים ביום (Time Limit) - אופציונלי.
     */
    private void handleFluidType(String possibility, @Nullable String flowRateOrTimes) {
        switch (possibility.toLowerCase()) {
            case "continuous":
                handleContinuousFluid(flowRateOrTimes);
                break;
            case "time limit":
                handleTimeLimitFluid(flowRateOrTimes);
                break;
        }
    }

    /**
     * מטפלת בנוזל רציף (Continuous).
     * 
     * @param flowRate קצב הזרימה (מ"ל לשעה) - אופציונלי.
     */
    private void handleContinuousFluid(@Nullable String flowRate) {
        if (flowRate != null && !flowRate.isEmpty()) {
            UIActions.typeText(inp_flowRate, flowRate);
        }
    }

    /**
     * מטפלת בנוזל בזמן מוגבל (Time Limit).
     * 
     * @param timesPerDay מספר פעמים ביום - אופציונלי.
     */
    private void handleTimeLimitFluid(@Nullable String timesPerDay) {
        if (timesPerDay != null && !timesPerDay.isEmpty()) {
            UIActions.click(btn_durationList);
            UIActions.selectFromList(durationList, timesPerDay);
        }
    }

    /**
     * לוחצת על כפתור "הוסף" להוספת התרופה/הנוזל לרשימה
     * מבלי לסגור את הפורם (יאפשר הוספת מספר פריטים)
     */
    public void clickAddButton() {
        log.info("Clicking 'Add' button.");
        UIActions.click(btn_add);
        UIActions.waitForSpinnerToDisappear();
    }

    /**
     * לוחצת על כפתור "הוסף וסגור" להוספת התרופה/הנוזל לרשימה וסגירת הפורם
     */
    public void clickAddAndCloseButton() {
        log.info("Clicking 'Add and Close' button.");
        UIActions.click(btn_addAndClose);
        UIActions.waitForSpinnerToDisappear();
    }

}