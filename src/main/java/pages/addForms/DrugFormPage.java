package pages.addForms;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import pages.BasePage;

import javax.annotation.Nullable;
import java.util.List;

public class DrugFormPage extends BasePage {

    // inputs
    private By inp_selectDrug = By.id("selectDrug");
    private By inp_selectDrugTopList = By.xpath("//div[@class='form-group code-list']//button");
    private By input_drugDosage = By.id("drugDosage");
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
    public By btn_numberOfTimesDaily = By.id("numberOfTimes_daily");
    public By numberOfTimesDaily = By.xpath("//ul[@aria-labelledby='numberOfTimes_daily']/li");

    // once-only
    public By hourList = By.xpath("//button[@id='btnHourToGive']/following-sibling::ul/li");
    public By btn_hour = By.id("btnHourToGive");

    //sos
    public By btn_sosMaxTimesPerDay = By.id("sosMaxTimesPerDay");

    public By sosMinTimesPerDayList = By.xpath("//button[@id='sosMinTimesPerDay']/following-sibling::ul/li");

    //weekly

    public By weekNumberOfTimesList = By.xpath("//button[@id='WeekNumberOfTimes']/following-sibling::ul/li");

    public By btn_WeekNumberOfTimes = By.id("WeekNumberOfTimes");

    // by-hour

    public By everyXTimeList = By.xpath("//button[@id='everyXtimeGivingPossibiltyDetail']/following-sibling::ul/li");
    public By btn_everyXTime = By.id("everyXtimeGivingPossibiltyDetail");

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

    
    // כפתור ביצוע בתוך טופס התרופה (מנוסח באופן גנרי לפי טקסט)
    private By btn_executeInForm = By.id("instructionWithExecution");

    // ----------------------------------------------------------------------------------
    // הפונקציה הראשית: addMedicine
    /**
     * מוסיפה תרופה למערכת על ידי הזנת נתונים כלליים וספציפיים לתדירות.
     * הפונקציה כוללת המתנה קבועה קצרה לטעינת רשימת התרופות ולחיצה על כפתור ההוספה הסופי.
     *
     * @param nameMed שם התרופה להזנה בשדה החיפוש.
     * @param possibility התדירות הנבחרת (לדוגמה: "Daily", "SOS", "Once Only").
     * @param dosage מינון התרופה (שדה גנרי) - אופציונלי.
     * @param timesDaily מספר הפעמים ביום עבור תדירות "Daily" - אופציונלי.
     * @param hourToGive שעת מתן מדויקת עבור תדירות "Once Only" - אופציונלי.
     * @param maxTimesPerDay מספר מירבי של פעמים ביום עבור תדירות "SOS" - אופציונלי.
     * @param minInterval מרווח מינימלי בין מנות עבור תדירות "SOS" - אופציונלי.
     * @param timesPerWeek מספר הפעמים בשבוע עבור תדירות "Weekly" - אופציונלי.
     * @param daysOfWeek רשימת ימי השבוע (כמחרוזות) עבור תדירות "Weekly" - אופציונלי.
     * @param everyXTime תדירות לפי שעות (לדוגמה: "6 שעות") עבור תדירות "By Hour" - אופציונלי.
     */
    // ----------------------------------------------------------------------------------
        public void addOneMedicine(
            String nameMed,
            String possibility,
            @Nullable String dosage,
            @Nullable String timesDaily,
            @Nullable String hourToGive,
            @Nullable String maxTimesPerDay,
            @Nullable String minInterval,
            @Nullable String timesPerWeek,
            @Nullable List<String> daysOfWeek,
            @Nullable String everyXTime,
            boolean alsoExecute
    ) {
        // 1. המתנה (Hard Wait) והזנת שם התרופה
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        UIActions.typeText(inp_selectDrug, nameMed);
        UIActions.click(inp_selectDrugTopList);

        // 2. בחירת התדירות
        By possibilityLocator = getDrugPossibilityLocator(possibility);
        UIActions.click(possibilityLocator);

        // 3. מילוי פרטים כללים
        fillCommonFields(dosage);

        // 4. קריאה לפונקציה לפי תדרות
        callPossibilityFunction(
                possibility,
                timesDaily,
                hourToGive,
                maxTimesPerDay,
                minInterval,
                timesPerWeek,
                daysOfWeek,
                everyXTime
        );

        // 5. ביצוע מיידי מתוך הטופס (אופציונלי)
        if (alsoExecute) {
            tryExecuteInForm();
        }

        // 6. לחיצה על כפתור 'הוספה וסגירה'
        UIActions.click(btn_addAndClose);
        //

    }

