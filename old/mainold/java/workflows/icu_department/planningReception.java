package workflows.icu_department;
import extensions.UIActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utilities.CommonOps;
import workflows.NavigateFlows;

public class planningReception extends CommonOps {


    @Step("")
    public static void savePlanningReception() throws InterruptedException {
        NavigateFlows.goToCategory("goals");
        UIActions.click(settingGoals.receptionPlanTab);
        Thread.sleep(1000);
        addingNutritionPlan();
        addingFluidPlan();
        addingBolusFluidPlan();
        Thread.sleep(1000);
        addingCommonMedicationPlan();
        Thread.sleep(500);
        planningReception.approvalReceptionPlanning();

    }

    @Step("adding nutrition plan")
    public static void addingNutritionPlan() throws InterruptedException {
        // תוכנית תזונה
        UIActions.click(receptionTreatmentPlanMain.div_nutritionPlan);

        Thread.sleep(500);
        //הוספת כלכלה למטופל
      //  JavascriptExecutor js = (JavascriptExecutor) driver;
      //  js.executeScript("arguments[0].checked = true;", nutritionPlan.radio_economy);
     //   Thread.sleep(500);

    //    js.executeScript("arguments[0].click;", nutritionPlan.input_economyDesc);
  //     js.executeScript("arguments[3].click;", listChoices.listChoices);
//        js.executeScript("arguments[3].text='1.2';", nutritionPlan.input_nut_continous);
//        js.executeScript("arguments[3].click;", nutritionPlan.checkBox_water);

        UIActions.click(nutritionPlan.radio_glucos);

        UIActions.updateText(nutritionPlan.input_glucos,"dextrose 10% 500ml");
        UIActions.click(listChoices.listChoices.get(0));
        UIActions.updateText(nutritionPlan.input_glu_continous,"1.2");
      //  UIActions.click(nutritionPlan.checkBox_water);

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

        }
    @Step("adding fluid plan")
    public static void addingFluidPlan() throws InterruptedException {
        //תוכנית נוזלים

        UIActions.click(receptionTreatmentPlanMain.div_openFluidPlan);


        UIActions.updateText(solutionPlan.input_solution_item,"heparin/saline 1000ml");
        UIActions.click(listChoices.listChoices.get(0));
        UIActions.updateText(solutionPlan.input_solution_rate,"20");

        // תוספות

        UIActions.click(solutionPlan.isMgsoYes);
        UIActions.click(solutionPlan.isKCLYes);
        UIActions.click(solutionPlan.isD50WYes);
        UIActions.updateText(solutionPlan.D50W_rate , "20");

    }
    @Step("adding bolus fluid plan")
    public static void addingBolusFluidPlan() throws InterruptedException {
        // בולוס נוזלים

        Thread.sleep(1000);
        UIActions.click(receptionTreatmentPlanMain.div_openFluidBolusPlan);

        Thread.sleep(1000);

        UIActions.updateText(solutionBolusPlan.input_solutionBolus_item,"heparin/saline 1000ml");
        UIActions.click(listChoices.listChoices.get(0));
        //UIActions.click(solutionBolusPlan.input_solutionBolus_dosage,);
        UIActions.click(solutionBolusPlan.btn_solution_bolus_during);
        UIActions.click((solutionBolusPlan.btn_solution_bolus_during).findElements(By.xpath("following-sibling::ul/li")).get(9));
        UIActions.updateText(solutionBolusPlan.input_solution_bolus_duringOther,"20");



    }
    @Step("adding common medication plan")
    public static void addingCommonMedicationPlan() throws InterruptedException {
        // תרופות נפוצות
        Thread.sleep(1000);

        UIActions.click(receptionTreatmentPlanMain.div_openDrugCommonPlan);
        Thread.sleep(1000);

        for (int i = 0; i < commonMedicationPlan.list_selectMedication.size()-3 ; i++) {
            Thread.sleep(1000);
            UIActions.click(commonMedicationPlan.list_selectMedication.get(i));
            Thread.sleep(1000);
            UIActions.click(driver.findElements(By.xpath("//drugs-for-chooose[2]//daily")).get(i).findElement(By.name("numberOfTimes_daily")));
            Thread.sleep(1000);
            UIActions.click(driver.findElements(By.xpath("//drugs-for-chooose[2]//daily//ul")).get(i).findElements(By.tagName("li")).get(0));

//            if(i==5) {
//                UIActions.click(commonMedicationPlan.list_selectMedication.get(5));
//
//            }

        }

    }
    //חתימה על הוספת כל ההוראות בקבלה
    @Step("sign on reception planning")
    public static void approvalReceptionPlanning(){
        UIActions.click(receptionTreatmentPlanMain.btn_ok);
      //  Verifications.isDisplay(doctorInstructionPage.btn_approvalDrug);
    }

}
