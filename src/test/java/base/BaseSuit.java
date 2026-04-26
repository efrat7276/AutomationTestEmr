package base;

import actionUtilies.DBExecuter;
import actionUtilies.UIActions;
import drivers.DriverManager;
import enums.InstructionType;
import helpers.Constants;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import pages.ChooseDepartmentListPage;
import pages.DoctorInstructionPage;
import pages.LoginPage;
import pages.PatientBoxPage;
import pages.mainPages.PatientsListPage;

@Slf4j
public class BaseSuit {

    protected WebDriverWait wait;
    
    protected LoginPage loginPage;
    protected PatientsListPage patientsListPage;
    protected PatientBoxPage patientBoxPage;
    protected DoctorInstructionPage doctorInstructionPage;
    protected ChooseDepartmentListPage chooseDepartmentListPage;

    protected String env; 

    @BeforeSuite
    public void setupBeforeSuite() {
        this.env = System.getProperty("env");
        log.info(">>> Setting up test environment: {}", env);
        if (env == null || env.isEmpty()) {
            log.warn("No environment specified. Defaulting to 'prod'.");
            this.env = "prod";
        }
        log.info("--- Execution Environment: {} ---", env);
        log.info("setup duration of waitting");
        int waitDuration = env.equals("qa") ? 60 : 30;
        this.wait = new WebDriverWait(DriverManager.getInstance(), Duration.ofSeconds(waitDuration));
    }

    @BeforeMethod
    public void setUp() {
    log.info(">>> Starting Test Setup");
    DriverManager.getInstance(); 
    loginPage = new LoginPage();
    patientsListPage = new PatientsListPage();
    patientBoxPage = new PatientBoxPage();
    doctorInstructionPage = new DoctorInstructionPage();
    chooseDepartmentListPage = new ChooseDepartmentListPage();
    
   //log.info(">>> Test Setup Complete");
  }
    @AfterClass
    public void tearDown() {
        log.info("<<< Quitting driver and cleaning up resources");
        DriverManager.quitDriver();
    }
    
    protected void loginAsDoctor() {
        log.info(" login as DOCTOR: {}", Constants.DOCTOR_USERNAME);
        loginPage.login(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD, Constants.DOCTOR_ROLE);
    }

    protected void loginAsNurse() {
        log.info(" login as NURSE: {}", Constants.NURSE_USERNAME);
        loginPage.login(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD, Constants.NURSE_ROLE);
    }

    
    protected void loginAsNutritionist() {
        log.info(" login as NUTRITIONIST: {}", Constants.NUTRITIONIST_USERNAME);
        loginPage.login(Constants.NUTRITIONIST_USERNAME, Constants.NUTRITIONIST_PASSWORD, Constants.NUTRITIONIST_ROLE);
    }

    protected void choosePatient(int patientIndex) {
        UIActions.waitForSpinnerToDisappear();
        log.info("Choosing patient at index: {}", patientIndex);
        UIActions.waitForVisible(patientsListPage.list_patients);
        patientsListPage.choosePatient(patientIndex);
        patientBoxPage.verifyPatientDetailsExisting();
    }

    protected void chooseDepartmentVerifyListPatients(String departmentName) {
        log.info("Choosing department: {}", departmentName);
        chooseDepartmentListPage.selectDepartment(departmentName);
        log.info("Verifying patients list is visible for department: {}", departmentName);
       patientsListPage.verifyPatientsListVisible();
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
       log.info("Fetching details for the first patient with query: {}", query);    
       List<String> patientDetails = DBExecuter.executeSelectFirstRow(query);
       return patientDetails;
    }

    public static boolean removePatientDataBeforeTest(String query, String param) throws SQLException {
    log.info("Executing pre-test data cleanup for patient with parameter: {}", param);
        String formattedQuery = String.format(query, param);
    return DBExecuter.isExecutionSuccessful(formattedQuery);
}

   public static boolean cancelAllWoundsForPatient(String query, String param) throws SQLException {
    log.info("Cancelling all wounds for patient with parameter: {}", param);
        String formattedQuery = String.format(query, param);
    return DBExecuter.isExecutionSuccessful(formattedQuery);
   }

}
