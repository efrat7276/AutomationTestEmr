package regression;

import extensions.Verifications;
import io.qameta.allure.Description;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.doctor.bloodProductInstructionFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.doctor.nutritionFlows;
import workflows.nurse.nurseFlows;

import java.util.Date;

@Listeners(utilities.Listeners.class)
public class regression1 extends CommonOps {

    @Test(description = "addToPatientAllPossibilityIns")
    @Description("addToPatientAllPossibilityIns")
    public void addToPatientAllPossibilityIns() throws InterruptedException {
       WebFlows.login('d');
      WebFlows.patientBoxEntry(11);
      doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newDrug();
        //add new daily drug
        doctorFlows.drugFormAddDrugDaily("acetaZOLAMIDE 500mg" , 20 , 1 , null , false,false ,false);
        //add new once-only drug
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(3000);
       doctorFlows.drugFormAddDrugOnceOnly("TAB acetylcysteine 200mg efferv (REOLIN )", "20" ,null , null ,false,false);
        //add new sos drug
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(1000);
        doctorFlows.drugFormAddDrugSOS("TAB paracetamol 500mg (ACAMOL)" ,"20" , null ,false );
        //add new byHour drug
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(1000);
        doctorFlows.drugFormAddDrugByHour("TAB paracetamol 500mg (ACAMOL)" ,48 ,"20" , null ,false );
       // add new weekly drug
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(1000);
        doctorFlows.drugFormAddDrugWeekly("TAB FLUoxetine 20mg (FLUTINE)" , 2, "20",null,true);
        //doctorFlows.clickReturn();


       // הוראות כלליות
        doctorFlows.newGeneralIns();
        generalInstructionFlows.generalFormAddGeneralInsDaily(1,1,1,false,false, false);
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(2,1,"16:00",false, true);

//        // מוצרי דם
//        //todo  לשלוח שם וכמות מוצר דם
        doctorFlows.newBloodProduct();
        bloodProductInstructionFlows.bloodProductFormAddBloodProduct(true);

        // תזונה
        doctorFlows.newNutrition();
        nutritionFlows.nutritionFormAddNutritionDaily("NUT daily protein cream 30/300",500,1,false,false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(3000);
        nutritionFlows.nutritionFormAddNutritionContinues("NUT conc. protein water 15/100" ,20 ,true);


        // תרופות נוזליות
        doctorFlows.newDrug();
        doctorFlows.drugFormAddLiquidDrug("INJ atracrium 25mg/2.5ml (TRACRIUM)","dextrose 5% 500ml",11,false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(2000);
        doctorFlows.drugFormAddLiquidDrug("INJ aflibercept 100mg (EYLEA)","dextrose 10% 500ml",12,false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(2000);
        doctorFlows.drugFormAddLiquidDrug("INF dextrose 5% 100ml (GLUCOSE)",null ,11,false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(1000);
        doctorFlows.drugFormAddLiquidDrug("INF dextrose 5% 100ml (GLUCOSE)",null ,12,true);

        doctorFlows.approvalInstruction();

////
////
////      //   3 דקות ללא כלום
////     //  Thread.sleep(240000);
       CommonOps.afterMethod();
//////

        WebFlows.login('n');
       WebFlows.patientBoxEntry(11);

       //todo הוספת ברנולה למטופל

//        Thread.sleep(3000);
//        NavigateFlows.goToCategory("nursing");
//        NavigateFlows.goToSubCategory("nursingIns");
//        doctorFlows.newGeneralIns();
//        generalInstructionFlows.generalFormAddGeneralInsDaily(2,1,1,false,false,true);
//        doctorFlows.approvalInstruction();
//
//
//        NavigateFlows.goToCategory("nurseConfirmation");
//        NavigateFlows.goToSubCategory("instructionConfirmation");

      //  todo לשלוח פרמטרים כמה פירוק מכל סוג
        nurseFlows.approvalAllPossibilitiesIns(true,true);

        nurseFlows.approvalNurseSign();
        Thread.sleep(5000);
     NavigateFlows.goToCategory("cardex");

        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
        nurseFlows.executeAllLiquidAfterApprovalNurse();
        nurseFlows.executeAllGeneralInsAfterApprovalNurse();
        Thread.sleep(2000);
        nurseFlows.executionNurseSign();



    }






}
