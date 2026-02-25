package base;

import actionUtilies.DBExecuter;
import drivers.DriverManager;
import helpers.Constants;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

import javax.print.Doc;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import pages.ChooseDepartmentListPage;
import pages.DoctorInstructionPage;
import pages.InstructionType;
import pages.LoginPage;
import pages.PatientBoxPage;
import pages.mainPages.PatientsListPage;

@Slf4j
public class BaseSuit {

    
    protected LoginPage loginPage;
    protected PatientsListPage patientsListPage;
    protected PatientBoxPage patientBoxPage;
    protected DoctorInstructionPage doctorInstructionPage;
    protected ChooseDepartmentListPage chooseDepartmentListPage;
   
    @BeforeMethod
    public void setUp() {
    log.info(">>> Starting Test Setup");
    DriverManager.getInstance(); 
    
    log.info(">>> Initializing pages with active driver");
    loginPage = new LoginPage();
    patientsListPage = new PatientsListPage();
    patientBoxPage = new PatientBoxPage();
    doctorInstructionPage = new DoctorInstructionPage();
    chooseDepartmentListPage = new ChooseDepartmentListPage();
    
    log.info(">>> Test Setup Complete");
  }
    @AfterClass
    public void tearDown() {
        log.info("<<< Quitting driver and cleaning up resources");
        DriverManager.quitDriver();
    }
    
    protected void loginAsDoctor() {
        log.info("Performing Login as DOCTOR: {}", Constants.DOCTOR_USERNAME);
        loginPage.login(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD, Constants.DOCTOR_ROLE);
    }

    protected void loginAsNurse() {
        log.info("Performing Login as NURSE: {}", Constants.NURSE_USERNAME);
        loginPage.login(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD, Constants.NURSE_ROLE);
    }

    
    protected void loginAsNutritionist() {
        log.info("Performing Login as NUTRITIONIST: {}", Constants.NUTRITIONIST_USERNAME);
        loginPage.login(Constants.NUTRITIONIST_USERNAME, Constants.NUTRITIONIST_PASSWORD, Constants.NUTRITIONIST_ROLE);
    }

    protected void choosePatient(int patientIndex) {
        log.info("Choosing patient at index: {}", patientIndex);
        patientsListPage.choosePatient(patientIndex);
        patientBoxPage.verifyPatientDetailsExisting();
    }


    private void selectFirstPatient() {
        patientsListPage.choosePatient(1);
        patientBoxPage.verifyPatientDetailsExisting();
    }

    protected void chooseDepartment(String departmentName) {
        log.info("Choosing department: {}", departmentName);
        chooseDepartmentListPage.selectDepartment(departmentName);
    }

      public void openInstructionForm(InstructionType type) {
        log.info("Opening instruction form for type: {}", type);
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

 


    public static List<String> getDetailsFirstPatient(String query) throws SQLException {
       List<String> patientDetails = DBExecuter.executeSelectFirstRow(query);
       return patientDetails;
    }

    public static boolean removePatientDataBeforeTest(String query, String param) throws SQLException {
    String formattedQuery = String.format(query, param);
    return DBExecuter.isExecutionSuccessful(formattedQuery);
}
    

}
