package sanity;
import base.BaseSuit;
import enums.HospitalDepartment;
import enums.InstructionType;
import helpers.Constants;
import helpers.QueriesUtils;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;

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
import pages.nurse.wound.WondFormPage;
import pages.nurse.wound.WoundPage;

@Slf4j
@org.testng.annotations.Listeners(helpers.Listeners.class)
public class SanitySuite1 extends BaseSuit {


    private static final int PATIENT_1 = 3;
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
    WoundPage woundPage;
    WondFormPage woundFormPage;
    FollowupPage followupPage;
    DischargedPatientListPage dischargedPatientListPage;
    BloodOrders bloodOrders;      

    HospitalDepartment currentDept; 

    @BeforeClass
    public void preTest() throws SQLException{

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
    log.info("* Starting Pre-Test Setup: Cleaning up patient data and preparing test environment");
       patientMisparIshpuz = getDetailsFirstPatient(QueriesUtils.getDetailsFirstPatient(currentDept.getCode())).get(0);
       boolean isRemoved = removePatientDataBeforeTest(QueriesUtils.removePatient_from_tbl, patientMisparIshpuz);
         if (isRemoved) {
              log.info("* Patient data removed successfully for misparIshpuz = {}", patientMisparIshpuz);
         } else {
              log.warn("* No patient data found to remove for misparIshpuz = {}", patientMisparIshpuz);
         }
       // cancelAllWoundsForPatient(QueriesUtils.cancelAllWoundsForPatient, patientMisparIshpuz);
       log.info("* Pre-Test Setup Complete: Patient data cleaned for misparIshpuz = {}", patientMisparIshpuz);
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
    @Test(description = "login as doctor and verify role name is displayed")
    public void test_01_login(){
        log.info("* Starting test_01_login: Logging in as doctor and verifying role name");
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
    public void test_04_patientListIsDisplayed(){
        log.info("* Starting test_04_patientListIsDisplayed: Logging in as doctor and verifying patient list tab");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        mainMenuPage.verifyPatientTableIsDisplayed();
    }

    @Test(description="discharded patient list visibility")
    public void test_05_dischargedPatientListVisibility() {
        log.info("* Starting test_05_dischargedPatientListVisibility: Logging in as doctor and verifying discharged patient list visibility");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        innerMenuPage.navigateToMenuEntry("רשימת משוחררים");
        dischargedPatientListPage.verifydischargedPatientsListVisible();
    }

    @Test(description = "entire to patient box")
    public void test_06_enterToPatientBox() {
        log.info("* Starting test_06_enterToPatientBox: Logging in as doctor and entering patient box");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
        // Add verification for patient box here
    }

    @Test(description = "adding a medicine")
    public void test_07_addingMedicine(){
        log.info("* Starting test_07_addingMedicine: Adding a medicine instruction for the patient");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
        doctorInstructionPage.addMedicineFullAndVerify("CARBOplatin", "daily", "20", "1", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

     @Test(description = "Doctor fills follow-up notes and saves")
      public void test_08_doctorFillFollowupByDoctor() {
        log.info("* Starting test_08_doctorFillFollowupByDoctor: Doctor fills follow-up notes and saves");
        loginAsDoctor();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(2);
        innerMenuPage.navigateToMenuEntry("FollowUp");
        followupPage.addFollowupAndVerify("111", "222", "333", "444", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "doctor adding fluid instruction, general instruction and blood product instruction")
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


    @Test(description = "approval all instruction by nurse")
    public void test_10_approvalAllInstructionByNurse(){
            log.info("* Starting test_10_approvalAllInstructionByNurse: Approving all instructions for the patient");
            loginAsNurse();
            chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
            choosePatient(PATIENT_1);
            approvalInstructionPage.approveAllInstructions(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }

  
  @Test(description ="add a simple wound by nurse")
  public void test_11_addSimpleWoundByNurse() {
      log.info("* Starting test_11_addSimpleWoundByNurse: Adding a simple wound for the patient");
      loginAsNurse();
      chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
      choosePatient(PATIENT_1);
      //בהנחה שבבחירת המטופל נJava: Configure Java Runtimeכנס למסך קרדקס
      cardexPageNew.clickArrowForwardToInnerMenu();
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
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
        cardexPageNew.executeAndApproveAllToThisShiftAndApproval(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }
     
    @Test(description = "print IV label from cardex", dependsOnMethods = {"test_13_executeAllInstructionByNurse"})
    public void test_14_printIVLabelFromCardex() {
        log.info("* Starting test_14_printIVLabelFromCardex: Printing IV label from cardex");
        loginAsNurse();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
        cardexPageNew.printIVLabelForFirstFluidInCardex();
    }



    @Test(description = "adding a nutrition") 
    public void test_15_addingNutrition(){
        log.info("* Starting test_15_addingNutrition: Adding a nutrition instruction for the patient");
        loginAsNutritionist();
        chooseDepartmentListPage.selectDepartment(this.currentDept.getDisplayName());
        choosePatient(PATIENT_1);
       doctorInstructionPage.addNutritionFull("Nut", "daily", "200", "1", Constants.NUTRITIONIST_USERNAME, Constants.NUTRITIONIST_PASSWORD);
    }

  
    @Test(description = "Adding and executing immediate instruction by ER doctor")
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

    
   
    @Test(description = "lab orders visibility")
    public void test_18_labOrdersList() {
        log.info("* Starting test_18_labOrdersList: Lab orders visibility");
        loginAsDoctor();
        innerMenuPage.navigateToMenuEntry("לקיחת דמים");
    //    bloodOrders
    }

  
}

    

    
 


