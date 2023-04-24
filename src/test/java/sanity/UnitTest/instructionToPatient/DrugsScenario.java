package sanity.UnitTest.instructionToPatient;

import extensions.UIActions;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.drugFlows;
import workflows.nurse.nurseFlows;

import java.util.Calendar;

@Listeners(utilities.Listeners.class)

public class DrugsScenario extends CommonOps {


    @Test(description = "stop all patient's instruction", groups = {"doctor"})
    @Description("test add and save drug daily ")
    public void test00_stopAllInstructionToPatient(){

        WebFlows.login('d');
    //  System.out.println("this is param: ");
        WebFlows.patientBoxEntry(7);
     // WebFlows.patientBoxEntry(patient_num);
        doctorFlows.stopAllActiveInstructionToPatient();
    }

    @Test(description = "test add and save drug daily", groups = {"doctor"})
    @Description("test add and save drug daily ")
    public void test01_addAndSaveDrugDailyToPatient(){

        WebFlows.login('d');
         WebFlows.patientBoxEntry(7);
        drugFlows.addAndSaveDrugDailyToPatient("TAB acetylsalicylic acid 100mg (GODAMED)",100 , 1 , false, false);
        doctorFlows.approvalInstruction();
    }

    @Test(description = "test nurse-approval and nurse-execute drug daily" , groups = {"nurse"})
    @Description("test nurse-approval and nurse-execute  drug daily ")
    public void test01_nurseApprovalDrugDailyToPatient() throws InterruptedException {

        WebFlows.login('n');
         WebFlows.patientBoxEntry(7);
        nurseFlows.approvalDrugsDaily(1, false);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
    }

    @Test(description = "test add and save future drug daily ", groups = {"doctor"})
    @Description("test add and save future drug daily ")
    public void test02_addAndSaveFutureDrugDailyToPatient(){

        WebFlows.login('d');
        WebFlows.patientBoxEntry(4);
        drugFlows.addAndSaveDrugDailyToPatient("INJ PACLitaxel 150mg",100 , 3 , false , true);
        doctorFlows.approvalInstruction();

    }

    @Test(description = "test nurse-approval future drug daily" , groups = {"nurse"})
    @Description("test nurse-approval and future drug daily ")
    public void test02_nurseApprovalFutureDrugDailyToPatient() throws InterruptedException {

        WebFlows.login('n');
        WebFlows.patientBoxEntry(4);
        Thread.sleep(30000);
        nurseFlows.approvalDrugsDaily(3,true);


    }

    @Test(description = "test add and save drug sos", groups = {"doctor"} )
    @Description("test add and save drug sos ")
    public void test03_addAndSaveDrugSOSToPatient(){

        WebFlows.login('d');
         WebFlows.patientBoxEntry(3);
        drugFlows.addAndSaveDrugSOSToPatient();
    }


    @Test(description = "test nurse-approval and nurse-execute drug sos", groups = {"nurse"} )
    @Description("test nurse-approval and nurse-execute drug sos ")
    public void test03_nurseApprovalDrugSOSToPatient() throws InterruptedException {

        WebFlows.login('n');
         WebFlows.patientBoxEntry(3);
        nurseFlows.approvalSOSDrugList();
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
    }


    @Test(description = "test add and save drug once only" , groups = {"doctor"})
    @Description("test add and save drug once only ")
    public void test04_addAndSaveDrugOnceOnlyToPatient(){

        WebFlows.login('d');
         WebFlows.patientBoxEntry(3);
        drugFlows.addAndSaveDrugOnceOnlyToPatient();

    }

    @Test(description = "test nurse-approval and nurse-execute drug once only" , groups = {"nurse"})
    @Description("test nurse-approval and nurse-execute drug once only ")
    public void test04_nurseApprovalDrugOnceOnlyToPatient() throws InterruptedException {

        WebFlows.login('n');
         WebFlows.patientBoxEntry(3);
       nurseFlows.approvalDrugOnceOnlyList(1);
       NavigateFlows.goToCategory("cardex");
       nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
    }

    @Test(description = "test add and save drug by hour" , groups = {"doctor"} )
    @Description("test add and save drug by hour ")
    public void test05_addAndSaveDrugByHourToPatient(){

        WebFlows.login('d');
         WebFlows.patientBoxEntry(3);
        drugFlows.addAndSaveDrugByHourToPatient();


    }
    @Test(description = "test nurse-approval and nurse-execute drug by-hour" , groups = {"nurse"})
    @Description("test nurse-approval and nurse-execute drug by-hour")
    public void test05_nurseApprovalDrugByHourToPatient() throws InterruptedException {

        WebFlows.login('n');
         WebFlows.patientBoxEntry(3);
        nurseFlows.approvalDrugsDaily(1, false);
        NavigateFlows.goToCategory("cardex");
       nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
    }

    @Test(description = "test add and save drug weekly" , groups = {"doctor"})
    @Description("test add and save drug weekly ")
    public void test06_addAndSaveDrugWeeklyToPatient(){

        WebFlows.login('d');
         WebFlows.patientBoxEntry(3);
        drugFlows.addAndSaveDrugWeeklyToPatient(2);


    }
    @Test(description = "test nurse-approval and nurse-execute drug weekly" , groups = {"nurse"})
    @Description("test nurse-approval and nurse-execute drug weekly")
    public void test06_nurseApprovalDrugWeeklyToPatient() throws InterruptedException {

        WebFlows.login('n');
         WebFlows.patientBoxEntry(3);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        nurseFlows.approvalWeeklyDrugList(day,2);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
    }


//    @Test(description = "test add and save drug daily", groups = {"doctor"})
//    @Description("test add and save drug daily ")
//    public void test01_checkConsoleLog(){
//
//        WebFlows.login('d');
//        WebFlows.patientBoxEntry(3);
//        doctorFlows.newDrug();
//        UIActions.click(drugFormByHourPossibility.btn_everyXTime);
//
//    }
}
