package base;

import actionUtilies.DBExecuter;
import drivers.DriverManager;
import helpers.Constants;

import javax.print.Doc;

import org.testng.annotations.AfterTest;

import pages.ChooseDepartmentListPage;
import pages.DoctorInstructionPage;
import pages.InstructionType;
import pages.LoginPage;
import pages.PatientBoxPage;
import pages.mainPages.PatientsListPage;

public class BaseSuit {

    @AfterTest()
    public void closeBrowser(){
        DriverManager.getInstance().quit();
    }

    LoginPage loginPage=new LoginPage();
    PatientsListPage patientsListPage = new PatientsListPage();
    PatientBoxPage patientBoxPage = new PatientBoxPage();
    DoctorInstructionPage doctorInstructionPage = new DoctorInstructionPage();
    ChooseDepartmentListPage chooseDepartmentListPage = new ChooseDepartmentListPage();
    protected void loginAsDoctor() {
        loginPage.login(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD, Constants.DOCTOR_ROLE);
    }

    protected void loginAsNurse() {
        loginPage.login(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD, Constants.NURSE_ROLE);
    }

    
    protected void loginAsNutritionist() {
        loginPage.login(Constants.NUTRITIONIST_USERNAME, Constants.NUTRITIONIST_PASSWORD, Constants.NUTRITIONIST_ROLE);
    }

    protected void choosePatient(int patientIndex) {
        patientsListPage.choosePatient(patientIndex);
    }


    private void selectFirstPatient() {
        patientsListPage.choosePatient(1);
        patientBoxPage.verifyPatientDetailsExisting();
    }

    protected void chooseDepartment(String departmentName) {
        chooseDepartmentListPage.selectDepartment(departmentName);
    }

      public void openInstructionForm(InstructionType type) {
        switch(type) {
            case MEDICINE:
               doctorInstructionPage.clickButtonAddInstruction(type);
                break;
            case BLOOD:
                //     clickButtonAddBloodProduct();
                break;
            case FLUID:
              //       clickButtonAddFluid();
                break;
            case GENERAL:
              //  clickButtonAddGeneralInstruction();
                break;
            case NUTRITION:
              //  clickButtonAddNutrition();
                break;
            case IMMEDIATE:
               // clickButtonAddImmediateInstruction();
                break;
            case TREATMENT_PROTOCOL:
               /////// clickButtonAddTreatmentProtocol();
                break;
            case MEDICINE_PROTOCOL:
            //////    clickButtonAddMedicineProtocol();
                break;
            case IMPORT_MEDICINE:
            //    clickButtonImportMedicine();
                break;
        }
    }

 


    protected void removePatientDataBeforeEachTest(String query) {
        int result = DBExecuter.executeUpdateQuery(query);
        if(result!=-1){
            System.out.println("Patient removed successfully");
        }
    }



}
