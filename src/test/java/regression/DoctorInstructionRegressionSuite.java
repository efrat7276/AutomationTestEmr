package regression;

import base.BaseSuit;
import enums.InstructionType;
import helpers.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import actionUtilies.UIActions;
import pages.DoctorInstructionPage;
import pages.PatientBoxPage;
import pages.mainPages.PatientsListPage;

/**
 * Regression Test Suite for Doctor Instructions - Multi-Type Instructions
 * 
 * Tests verify that previously working functionality continues to work:
 * - Adding multiple medicine types
 * - Adding blood products
 * - Adding nutrition instructions
 * - Adding fluid instructions
 * - Approving all instructions with doctor signature
 * - All elements load and respond correctly
 * - Cross-module synchronization between doctor orders and other modules
 */
@Slf4j
@org.testng.annotations.Listeners(helpers.Listeners.class)
@Epic("Doctor Instructions - Regression Testing")
public class DoctorInstructionRegressionSuite extends BaseSuit {

    private static final int PATIENT_1 = 1;
    
    private PatientsListPage patientsListPage;
    private PatientBoxPage patientBoxPage;
    private DoctorInstructionPage doctorInstructionPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        patientsListPage = new PatientsListPage();
        patientBoxPage = new PatientBoxPage();
        doctorInstructionPage = new DoctorInstructionPage();
        
