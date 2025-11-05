package RegressionTest;

import extensions.UIActions;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.*;
import workflows.doctor.bloodProductInstructionFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.doctor.nutritionFlows;
import workflows.nurse.nurseFlows;                                                                  

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Listeners(utilities.Listeners.class)
public class regression1 extends CommonOps {

    /**
     * adds by doctor all types of instruction for the patient : drugs , fluids and boluses , continuous infusions ,  general instruction and blood products.
     * all instructions are approved by nurse
     *  all instructions are executed by nurse in Cardex
     */
    @Test(description = "addToPatientAllPossibilityIns")
    @Description("add to patient all possibility ins ")
    public void test01_addToPatientAllPossibilityIns() throws InterruptedException {
        int patient_num = 5;
                ;
       String department ="ט'נ' כללי";
        WebFlows.login('d');
     //  departmentFlows.chooseDepartment(department);

        WebFlows.patientBoxEntry(patient_num);
        doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newDrug();
        //daily drug
        doctorFlows.drugFormAddDrugDaily("acetylsalicylic", 20, 1, "1", false, false, false);
        //once only drug
        UIActions.updateText(drugForm.inp_selectDrug,"   ");
        doctorFlows.drugFormAddDrugOnceOnly("INJ bevacizumab 100mg/4ml ( AVASTIN)", "20", null, null, false, false);
//        //sos drug
        //drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        UIActions.updateText(drugForm.inp_selectDrug,"   ");

        doctorFlows.drugFormAddDrugSOS("TAB paracetamol 500mg (ACAMOL)", "20", null, 4, 3, false);
        //byHour drug
       // drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        UIActions.updateText(drugForm.inp_selectDrug,"   ");

        doctorFlows.drugFormAddDrugByHour("TAB paracetamol 500mg (ACAMOL)", 48, "20", null, false);
      //  weekly drug
       // drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        UIActions.updateText(drugForm.inp_selectDrug,"   ");

        doctorFlows.drugFormAddDrugWeekly("TAB FLUoxetine 20mg (FLUTINE)" , 2, "20",null,true);


//        //  תרופות נוזליות
        doctorFlows.newDrug();
        doctorFlows.drugFormAddLiquidDrug("INJ atracrium 25mg/2.5ml (TRACRIUM)", "dextrose 5% 500ml", 11,false);
        //drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        UIActions.updateText(drugForm.inp_selectDrug,"   ");

        doctorFlows.drugFormAddLiquidDrug("INJ heparin 5000u/ml 5ml", "dextrose 5% 100ml", 12, false);
      //  drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        UIActions.updateText(drugForm.inp_selectDrug,"   ");

        doctorFlows.drugFormAddLiquidDrug("INJ aflibercept 100mg (EYLEA)", "dextrose 5% 100ml", 12, true);

        doctorFlows.newDrug();

        doctorFlows.drugFormAddLiquidDrug("INF dextrose 5% 100ml (GLUCOSE)", null, 11, false);
      //  drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        UIActions.updateText(drugForm.inp_selectDrug,"   ");

        doctorFlows.drugFormAddLiquidDrug("INF dextrose 5% 100ml (GLUCOSE)", null, 12, true);


        // general instruction
        // daily - פעם ביום
        // onceOnly - פעם ביום
//
        doctorFlows.newGeneralIns();
        generalInstructionFlows.generalFormAddGeneralInsDaily(1, 1, 1, false, false, false);
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(2, 1, "16:00", false, true);
//
////        // מוצרי דם
//////        //todo  לשלוח שם וכמות מוצר דם
        doctorFlows.newBloodProduct();
        bloodProductInstructionFlows.bloodProductFormAddBloodProduct(true);

        // תזונה
        doctorFlows.newNutrition();
        nutritionFlows.nutritionFormAddNutritionDaily("NUT daily protein cream 30/300", 500, 1, false, false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        UIActions.updateText(drugForm.inp_selectDrug,"   ");
        Thread.sleep(500);

        nutritionFlows.nutritionFormAddNutritionContinues("NUT conc. protein water 15/100", 20, true);


        doctorFlows.approvalInstruction();

////
////
//////      //   3 דקות ללא כלום
//////     //  Thread.sleep(240000);
       afterMethod();
////

//
        WebFlows.login('n');
        Thread.sleep(2000);
     // departmentFlows.chooseDepartment(department);

        WebFlows.patientBoxEntry(patient_num);

//        ////  todo הוספת ברנולה למטופל
//
//
//        // //  הוספת הוראה סיעודית
////        Thread.sleep(3000);
////        NavigateFlows.goToCategory("nursing");
////        NavigateFlows.goToSubCategory("nursingIns");
////        doctorFlows.newGeneralIns();
////        generalInstructionFlows.generalFormAddGeneralInsDaily(1,1,1,false,false,true);
////        doctorFlows.approvalInstruction();
////        NavigateFlows.goToCategory("nurseConfirmation");
////        NavigateFlows.goToSubCategory("instructionConfirmation");
//
//        ////   todo לשלוח פרמטרים כמה פירוק מכל סוג
      nurseFlows.approvalAllPossibilitiesIns(true, false);
//
       nurseFlows.approvalNurseSign();
        Thread.sleep(5000);
        NavigateFlows.goToCategory("cardex");

        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
        nurseFlows.executionNurseSign();

        Thread.sleep(2000);

        nurseFlows.executeAllLiquidAfterApprovalNurse();
        //ביצוע הוראות כלליות לא עובד
        Thread.sleep(2000);
        nurseFlows.executeAllGeneralInsAfterApprovalNurse();
//       Thread.sleep(2000);
        nurseFlows.executionNurseSign();


    }

    @Test(description = "addToPatientFutureDrug")
    @Description("add to patient future drug")
    public void test02_addToPatientFutureDrug() throws InterruptedException {
        WebFlows.login('d');
        WebFlows.patientBoxEntry(2);
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugDaily("enoxaparin", 100 , 1 , null , false , true , true);
        doctorFlows.approvalInstruction();
    }

    @Test(description = "updateDosageApprovalDrug")
    @Description("update dosage approval drug")
    public void test03_updateDosageApprovalDrug() throws InterruptedException {
        WebFlows.login('d');
        WebFlows.patientBoxEntry(2);
        doctorFlows.editDrugDosage(0,41);

    }

    @Test(description = "updateDosageNotApprovalDrug")
    @Description("update dosage not approval drug")
    public void test04_updateDosageNotApprovalDrug() throws InterruptedException {
        WebFlows.login('d');
        WebFlows.patientBoxEntry(2);
        doctorFlows.editDrugDosage(0,90);
        doctorFlows.approvalInstruction();

    }





}
