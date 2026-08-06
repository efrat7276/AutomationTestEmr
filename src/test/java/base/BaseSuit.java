package base;

import actionUtilies.DBExecuter;
import actionUtilies.UIActions;
import drivers.DriverManager;
import enums.InstructionType;
import helpers.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

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
        if (env == null || env.isEmpty()) {
            log.warn("No environment specified. Defaulting to 'qa'.");
            this.env = "qa";
        }
        log.info("--- Execution Environment: {} ---", env);
        log.info("setup duration of waitting");
        int waitDuration = env.equals("qa") ? 60 : 30;
        this.wait = new WebDriverWait(DriverManager.getInstance(), Duration.ofSeconds(waitDuration));
   
        // חילוץ נתוני הדרייבר והדפדפן
String browserName = "Chrome";
String browserVersion = "Unknown";
String driverVersion = "Unknown";

try {
    org.openqa.selenium.Capabilities caps = ((org.openqa.selenium.remote.RemoteWebDriver) DriverManager.getInstance()).getCapabilities();
    browserName = caps.getBrowserName();
    browserVersion = caps.getBrowserVersion();

    @SuppressWarnings("unchecked")
    java.util.Map<String, Object> chromeMap = (java.util.Map<String, Object>) caps.getCapability("chrome");
    if (chromeMap != null && chromeMap.containsKey("chromedriverVersion")) {
        driverVersion = chromeMap.get("chromedriverVersion").toString().split(" ")[0];
    }
} catch (Exception e) {
    log.error("Failed to extract browser/driver capabilities: {}", e.getMessage());
}

// חילוץ נתוני מערכת ההפעלה
String osName = System.getProperty("os.name");
String osVersion = System.getProperty("os.version");
String osInfo = osName + " (" + osVersion + ")";

       Properties properties = new Properties();
properties.setProperty("Execution Environment", env.toUpperCase());
properties.setProperty("Operating System", osInfo);
properties.setProperty("Browser", browserName);
properties.setProperty("Browser Version", browserVersion);
properties.setProperty("ChromeDriver Version", driverVersion);


        File envFileForAllure = new File("allure-results/SanitySuite/"  + env.toLowerCase(), "environment.properties");
        try (FileOutputStream fos = new FileOutputStream(envFileForAllure)) {
            properties.store(fos, "Allure Environment Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        log.info("Login as doctor with : {} user", Constants.DOCTOR_USERNAME);
        loginPage.login(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD, Constants.DOCTOR_ROLE);
    }

    protected void loginAsNurse() {
        log.info("Login as nurse with : {} user", Constants.NURSE_USERNAME);
        loginPage.login(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD, Constants.NURSE_ROLE);
    }

    
    protected void loginAsNutritionist() {
        log.info("Login as nutritionist with : {} user", Constants.NUTRITIONIST_USERNAME);
        loginPage.login(Constants.NUTRITIONIST_USERNAME, Constants.NUTRITIONIST_PASSWORD, Constants.NUTRITIONIST_ROLE);
    }

    protected void choosePatient(int patientIndex) {
        try {
            UIActions.waitForSpinnerToDisappear();
            log.info("Patient List screen: Choosing patient number: {}", patientIndex);
            UIActions.waitForVisible(patientsListPage.list_patients);
            patientsListPage.choosePatient(patientIndex);
            log.info("✓ Successfully selected patient number {}", patientIndex);
          //  patientBoxPage.verifyPatientDetailsExisting();
        } catch (Exception e) {
            log.error("✗ FAILED to choose patient number {}: {}", patientIndex, e.getMessage(), e);
            throw new RuntimeException("Patient selection failed for patient number " + patientIndex, e);
        }
    }

    protected void chooseDepartment(String departmentName) {
        log.info("Choosing department: {}", departmentName);
        chooseDepartmentListPage.selectDepartment(departmentName);
        UIActions.waitForSpinnerToDisappear();
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

    public static boolean preparePatientDataBeforeTest(String query, String param) throws SQLException {
    log.info("Executing pre-class data cleanup for patient with parameter: {}", param);
        String formattedQuery = String.format(query, param);
    return DBExecuter.isExecutionSuccessful(formattedQuery);
}

}
