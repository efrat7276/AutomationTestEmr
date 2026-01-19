package sanity;
import base.BaseSuit;
import helpers.Constants;
import helpers.QueriesUtils;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.List;

import org.testng.annotations.BeforeClass;
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
import pages.doctor.FollowupPage;
import pages.nurse.approval.ApprovalInstructionPage;
import pages.nurse.wound.WondFormPage;
import pages.nurse.wound.WoundPage;

public class SanitySuite extends BaseSuit {


    private static final int PATIENT_1 = 1;
    private static final int PATIENT_3 = 3;
    String patientMisparIshpuz =null; 

    MainMenuPage mainMenuPage=new MainMenuPage();
    PatientBoxPage patientBoxPage = new PatientBoxPage();
    DoctorInstructionPage doctorInstructionPage = new DoctorInstructionPage();
    ApprovalInstructionPage approvalInstructionPage = new ApprovalInstructionPage();
    CardexPage cardexPage = new CardexPage();
    UserSignModalPage userSignModalPage = new UserSignModalPage();
    InnerMenuPage innerMenuPage = new InnerMenuPage();
    DrugFormPage drugForm = new DrugFormPage();
    PatientsListPage patientsListPage = new PatientsListPage();
    ChooseDepartmentListPage chooseDepartmentListPage = new ChooseDepartmentListPage();
    WoundPage woundPage = new WoundPage();
    WondFormPage woundFormPage = new WondFormPage();
    FollowupPage followupPage = new FollowupPage();
    DischargedPatientListPage dischargedPatientListPage = new DischargedPatientListPage();
    BloodOrders bloodOrders = new BloodOrders();
    @BeforeClass
    public void preTest() throws SQLException{

       getDetailsFirstPatient(QueriesUtils.getDetailsFirstPatient).get(0);
       removePatientDataBeforeTest(QueriesUtils.removePatient_from_tbl, patientMisparIshpuz);
    }

    /**
     * check login is succeeded
     */
    @Test(description = "login")
    public void test_01_login(){
        loginAsDoctor();
        mainMenuPage.verificationPatientListTabExisting();

    }

    @Test(description = "adding a medicine")
    public void test_02_addingMedicine(){
        loginAsDoctor();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        doctorInstructionPage.addMedicineFullAndVerify("CARBOplatin", "daily", "20", "1", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "approval medicine by nurse")
    public void test_03_approvalMedicineByNurse(){
        loginAsNurse();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        approvalInstructionPage.approveDrugsSelectFourthCurrentDayHour();
        approvalInstructionPage.approvalAllInstructionByNurseAndVerify(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }

    @Test(description = "execute medicine by nurse")
    public void test_04_executeMedicine(){
        loginAsNurse();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        cardexPage.executeAllDrugsToThisShift();
        cardexPage.approvalAllExecution(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }


    @Test(description = "adding a nutrition")
    public void test_05_addingNutrition(){
        loginAsNutritionist();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
       doctorInstructionPage.addNutritionFull("Nut", "daily", "200", "1", Constants.NUTRITIONIST_USERNAME, Constants.NUTRITIONIST_PASSWORD);
    }

    @Test(description = "Adding a fluid")
    public void test_06_addingFluid() {
        loginAsDoctor();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        doctorInstructionPage.addFluidFull("INJ", "continuous", "50", "1L", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "Adding a blood product")
    public void test_07_addingBloodProduct() {
        loginAsDoctor();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        doctorInstructionPage.addBloodProductFull("דם דחוס", "1", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "Adding a continuous fluid")
    public void test_08_addingContinuousFluid() {
        loginAsDoctor();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        doctorInstructionPage.addFluidFull("INF", "Continuous", "500", "1000", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
        doctorInstructionPage.approveAndVerifyInstructions(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "Adding and executing immediate instruction by ER doctor")
    public void test_09_addingAndExecutingImmediateInstructionER() {
        loginAsDoctor();
        chooseDepartment(Constants.EMERGENCY_ROOM_DEPARTMENT_STRING);
        patientsListPage.verifyPatientsListVisible();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        doctorInstructionPage.clickButtonAddInstruction(InstructionType.MEDICINE);
        drugForm.addOneMedicine("Aspirin", "once only", "500mg", null, null, null, null, null, null, null, true);
        doctorInstructionPage.approveAndVerifyInstructions(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }


    @Test(description = "Nurse navigates to wounds screen, fills form, and adds wound")
    public void test_10_nurseAddWound() {
        loginAsNurse();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        //בהנחה שבבחירת המטופל נכנס למסך קרדקס
        cardexPage.clickArrowForwardToInnerMenu();
        innerMenuPage.navigateToMenuEntry("סיעוד");
        innerMenuPage.navigateToMenuEntry("פצעים");
        
        // Fill minimal wound details and save
        woundFormPage.addNewWound("פצע ניתוחי", 1, null, null);
       // woundFormPage.saveWound();
        System.out.println("נפתח טופס הוספת פצע בהצלחה.");
    }

    @Test(description = "Doctor fills follow-up notes and saves")
    public void test_11_doctorFillFollowup() {
        loginAsDoctor();
        choosePatient(PATIENT_3);
        patientBoxPage.verifyPatientDetailsExisting();
        innerMenuPage.navigateToMenuEntry("FollowUp");
        followupPage.addFollowup("111", "222", "333", "444", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
        System.out.println("Follow-up notes added and saved successfully.");
    }

    @Test(description = "discharged patient list visibility")
    public void test_12_dischargedPatientList() {
        loginAsDoctor();
        innerMenuPage.navigateToMenuEntry("רשימת משוחררים");
        dischargedPatientListPage.verifyIsDischargedPatientsListVisible();
    }

    @Test(description = "lab orders visibility")
    public void test_13_labOrdersList() {
        loginAsDoctor();
        innerMenuPage.navigateToMenuEntry("לקיחת דמים");
    //    bloodOrders
    }

    @Test(description = "print IV label from cardex")
    public void test_14_printIVLabelFromCardex() {
        loginAsNurse();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        cardexPage.printIVLabelForFirstFluidInCardex();
    }
}

    

    
 


