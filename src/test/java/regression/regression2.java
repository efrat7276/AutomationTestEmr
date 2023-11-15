package regression;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
//import extensions.DBAction;
import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import io.qameta.allure.Muted;
//import org.apache.commons.io.FileUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.emr.nurse.Execute.UpdateExecutionPage;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.nurse.nurseFlows;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Listeners(utilities.Listeners.class)
public class regression2 extends CommonOps {

   static String emergencyDep = "חדר מיון";

   @Test(description = "test add and save drug to patient emergency")
   @Description("add and save drug to patient emergency")
   public void test00_AddAndSavaDruToPatientEmergency() throws InterruptedException {
       WebFlows.login('d');
       departmentFlows.chooseDepartment(emergencyDep);
       WebFlows.patientBoxEntry(1);
       doctorFlows.stopAllActiveInstructionToPatient();
       doctorFlows.newDrug();
       doctorFlows.drugFormAddDrugOnceOnly("TAB acetylsalicylic acid 100mg (MICROPIRIN)",null, null,null,false,true);
       doctorFlows.approvalInstruction();
   }

    @Test(description = "add all instruction emergency to patient")
    @Description("add all instruction emergency to patient")
    public void test01_addAndSaveAllInstructionToPatient() throws InterruptedException, SQLException, IOException {

        int patient_num =1;
        //החדרת צנתר בDB
//
//        String query_addTzantarToPatient = "INSERT INTO dbo.emr_tzantar VALUES(" + "2023059578"+",4"+",NULL" +",1"+",NULL"+",NULL"+",NULL,'" +formatDateTime.toString()+"',NULL"+",NULL"+",NULL"+",NULL" +",NULL"+",NULL,"+"0" +",NULL,'"+formatDateTime.toString()+ "'," +"'בדיקות אוטומציה'"+",NULL"+",NULL"+",NULL" +",NULL"+",NULL,"+"3)";
//        DBAction.InsertQuery(query_addTzantarToPatient);

        WebFlows.login('d');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(patient_num);
        doctorFlows.stopAllActiveInstructionToPatient();


        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugOnceOnly("INJ acetylcysteine 2g/10ml (PARVOLEX)",null, null,null,false,false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(1000);
        doctorFlows.drugFormAddDrugSOS("INJ acetylcysteine 2g/10ml (PARVOLEX)","20" , null,4 ,3,false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(1000);
        doctorFlows.drugFormAddLiquidDrug("INJ dexamethasone 4mg/1ml (DEXACORT)",null ,3,false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
       Thread.sleep(1000);
        doctorFlows.drugFormAddLiquidDrug("INJ traMADol ","saline 0.9% 100ml" ,3,false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        Thread.sleep(1000);
        doctorFlows.drugFormAddLiquidDrug("INF dextrose 5% 1000ml (GLUCOSE)",null ,12,true);

        doctorFlows.newGeneralIns();
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(2,1,null,false,true);

        Thread.sleep(1000);
        doctorFlows.approvalInstruction();

       CommonOps.afterMethod();
       WebFlows.login('n');
        departmentFlows.chooseDepartment(emergencyDep);
       WebFlows.patientBoxEntry(patient_num);

       UIActions.click( cardexPage.i_arrow);
        NavigateFlows.goToCategory("nursing");
        NavigateFlows.goToSubCategory("nursingIns");
        doctorFlows.newGeneralIns();
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(2,1,null, false,true);
         doctorFlows.approvalInstruction();
        Thread.sleep(2000);

        NavigateFlows.goToCategory("cardex");

// שחזור באג של הכנת תרופות בלילה
//        UIActions.click(cardexPage.btn_printStickers);
//        Thread.sleep(5000);
//        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("C:\\Automation\\AutomationProject_emr\\temp\\"+getFileName("picPrintStickersAt23")+".png"));
//        UIActions.click(cardexPage.exit_printStickers);



        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
        nurseFlows.executeAllLiquidAfterApprovalNurse();
       // nurseFlows.executeAllGeneralInsAfterApprovalNurse();
        Thread.sleep(1000);
        nurseFlows.executionNurseSign();

//        UIActions.click(cardexPage.i_arrow);
//        NavigateFlows.goToCategory("nurseConfirmation");
//        NavigateFlows.goToSubCategory("updateExecution");
//        UIActions.click(updateExecutionPage.btn_updateExecList.get(0));
//        UIActions.click(updateExecutionPage.btn_iconExecList.get(0));
//        Thread.sleep(1000);
//        UIActions.click(cardexPage.checkboxXInput);

    }


    @Test(description = "addToPatientNotApprovalDrug")
    @Description("add to patient not approval drug" )
    public void test02_addToPatientNotApprovalDrug() throws InterruptedException {
        WebFlows.login('d');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(1);
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugOnceOnly("TAB acetylsalicylic acid 100mg" , "100" , null , null , false , true );
    }



//    @Test(description = "stopToPatientAllActiveDrugs")
//    @Description("stop to patient all active drugs")
//    public void test03_stopToPatientAllActiveDrugs() throws InterruptedException {
//        WebFlows.login('d');
//        departmentFlows.chooseDepartment(emergencyDep);
//        WebFlows.patientBoxEntry(1);
//        doctorFlows.stopAllActiveInstructionToPatient();
//    }


//    @Test(description = "addToPatientDrugsOneNotApproved")
//    @Description("add to patient drugs one not approved")
//    public void test04_addToPatientDrugsOneNotApproved() throws InterruptedException {
//        WebFlows.login('d');
//        departmentFlows.chooseDepartment(emergencyDep);
//        WebFlows.patientBoxEntry(1);
//        doctorFlows.newDrug();
//        doctorFlows.drugFormAddDrugOnceOnly("TAB acetylsalicylic acid 100mg" , "100" , null , null , false , true );
//    }

    @Test(description = "updateDosageNotApprovalDrug")
    @Description("update dosage not approval drug")
    public void test03_updateDosageNotApprovalDrug() throws InterruptedException {
        WebFlows.login('d');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(1);
        doctorFlows.editDrugDosage(0,90);
        doctorFlows.approvalInstruction();

    }

    @Test(description = "updateDosageApprovalDrug")
    @Description("update dosage approval drug")
    public void test04_updateDosageApprovalDrug() throws InterruptedException {
        WebFlows.login('d');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(1);
        doctorFlows.editDrugDosage(0,41);
    }

//    @Test(description = "add and execute generalIns list to patient at 3 ")
//    @Description("add and execute generalIns list to patient")
//    public void test07_addAndSaveGeneralInsAt4() throws InterruptedException {
//
//        WebFlows.login('d');
//        departmentFlows.chooseDepartment(emergencyDep);
//        WebFlows.patientBoxEntry(4);
//        doctorFlows.stopAllActiveInstructionToPatient();
//        doctorFlows.newGeneralIns();
//        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(1,1,null , false,false);
//        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(2,1,null , false,false);
//        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(3,1,null , false,false);
//        // generalInstructionFlows.generalFormAddGeneralInsDaily(4,1,1 , false,false);
//
//        generalInstructionFlows.saveGeneralInstructionsSelected();
//
//        doctorFlows.approvalInstruction();
//
//        CommonOps.afterMethod();
//        WebFlows.login('n');
//        departmentFlows.chooseDepartment(emergencyDep);
//        WebFlows.patientBoxEntry(4);
//        nurseFlows.executeAllGeneralInsAfterApprovalNurse();
//        nurseFlows.executionNurseSign();
//    }


}
