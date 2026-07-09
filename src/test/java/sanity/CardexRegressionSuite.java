package sanity;

import base.BaseSuit;
import enums.HospitalDepartment;
import helpers.Constants;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import actionUtilies.UIActions;
import pages.*;
import pages.nurse.Execute.CardexPageNew;
import pages.nurse.Execute.CardexPageRegression;

@Slf4j
@org.testng.annotations.Listeners(helpers.Listeners.class)
public class CardexRegressionSuite extends BaseSuit {

    private static final int PATIENT_1 = 1;
    CardexPageNew cardexPageNew;
    CardexPageRegression cardexPageRegression;
    ChooseDepartmentListPage chooseDepartmentListPage;
    String deptName;

    @BeforeClass
    public void setUpClass() {
       // this.currentDept = HospitalDepartment.INTERNAL_B;
     deptName = System.getProperty("dept", "פנימית א");
    }

    @BeforeMethod
    public void setUp() {
        super.setUp();
        cardexPageNew = new CardexPageNew();
        cardexPageRegression = new CardexPageRegression();
        chooseDepartmentListPage = new ChooseDepartmentListPage();
    }

    @Test(description = "Cardex regression - verify all major components are loaded")
    @Description("Test verifies that all major Cardex components are properly loaded and visible")
    public void test_01_cardexLoadRegression() {
        try {
            loginAsNurse();
            chooseDepartmentListPage.selectDepartment(deptName);
            choosePatient(PATIENT_1);
            
            // Navigate to cardex
            cardexPageNew.clickArrowForwardToInnerMenu();
            
            // Run regression tests
            CardexPageRegression.CardexRegressionReport report = cardexPageRegression.runCardexRegression();
            
            log.info(report.generateReport());
            
            assertTrue(report.isFullyLoaded(), "Cardex should be fully loaded");
          //  assertTrue(report.onCardexPage, "Should be on Cardex page");
          //  assertTrue(report.allTabsVisible, "All tabs should be visible");
            assertTrue(report.allActionButtonsVisible, "All action buttons should be visible");
            assertTrue(report.activeDrugsTableLoaded, "Active drugs table should be loaded");
            
            log.info("✓ Test passed: Cardex regression successful");
            
        } catch (Exception e) {
            log.error("✗ Test failed: {}", e.getMessage());
            assertTrue(false, "Cardex regression failed: " + e.getMessage());
        }
    }

    @Test(description = "Cardex regression - verify active drugs are displayed")
    @Description("Test verifies that active drugs are properly loaded and displayed in the Cardex table")
    public void test_02_cardexActiveDrugsRegression() {
        try {
            loginAsDoctor();
            chooseDepartmentListPage.selectDepartment(deptName);
            choosePatient(PATIENT_1);
            
            // Navigate to cardex
            cardexPageNew.clickArrowForwardToInnerMenu();
            
            // Get active drugs count
            int drugCount = cardexPageRegression.getActiveDrugCount();
            assertTrue(drugCount > 0, "Should have at least one active drug");
            
            // Get drug names
            java.util.List<String> drugNames = cardexPageRegression.getActiveDrugNames();
            assertTrue(!drugNames.isEmpty(), "Should have retrieved drug names");
            
            log.info("✓ Found {} active drugs: {}", drugCount, drugNames);
            log.info("✓ Test passed: Active drugs regression successful");
            
        } catch (Exception e) {
            log.error("✗ Test failed: {}", e.getMessage());
            assertTrue(false, "Active drugs regression failed: " + e.getMessage());
        }
    }

    @Test(description = "Cardex regression - verify all UI components are visible")
    @Description("Test verifies that all Cardex UI components (tabs, buttons, time strip) are properly visible")
    public void test_03_cardexUIComponentsRegression() {
        try {
            loginAsDoctor();
            chooseDepartmentListPage.selectDepartment(deptName);
            choosePatient(PATIENT_1);
            
            // Navigate to cardex
            cardexPageNew.clickArrowForwardToInnerMenu();
            
            // Verify all components
            assertTrue(cardexPageRegression.isCardexTabActive(), "Cardex tab should be active");
            assertTrue(cardexPageRegression.areAllTabsVisible(), "All tabs should be visible");
            assertTrue(cardexPageRegression.isSignatureButtonVisible(), "Signature button should be visible");
            assertTrue(cardexPageRegression.isOTCButtonVisible(), "OTC button should be visible");
            assertTrue(cardexPageRegression.isStickersButtonVisible(), "Stickers button should be visible");
            assertTrue(cardexPageRegression.isTimeStripVisible(), "Time strip should be visible");
            assertTrue(cardexPageRegression.isCurrentShiftButtonVisible(), "Current shift button should be visible");
            
            log.info("✓ Test passed: All UI components regression successful");
            
        } catch (Exception e) {
            log.error("✗ Test failed: {}", e.getMessage());
            assertTrue(false, "UI components regression failed: " + e.getMessage());
        }
    }
}
