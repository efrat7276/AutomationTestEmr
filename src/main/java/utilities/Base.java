package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.sikuli.script.Screen;
import org.testng.asserts.SoftAssert;
import pageObjects.emr.*;
import pageObjects.emr.addForms.*;
import pageObjects.emr.generalComponents.ListChoicesComponent;
import pageObjects.emr.generalComponents.NumberOfTimeListComponent;
import pageObjects.emr.icu.patientSheet.PatientSheetMain;
import pageObjects.emr.icu.receptionTreatmentPlan.*;
import pageObjects.emr.mainPages.*;
import pageObjects.emr.menu.InnerMenuPage;
import pageObjects.emr.menu.MainMenuPage;
import pageObjects.emr.nurse.Execute.CardexPage;
import pageObjects.emr.nurse.Execute.UpdateExecutionPage;
import pageObjects.emr.nurse.approval.ApprovalInstructionPage;
import pageObjects.emr.nurse.prepMedicine.prepMedcinePage;

import pageObjects.emr.nurse.wound.BedsoreMoreFieldsToAddPage;
import pageObjects.emr.nurse.wound.SurgicalWoundMoreFieldsToAddPage;
import pageObjects.emr.nurse.wound.WondFormPage;
import pageObjects.emr.nurse.wound.WoundPage;
import pageObjects.emr.popUp.CardexDailyReport;
import pageObjects.emr.popUp.ConfirmationAlert;
import pageObjects.emr.icu.settingGoals.SettingGoals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Base {

    protected static WebDriver driver ;
    protected static WebDriverWait wait;
    protected static Actions action;
  //  protected static Screen screen;
    protected static SoftAssert softAssert;

    //DB

    protected static Connection con;
    protected static Statement stmt;
    protected static ResultSet rs;

    //Pages Object
    protected static LoginPage emrLogin;
    protected static ChooseRolePage chooseRole;
    protected static InnerMenuPage innerMenuPage;
    protected static MainMenuPage mainMenuPage;

    //main pages
    protected static PatientsListPage patientsList ;
    protected static DischargedListPage dischargedListPage;
    protected static DepReport depReport;
    protected static OperationsPage operationsPage;
    protected static OrderListPage orderListPage;
    protected static ProtocolListPage protocolListPage ;










    protected static ChooseDepartmentListPage chooseDepartmentListPage ;
    protected static PatientBoxPage patientBox ; ;
    protected static DemogeDataBarPage demogeDataBar ;


    protected static DoctorInstructionPage doctorInstructionPage ;
    protected static DrugsSection drugSection ;
    protected static DrugForm drugForm ;
    protected static DrugFormDailyPossibility drugFormDailyPossibility ;
    protected static DrugFormSOSPossibility drugFormSOSPossibility ;
    protected static DrugFormOnceOnlyPossibility drugFormOnceOnlyPossibility ;
    protected static DrugFormByHourPossibility drugFormByHourPossibility ;
    protected static DrugFormWeeklyPossibility drugFormWeeklyPossibility ;

  protected static DrugFormCyclePossibility drugFormCyclePossibility ;
    protected static DrugFormTimeLimitPossibility drugFormTimeLimitPossibility;
    protected static DrugFormContinuesPossibility drugFormContinuesPossibility;

    protected static DrugConfirmation drugConfirmation;



    protected static GeneralInstructionPage generalInstructionPage ;
    protected static BloodProducts bloodProducts;
    protected static NutritionForm nutritionForm;

    protected static ApprovalInstructionPage approvalInstructionPage;
    protected static CardexPage cardexPage;
    protected static UpdateExecutionPage updateExecutionPage;

    protected static  prepMedcinePage prepMedcinePage;

    protected static UserSignModalPage userSignModalPage;
     //popUp
    protected static ConfirmationAlert confirmationAlert;

  protected static CardexDailyReport cardexDailyReport;

    protected static SettingGoals settingGoals;
    protected static ReceptionTreatmentPlanMain receptionTreatmentPlanMain;
    protected static NutritionPlan nutritionPlan;
    protected static SolutionPlan solutionPlan;
    protected static SolutionBolusPlan solutionBolusPlan;
    protected static CommonMedicationPlan commonMedicationPlan;




  //general

   protected static ListChoicesComponent listChoices;
    protected static NumberOfTimeListComponent numberOfTimeList;



   //ICU

  protected static PatientSheetMain patientSheetMain;

  //nursing

  protected static WoundPage woundPage;

  protected static WondFormPage woundFormPage;
  protected static SurgicalWoundMoreFieldsToAddPage surgicalWoundMoreFieldsToAddPage;
  protected static BedsoreMoreFieldsToAddPage bedsoreMoreFieldsToAddPage;

}
