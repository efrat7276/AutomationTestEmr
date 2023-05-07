package regression;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
//import extensions.DBAction;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.nurse.nurseFlows;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Listeners(utilities.Listeners.class)
public class regression2 extends CommonOps {

   static String emergencyDep = "חדר מיון";

    @Test(description = "add all instruction emergency to patient")
    @Description("add all instruction emergency to patient")
    public void addAndSaveAllInstructionToPatient() throws InterruptedException, SQLException {

        int patient_num = 1;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formatDateTime = now.format(formatter);
        System.out.println("    שעה נוכחית"+formatDateTime);

      //  String query_addTzantarToPatient = "INSERT INTO dbo.emr_tzantar VALUES(" + "2023059578"+",4"+",NULL" +",1"+",NULL"+",NULL"+",NULL,'" +formatDateTime.toString()+"',NULL"+",NULL"+",NULL"+",NULL" +",NULL"+",NULL,"+"0" +",NULL,'"+formatDateTime.toString()+ "'," +"'בדיקות אוטומציה'"+",NULL"+",NULL"+",NULL" +",NULL"+",NULL,"+"3)";
      //  DBAction.InsertQuery(query_addTzantarToPatient);

        WebFlows.login('d');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(patient_num);
        doctorFlows.stopAllActiveInstructionToPatient();
//
//        // החדרת צנתר
//
//
//
//
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugOnceOnly("TAB acetylsalicylic acid 100mg (MICROPIRIN)",null, null,null,false,false);
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
//        NavigateFlows.goToCategory("nursing");
//        NavigateFlows.goToSubCategory("nursingIns");
//        doctorFlows.newGeneralIns();
//        generalInstructionFlows.generalFormAddGeneralInsDaily(2,1,1,false,false,true);

     //   Thread.sleep(2000);

     //   NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
        nurseFlows.executeAllLiquidAfterApprovalNurse();
        nurseFlows.executeAllGeneralInsAfterApprovalNurse();

        Thread.sleep(1000);
        nurseFlows.executionNurseSign();

    }

     @Test(description = "addToPatientNotApprovalDrug")
     @Description("add to patient not approval drug")
     public void addToPatientNotApprovalDrug() throws InterruptedException {
         WebFlows.login('d');
         departmentFlows.chooseDepartment(emergencyDep);
         WebFlows.patientBoxEntry(1);
         doctorFlows.newDrug();
         doctorFlows.drugFormAddDrugOnceOnly("TAB acetylsalicylic acid 100mg" , "100" , null , null , false , true );
     }



    @Test(description = "stopToPatientAllActiveDrugs")
    @Description("stop to patient all active drugs")
    public void stopToPatientAllActiveDrugs() throws InterruptedException {
        WebFlows.login('d');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(1);
        doctorFlows.stopAllActiveInstructionToPatient();
    }


    @Test(description = "addToPatientDrugsOneNotApproved")
    @Description("add to patient drugs one not approved")
    public void addToPatientDrugsOneNotApproved() throws InterruptedException {
        WebFlows.login('d');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(1);
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugOnceOnly("TAB acetylsalicylic acid 100mg" , "100" , null , null , false , true );
    }

    @Test(description = "updateDosageNotApprovalDrug")
    @Description("update dosage not approval drug")
    public void updateDosageNotApprovalDrug() throws InterruptedException {
        WebFlows.login('d');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(1);
        doctorFlows.editDrugDosage(0,90);
        doctorFlows.approvalInstruction();

    }

    @Test(description = "updateDosageApprovalDrug")
    @Description("update dosage approval drug")
    public void updateDosageApprovalDrug() throws InterruptedException {
        WebFlows.login('d');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(1);
        doctorFlows.editDrugDosage(0,41);
    }

    @Test(description = "add and execute generalIns list to patient at 3 ")
    @Description("add and execute generalIns list to patient")
    public void addAndSaveGeneralInsAt4() throws InterruptedException {

        WebFlows.login('d');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(4);
        doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newGeneralIns();
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(1,1,null , false,false);
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(2,1,null , false,false);
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(3,1,null , false,false);
       // generalInstructionFlows.generalFormAddGeneralInsDaily(4,1,1 , false,false);

        generalInstructionFlows.saveGeneralInstructionsSelected();

        doctorFlows.approvalInstruction();

        WebFlows.login('n');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(4);
        nurseFlows.executeAllGeneralInsAfterApprovalNurse();
        nurseFlows.executionNurseSign();
    }
}
