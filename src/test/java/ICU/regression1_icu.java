package ICU;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.bloodProductInstructionFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.doctor.nutritionFlows;
import workflows.general.general;
import workflows.nurse.nurseFlows;

import java.io.IOException;
import java.util.List;

@Listeners(utilities.Listeners.class)
public class regression1_icu extends CommonOps {

    @Test(description = "")
    @Description("")
    public void test01_icu_addToPatientDrugDaily() throws InterruptedException, IOException {
        int patient_num = 1;
        String departmentICU = "ט'נ' כללי";
        WebFlows.login('d');
        departmentFlows.chooseDepartment(departmentICU);
        WebFlows.patientBoxEntry(patient_num);
      //  doctorFlows.stopAllActiveInstructionToPatient();

        // הוספת תוכנית בקבלה
        NavigateFlows.goToCategory("goals");
        UIActions.click(settingGoals.receptionPlanTab);

        Thread.sleep(1000);

        // תוכנית תזונה
        UIActions.click(receptionTreatmentPlanMain.div_nutritionPlan);

        //הוספת כלכלה למטופל
//        UIActions.click(nutritionPlan.radio_economy);
//        UIActions.click(nutritionPlan.input_economyDesc);
//        UIActions.click(listChoices.listChoices.get(0));
//        UIActions.updateText(nutritionPlan.input_nut_continous,"1.2");
//        UIActions.click(nutritionPlan.checkBox_water);

        // תוספת חלבון

        UIActions.click(nutritionPlan.isProteinYes);
        UIActions.click(nutritionPlan.input_protein);
        UIActions.click(listChoices.listChoices.get(0));
        UIActions.click(nutritionPlan.list_numberOfTime.get(0));
        Thread.sleep(500);

        UIActions.click(nutritionPlan.list_numberOfTime.get(0).findElements(By.xpath("following-sibling::ul/li")).get(0));
        UIActions.updateText(nutritionPlan.input_proteinComment , "בדיקות אוטו'");

        // תוספת מי אורז
        Thread.sleep(500);

        UIActions.click(nutritionPlan.isRiceWaterYes);
        UIActions.click(nutritionPlan.list_numberOfTime.get(1));
        Thread.sleep(500);
        UIActions.click(nutritionPlan.list_numberOfTime.get(1).findElements(By.xpath("following-sibling::ul/li")).get(0));
        UIActions.updateText(nutritionPlan.input_RiceWaterComment , "בדיקות אוטו'");

       // הוספת תמיסה IV
        Thread.sleep(500);
        UIActions.click(nutritionPlan.input_solution);
        UIActions.click(listChoices.listChoices.get(0));



        //תוכנית נוזלים

        UIActions.click(receptionTreatmentPlanMain.div_openFluidPlan);


        UIActions.click(solutionPlan.input_solution_item);
        UIActions.click(listChoices.listChoices.get(0));
        UIActions.updateText(solutionPlan.input_solution_rate,"20");

        // תוספות

        UIActions.click(solutionPlan.isMgsoYes);
        UIActions.click(solutionPlan.isKCLYes);
        UIActions.click(solutionPlan.isD50WYes);
        UIActions.updateText(solutionPlan.D50W_rate , "20");

     // בולוס נוזלים

        UIActions.click(receptionTreatmentPlanMain.div_openFluidBolusPlan);


        UIActions.click(solutionBolusPlan.input_solutionBolus_item);
        UIActions.click(listChoices.listChoices.get(0));
        //UIActions.click(solutionBolusPlan.input_solutionBolus_dosage,);
        UIActions.click(solutionBolusPlan.btn_solution_bolus_during);
        UIActions.click((solutionBolusPlan.btn_solution_bolus_during).findElements(By.xpath("following-sibling::ul/li")).get(9));
        UIActions.updateText(solutionBolusPlan.input_solution_bolus_duringOther,"20");






        //חתימה על הוספת כל ההוראות בקבלה
        UIActions.click(receptionTreatmentPlanMain.btn_ok);

        Verifications.isDisplay(doctorInstructionPage.btn_approvalDrug);

        Thread.sleep(1000);
        // חתימה על כל ההוראות מהקבלה
        doctorFlows.approvalInstruction();


        Thread.sleep(1000);

        CommonOps.afterMethod();

        WebFlows.login('n');
        departmentFlows.chooseDepartment(departmentICU);
        WebFlows.patientBoxEntry(patient_num);

        nurseFlows.approvalAllPossibilitiesIns(true,false);
        nurseFlows.approvalNurseSign();

        NavigateFlows.goToCategory("cardex");

        nurseFlows.executeSupervisionToDrug();
        nurseFlows.executeSupervisionToSolution();

        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
        nurseFlows.executeAllLiquidAfterApprovalNurse();

        nurseFlows.executionNurseSign();







    }




//
//void aa() throws InterruptedException, IOException {
//
//
//
//    }
//
//
//    @Test(description = "addToPatientTwoDrugsOneApprovedAndOneNotApproved")
//    @Description("add to patient two drugs one approved and one not approved")
//    public void test02_addToPatientTwoDrugsOneApprovedAndOneNotApproved(){
//        // add to patient one drug not approved and drug approved
//
//        WebFlows.login('d');
//        WebFlows.patientBoxEntry(1);
//        doctorFlows.newDrug();
//        doctorFlows.drugFormAddDrugDaily("TAB ALPRAZolam 0.5mg (XANAX)", 20 , 3 , null , false , false , true);
//        doctorFlows.approvalInstruction();
//        doctorFlows.newDrug();
//        doctorFlows.drugFormAddDrugDaily("TAB ALPRAZolam 0.5mg (XANAX)", 100 , 3 , null , false , false , true);
//    }
//
//



}
