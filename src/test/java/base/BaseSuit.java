package base;

import actionUtilies.DBExecuter;
import drivers.DriverManager;
import helpers.Constants;

import java.sql.SQLException;
import java.util.List;

import javax.print.Doc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class BaseSuit {

    protected static final Logger logger = LoggerFactory.getLogger(BaseSuit.class);
    protected LoginPage loginPage;
    protected PatientsListPage patientsListPage;
    protected PatientBoxPage patientBoxPage;
    protected DoctorInstructionPage doctorInstructionPage;
    protected ChooseDepartmentListPage chooseDepartmentListPage;
    @BeforeMethod
    public void setUp() {
    logger.info(">>> Starting Test Setup");
    DriverManager.getInstance(); 
    
    logger.info(">>> Initializing pages with active driver");
    loginPage = new LoginPage();
    patientsListPage = new PatientsListPage();
    patientBoxPage = new PatientBoxPage();
    doctorInstructionPage = new DoctorInstructionPage();
    chooseDepartmentListPage = new ChooseDepartmentListPage();
    
    logger.info(">>> Test Setup Complete");
  }

    @AfterMethod // עדיף AfterMethod כדי לסגור דפדפן אחרי כל טסט בנפרד
    public void closeBrowser(){
        logger.info("<<< Closing browser and cleaning up");
        logger.info("<<< Closing browser. Driver instance: " + DriverManager.getInstance());
    }

    @AfterClass
    public void tearDown() {
        logger.info("<<< Quitting driver and cleaning up resources");
        DriverManager.quitDriver();
    }
    
    protected void loginAsDoctor() {
        logger.info("Performing Login as DOCTOR: {}", Constants.DOCTOR_USERNAME);
        loginPage.login(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD, Constants.DOCTOR_ROLE);
    }

    protected void loginAsNurse() {
        logger.info("Performing Login as NURSE: {}", Constants.NURSE_USERNAME);
        loginPage.login(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD, Constants.NURSE_ROLE);
    }

    
    protected void loginAsNutritionist() {
        logger.info("Performing Login as NUTRITIONIST: {}", Constants.NUTRITIONIST_USERNAME);
        loginPage.login(Constants.NUTRITIONIST_USERNAME, Constants.NUTRITIONIST_PASSWORD, Constants.NUTRITIONIST_ROLE);
    }

    protected void choosePatient(int patientIndex) {
        logger.info("Choosing patient at index: {}", patientIndex);
        patientsListPage.choosePatient(patientIndex);
        patientBoxPage.verifyPatientDetailsExisting();
    }


    private void selectFirstPatient() {
        patientsListPage.choosePatient(1);
        patientBoxPage.verifyPatientDetailsExisting();
    }

    protected void chooseDepartment(String departmentName) {
        logger.info("Choosing department: {}", departmentName);
        chooseDepartmentListPage.selectDepartment(departmentName);
    }

      public void openInstructionForm(InstructionType type) {
        logger.info("Opening instruction form for type: {}", type);
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
