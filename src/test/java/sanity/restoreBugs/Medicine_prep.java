package sanity.restoreBugs;

import extensions.UIActions;
import io.qameta.allure.Description;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.*;
import utilities.CommonOps;
import utilities.Listeners;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.doctor.drugFlows;
import workflows.nurse.nurseFlows;

import java.io.File;
import java.io.IOException;


@org.testng.annotations.Listeners(utilities.Listeners.class)
public class Medicine_prep extends CommonOps {

    @Test(description = "")
    @Description("")
    public void test01_AddDrugDaily()  {
        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        drugFlows.addAndSaveDrugDailyToPatient("INJ ceFAZolin  1g (CEFAMEZINE)", 1, 1, true , false);
//        Verifications.isElementDisplay(patientsList.menu_patientList);

    }
    @Test(description = "")
    @Description("")
    public void test01_EditDrugDaily() throws InterruptedException {
        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        drugFlows.editDrugDailyNumberOfTime(3);
//        Verifications.isElementDisplay(patientsList.menu_patientList);

    }

    @Test(description = "")
    @Description("")
    public void test01_ApprovalDrugDailyNight() throws InterruptedException {
        WebFlows.login('n');
        WebFlows.patientBoxEntry(1);
        nurseFlows.approvalOneDrugDailyToNonGiving( 3 );
       // NavigateFlows.goToCategory("cardex");
      //  nurseFlows.executeAllToCurrentHourAfterApprovalNurse();
//        Verifications.isElementDisplay(patientsList.menu_patientList);

    }

    @Test(description = "")
    @Description("")
    public void test01_ExecuteDrugNight() throws InterruptedException {
        WebFlows.login('n');
        WebFlows.patientBoxEntry(1);
        nurseFlows.executeAllLiquidAfterApprovalNurse();

    }

    @Test(description = "t")
    @Description("")
    public void test01_printStickDrug() throws InterruptedException, IOException {
        WebFlows.login('n');
        UIActions.click(mainMenuPage.category_drugPreparation);
        Thread.sleep(6000);
        UIActions.click(prepMedcinePage.filterHourBtn);
        UIActions.selectFromList(prepMedcinePage.filterHourList,"12:00");
        Thread.sleep(2000);
        FileUtils.copyFile(Listeners.saveScreenshotFile(), new File("C:\\Automation\\AutomationProject_emr\\temp\\"+getFileName("12")+".png"));

    }


}
