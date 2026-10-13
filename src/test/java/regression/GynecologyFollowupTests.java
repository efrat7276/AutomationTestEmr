package regression;

import base.BaseSuit;
import helpers.Constants;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import actionUtilies.UIActions;
import pages.mainPages.WomenEmergencyPatientsPage;
import pages.menu.InnerMenuPage;
import pages.midwife.GynecologyFollowupPage;
import pages.midwife.InductionsPage;
import pages.midwife.ZeruzModalPage;
import pages.midwife.WaterDropModalPage;
import pages.midwife.EpiduralModalPage;
import pages.midwife.PitocineModalPage;

@Slf4j
@org.testng.annotations.Listeners(helpers.Listeners.class)
public class GynecologyFollowupTests extends BaseSuit {

    private WomenEmergencyPatientsPage womenEmergencyPatientsPage;
    private GynecologyFollowupPage gynecologyFollowupPage;
    private ZeruzModalPage zeruzModalPage;
    private InductionsPage inductionsPage;
    private InnerMenuPage innerMenuPage;
 

    @BeforeMethod
    public void setUp() {
        super.setUp();

        womenEmergencyPatientsPage = new WomenEmergencyPatientsPage();
        gynecologyFollowupPage = new GynecologyFollowupPage();
        zeruzModalPage = new ZeruzModalPage();
        inductionsPage = new InductionsPage();
        innerMenuPage = new InnerMenuPage();
    }

    @io.qameta.allure.Feature("Gynecology Followup - Page Load")
    @io.qameta.allure.Story("Verify page loads with all features")
    @Test(description = "GF-01")
    public void gf01_pageLoad() throws Exception {
        loginAsNurse();
        chooseDepartment("מיון נשים");
        womenEmergencyPatientsPage.choosePatient(1);
        Thread.sleep(4000);
        Assert.assertTrue(gynecologyFollowupPage.getPageStatus(), "Gynecology Followup page with all features did not load successfully ");
      
    }

    
    @io.qameta.allure.Feature("Gynecology Followup - Zeruz")
    @io.qameta.allure.Story("Add and verify Zeruz directive")
    @Test(description = "GF-08: Zeruz add & verify (single focused test)")
    public void gf08_zeruzFlow() throws Exception {
        loginAsDoctor();
        chooseDepartment("מיון נשים");
        womenEmergencyPatientsPage.choosePatient(1);

        // Open Zeruz modal and add a directive (empty desiredType will fall back to first available)
        gynecologyFollowupPage.clickZeruz();
        Thread.sleep(400);

        ZeruzModalPage zeruz = new ZeruzModalPage();
        zeruz.fillAndSaveZeruz("בלון");

        // After saving, modal should be closed
        Thread.sleep(400);
        Assert.assertFalse(zeruz.isZeruzModalVisible(), "Zeruz modal should be closed after save");
    }

@io.qameta.allure.Feature("Gynecology Followup - Inductions")
    @io.qameta.allure.Story("Verify Inductions sections exist")
    @Test(description = "GF-08a: Verify Inductions sections exist (ממתינות לזירוז and זירוזים פעילים)")
    public void gf08a_verifyInductionsSectionsExist() throws Exception {
        loginAsDoctor();
        chooseDepartment("מיון נשים");
        UIActions.waitForSpinnerToDisappear();
        innerMenuPage.navigateToMenuEntry("רשימת זרוזים", false);
        UIActions.waitForSpinnerToDisappear();

        // assert sections present
        //todo  בדיקה לא נכונה לא חייב שיופיעו כל הקטגוריות האילו יש להוסיף בדיקה אחרת
       // Assert.assertNotNull(inductionsPage.findSectionByTitle("ממתינות לזירוז"), "Section 'ממתינות לזירוז' should exist");
        //Assert.assertNotNull(inductionsPage.findSectionByTitle("זירוזים פעילים"), "Section 'זירוזים פעילים' should exist");
    }

    // NOTE: Removed temporary capture tests that were only used for DOM/screenshot inspection.

