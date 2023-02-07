package utilities;

import org.openqa.selenium.support.PageFactory;
import pageObjects.emr.*;
import pageObjects.emr.addForms.*;
import pageObjects.emr.menu.InnerMenuPage;
import pageObjects.emr.menu.MainMenuPage;
import pageObjects.emr.nurse.Execute.CardexPage;
import pageObjects.emr.nurse.Execute.UpdateExecutionPage;
import pageObjects.emr.nurse.prepMedicine.prepMedcinePage;
import pageObjects.emr.nurse.approval.ApprovalInstructionPage;
import pageObjects.emr.popUp.ConfirmationAlert;
//import pageObjects.emr.popUp.DrugConfirmationPopUp;

public class ManagePages extends Base {

    public static void initEmr(){
        emrLogin = PageFactory.initElements(driver , LoginPage.class);
        chooseRole = PageFactory.initElements(driver , ChooseRolePage.class);
        innerMenuPage = PageFactory.initElements(driver, InnerMenuPage.class);
        mainMenuPage = PageFactory.initElements(driver , MainMenuPage.class);
        patientsList = PageFactory.initElements(driver , PatientsListPage.class);
        chooseDepartmentListPage = PageFactory.initElements(driver , ChooseDepartmentListPage.class);

        patientBox = PageFactory.initElements(driver , PatientBoxPage.class);
        demogeDataBar = PageFactory.initElements(driver , DemogeDataBarPage.class);
        doctorInstructionPage = PageFactory.initElements(driver, DoctorInstructionPage.class);
      //  nursingInstructionPage = PageFactory.initElements(driver , NursingInstructionPage.class);
        drugSection  = PageFactory.initElements(driver, DrugsSection.class);
        drugForm = PageFactory.initElements(driver, DrugForm.class);
        drugFormDailyPossibility = PageFactory.initElements(driver, DrugFormDailyPossibility.class);
        drugFormSOSPossibility = PageFactory.initElements(driver, DrugFormSOSPossibility.class);
        drugFormOnceOnlyPossibility = PageFactory.initElements(driver, DrugFormOnceOnlyPossibility.class);
        drugFormByHourPossibility = PageFactory.initElements(driver, DrugFormByHourPossibility.class);
        drugFormWeeklyPossibility = PageFactory.initElements(driver, DrugFormWeeklyPossibility.class);
        drugFormTimeLimitPossibility = PageFactory.initElements(driver , DrugFormTimeLimitPossibility.class);
        drugFormContinuesPossibility = PageFactory.initElements(driver , DrugFormContinuesPossibility.class);
        drugConfirmation = PageFactory.initElements(driver,DrugConfirmation.class);

        generalInstructionPage = PageFactory.initElements(driver, GeneralInstructionPage.class);
        bloodProducts = PageFactory.initElements(driver, BloodProducts.class);
        nutritionForm = PageFactory.initElements(driver, NutritionForm.class);


        approvalInstructionPage = PageFactory.initElements(driver, ApprovalInstructionPage.class);
        cardexPage= PageFactory.initElements(driver , CardexPage.class);
        updateExecutionPage = PageFactory.initElements(driver , UpdateExecutionPage.class);
        prepMedcinePage = PageFactory.initElements(driver , prepMedcinePage.class);
        userSignModalPage = PageFactory.initElements(driver, UserSignModalPage.class);

        confirmationAlert = PageFactory.initElements(driver, ConfirmationAlert.class);
     //   drugConfirmationPopUp = PageFactory.initElements(driver, DrugConfirmationPopUp.class);
    }
}
