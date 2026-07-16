package sanity;
import base.BaseSuit;
import enums.HospitalDepartment;
import enums.InstructionType;
import helpers.Constants;
import helpers.QueriesUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import actionUtilies.UIActions;
import pages.*;
import pages.addForms.DrugFormPage;
import pages.mainPages.BloodOrders;
import pages.mainPages.DischargedPatientListPage;
import pages.mainPages.PatientsListPage;
import pages.menu.InnerMenuPage;
import pages.menu.MainMenuPage;
import pages.nurse.Execute.CardexPage;
import pages.nurse.Execute.CardexPageNew;
import pages.doctor.FollowupPage;
import pages.nurse.approval.ApprovalInstructionPage1;
import pages.nurse.catheter.CatheterPage;
import pages.nurse.wound.WondFormPage;
import pages.nurse.wound.WoundPage;
import pages.patient_admin.DocumentsPage;
import pages.patient_admin.ImagingPage;

@Slf4j
@org.testng.annotations.Listeners(helpers.Listeners.class)
@Epic("Sanity Suite")
public class SanitySuite1 extends BaseSuit {


    private static final int PATIENT_1 = 1;
    String patientMisparIshpuz =null; 
    MainMenuPage mainMenuPage;
    ApprovalInstructionPage1 approvalInstructionPage; 
    CardexPageNew cardexPageNew;
    CardexPage cardexPage;
    UserSignModalPage userSignModalPage; 
    InnerMenuPage innerMenuPage;
    DrugFormPage drugForm;
    PatientsListPage patientsListPageLocal ;
    ChooseDepartmentListPage chooseDepartmentListPageLocal;
    DocumentsPage documentsPage;
    ImagingPage imagingPage;
    WoundPage woundPage;
    WondFormPage woundFormPage;
    FollowupPage followupPage;
    DischargedPatientListPage dischargedPatientListPage;
    BloodOrders bloodOrders;  
    CatheterPage catheterPage;    

    HospitalDepartment currentDept; 

    @BeforeClass
    public void preClass() throws SQLException{

    String deptNameParam = System.getProperty("department", "אורטופדיה");
    HospitalDepartment foundDept = HospitalDepartment.getByHebrewName(deptNameParam);
    
    if (foundDept != null) {
        this.currentDept = foundDept;
        log.info("Successfully set department to: {} (Code: {})", 
                 currentDept.getDisplayName(), currentDept.getCode());
    } else {
        this.currentDept = HospitalDepartment.INTERNAL_B;
        log.error("Department '{}' not found! Using default: {}", 
                  deptNameParam, currentDept.getDisplayName());
    }
      patientMisparIshpuz = getDetailsFirstPatient(QueriesUtils.getDetailsFirstPatient(currentDept.getCode())).get(0);
     boolean isSucceeded=
      preparePatientDataBeforeTest(QueriesUtils.preparePatientData, patientMisparIshpuz);
         if (isSucceeded) {
              log.info("* Patient data removed successfully for misparIshpuz = {}", patientMisparIshpuz);
         } else {
              log.warn("* No patient data found to remove for misparIshpuz = {}", patientMisparIshpuz);
         }
       log.info("* Pre-Class Setup Complete: Patient data cleaned for misparIshpuz = {}", patientMisparIshpuz);
    
     }