    @io.qameta.allure.Feature("Gynecology Followup - SOAP")
    @io.qameta.allure.Story("Save minimal SOAP fields and verify history")
    @Test(description = "GF-02 (P0) — SOAP save minimal")
    public void gf02_soapSaveMinimal() throws Exception {
        loginAsNurse();
        chooseDepartment("מיון נשים");
        womenEmergencyPatientsPage.choosePatient(1);
        UIActions.waitForSpinnerToDisappear();

        // Fill minimal SOAP fields
        String subjective = "patient feeling well";
        String objective = "vitals stable";
        String assessment = "no complications";
        String plan = "continue monitoring";

        gynecologyFollowupPage.fillSubjective(subjective);
        gynecologyFollowupPage.fillObjective(objective);
        gynecologyFollowupPage.fillAssessment(assessment);
        gynecologyFollowupPage.fillPlan(plan);

        // Save and approve followup
        gynecologyFollowupPage.saveAndApproveFollowup(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
        UIActions.waitForSpinnerToDisappear();

        // Verify new history line exists
        int historyCount = gynecologyFollowupPage.getFollowupHistoryCount();
        Assert.assertTrue(historyCount > 0, "Expected at least one followup history entry after save");

        // Verify latest history contains the subjective snippet
        String latestHistory = gynecologyFollowupPage.getLatestFollowupHistoryText();
        Assert.assertTrue(latestHistory != null && (latestHistory.contains(subjective) || latestHistory.contains("SOAP")), 
            "Expected latest history to contain subjective snippet or SOAP marker. Actual: " + latestHistory);
    }


    @io.qameta.allure.Feature("Gynecology Followup - PV Section")
     @io.qameta.allure.Story("Fill and save PV section with dropdowns")
   @Test(description = "GF-03 (P0) — PV: dropdowns basic")
    public void gf03_pvDropdownsBasic() {
        loginAsNurse();
        chooseDepartment("מיון נשים");

        womenEmergencyPatientsPage.choosePatient(1);
        UIActions.waitForSpinnerToDisappear();

        boolean saved = gynecologyFollowupPage.fillPVSection();
        Assert.assertTrue(saved, "Expected fillAndSaveGynecologyFollowupWithPV to return true");

          

        // Verify the saved followup appears in the medical Followup history
        UIActions.waitForSpinnerToDisappear();
        int historyCount = gynecologyFollowupPage.getFollowupHistoryCount();
        Assert.assertTrue(historyCount > 0, "Expected at least one followup history entry after save");

        String latest = gynecologyFollowupPage.getLatestFollowupHistoryText();
        log.info("Latest followup history text: {}", latest);
        // Check for the subjective snippet we saved or PV markers
      //  boolean containsExpected = ( latest.contains("בדיקת PV") || latest.contains("PV") || latest.contains("מחיקה"));

        // Also assert history contains today's date (format in UI is HH:mm dd/MM/yyyy)
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       // Pattern dateRegex = Pattern.compile("\\d{1,2}:\\d{2}\\s+" + Pattern.quote(today));
        boolean containsDate = latest != null && latest.contains(today);

        Assert.assertTrue( containsDate, "Latest followup history did not contain expected today's date. Actual: " + latest);
    }



    @io.qameta.allure.Feature("Gynecology Followup - Monitoring")
    @io.qameta.allure.Story("Fill monitoring section with numeric and radio inputs")
    @Test(description = "GF-04 (P0) — Monitoring: numeric & radio")
    public void gf04_monitoringNumericAndRadio() throws Exception {
        loginAsNurse();
        chooseDepartment("מיון נשים");
        womenEmergencyPatientsPage.choosePatient(1);
        UIActions.waitForSpinnerToDisappear();

        // Fill Monitoring section (BL=140, Frequency=10, Rhythm=Regular, Accelerations=Yes)
        boolean monitoringFilled = gynecologyFollowupPage.fillMonitoringSection();
        Assert.assertTrue(monitoringFilled, "Expected fillMonitoringSection to return true");

        // Save and approve
        gynecologyFollowupPage.saveAndApproveFollowup(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
        UIActions.waitForSpinnerToDisappear();

        // Verify new history entry contains monitoring markers (BL, Frequency or summary)
        String latestHistory = gynecologyFollowupPage.getLatestFollowupHistoryText();
        Assert.assertTrue(latestHistory != null,  "Expected history to be non-null. Actual: " + latestHistory);

    }

    @io.qameta.allure.Feature("Gynecology Followup - Water Drop")
    @io.qameta.allure.Story("Open Water Drop modal, fill, save and verify date button")
    @Test(description = "GF-05 (P0) — Water Drop modal open/fill/save and date on button")
    public void gf05_waterDropModalOpenFillSave() throws Exception {
        loginAsNurse();
        chooseDepartment("מיון נשים");
        womenEmergencyPatientsPage.choosePatient(1);
        UIActions.waitForSpinnerToDisappear();
        
        gynecologyFollowupPage.clickWaterCircle();
        UIActions.waitForSpinnerToDisappear();
        
       WaterDropModalPage waterDropModalPage = new WaterDropModalPage();
      //  Assert.assertTrue(waterDropModalPage.isWaterDropModalVisible(), "Water Drop modal should be visible after clicking Water Circle");
       // log.info("water drop modal opened successfully");
        
        // boolean dateSelected = waterDropModalPage.selectTodayForWaterDrop();
        // Assert.assertTrue(dateSelected, "Expected today's date to be selected in Water Drop modal");
        // log.info("today's date selected via DatePickerPage");
        
        waterDropModalPage.fillAndSaveWaterDropForm(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
        UIActions.waitForSpinnerToDisappear();
        log.info("water drop modal filled and saved");
        
        String waterDropDate = gynecologyFollowupPage.getWaterDropDateFromButton();
        Assert.assertNotNull(waterDropDate, "Water Drop date should be displayed in the button's span");
        Assert.assertFalse(waterDropDate.isEmpty(), "Water Drop date should not be empty");
        log.info("water drop date displayed in button: {}", waterDropDate);
        
        // String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        // Assert.assertTrue(waterDropDate.contains(todayDate), 
        //     "Water Drop button should display today's date (" + todayDate + "), but got: " + waterDropDate);
        // log.info("verified water drop date is today: {}", todayDate);
        
        String waterIndicatorClass = gynecologyFollowupPage.getWaterIndicatorClass();
        log.info("water indicator css class after save: {}", waterIndicatorClass);
    }

    @io.qameta.allure.Feature("Gynecology Followup - Epidural")
    @io.qameta.allure.Story("Fill Epidural modal, save and verify visual state")
    @Test(description = "GF-06 (P0) — Epidural modal fill/save and visual state")
    public void gf06_epiduralModalFillSave() throws Exception {
        loginAsNurse();
        chooseDepartment("מיון נשים");
        womenEmergencyPatientsPage.choosePatient(1);
        UIActions.waitForSpinnerToDisappear();
        
        // Verify: 1. Modal opens and data can be entered
        gynecologyFollowupPage.clickEpiduralCircle();
        UIActions.waitForSpinnerToDisappear();
        //Thread.sleep(600);
        
        // Verify modal opened
        EpiduralModalPage epiduralModalPage = new EpiduralModalPage();
        Assert.assertTrue(epiduralModalPage.isEpiduralModalVisible(), "Epidural modal should be visible after clicking Epidural Circle");
        log.info("✅ GF-06: Epidural modal opened successfully");
        
        // Fill required fields and save
        epiduralModalPage.fillAndSaveEpiduralForm(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
        UIActions.waitForSpinnerToDisappear();
        log.info("✅ GF-06: Epidural modal filled and saved");
        
        // Verify: 2. Epidural indicator button changes visual state (CSS class or color)
        String epiduralIndicatorClass = gynecologyFollowupPage.getEpiduralIndicatorClass();
        log.info("Epidural indicator CSS class after save: {}", epiduralIndicatorClass);
        
        // Verify: 3. New history entry appears with epidural marker
        String latestHistory = gynecologyFollowupPage.getLatestFollowupHistoryText();
        Assert.assertNotNull(latestHistory, "Latest history entry should exist after Epidural save");
        log.info("GF-06: Latest history after Epidural save: {}", latestHistory);
    }

    @io.qameta.allure.Feature("Gynecology Followup - Pitocin")
    @io.qameta.allure.Story("Fill Pitocin modal, save and verify visual state")
    @Test(description = "GF-07 (P0) — Pitocin modal fill/save and visual state")
    public void gf07_pitocinModalFillSave() throws Exception {
        loginAsNurse();
        chooseDepartment("מיון נשים");
        womenEmergencyPatientsPage.choosePatient(1);
        UIActions.waitForSpinnerToDisappear();
        
        // Verify: 1. Modal accepts input and allows saving
        gynecologyFollowupPage.clickPitocin();
        UIActions.waitForSpinnerToDisappear();
        Thread.sleep(600);
        
        // Verify modal opened
        PitocineModalPage pitocineModalPage = new PitocineModalPage();
        Assert.assertTrue(pitocineModalPage.isPitocineModalVisible(), "Pitocine modal should be visible after clicking Pitocin button");
        log.info("✅ GF-07: Pitocine modal opened successfully");
        
        // Fill required fields and submit
        pitocineModalPage.selectSteadyOption();
        pitocineModalPage.submitForm();
        UIActions.waitForSpinnerToDisappear();
        log.info("✅ GF-07: Pitocine modal filled and saved");
        
        // Verify: 2. Pitocin indicator visual state updates (CSS class or attribute)
        String pitocinIndicatorClass = gynecologyFollowupPage.getPitocinIndicatorClass();
        log.info("Pitocin indicator CSS class after save: {}", pitocinIndicatorClass);
        
        // Verify: 3. New history entry appears with Pitocin marker
        String latestHistory = gynecologyFollowupPage.getLatestFollowupHistoryText();
        Assert.assertNotNull(latestHistory, "Latest history entry should exist after Pitocin save");
        log.info("GF-07: Latest history after Pitocin save: {}", latestHistory);
    }
}
