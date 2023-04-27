package regression;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
//import extensions.DBAction;
import io.qameta.allure.Description;
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
import workflows.nurse.nurseFlows;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Listeners(utilities.Listeners.class)
public class regression2 extends CommonOps {


    @Test(description = "add all instruction emergency to patient")
    @Description("add all instruction emergency to patient")
    public void addAndSaveAllInstructionToPatient() throws InterruptedException, SQLException {

        String department = "חדר מיון";
        String mispar_sherut="23059995";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formatDateTime = now.format(formatter);
        System.out.println("    שעה נוכחית"+formatDateTime);

      //  String query_addTzantarToPatient = "INSERT INTO dbo.emr_tzantar VALUES(" + "2023059578"+",4"+",NULL" +",1"+",NULL"+",NULL"+",NULL,'" +formatDateTime.toString()+"',NULL"+",NULL"+",NULL"+",NULL" +",NULL"+",NULL,"+"0" +",NULL,'"+formatDateTime.toString()+ "'," +"'בדיקות אוטומציה'"+",NULL"+",NULL"+",NULL" +",NULL"+",NULL,"+"3)";
      //  DBAction.InsertQuery(query_addTzantarToPatient);

        WebFlows.login('d');
        departmentFlows.chooseDepartment(department);
        WebFlows.patientBoxEntry(mispar_sherut);
        doctorFlows.stopAllActiveInstructionToPatient();

        // החדרת צנתר




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
        departmentFlows.chooseDepartment(department);
       WebFlows.patientBoxEntry(mispar_sherut);
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


//    @Test(description = "add generalIns list to patient")
//    @Description("add generalIns list to patient")
//    public void addAndSaveGeneralInsAt4(){
//
//        WebFlows.login('d');
//        WebFlows.patientBoxEntry(4);
//        doctorFlows.stopAllActiveInstructionToPatient();
//        doctorFlows.newGeneralIns();
//        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(1,1,null , false);
//        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(2,1,null , false);
//        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(3,1,null , false);
//        generalInstructionFlows.generalFormAddGeneralInsDaily(4,1,1 , false,false);
//
//        generalInstructionFlows.saveGeneralInstructionsSelected();
//
//        doctorFlows.approvalInstruction();
//
//
//
//    }




}
