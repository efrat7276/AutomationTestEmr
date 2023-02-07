package workflows.doctor;

import extensions.Verifications;
import io.qameta.allure.Step;
import utilities.CommonOps;

public class drugFlows extends CommonOps {


    @Step("add and save the drug as daily to patient")
    public static void addAndSaveDrugDailyToPatient(String drugName , int dosage , int numberOfTime ,boolean isAntibiotic, boolean isFuture){

        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugDaily(drugName,dosage ,3,null,isAntibiotic,isFuture,true);
      //  Verifications.textIsContains(doctorInstructionPage.btn_approvalDrug , "0");
    }

    @Step("add and save the drug as once-only to patient")
    public static void addAndSaveDrugOnceOnlyToPatient(){

        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugOnceOnly("TAB acetylsalicylic acid 100mg (GODAMED)",null ,null,null ,false,true);
        Verifications.isNotDisplay(doctorInstructionPage.redStamp_icons.get(0));
    }

    @Step("add and save the drug as once-only with execution to patient")
    public static void  addAndSaveDrugOnceOnlyWithExecuteToPatient(){

        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugOnceOnly("TAB acetylsalicylic acid 100mg (GODAMED)",null ,null,null, true,true);
        Verifications.isNotDisplay(doctorInstructionPage.redStamp_icons.get(0));
    }

    @Step("add and save the drug as SOS to patient")
    public static void  addAndSaveDrugSOSToPatient(){

        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugSOS("TAB acetylsalicylic acid 100mg (GODAMED)",null ,null,true);
        Verifications.isNotDisplay(doctorInstructionPage.redStamp_icons.get(0));
    }

    @Step("add and save the drug as by-hour to patient")
    public static void  addAndSaveDrugByHourToPatient(){

        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugByHour("TAB acetylsalicylic acid 100mg (GODAMED)",48, null,null , true);
        Verifications.isNotDisplay(doctorInstructionPage.redStamp_icons.get(0));
    }

    @Step("add and save the drug as weekly to patient")
    public static void  addAndSaveDrugWeeklyToPatient(int timeInWeek){

        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugWeekly("TAB acetylsalicylic acid 100mg (GODAMED)",2 ,null ,null ,true);
        Verifications.isNotDisplay(doctorInstructionPage.redStamp_icons.get(0));
    }

    @Step("add and save the drug diluted duration patient")
    public static void  addAndSaveDrugDilutedToPatient(){

        doctorFlows.newDrug();
        doctorFlows.drugFormAddLiquidDrug("INJ naloxone 400mcg/1ml (NARCAN)" ,"saline 0.9% 100ml",11,true);
        Verifications.isNotDisplay(doctorInstructionPage.redStamp_icons.get(0));
    }

    @Step("add and save the liquid as continues to patient")
    public static void addAndSaveDrugContinuesToPatient(){

        doctorFlows.newDrug();
        doctorFlows.drugFormAddLiquidDrug("INF dextrose 5% 250ml (GLUCOSE)" ,null ,12,true);
        Verifications.isNotDisplay(doctorInstructionPage.redStamp_icons.get(0));
    }

    @Step("edit drug daily dosage to patient")
    public static void editDrugDailyDosage() throws InterruptedException {

        doctorFlows.editDrug();
        doctorFlows.editDrugDosage();
        Verifications.isNotDisplay(doctorInstructionPage.redStamp_icons.get(0));
    }

    @Step("edit drug daily number of time to patient")
    public static void editDrugDailyNumberOfTime(int numberPerDay) throws InterruptedException {

        doctorFlows.editDrug();
        doctorFlows.editDrugNumberOfTime(numberPerDay);
        Verifications.isNotDisplay(doctorInstructionPage.redStamp_icons.get(0));
    }
}
