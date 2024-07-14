package restoreBugs;
import extensions.UIActions;
import io.qameta.allure.Description;
//import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
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
public class restoreBugs extends CommonOps {

 //   static String emergencyDep = "חדר מיון";

    @Test(description = "take picture to depMeushpazim , cardexPage and printSticker")
    @Description("take picture to depMeushpazim , cardexPage and printSticker")
    public void takePictureDepMeushpazim_cardexPage_printSticker() throws InterruptedException, IOException {
        WebFlows.login('n');
        Thread.sleep(4000);
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+ Helpers.getFileName("depMeushpazim")+".png"));
        WebFlows.patientBoxEntry(3);
        Thread.sleep(4000);
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+Helpers.getFileName("cardex")+".png"));
        UIActions.click(cardexPage.btn_printStickers);
        Thread.sleep(4000);
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+Helpers.getFileName("printStickers")+".png"));
    }

    @Test(description = "take picture cardex patient at 00 ")
    @Description("take picture cardex patient at 00 ")
    public void box_in_cardex() throws InterruptedException, IOException {
        WebFlows.login('n');
        Thread.sleep(7000);
        WebFlows.patientBoxEntry(1);
        Thread.sleep(10000);
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+Helpers.getFileName("cardexAt00ToPatient12")+".png"));

       }

    @Test(description = "addDrugToPatient")
    @Description("addDrugToPatient ")
    public void addDrugToPatient() throws InterruptedException, IOException {
        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        Thread.sleep(2000);
        doctorFlows.newDrug();
        Thread.sleep(1000);
        doctorFlows.drugFormAddDrugDaily("TAB paracetamol 500mg (ACAMOL)" , 10,3,null,false,false,true);

        Thread.sleep(1000);
        doctorFlows.approvalInstruction();

    }

    @Test(description = "take picture cardex ")
    @Description("take picture cardex")
    public void take_pic_cardex() throws InterruptedException, IOException {
        WebFlows.login('n');
        Thread.sleep(6000);
        WebFlows.patientBoxEntry(5);
        Thread.sleep(5000);
//        UIActions.click(cardexPage.arrowForward);
//        Thread.sleep(500);
//
       //nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
//        nurseFlows.executeAllLiquidAfterApprovalNurse();
//        // nurseFlows.executeAllGeneralInsAfterApprovalNurse();
 //   Thread.sleep(1000);
   //    nurseFlows.executionNurseSign();
// נפילה מכוונת
      //  Thread.sleep(1000);

        Assert.fail();
//
//        //   FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+ Helpers.getFileName("cardex")+".png"));
//        try {
//
//            FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File( Helpers.getFileName("cardex") + ".png"));
//        }
//        catch (Exception e){
//            System.out.println("תמונה לא נשמרה");
//        }

    }


}
