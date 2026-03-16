package sanity;
import base.BaseSuit;
import helpers.Constants;
import helpers.QueriesUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
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
import pages.nurse.approval.ApprovalInstructionPage;
import pages.nurse.approval.ApprovalInstructionPage1;
import pages.nurse.wound.WondFormPage;
import pages.nurse.wound.WoundPage;

@Slf4j
public class SanitySuite1 extends BaseSuit {


    private static final int PATIENT_1 = 1;
    private static final int PATIENT_3 = 3;
    String patientMisparIshpuz =null; 
    MainMenuPage mainMenuPage= new MainMenuPage();
    ApprovalInstructionPage1 approvalInstructionPage= new ApprovalInstructionPage1(); 
    CardexPageNew cardexPageNew= new CardexPageNew();
    CardexPage cardexPage = new CardexPage();
    UserSignModalPage userSignModalPage= new UserSignModalPage(); 
    InnerMenuPage innerMenuPage= new InnerMenuPage();
    DrugFormPage drugForm= new DrugFormPage();
    PatientsListPage patientsListPageLocal= new PatientsListPage();
    ChooseDepartmentListPage chooseDepartmentListPageLocal= new ChooseDepartmentListPage();
    WoundPage woundPage= new WoundPage();
    WondFormPage woundFormPage= new WondFormPage();
    FollowupPage followupPage= new FollowupPage();
    DischargedPatientListPage dischargedPatientListPage= new DischargedPatientListPage();
    BloodOrders bloodOrders= new BloodOrders();
           
    
    @BeforeClass
    public void preTest() throws SQLException{

        log.info("* Starting Pre-Test Setup: Cleaning up patient data and preparing test environment");
       patientMisparIshpuz = getDetailsFirstPatient(QueriesUtils.getDetailsFirstPatient).get(0);
       removePatientDataBeforeTest(QueriesUtils.removePatient_from_tbl, patientMisparIshpuz);
     // cancelAllWoundsForPatient(QueriesUtils.cancelAllWoundsForPatient, patientMisparIshpuz);
       log.info("* Pre-Test Setup Complete: Patient data cleaned for misparIshpuz = {}", patientMisparIshpuz);
    }

    @Test(description = "renew instruction to spetif patient for Bug -solutinInstructionTimes", enabled = false)
    public void test_00_renewInstructionToSpetifPatient() throws SQLException {
      log.info("* Starting test_00_renewInstructionToSpetifPatient: Renewing instructions for patient with misparIshpuz = {}", patientMisparIshpuz);
       loginAsDoctor();
       chooseDepartmentVerifyListPatients(Constants.ICU_DEPARTMENT_STRING);
       choosePatient(3);
      doctorInstructionPage.renewAllInstructions();
    }
    /**
     * check login is succeeded
     */
    @Test(description = "login as doctor and verify patient list tab existing")
    public void test_01_login(){
        log.info("* Starting test_01_login: Logging in as doctor and verifying patient list tab");
        loginAsDoctor();
        mainMenuPage.verifyRoleIsDisplayed("רופא");
    }

    @Test(description = "num version existing in main menu")
    public void test_02_numVersionExisting(){
        log.info("* Starting test_02_numVersionExisting: Verifying version number in main menu");
        loginAsDoctor();
        mainMenuPage.verificationNumVersionExisting();
    }

    @Test(description = "logout and verify login page")
    public void test_03_logout(){
        log.info("* Starting test_03_logout: Logging out and verifying login page");
        loginAsDoctor();
        mainMenuPage.logout();
        // Add verification for login page here
    }

    @Test(description = "login as doctor and verify patient list is displayed")
    public void test_04_patientListisDisplayed(){
        log.info("* Starting test_04_patientListisDisplayed: Logging in as doctor and verifying patient list tab");
        loginAsDoctor();
        mainMenuPage.verifyPatientTableIsDisplayed();
    }

    @Test(description="discharded patient list visibility")
    public void test_05_dischargedPatientListVisibility() {
        log.info("* Starting test_05_dischargedPatientListVisibility: Logging in as doctor and verifying discharged patient list visibility");
        loginAsDoctor();
        innerMenuPage.navigateToMenuEntry("רשימת משוחררים");
        dischargedPatientListPage.verifyIsDischargedPatientsListVisible();
    }

    @Test(description = "entire to patient box")
    public void test_06_enterToPatientBox() {
        log.info("* Starting test_06_enterToPatientBox: Logging in as doctor and entering patient box");
        loginAsDoctor();
        choosePatient(PATIENT_1);
        // Add verification for patient box here
    }

