package sanity;

import base.BaseSuit;
import helpers.Constants;
import helpers.QueriesUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.*;
import pages.addForms.DrugFormPage;
import pages.mainPages.PatientsListPage;
import pages.menu.InnerMenuPage;
import pages.menu.MainMenuPage;
import pages.nurse.Execute.CardexPage;
import pages.nurse.approval.ApprovalInstructionPage;

public class SanitySuite extends BaseSuit {


    private static final int PATIENT_1 = 1;

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

    @BeforeTest
    public void preTest(){
        removePatientDataBeforeEachTest( QueriesUtils.removePatient_from_tbl );
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
        doctorInstructionPage.addMedicineFull("dep", "daily", "20", "1", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "approval medicine by nurse")
    public void test_03_approvalMedicineByNurse(){
        loginAsNurse();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        approvalInstructionPage.approveDrugsSelectFourthCurrentDayHour();
        approvalInstructionPage.approvalAllInstructionByNurse(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
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

    


    
 

}