    private void tryExecuteInForm() {
        try {
            if (UIActions.isElementDisplayed(btn_executeInForm)) {
                UIActions.click(btn_executeInForm);
            }
        } catch (Exception e) {
            System.out.println("Execute-in-form button not found or not visible: " + e.getMessage());
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
            @Nullable String flowRateOrTimes
    ) {
        // 1. המתנה והזנת שם הנוזל
        try {
            Thread.sleep(3000);  // המתנה קטנה יותר לנוזלים
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        UIActions.typeText(inp_selectDrug, nameFluid);
        UIActions.click(inp_selectDrugTopList);

        // 2. בחירת התדירות
        By possibilityLocator = getFluidPossibilityLocator(possibility);
        UIActions.click(possibilityLocator);

        // 3. מילוי פרטים כללים
        fillCommonFields(dosage);

        // 4. קריאה לפונקציה הטיפול הספציפית לנוזל
        handleFluidType(possibility, flowRateOrTimes);

        // 5. לחיצה על כפתור 'הוספה וסגירה'
        UIActions.click(btn_addAndClose);
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
            default: throw new IllegalArgumentException("Invalid possibility: " + possibility);
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
                throw new IllegalArgumentException("Invalid fluid possibility: " + possibility + ". Use 'Continuous' or 'Time Limit'");
        }
    }

    /**
     * מפעילה את פונקציית הטיפול הספציפית (handleX) בהתאם לתדירות שנבחרה.
     *
     * @param possibility שם התדירות (מחרוזת).
     * @param timesDaily מספר הפעמים ביום עבור Daily.
     * @param hourToGive שעת מתן מדויקת עבור Once Only.
     * @param maxTimesPerDay מספר מירבי של פעמים ביום עבור SOS.
     * @param minInterval מרווח מינימלי בין מנות עבור SOS.
     * @param timesPerWeek מספר הפעמים בשבוע עבור Weekly.
     * @param daysOfWeek רשימת ימי השבוע עבור Weekly.
     * @param everyXTime תדירות לפי שעות עבור By Hour.
     */
    private void callPossibilityFunction(
            String possibility,
            @Nullable String timesDaily,
            @Nullable String hourToGive,
            @Nullable String maxTimesPerDay,
            @Nullable String minInterval,
            @Nullable String timesPerWeek,
            @Nullable List<String> daysOfWeek,
            @Nullable String everyXTime
    ) {
        switch (possibility.toLowerCase()) {
            case "daily":
                handleDaily(timesDaily);
                break;
            case "once only":
                handleOnceOnly(hourToGive);
                break;
            case "sos":
                handleSOS(maxTimesPerDay, minInterval);
                break;
            case "by hour":
                handleByHour(everyXTime);
                break;
            case "weekly":
                handleWeekly(timesPerWeek, daysOfWeek);
                break;
        }
    }
    /**
     * ממלאת שדות קלט שמשותפים לכלל התדירויות (מינון ודרך מתן).
     *
     * @param dosage מינון התרופה - אופציונלי.
     * @param routeAdministration דרך מתן התרופה - אופציונלי.
     */
    private void fillCommonFields(@Nullable String dosage) {
        // 1. הזנת מינון
        if (dosage != null && !dosage.isEmpty()) {
            UIActions.clearText(input_drugDosage);
            UIActions.typeText(input_drugDosage, dosage);
            // TODO: לוגיקה לבחירת יחידות מידה
        }
      
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
        // 1. הזנת מספר מקסימלי לפעמים ביום
        if (maxTimesPerDay != null && !maxTimesPerDay.isEmpty()) {
            UIActions.typeText(btn_sosMaxTimesPerDay, maxTimesPerDay);
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

}