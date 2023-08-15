package emergency;
import extensions.UIActions;
import io.qameta.allure.Description;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.nurse.nurseFlows;

import java.io.File;
import java.io.IOException;

@Listeners(utilities.Listeners.class)
public class generalinstruction extends CommonOps {

    static String emergencyDep = "חדר מיון";

    @Test(description = "test add and save drug to patient emergency")
    @Description("add and save drug to patient emergency")
    public void test00_AddAndSavaDruToPatientEmergency() throws InterruptedException, IOException {
        WebFlows.login('d');
        Thread.sleep(1000);
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(1);
        doctorFlows.stopAllActiveInstructionToPatient();

        doctorFlows.newGeneralIns();
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(2, 1, null, false, true);
        doctorFlows.approvalInstruction();
        Thread.sleep(1000);
        NavigateFlows.goToCategory("cardex");
        Thread.sleep(5000);

        nurseFlows.executionNurseSign();
        //  FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("C:\\Automation\\AutomationProject_emr\\temp\\+"+getFileName("cardex")+".png"));

    }

    @Test(description = "take Print Screen Emergency")
    @Description("takePrintScreenEmergency")
    public void test01_takePrintScreenEmergency() throws InterruptedException, IOException {
        WebFlows.login('n');
        Thread.sleep(1000);
        departmentFlows.chooseDepartment(emergencyDep);
        Thread.sleep(1000);
        WebFlows.patientBoxEntry(1);
        Thread.sleep(500);
       UIActions.click(cardexPage.btn_printStickers);
        Thread.sleep(2000);
      //  FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("C:\\Automation\\AutomationProject_emr\\temp\\+"+getFileName("cardex_stickersPrintAt0020")+".png"));
       // utilities.Listeners.saveScreenshotFile();
        UIActions.updateText(cardexPage.exit_printStickers,"'yj");

        UIActions.click(cardexPage.exit_printStickers);

    }

}
