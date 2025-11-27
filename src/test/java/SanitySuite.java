import org.testng.annotations.Test;
import pages.*;
import pages.addForms.DrugFormPage;
import pages.mainPages.PatientsListPage;
import pages.menu.InnerMenuPage;
import pages.menu.MainMenuPage;
import pages.nurse.Execute.CardexPage;
import pages.nurse.approval.ApprovalInstructionPage;

import static org.testng.Assert.assertTrue;
public class SanitySuite extends BaseTest {

    private static final String DOCTOR_ROLE = "רופא";
    private static final String NURSE_ROLE = "אחות";
    
    private static final String DOCTOR_USERNAME = "test";
    private static final String DOCTOR_PASSWORD = "Te231121";
    
    private static final String NURSE_USERNAME = "test";
    private static final String NURSE_PASSWORD = "Te231121";

    private static final int PATIENT_1 = 1;
    private static final int PATIENT_2 = 2;

    LoginPage loginPage=new LoginPage();
    MainMenuPage mainMenuPage=new MainMenuPage();

    PatientsListPage patientsListPage = new PatientsListPage();

    PatientBoxPage patientBoxPage = new PatientBoxPage();

    DoctorInstructionPage doctorInstructionPage = new DoctorInstructionPage();

    ApprovalInstructionPage approvalInstructionPage = new ApprovalInstructionPage();

    CardexPage cardexPage = new CardexPage();

    UserSignModalPage userSignModalPage = new UserSignModalPage();

    InnerMenuPage innerMenuPage = new InnerMenuPage();
    private DrugFormPage drugForm = new DrugFormPage();

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
        doctorInstructionPage.addMedicineFull("dep", "daily", "20", "1", DOCTOR_USERNAME, DOCTOR_PASSWORD);
    }
    @Test(description = "approval medicine by nurse")
    public void test03_approvalMedicineByNurse(){
        loginAsNurse();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        approvalInstructionPage.approveDrugsSelectFourthCurrentDayHour();
        approvalInstructionPage.approvalAllInstructionByNurse(NURSE_USERNAME, NURSE_PASSWORD);
    }

    @Test(description = "execute medicine by nurse")
    public void test04_executeMedicine(){
        loginAsNurse();
        choosePatient(PATIENT_1);
        patientBoxPage.verifyPatientDetailsExisting();
        cardexPage.executeAllDrugsToThisShift();
        cardexPage.approvalAllExecution(NURSE_USERNAME, NURSE_PASSWORD);
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
        doctorInstructionPage.addFluidFull("INF", "Continuous", "500", "1000", DOCTOR_USERNAME, DOCTOR_PASSWORD);
    }

    // helper methods to reduce duplication and centralize credentials / actions
    private void loginAsDoctor() {
        loginPage.login(DOCTOR_USERNAME, DOCTOR_PASSWORD, DOCTOR_ROLE);
    }

    private void loginAsNurse() {
        loginPage.login(NURSE_USERNAME, NURSE_PASSWORD, NURSE_ROLE);
    }

    private void choosePatient(int patientIndex) {
        patientsListPage.choosePatient(patientIndex);
    }

}
