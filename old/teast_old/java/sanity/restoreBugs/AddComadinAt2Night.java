package sanity.restoreBugs;

import io.qameta.allure.Description;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;
import utilities.CommonOps;
import utilities.Helpers;
import utilities.Listeners;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.doctor.doctorFlows;
import workflows.nurse.nurseFlows;

import java.io.File;
import java.io.IOException;


@org.testng.annotations.Listeners(Listeners.class)
public class AddComadinAt2Night extends CommonOps {

    @Test(description = "addToPatientCoumadinDrugAndApproval")
    @Description("addToPatientCoumadinDrugAndApproval")
    public void addToPatientCoumadinDrugAndApproval() throws InterruptedException, IOException {
        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugDaily("TAB warfarin 1mg (COUMADIN)", 1, 1, null, false,false,true);
        doctorFlows.approvalInstruction();
        afterMethod();
       //reLogin();
        WebFlows.login('n');
        WebFlows.patientBoxEntry(1);
        nurseFlows.approvalDrugsDaily(1,false);
        NavigateFlows.goToCategory("cardex");
        Thread.sleep(3000);
       FileUtils.copyFile(Listeners.saveScreenshotFile(), new File("C:\\Automation\\AutomationProject_emr\\temp\\"+ Helpers.getFileName("picCardexAtHour02")+".png"));

    }



}
