package sanity.UnitTest.general;

import actionUtilies.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
//import workflows.doctor.doctorInstructionFlows;

@Listeners(utilities.Listeners.class)
public class Patient extends CommonOps {

    private static final String doctor_role = "רופא";

    @Test(description = "Verify Entry To Box Patient")
    @Description("verify box patient by label 'מס' שרות'")
    public void test03_verifyEntryToBoxPatient(){

     //  GeneralWithDBFlow.loginWithDB();
        WebFlows.login('d');
  //      WebFlows.chooseRole("ddd");
       WebFlows.patientBoxEntry(1);
       Verifications.textIsVisible(demogeDataBar.sherut_label , "מס' שרות:");

    }

    @Test(description = "Verify Entry To Default Page")
    @Description("verify entry to default page")
    public void test03_verifyEntryToDefaultPage() throws InterruptedException {

        //GeneralWithDBFlow.loginWithDB();
        WebFlows.login('d');
        WebFlows.patientBoxEntry(3);
        Thread.sleep(6000);
        Verifications.textIsVisible(doctorInstructionPage.title, "הוראות רופא");

    }


    @Test(description = "Verify Entry To Default Page")
    @Description("verify entry to default page")
    public void generateDrugForm() {

        //GeneralWithDBFlow.loginWithDB();
        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
   //     doctorInstructionFlows.newDrug();
        drugForm.inp_selectDrug.sendKeys("ACAMOL");

    }



}