    @Test(description = "adding a medicine")
    public void test_07_addingMedicine(){
        log.info("* Starting test_07_addingMedicine: Adding a medicine instruction for the patient");
        loginAsDoctor();
        choosePatient(PATIENT_1);
        doctorInstructionPage.addMedicineFullAndVerify("CARBOplatin", "daily", "20", "1", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

     @Test(description = "Doctor fills follow-up notes and saves")
      public void test_08_doctorFillFollowupByDoctor() {
        log.info("* Starting test_08_doctorFillFollowupByDoctor: Doctor fills follow-up notes and saves");
        loginAsDoctor();
        choosePatient(PATIENT_1);
        innerMenuPage.navigateToMenuEntry("FollowUp");
        followupPage.addFollowupAndVerify("111", "222", "333", "444", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "doctor adding fluid instruction, general instruction and blood product instruction and approve them to patient")
    public void test_09_doctorAddingFluidGeneralBloodProductAndApprove() {
        log.info("* Starting test_09_doctorAddingFluidGeneralBloodProductAndApprove: Doctor's adding fluid instruction, general instruction and blood product instruction and approve them to patient");
        loginAsDoctor();
        choosePatient(PATIENT_1);   
        doctorInstructionPage.addFluidAndClose("INJ furosemide 250mg/25ml (FUROVENIR)", "continuous", "50", "1000");
        doctorInstructionPage.addGeneralInstructionAndClose();
        doctorInstructionPage.addBloodProductAndClose("דם דחוס", "1");
        doctorInstructionPage.approveAndVerifyInstructions(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
        }


    @Test(description = "approval all instruction by nurse")
    public void test_10_approvalAllInstructionByNurse(){
            log.info("* Starting test_10_approvalAllInstructionByNurse: Approving all instructions for the patient");
            loginAsNurse();
            choosePatient(PATIENT_1);
            approvalInstructionPage.approveAllInstructions(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }

  
  @Test(description ="add a simple wound by nurse")
  public void test_11_addSimpleWoundByNurse() {
      log.info("* Starting test_11_addSimpleWoundByNurse: Adding a simple wound for the patient");
      loginAsNurse();
      choosePatient(PATIENT_1);
      //בהנחה שבבחירת המטופל נכנס למסך קרדקס
      cardexPage.clickArrowForwardToInnerMenu();
      innerMenuPage.navigateToMenuEntry("סיעוד");
      innerMenuPage.navigateToMenuEntry("פצעים");
      woundPage.clickAddWound();
       woundFormPage.addNewWound("פצע איסכמי", null, null, null);
      woundFormPage.saveWound(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);

     }

     @Test(description = "execute all instruction by nurse", dependsOnMethods = {"test_10_approvalAllInstructionByNurse"})
    public void test_13_executeAllInstructionByNurse(){
        log.info("* Starting test_13_executeAllInstructionByNurse: Executing all instructions for the patient");
        loginAsNurse();
        choosePatient(PATIENT_1);
        cardexPageNew.executeAndApproveAllToThisShiftAndApproval(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }
     
    @Test(description = "print IV label from cardex", dependsOnMethods = {"test_11_executeAllInstructionByNurse"})
    public void test_14_printIVLabelFromCardex() {
        log.info("* Starting test_14_printIVLabelFromCardex: Printing IV label from cardex");
        loginAsNurse();
        choosePatient(PATIENT_1);
        cardexPageNew.printIVLabelForFirstFluidInCardex();
    }



    @Test(description = "adding a nutrition") 
    public void test_15_addingNutrition(){
        log.info("* Starting test_15_addingNutrition: Adding a nutrition instruction for the patient");
        loginAsNutritionist();
        choosePatient(PATIENT_1);
       doctorInstructionPage.addNutritionFull("Nut", "daily", "200", "1", Constants.NUTRITIONIST_USERNAME, Constants.NUTRITIONIST_PASSWORD);
    }

  
    @Test(description = "Adding and executing immediate instruction by ER doctor")
    public void test_16_addingAndExecutingImmediateInstructionER() {
        log.info("* Starting test_16_addingAndExecutingImmediateInstructionER: Adding and executing immediate instruction by ER doctor");
        loginAsDoctor();
        chooseDepartmentVerifyListPatients(Constants.EMERGENCY_ROOM_DEPARTMENT_STRING);
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

    @Test(description = "discharged patient list visibility")
    public void test_17_dischargedPatientList() {
        log.info("* Starting test_17_dischargedPatientList: Discharged patient list visibility");
        loginAsDoctor();
        innerMenuPage.navigateToMenuEntry("רשימת משוחררים");
        dischargedPatientListPage.verifyIsDischargedPatientsListVisible();
    }

    @Test(description = "lab orders visibility")
    public void test_18_labOrdersList() {
        log.info("* Starting test_18_labOrdersList: Lab orders visibility");
        loginAsDoctor();
        innerMenuPage.navigateToMenuEntry("לקיחת דמים");
    //    bloodOrders
    }

  
}

    

    
 


