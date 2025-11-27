package workflows.doctor;

import actionUtilies.Verifications;
import io.qameta.allure.Step;
import utilities.CommonOps;

public class nutritionFlows extends CommonOps {


    @Step("add a1 nutrition to patient")
    public static void addAndSaveNutritionToPatient(String drugName , int dosage , int numberOfTime , boolean isAntibiotic ) throws InterruptedException {


        doctorFlows.newNutrition();
        Thread.sleep(1000);
        doctorFlows.drugFormAddDrugDaily(drugName,dosage ,numberOfTime,null,isAntibiotic, false, true);
        Verifications.textIsContains(doctorInstructionPage.title , "הוראות רפואיות");
    }

    @Step("add nutrition daily ins")
    public static void nutritionFormAddNutritionDaily(String drugName , int dosage , int numberOfTime , boolean isAntibiotic ,boolean addAndClose)  {
        doctorFlows.drugFormAddDrugDaily(drugName,dosage ,numberOfTime,null,isAntibiotic, false, addAndClose);
    }

    @Step("add nutrition onceOnly ins")
    public static void nutritionFormAddNutritionOnceOnly(String drugName , String  dosage , boolean addAndClose)  {
        doctorFlows.drugFormAddDrugOnceOnly(drugName,dosage ,null,null, false, addAndClose);
    }

    @Step("add nutrition onceOnly ins")
    public static void nutritionFormAddNutritionOnceOnlyDrCommand(String drugName , String dosage ,String hour ,boolean addAndClose )  {
        doctorFlows.drugFormAddDrugOnceOnly(drugName,dosage ,hour,null,false,  addAndClose);
    }

    @Step("add nutrition continues ins")
    public static void nutritionFormAddNutritionContinues(String drugName , int rate , boolean addAndClose )  {
        doctorFlows.drugFormAddLiquidDrug(drugName,null , 12,true);
    }

    @Step("add nutrition continues ins")
    public static void nutritionFormAddNutritionTimeLimitIns(String drugName , int dosage , int numberOfTime , boolean isAntibiotic )  {
        doctorFlows.drugFormAddDrugDaily(drugName,dosage ,numberOfTime,null,isAntibiotic, false, true);
    }

}
