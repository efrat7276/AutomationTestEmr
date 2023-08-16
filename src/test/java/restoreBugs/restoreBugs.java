package restoreBugs;
import extensions.UIActions;
import io.qameta.allure.Description;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;

import java.io.File;
import java.io.IOException;

@Listeners(utilities.Listeners.class)
public class restoreBugs extends CommonOps {

 //   static String emergencyDep = "חדר מיון";

    @Test(description = "take picture to depMeushpazim , cardexPage and printSticker")
    @Description("take picture to depMeushpazim , cardexPage and printSticker")
    public void takePictureDepMeushpazim_cardexPage_printSticker() throws InterruptedException, IOException {
        WebFlows.login('d');
        Thread.sleep(1000);
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+getFileName("depMeushpazim")+".png"));
        WebFlows.patientBoxEntry(3);
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+getFileName("cardex")+".png"));
        UIActions.click(cardexPage.btn_printStickers);
        FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("E:\\import\\AutomationProject_emr\\temp\\"+getFileName("printStickers")+".png"));
    }



}