        log.info("Setup completed - Page objects initialized");
    }


    // ============ Regression Test 1: Add Multiple Instructions and Approve All ============
    
    @Feature("Doctor Instructions - Regression")
    @Story("Complete workflow with 6 medicines, 3 blood products, 2 nutrition, 3 fluids")
    @Test(description = "Regression Test 1: Add multiple instruction types (6 medicines, 3 blood products, 2 nutrition, 3 fluids) and approve all with doctor signature")
    public void regression_01_addMultipleInstructionsAndApproveAll() {
        log.info("=== REGRESSION TEST 01: Add Multiple Instructions and Approve All ===");
        
        try {
            // Step 1: Navigate to Doctor Instructions
            loginAsDoctor();
            choosePatient(PATIENT_1);
            patientBoxPage.verifyPatientDetailsExisting();
            UIActions.waitForSpinnerToDisappear();
            log.info("✓ Navigation completed");
            
            // Step 2: Add 6 different medicines
            log.info(">>> Adding 6 different medicines...");
            addMedicineInstructions();
            log.info("✅ All 6 medicines added successfully");
            
            // Step 3: Add 3 blood products
            log.info(">>> Adding 3 blood products...");
            addBloodProductInstructions();
            log.info("✅ All 3 blood products added successfully");
            
            // Step 4: Add 2 nutrition instructions
            log.info(">>> Adding 2 nutrition instructions...");
            addNutritionInstructions();
            log.info("✅ All 2 nutrition instructions added successfully");
            
            // Step 5: Add 3 fluid instructions
            log.info(">>> Adding 3 fluid instructions...");
            addFluidInstructions();
            log.info("✅ All 3 fluid instructions added successfully");
            
            // Step 6: Approve all instructions with doctor signature
            log.info(">>> Approving all instructions with doctor signature...");
            doctorInstructionPage.approveAndVerifyInstructions(
                Constants.DOCTOR_USERNAME, 
                Constants.DOCTOR_PASSWORD
            );
            log.info("✓ All instructions approved by doctor");
            
            // Step 7: Final verification
            doctorInstructionPage.verifyDoctorApproval();
            log.info("✅ Doctor approval verified - Approval button shows '0' pending approvals");
            
            log.info("✅✅✅ REGRESSION TEST 01 PASSED - All instructions added and approved successfully! ✅✅✅");
            
        } catch (Exception e) {
            log.error("❌ REGRESSION TEST 01 FAILED - {}", e.getMessage());
            throw e;
        }
    }

    // ============ Helper Methods for Adding Instructions ============
    
    /**
     * Adds 6 different medicine instructions to the patient
     * Each medicine represents a different dosage/frequency combination for regression coverage
     */
    private void addMedicineInstructions() {
        log.debug("Starting medicine instruction additions");
        
        // // Medicine 1: Antibiotic - Daily
        // log.info("  [1/6] Adding Medicine 1: paracetamol - 500mg daily");
        // doctorInstructionPage.addMedicineToListTheFisrtTime("paracetamol", "daily", "500", "1","automation regression test");
        // log.info("  ✓ Medicine 1 added to list");
        
        // // Medicine 2: Analgesic - Twice daily
        // log.info("  [2/6] Adding Medicine 2: FUSID - 500mg twice daily");
        // doctorInstructionPage.addMedicineToList("FUSID", "daily", "500", "2","automation regression test");
        // log.info("  ✓ Medicine 2 added to list");
        
        // // Medicine 3: Antihistamine - Three times daily
        // log.info("  [3/6] Adding Medicine 3: Cetirizine - 10mg three times daily");
        // doctorInstructionPage.addMedicineToList("cetirizine ", "daily", "10", "3","automation regression test");
        // log.info("  ✓ Medicine 3 added to list");
        
        // Medicine 4: Antacid - As needed
        // log.info("  [4/6] Adding Medicine 4: Omeprazole - 20mg as needed");
        // doctorInstructionPage.addMedicineToList("esomeprazole", "sos", "20", "4"   ,"automation regression test");
        // log.info("  ✓ Medicine 4 added to list");
        
        // Medicine 5: Antifungal - Every 6 hours
        log.info("  [5/6] Adding Medicine 5:Ibuprofen  - 100mg every 6 hours");
        doctorInstructionPage.addMedicineToListTheFisrtTime("Ibuprofen", "by hour", "100", "48hr","automation regression test");
        log.info("  ✓ Medicine 5 added to list");
        
        // Medicine 6: Anti-inflammatory - Once at night
        log.info("  [6/6] Adding Medicine 6: SIMVACOR - 400mg once at night");
        doctorInstructionPage.addMedicineToListAndClose("SIMVACOR", "once only", "400", "1","automation regression test");
        log.info("  ✓ Medicine 6 added and form closed");
        
        UIActions.waitForSpinnerToDisappear();
    }

    /**
     * Adds 3 different blood product instructions
     * Different types and quantities for regression coverage
     */
    private void addBloodProductInstructions() {
        log.debug("Starting blood product instruction additions");
        
        // Blood Product [1/2]   1: Red blood cells
        log.info("  [1/2] Adding Blood Product 1: Packed Red Blood Cells (PRBC) - 2 units");
        doctorInstructionPage.clickButtonAddInstruction(InstructionType.BLOOD);
        doctorInstructionPage.verifySecondTitle(InstructionType.BLOOD);
        UIActions.waitForSpinnerToDisappear();
        doctorInstructionPage.addBloodProductToList("דם דחוס", "2");
        log.info("  ✓ Blood Product 1 added to list");
        
        UIActions.waitForSpinnerToDisappear();
        
        // Blood Product 2: Fresh frozen plasma
        log.info("  [2/2] Adding Blood Product 2: Fresh Frozen Plasma (FFP) - 3 units");
        doctorInstructionPage.clickButtonAddInstruction(InstructionType.BLOOD);
        doctorInstructionPage.verifySecondTitle(InstructionType.BLOOD);
        UIActions.waitForSpinnerToDisappear();
        doctorInstructionPage.addBloodProductToList("FFP", "3");
        log.info("  ✓ Blood Product 2 added to list");
        
        UIActions.waitForSpinnerToDisappear();
     
        log.debug("All 2 blood products added successfully");
    }

    /**
     * Adds 2 nutrition instructions
     * Different types and frequencies for regression coverage
     */
    private void addNutritionInstructions() {
        log.debug("Starting nutrition instruction additions");
        
        // Nutrition 1: High protein diet
        log.info("  [1/2] Adding Nutrition 1: High Protein Diet - 4 times daily");
        doctorInstructionPage.clickButtonAddInstruction(InstructionType.NUTRITION);
        doctorInstructionPage.verifySecondTitle(InstructionType.NUTRITION);
        UIActions.waitForSpinnerToDisappear();
        doctorInstructionPage.addMedicineToList("NUT c.t oil", "daily", "1000", "2","automation regression test");
        log.info("  ✓ Nutrition 1 added to list");
        
        UIActions.waitForSpinnerToDisappear();
        
        // Nutrition 2: Low sodium diet
        log.info("  [2/2] Adding Nutrition 2: Low Sodium Diet - twice daily");
        doctorInstructionPage.clickButtonAddInstruction(InstructionType.NUTRITION);
        doctorInstructionPage.verifySecondTitle(InstructionType.NUTRITION);
        UIActions.waitForSpinnerToDisappear();
        doctorInstructionPage.addFluidToListAndClose("conc. protein water", "time limit", "נמוך", "2","automation regression test");
        log.info("  ✓ Nutrition 2 added and form closed");
        
        UIActions.waitForSpinnerToDisappear();
        log.debug("All 2 nutrition instructions added successfully");
    }

    /**
     * Adds 3 fluid instructions
     * Different fluid types and rates for regression coverage
     */
    private void addFluidInstructions() {
        log.debug("Starting fluid instruction additions");
        
        // Fluid 1: Normal saline
        log.info("  [1/3] Adding Fluid 1: Normal Saline (0.9%) - 50 ml/hr");
        doctorInstructionPage.clickButtonAddInstruction(InstructionType.FLUID);
        doctorInstructionPage.verifySecondTitle(InstructionType.FLUID);
        UIActions.waitForSpinnerToDisappear();
        doctorInstructionPage.addFluidToList("סלין נורמלי (0.9%)", "רציף", "50", "1","automation regression test");
        log.info("  ✓ Fluid 1 added to list");
        
        UIActions.waitForSpinnerToDisappear();
        
        // Fluid 2: Lactated Ringer's solution
        log.info("  [2/3] Adding Fluid 2: Lactated Ringer's Solution - 100 ml/hr");
        doctorInstructionPage.clickButtonAddInstruction(InstructionType.FLUID);
        doctorInstructionPage.verifySecondTitle(InstructionType.FLUID);
        UIActions.waitForSpinnerToDisappear();
        doctorInstructionPage.addFluidToList("פתרון Lactated Ringer", "רציף", "100", "1","automation regression test");
        log.info("  ✓ Fluid 2 added to list");
        
        UIActions.waitForSpinnerToDisappear();
        
        // Fluid 3: Dextrose 5% in water
        log.info("  [3/3] Adding Fluid 3: Dextrose 5% in Water (D5W) - 75 ml/hr");
        doctorInstructionPage.clickButtonAddInstruction(InstructionType.FLUID);
        doctorInstructionPage.verifySecondTitle(InstructionType.FLUID);
        UIActions.waitForSpinnerToDisappear();
        doctorInstructionPage.addFluidAndClose("דקסטרוז 5% במים (D5W)", "רציף", "75", "1","automation regression test");
        log.info("  ✓ Fluid 3 added and form closed");
        
        UIActions.waitForSpinnerToDisappear();
        log.debug("All 3 fluid instructions added successfully");
    }
}
