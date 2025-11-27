package restoreBugs;

import io.qameta.allure.Description;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import utilities.Helpers;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.doctorFlows;
import workflows.nurse.nurseFlows;

import java.io.File;
import java.io.IOException;

@Listeners(utilities.Listeners.class)
public class restoreBugsAt23 extends CommonOps {


    @Test(description = "add drug  future")
    @Description("add drug ")
    public void addDrug() throws InterruptedException, IOException {
        // doctor add daily drug
       // String departmentName = "חדר מיון";
        WebFlows.login('d');
        Thread.sleep(6000);
        //  departmentFlows.chooseDepartment(departmentName);

        WebFlows.patientBoxEntry(9);
        Thread.sleep(6000);
        doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugDaily("TAB ALPRAZolam 0.5mg (ALPRALID)", 100, 1, null, false, true, true);
        doctorFlows.approvalInstruction();
    }
    @Test(description = "take picture cardex daily report and updateExecutionPage at 23 o'clock ",enabled = false)
    @Description("take picture cardex daily report and updateExecutionPage at 23 o'clock ")
     public void addDrug_andExecute_takePictureToDailyReportAndUpdateExecutionPage() throws InterruptedException, IOException {
         // doctor add daily drug
         String departmentName = "חדר מיון";
//        WebFlows.login('d');
//        Thread.sleep(6000);
//       //  departmentFlows.chooseDepartment(departmentName);
//
//        WebFlows.patientBoxEntry(9);
//        Thread.sleep(6000);
//         doctorFlows.stopAllActiveInstructionToPatient();
//         doctorFlows.newDrug();
//         doctorFlows.drugFormAddDrugDaily("TAB ALPRAZolam 0.5mg (ALPRALID)",100,1,null,false,false,true);
//          doctorFlows.approvalInstruction();
//         afterMethod();
         //nurse approval drug and execute
         WebFlows.login('n');
         Thread.sleep(2000);
        // departmentFlows.chooseDepartment(departmentName);

           WebFlows.patientBoxEntry(9);
         nurseFlows.approvalDrugsDaily(1,false);
         Thread.sleep(7000);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
        Thread.sleep(1000);
        nurseFlows.executionNurseSign();
        cardexPage.dailyReportIcon.click();
        Thread.sleep(3000);
        //take pic 1 - daily report
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+ Helpers.getFileName("clickDailyReport")+".png"));
         cardexDailyReport.btnExit.click();
         // go to updateExecution page
         cardexPage.i_arrow.click();
        NavigateFlows.goToCategory("nurseConfirmation");
        NavigateFlows.goToSubCategory("updateExecution");
         Thread.sleep(2000);
         //take pic 2 - updateExecutionTable
         FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+ Helpers.getFileName("updateExecution")+".png"));
     }

    @Test(description = "take picture patient list ")
    @Description("take picture patient list at 23 o'clock ")
    public void take_pic_patientList() throws InterruptedException, IOException {
        WebFlows.login('n');
        Thread.sleep(6000);
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+ Helpers.getFileName("patientList")+".png"));
    }


}
