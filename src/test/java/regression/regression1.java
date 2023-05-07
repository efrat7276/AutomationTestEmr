package regression;

import extensions.Verifications;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.general.general;
import workflows.*;
import workflows.doctor.bloodProductInstructionFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.doctor.nutritionFlows;
import workflows.nurse.nurseFlows;

@Listeners(utilities.Listeners.class)
public class regression1 extends CommonOps {

    @Test(description = "addToPatientAllPossibilityIns")
    @Description("add to patient all possibility ins ")
    public void addToPatientAllPossibilityIns() throws InterruptedException {
        // מוסיף למטופל תרופות , הוראות כלליות , נוזלים , מוצרי דם

        // תרופת
        // daily  - פעם ביום
        // onceOnly -
        // weekly - פעמיים בשבוע
        // SOS -
       // byHour - פעם ב-48 שעות
   int patient_num = 11;
    //   DrugObj drugDaily = new DrugObj ("acetylsalicylic",20,null,1,1,false,false);
       WebFlows.login('d');
        departmentFlows.chooseDepartment("ט'נ' כללי");

        WebFlows.patientBoxEntry(patient_num);
      doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newDrug();
        //daily drug
        doctorFlows.drugFormAddDrugDaily("acetylsalicylic",20,1,"1",false,false,false);
        // doctorFlows.drugFormAddDrugDaily(drugDaily.drug_desc,drugDaily.dosage,drugDaily.numberOfTime,drugDaily.routeAdmin,drugDaily.isAntibiotic,drugDaily.isFutureDate,false);
        //once-only drug
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(3000);
        doctorFlows.drugFormAddDrugOnceOnly("INJ bevacizumab 100mg/4ml ( AVASTIN)", "20" ,null , null ,false,false);
        //sos drug
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(1000);
        doctorFlows.drugFormAddDrugSOS("TAB paracetamol 500mg (ACAMOL)" ,"20" , null , 4 , 3,false  );
        //byHour drug
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(1000);
        doctorFlows.drugFormAddDrugByHour("TAB paracetamol 500mg (ACAMOL)" ,48 ,"20" , null ,false );
        //weekly drug
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(1000);
        doctorFlows.drugFormAddDrugWeekly("TAB FLUoxetine 20mg (FLUTINE)" , 2, "20",null,true);


        ////  תרופות נוזליות
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
//

       // הוראות כלליות
        // daily - פעם ביום
        // onceOnly - פעם ביום

        doctorFlows.newGeneralIns();
        generalInstructionFlows.generalFormAddGeneralInsDaily(1,1,1,false,false, false);
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(2,1,"16:00",false, true);

//        // מוצרי דם
////        //todo  לשלוח שם וכמות מוצר דם
        doctorFlows.newBloodProduct();
        bloodProductInstructionFlows.bloodProductFormAddBloodProduct(true);

        // תזונה
        doctorFlows.newNutrition();
        nutritionFlows.nutritionFormAddNutritionDaily("NUT daily protein cream 30/300",500,1,false,false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(3000);
        nutritionFlows.nutritionFormAddNutritionContinues("NUT conc. protein water 15/100" ,20 ,true);




        doctorFlows.approvalInstruction();

////
////
////      //   3 דקות ללא כלום
////     //  Thread.sleep(240000);
       CommonOps.afterMethod();
//

       WebFlows.login('n');
        departmentFlows.chooseDepartment("ט'נ' כללי");

        WebFlows.patientBoxEntry(patient_num);

     ////  todo הוספת ברנולה למטופל


     // //  הוספת הוראה סיעודית
//        Thread.sleep(3000);
//        NavigateFlows.goToCategory("nursing");
//        NavigateFlows.goToSubCategory("nursingIns");
//        doctorFlows.newGeneralIns();
//        generalInstructionFlows.generalFormAddGeneralInsDaily(1,1,1,false,false,true);
//        doctorFlows.approvalInstruction();
//
//
//        NavigateFlows.goToCategory("nurseConfirmation");
//        NavigateFlows.goToSubCategory("instructionConfirmation");

       ////   todo לשלוח פרמטרים כמה פירוק מכל סוג
        nurseFlows.approvalAllPossibilitiesIns(true,true);

        nurseFlows.approvalNurseSign();
        Thread.sleep(5000);
        NavigateFlows.goToCategory("cardex");

        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
        nurseFlows.executeAllLiquidAfterApprovalNurse();
        Thread.sleep(2000);
        nurseFlows.executeAllGeneralInsAfterApprovalNurse();
        Thread.sleep(2000);
        nurseFlows.executionNurseSign();

        //עדכון תרופה
//
        CommonOps.afterMethod();
        WebFlows.login('d');
        departmentFlows.chooseDepartment("ט'נ' כללי");

        WebFlows.patientBoxEntry(patient_num);
       int indexDrug =  general.searchDrugByName("acetylsalicylic");
        doctorFlows.editDrugDosage(indexDrug,41);



    }


    @Test(description = "addToPatientTwoDrugsOneApprovedAndOneNotApproved")
    @Description("add to patient two drugs one approved and one not approved")
    public void addToPatientTwoDrugsOneApprovedAndOneNotApproved(){
        // add to patient one drug not approved and drug approved

        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugDaily("TAB ALPRAZolam 0.5mg (XANAX)", 20 , 3 , null , false , false , true);
        doctorFlows.approvalInstruction();
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugDaily("TAB ALPRAZolam 0.5mg (XANAX)", 100 , 3 , null , false , false , true);
    }

    @Test(description = "stopToPatientAllActiveDrugs")
    @Description("stop to patient all active drugs")
    public void stopToPatientAllActiveDrugs(){
        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        doctorFlows.stopAllActiveInstructionToPatient();
    }


    @Test(description = "addToPatientFutureDrug")
    @Description("add to patient future drug")
    public void addToPatientFutureDrug(){
        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugDaily("TAB ALPRAZolam 0.5mg (XANAX)", 100 , 3 , null , false , true , true);
        doctorFlows.approvalInstruction();
    }

    @Test(description = "updateDosageApprovalDrug")
    @Description("update dosage approval drug")
    public void updateDosageApprovalDrug() throws InterruptedException {
        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        doctorFlows.editDrugDosage(0,41);

    }

    @Test(description = "updateDosageNotApprovalDrug")
    @Description("update dosage not approval drug")
    public void updateDosageNotApprovalDrug() throws InterruptedException {
        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        doctorFlows.editDrugDosage(0,90);
        doctorFlows.approvalInstruction();

    }





}
