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

import static org.testng.Assert.assertTrue;
public class SanitySuite extends BaseSuit {


    private static final int PATIENT_1 = 1;
    private static final int PATIENT_2 = 2;

    MainMenuPage mainMenuPage=new MainMenuPage();
    PatientBoxPage patientBoxPage = new PatientBoxPage();
    DoctorInstructionPage doctorInstructionPage = new DoctorInstructionPage();
    ApprovalInstructionPage approvalInstructionPage = new ApprovalInstructionPage();
    CardexPage cardexPage = new CardexPage();
    UserSignModalPage userSignModalPage = new UserSignModalPage();
    InnerMenuPage innerMenuPage = new InnerMenuPage();
    private DrugFormPage drugForm = new DrugFormPage();

    @BeforeTest
    public void preTest(){
        removePatientDataBeforeEachTest( QueriesUtils.removePatient_from_tbl );
    }

    /**
     * check login is succeeded
     */
    @Test(description = "login")
    public void test01_login(){
        loginAsDoctor();
        mainMenuPage.verificationPatientListTabExisting();
    }

    @Test(description = "adding a medicine")
    public void test02_addingMedicine(){
        loginAsDoctor();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        doctorInstructionPage.addMedicineFull("dep", "daily", "20", "1", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }

    @Test(description = "approval medicine by nurse")
    public void test03_approvalMedicineByNurse(){
        loginAsNurse();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        approvalInstructionPage.approveDrugsSelectFourthCurrentDayHour();
        approvalInstructionPage.approvalAllInstructionByNurse(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }

    @Test(description = "execute medicine by nurse")
    public void test04_executeMedicine(){
        loginAsNurse();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        cardexPage.executeAllDrugsToThisShift();
        cardexPage.approvalAllExecution(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD);
    }

    // הוספת הוראה כללית בתור טסט מעצמו ??
    @Test(description = "view documents")
    public void test05_viewDocuments(){
        loginAsDoctor();
        choosePatient(PATIENT_2);
        patientBoxPage.verifyPatientDetailsExisting();
        innerMenuPage.navigateToMenuEntry("מנהל חולים");
        innerMenuPage.navigateToMenuEntry("מסמכים");
        assertTrue(innerMenuPage.isMenuEntryDisplayed("מסמכים"));


    }

    @Test(description = "adding a fluid instruction")
    public void test06_addingFluidInstruction(){
        loginAsDoctor();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        doctorInstructionPage.addFluidFull("INF", "Continuous", "500", "1000", Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD);
    }


}
