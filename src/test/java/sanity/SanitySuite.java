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
import pages.nurse.wound.WondFormPage;
import pages.nurse.wound.WoundPage;

@Slf4j
public class SanitySuite extends BaseSuit {


    private static final int PATIENT_1 = 1;
    private static final int PATIENT_3 = 3;
    String patientMisparIshpuz =null; 
    MainMenuPage mainMenuPage= new MainMenuPage();
    ApprovalInstructionPage approvalInstructionPage= new ApprovalInstructionPage(); 
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
         log.info("* Pre-Test Setup Complete: Patient data cleaned for misparIshpuz = {}", patientMisparIshpuz);
    }

    @Test(description = "renew instruction to spetif patient for Bug -solutinInstructionTimes")
    public void test_00_renewInstructionToSpetifPatient() throws SQLException {
      log.info("* Starting test_00_renewInstructionToSpetifPatient: Renewing instructions for patient with misparIshpuz = {}", patientMisparIshpuz);
        loginAsDoctor();
       chooseDepartment(Constants.ICU_DEPARTMENT_STRING);
       choosePatient(1);
      doctorInstructionPage.renewAllInstructions();
    }
    /**
     * check login is succeeded
     */
    @Test(description = "login as doctor and verify patient list tab existing")
    public void test_01_login(){
        log.info("* Starting test_01_login: Logging in as doctor and verifying patient list tab");
        loginAsDoctor();
        mainMenuPage.verificationPatientListTabExisting();
    }

    @Test(description = "adding a medicine")
    public void test_02_addingMedicine(){
        log.info("* Starting test_02_addingMedicine: Adding a medicine instruction for the patient");
        loginAsDoctor();
        choosePatient(PATIENT_1);
        doctorInstructionPage.addMedicineFullAndVerify("CARBOplatin", "daily", "20", "1", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "approval medicine by nurse",dependsOnMethods = {"test_02_addingMedicine"})
    public void test_03_approvalMedicineByNurse(){
        log.info("* Starting test_03_approvalMedicineByNurse: Approving medicine instruction for the patient");
        loginAsNurse();
        choosePatient(PATIENT_1);
        approvalInstructionPage.approveDrugsSelectFourthCurrentDayHourAndVerify(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }

    @Test(description = "execute medicine by nurse",dependsOnMethods = {"test_03_approvalMedicineByNurse"})
    public void test_04_executeMedicine(){
        log.info("* Starting test_04_executeMedicine: Executing medicine instruction for the patient");
        loginAsNurse();
        choosePatient(PATIENT_1);
        cardexPageNew.executeAndApproveAllToThisShiftAndApproval(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }
     
    @Test(description = "print IV label from cardex")
    public void test_04_1_printIVLabelFromCardex() {
        log.info("* Starting test_04_1_printIVLabelFromCardex: Printing IV label from cardex");
        loginAsNurse();
        choosePatient(PATIENT_1);
        cardexPageNew.printIVLabelForFirstFluidInCardex();
    }

    @Test(description = "Nurse navigates to wounds screen, fills form, and adds wound", enabled = false)
    public void test_04_2_nurseAddWound() {
        log.info("* Starting test_04_2_nurseAddWound: Nurse navigates to wounds screen, fills form, and adds wound");
        loginAsNurse();
        choosePatient(PATIENT_1);
        //בהנחה שבבחירת המטופל נכנס למסך קרדקס
        cardexPage.clickArrowForwardToInnerMenu();
        innerMenuPage.navigateToMenuEntry("סיעוד");
        innerMenuPage.navigateToMenuEntry("פצעים");
        
        // Fill minimal wound details and save
        woundFormPage.addNewWound("פצע ניתוחי", 1, null, null);
       // woundFormPage.saveWound();
    }


    @Test(description = "adding a nutrition") 
    public void test_05_addingNutrition(){
        log.info("* Starting test_05_addingNutrition: Adding a nutrition instruction for the patient");
        loginAsNutritionist();
        choosePatient(PATIENT_1);
       doctorInstructionPage.addNutritionFull("Nut", "daily", "200", "1", Constants.NUTRITIONIST_USERNAME, Constants.NUTRITIONIST_PASSWORD);
    }

    @Test(description = "Adding a fluid")
    public void test_06_addingFluid() {
        log.info("* Starting test_06_addingFluid: Adding a fluid instruction for the patient");
        loginAsDoctor();
        choosePatient(1);
        doctorInstructionPage.addFluidFull("INJ furosemide 250mg/25ml (FUROVENIR)", "continuous", "50", "1L", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "Adding a blood product")
    public void test_07_addingBloodProduct() {
        log.info("* Starting test_07_addingBloodProduct: Adding a blood product instruction for the patient");
        loginAsDoctor();
        choosePatient(PATIENT_1);
        doctorInstructionPage.addBloodProductFull("דם דחוס", "1", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "Adding a continuous fluid")
    public void test_08_addingContinuousFluid() {
        log.info("* Starting test_08_addingContinuousFluid: Adding a continuous fluid instruction for the patient");
        loginAsDoctor();
        choosePatient(PATIENT_1);
        doctorInstructionPage.addFluidFull("INF", "Continuous", "500", "1000", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
        doctorInstructionPage.approveAndVerifyInstructions(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "Adding and executing immediate instruction by ER doctor")
    public void test_09_addingAndExecutingImmediateInstructionER() {
        log.info("* Starting test_09_addingAndExecutingImmediateInstructionER: Adding and executing immediate instruction by ER doctor");
        loginAsDoctor();
        chooseDepartment(Constants.EMERGENCY_ROOM_DEPARTMENT_STRING);
        patientsListPage.verifyPatientsListVisible();
        choosePatient(PATIENT_3);
        doctorInstructionPage.clickButtonAddInstruction(InstructionType.MEDICINE);
        drugForm.addOneMedicine("Aspirin", "once only", "500mg", null, null, null, null, null, null, null, true);
        doctorInstructionPage.approveAndVerifyInstructions(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }


   

    @Test(description = "Doctor fills follow-up notes and saves")
    public void test_11_doctorFillFollowupByDoctor() {
        log.info("* Starting test_11_doctorFillFollowupByDoctor: Doctor fills follow-up notes and saves");
        loginAsDoctor();
        choosePatient(PATIENT_1);
        innerMenuPage.navigateToMenuEntry("FollowUp");
        followupPage.addFollowupAndVerify("111", "222", "333", "444", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "discharged patient list visibility")
    public void test_12_dischargedPatientList() {
        log.info("* Starting test_12_dischargedPatientList: Discharged patient list visibility");
        loginAsDoctor();
        innerMenuPage.navigateToMenuEntry("רשימת משוחררים");
        dischargedPatientListPage.verifyIsDischargedPatientsListVisible();
    }

    @Test(description = "lab orders visibility")
    public void test_13_labOrdersList() {
        log.info("* Starting test_13_labOrdersList: Lab orders visibility");
        loginAsDoctor();
        innerMenuPage.navigateToMenuEntry("לקיחת דמים");
    //    bloodOrders
    }

  
}

    

    
 


