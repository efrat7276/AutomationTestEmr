package regression;

import extensions.UIActions;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.nurse.nurseFlows;

import java.io.File;
import java.io.IOException;

@Listeners(utilities.Listeners.class)
public class regression3 extends CommonOps {

    static String emergencyDep = "חדר מיון";

    @Test(description = "add one daily drug patient")
    @Description("add drug daily to patient")
    public void addDrugDailyToPatient() throws InterruptedException, IOException {

        WebFlows.login('d');
       departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(6);
        doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugDaily("TAB acetylcysteine 200mg efferv (REOLIN )", 20 ,1 , null ,false,false,true);
        doctorFlows.approvalInstruction();
        CommonOps.afterMethod();
        WebFlows.login('n');
        WebFlows.patientBoxEntry(6);
        nurseFlows.approvalAllPossibilitiesIns(true,false);
        nurseFlows.approvalNurseSign();





        //  doctorFlows.approvalInstruction();



    }

    @Test(description = "print stickers from cardex at 00 night")
   @Description("print stickers from cardex at 00 night")
    public void test02_printstickersfromcardexAt00night() throws InterruptedException, IOException {
        WebFlows.login('n');
        departmentFlows.chooseDepartment(emergencyDep);
        WebFlows.patientBoxEntry(1);
        UIActions.click(cardexPage.btn_printStickers);
        Thread.sleep(5000);
     //   FileUtils.copyFile(utilities.Listeners.saveScreenshotFile(), new File("C:\\Automation\\AutomationProject_emr\\temp\\"+getFileName("picPrintStickersAt00")+".png"));
        UIActions.click(cardexPage.exit_printStickers);

    }



}
