package restoreBugs;

import extensions.UIActions;
import io.qameta.allure.Description;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import utilities.Helpers;
import workflows.WebFlows;
import workflows.doctor.doctorFlows;

import workflows.nurse.nurseFlows;

import java.io.File;
import java.io.IOException;

@Listeners(utilities.Listeners.class)
public class restoreBugs2 extends CommonOps {


//     @Test(description = "take picture cardex daily report at 23 o'clock ")
//     @Description("take picture cardex daily report at 23 o'clock ")
//     public void cardex_executeAndOpenDailyReport() throws InterruptedException, IOException {
//        WebFlows.login('n');
//        Thread.sleep(6000);
//        WebFlows.patientBoxEntry(9);
//        Thread.sleep(6000);
//        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
//        nurseFlows.executionNurseSign();
//        cardexPage.dailyReportIcon.click();
//        Thread.sleep(1000);
//        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+ Helpers.getFileName("clickDailyReport")+".png"));
//       }

    @Test(description = "take picture patient list ")
    @Description("take picture patient list at 23 o'clock ")
    public void take_pic_patientList() throws InterruptedException, IOException {
        WebFlows.login('n');
        Thread.sleep(6000);
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+ Helpers.getFileName("patientList")+".png"));
    }


}
