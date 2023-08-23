package utilities;

import org.openqa.selenium.support.PageFactory;
import pageObjects.emr.*;
import pageObjects.emr.addForms.*;
import pageObjects.emr.generalComponents.ListChoicesComponent;
import pageObjects.emr.generalComponents.NumberOfTimeListComponent;
import pageObjects.emr.mainPages.*;
import pageObjects.emr.menu.InnerMenuPage;
import pageObjects.emr.menu.MainMenuPage;
import pageObjects.emr.nurse.Execute.CardexPage;
import pageObjects.emr.nurse.Execute.UpdateExecutionPage;
import pageObjects.emr.nurse.prepMedicine.prepMedcinePage;
import pageObjects.emr.nurse.approval.ApprovalInstructionPage;
import pageObjects.emr.popUp.ConfirmationAlert;
import pageObjects.emr.settingGoals.*;
import pageObjects.emr.receptionTreatmentPlan.*;

import java.util.List;

//import pageObjects.emr.popUp.DrugConfirmationPopUp;

public class ManagePages extends Base {

    public static void initEmr(){
        emrLogin = PageFactory.initElements(driver , LoginPage.class);
        chooseRole = PageFactory.initElements(driver , ChooseRolePage.class);
        innerMenuPage = PageFactory.initElements(driver, InnerMenuPage.class);
        mainMenuPage = PageFactory.initElements(driver , MainMenuPage.class);
        patientsList = PageFactory.initElements(driver , PatientsListPage.class);
        dischargedListPage = PageFactory.initElements(driver , DischargedListPage.class);
        depReport = PageFactory.initElements(driver , DepReport.class);
        operationsPage = PageFactory.initElements(driver , OperationsPage.class);
        orderListPage = PageFactory.initElements(driver , OrderListPage.class);
        protocolListPage = PageFactory.initElements(driver , ProtocolListPage.class);




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

        settingGoals =  PageFactory.initElements(driver, SettingGoals.class);
        receptionTreatmentPlanMain = PageFactory.initElements(driver, ReceptionTreatmentPlanMain.class);
        nutritionPlan = PageFactory.initElements(driver, NutritionPlan.class);
        solutionPlan = PageFactory.initElements(driver, SolutionPlan.class);
        solutionBolusPlan = PageFactory.initElements(driver, SolutionBolusPlan.class);
        commonMedicationPlan =PageFactory.initElements(driver, CommonMedicationPlan.class) ;
        listChoices= PageFactory.initElements(driver, ListChoicesComponent.class);
        numberOfTimeList= PageFactory.initElements(driver, NumberOfTimeListComponent.class);


        //   drugConfirmationPopUp = PageFactory.initElements(driver, DrugConfirmationPopUp.class);
    }
}