    @BeforeMethod
    public void setUp(){
        super.setUp();
        mainMenuPage= new MainMenuPage();
        approvalInstructionPage= new ApprovalInstructionPage1();    
        cardexPageNew= new CardexPageNew();
        cardexPage = new CardexPage();
        userSignModalPage= new UserSignModalPage();
        innerMenuPage= new InnerMenuPage();
        drugForm= new DrugFormPage();
        patientsListPageLocal= new PatientsListPage();
        dischargedPatientListPage= new DischargedPatientListPage();
        chooseDepartmentListPageLocal= new ChooseDepartmentListPage();
        woundPage= new WoundPage();
        woundFormPage= new WondFormPage();
        followupPage= new FollowupPage();
        bloodOrders= new BloodOrders();
        catheterPage = new CatheterPage();
        documentsPage= new DocumentsPage();
        imagingPage= new ImagingPage();

    }
    @Feature("Functional Tests")
    @Story("Renewing instruction to patient due to Bug -solutinInstructionTimes")
    @Test()
    public void test_00_renewInstructionToSpetifPatient() throws SQLException {
      log.info("* Starting test_00_renewInstructionToSpetifPatient: Renewing instructions for patient with misparIshpuz = {}", patientMisparIshpuz);
       loginAsDoctor();
       chooseDepartmentVerifyListPatients(Constants.ICU_DEPARTMENT_STRING);
       choosePatient(3);
      doctorInstructionPage.renewAllInstructions();
    }
 
    @Feature("Visual Tests")
    @Story("Logging in as doctor and verify role name is displayed")
    @Test()
    public void test_01_login(){
        log.info("* Starting test_01_login: Logging in as doctor and verifying role name");
        loginAsDoctor();
        mainMenuPage.verifyRoleIsDisplayed("רופא");
    }
    
    @Feature("Visual Tests")
    @Story("Num version existing in main menu")
    @Test(enabled = false)
    public void test_02_numVersionExisting(){
        log.info("* Starting test_02_numVersionExisting: Verifying version number in main menu");
        loginAsDoctor();
        mainMenuPage.verificationNumVersionExisting();
    }

    @Feature("Navigation Tests")
    @Story("Logging out and verify login page is displayed")
    @Test()
    public void test_03_logout(){
        log.info("* Starting test_03_logout: Logging out");
        loginAsDoctor();
        mainMenuPage.logout();
    }

    @Feature("Visual Tests")
    @Story("Logging in as doctor and verify patient list is displayed")
    @Test()
    public void test_04_patientListIsDisplayed(){
        log.info("* Starting test_04_patientListIsDisplayed: Logging in as doctor");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        mainMenuPage.verifyPatientTableIsDisplayed();
    }

    @Feature("Visual Tests")
    @Story("Discharged patient list is visible after navigating to the menu entry")
    @Test()
    public void test_05_dischargedPatientListVisibility() {
        log.info("* Starting test_05_dischargedPatientListVisibility: Logging in as doctor ");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        innerMenuPage.navigateToMenuEntry("רשימת משוחררים");
        dischargedPatientListPage.verifydischargedPatientsListVisible();
    }

    @Feature("Functional Tests")
    @Story("Entering to patient box")
    @Test(enabled = false)
    public void test_06_enterToPatientBox() {
        log.info("* Starting test_06_enterToPatientBox: Logging in as doctor and entering patient box");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
    }

