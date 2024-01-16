package restoreBugs;
import extensions.UIActions;
import io.qameta.allure.Description;
//import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import utilities.Helpers;
import workflows.WebFlows;

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
        Thread.sleep(4000);
        WebFlows.patientBoxEntry(12);
        Thread.sleep(7000);
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+Helpers.getFileName("cardexAt00ToPatient12")+".png"));

       }



}
