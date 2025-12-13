package base;

import drivers.DriverManager;
import helpers.Constants;
import org.testng.annotations.AfterTest;
import pages.LoginPage;
import pages.mainPages.PatientsListPage;

public class BaseSuit {

    @AfterTest()
    public void closeBrowser(){
        DriverManager.getInstance().quit();
    }

    LoginPage loginPage=new LoginPage();
    PatientsListPage patientsListPage = new PatientsListPage();

    protected void loginAsDoctor() {
        loginPage.login(Constants.DOCTOR_USERNAME, Constants.DOCTOR_PASSWORD, Constants.DOCTOR_ROLE);
    }

    protected void loginAsNurse() {
        loginPage.login(Constants.NURSE_USERNAME, Constants.NURSE_PASSWORD, Constants.NURSE_ROLE);
    }

    protected void choosePatient(int patientIndex) {
        patientsListPage.choosePatient(patientIndex);
    }

}