    @Feature("Functional Tests")
    @Story("Adding a medicine instruction by doctor")
    @Test()
    public void test_07_addingMedicine(){
        log.info("* Starting test_07_addingMedicine: Adding a medicine instruction for the patient");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
        doctorInstructionPage.addMedicineFullAndVerify("CARBOplatin", "daily", "20", "1", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
      log.info("Added medicine instruction successfully.");
    
    }

    @Feature("Functional Tests")
    @Story("Filling follow-up and saving by doctor")
     @Test()
      public void test_08_doctorFillFollowupByDoctor() {
        log.info("* Starting test_08_doctorFillFollowupByDoctor: Doctor fills follow-up notes and saves");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
        innerMenuPage.navigateToMenuEntry("FollowUp");
        followupPage.addFollowupAndVerify("111", "222", "333", "444", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Feature("Functional Tests")
    @Story("Adding fluid instruction, general instruction and blood product instruction by doctor")
    @Test()
    public void test_09_doctorAddingFluidGeneralBloodProductAndApprove() {
        log.info("* Starting test_09_doctorAddingFluidGeneralBloodProductAndApprove: Doctor's adding fluid instruction, general instruction and blood product instruction");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);   
        doctorInstructionPage.addFluidAndClose("INJ furosemide 250mg/25ml (FUROVENIR)", "continuous", "50", "1000");
        doctorInstructionPage.addGeneralInstructionAndClose();
        doctorInstructionPage.addBloodProductAndClose("דם דחוס", "1");
        doctorInstructionPage.approveAndVerifyInstructions(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Feature("Functional Tests")
    @Story("Approving instructions and moving to cardex by nurse")
    @Test()
    public void test_10_approvalAllInstructionByNurse(){
            log.info("* Starting test_10_approvalAllInstructionByNurse: Approving all instructions for the patient");
            loginAsNurse();
            chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
            choosePatient(PATIENT_1);
            approvalInstructionPage.approveAllInstructionsAndVerify(true, true, true , Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
  
     }

    @Feature("Functional Tests")
    @Story("Adding a simple wound by nurse")
    @Test()
     public void test_11_addSimpleWoundByNurse() {
      log.info("* Starting test_11_addSimpleWoundByNurse: Adding a simple wound for the patient");
      loginAsNurse();
      chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
      choosePatient(PATIENT_1);
      cardexPageNew.clickArrowForwardToInnerMenu();
      innerMenuPage.navigateToMenuEntry("סיעוד");
      innerMenuPage.navigateToMenuEntry("פצעים");
      woundPage.clickAddWound();
      woundFormPage.addNewWound("פצע איסכמי", null, null, null);
      woundFormPage.saveWound(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);

     }
     @Feature("Functional Tests")
    @Story("Adding a catheter by nurse")
     @Test(description = "add catheter by nurse")
     public void test_12_addCatheterByNurse() {
         log.info("* Starting test_12_addCatheterByNurse: Adding a catheter for the patient");
         loginAsNurse();
         chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
         choosePatient(PATIENT_1);
         cardexPageNew.clickArrowForwardToInnerMenu();
         innerMenuPage.navigateToMenuEntry("סיעוד");
         innerMenuPage.navigateToMenuEntry("נקזים וצנתרים");
         catheterPage.addCatheterBranola(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
     }

    @Feature("Functional Tests")
    @Story("Executing all active instructions by nurse in cardex")
    @Test()
    public void test_13_executeAllInstructionByNurse(){
        log.info("* Starting test_13_executeAllInstructionByNurse: Executing all instructions for the patient");
        loginAsNurse();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
        cardexPageNew.executeAndApproveAllToThisShiftAndApproval(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }
    
    @Feature("Functional Tests")
    @Story("Printing IV label from cardex")
    @Test()
    public void test_14_printIVLabelFromCardex() {
        log.info("* Starting test_14_printIVLabelFromCardex: Printing IV label from cardex");
        loginAsNurse();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
        cardexPageNew.printIVLabelForFirstFluidInCardex();
    }


    @Feature("Functional Tests")
    @Story("Adding a nutrition instruction by nutritionist")
    @Test()
    public void test_15_addingNutrition(){
        log.info("* Starting test_15_addingNutrition: Adding a nutrition instruction for the patient");
        loginAsNutritionist();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
       doctorInstructionPage.addNutritionFull("Nut", "daily", "200", "1", Constants.NUTRITIONIST_USERNAME, Constants.NUTRITIONIST_PASSWORD);
    }

    @Feature("Functional Tests")
    @Story("Adding and executing immediate instruction by ER doctor")
    @Test()
    public void test_16_addingAndExecutingImmediateInstructionER() {
        log.info("* Starting test_16_addingAndExecutingImmediateInstructionER: Adding and executing immediate instruction by ER doctor");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(HospitalDepartment.getByHebrewName("חדר מיון").getDisplayName());
       try {
        Thread.sleep(1000);
       } catch (InterruptedException e) {
        e.printStackTrace();
      }
        choosePatient(1);
        doctorInstructionPage.clickButtonAddInstruction(InstructionType.MEDICINE);
        drugForm.addOneMedicine("Aspirin", "once only", "500mg", null, null, null, null, null, null, null, true);
        doctorInstructionPage.approveAndVerifyInstructions(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
        innerMenuPage.navigateToMenuEntry("קרדקס");
        UIActions.waitForSpinnerToDisappear();
        cardexPageNew.verifyDrugExecuted("Aspirin");

    }

    @Feature("Functional Tests")
    @Story("Editing a dosage drug instruction by doctor")
    @Test()
    public void test_17_editDrug() {
        log.info("* Starting test_17_editDrug: Editing a drug instruction for the patient");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
        doctorInstructionPage.editFirstDrugInstruction(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD, null, "250");
    }

    
   
    @Feature("Visual Tests")
    @Story("Lab orders visibility")
    @Test()
    public void test_18_labOrdersList() {
        log.info("* Starting test_18_labOrdersList: Lab orders visibility");
        loginAsDoctor();
        innerMenuPage.navigateToMenuEntry("לקיחת דמים");
    //    bloodOrders
    }

    @Feature("Functional Tests")
    @Story("Adding and approving a single dose medicine by nurse")
    @Test(enabled = false)
     public void test_19_addAndApprovalByNurseSingleOnceOnlyMedicineAndVerify() {
      log.info("* Starting test_19_addAndApprovalByNurseSingleOnceOnlyMedicineAndVerify: Adding a single dose medicine for the patient");
      loginAsDoctor();
      chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
      choosePatient(PATIENT_1);
      doctorInstructionPage.clickButtonAddInstruction(InstructionType.MEDICINE);
      drugForm.addOneMedicine("ACAMOL", "once only", "500", null, null, null, null, null, null, null, false);
      doctorInstructionPage.approveAndVerifyInstructions(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
      log.info("Single dose medicine ACAMOL added successfully to patient");
      
      // Step 2: Nurse approves the medicine (ONLY drugs - no liquids or blood products)
      log.info("* Nurse approving the ACAMOL medicine instruction");
      loginAsNurse();
      chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
      choosePatient(PATIENT_1);
      approvalInstructionPage.approveAllInstructionsAndVerify(true, false, false, Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
      log.info("* Successfully: Nurse approved ACAMOL medicine instruction");
  }

  
    @Feature("Visual Tests")
    @Story("viewing a patient's document")
    @Test()
    public void test_20_viewPatientDocuments() {
      try {
          loginAsDoctor();
          chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
          choosePatient(PATIENT_1);
          documentsPage.navigateToDocuments(); 
          assertTrue(documentsPage.isOnDocumentsPage(), "Should be on documents page"); 
          int docCount = documentsPage.getDocumentsCount();
          assertTrue(docCount > 0, "Should have at least one document available"); 
          documentsPage.openFirstDocument(); 
          assertTrue(documentsPage.isDocumentViewerOpened(), "Document viewer should open"); 
          log.info("✓ Test passed: Document viewed successfully");
          
      } catch (Exception e) {
          log.error("✗ Test failed: {}", e.getMessage());
          assertTrue(false, "Failed to view document: " + e.getMessage());
      }
  }

  @Feature("Functional Tests")
  @Story("viewing a patient's imaging in PACS viewer")
  @Test()
  public void test_21_viewImagingInPacs() {
      try {
          loginAsDoctor();
          chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
          choosePatient(PATIENT_1);   
          imagingPage.navigateToImaging(); 
          assertTrue(imagingPage.isOnImagingPage(), "Should be on imaging page");  
          int imagingCount = imagingPage.getImagingCount();
          assertTrue(imagingCount > 0, "Should have at least one imaging study available");
          imagingPage.openFirstImagingInPacs();   
          assertTrue(imagingPage.isPacsViewerOpened(), "PACS viewer should open");  
          log.info("✓ Test passed: Imaging viewed in PACS successfully");
         
      } catch (Exception e) {
          log.error("✗ Test failed: {}", e.getMessage());
          assertTrue(false, "Failed to view imaging in PACS: " + e.getMessage());
      }
  }  
}

    

    
 


