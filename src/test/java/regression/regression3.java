package regression;

import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;

@Listeners(utilities.Listeners.class)
public class regression3 extends CommonOps {


    @Test(description = "add generalIns list to patient")
    @Description("add generalIns list to patient")
    public void addAndDonSaveGeneralInsAt5() throws InterruptedException{

        WebFlows.login('d');
        departmentFlows.chooseDepartment("חדר מיון");
        WebFlows.patientBoxEntry(5);
        doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newGeneralIns();
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(1,1,null , false,false);
        generalInstructionFlows.generalFormAddGeneralInsOnceOnly(2,1,null , false,true);

        generalInstructionFlows.saveGeneralInstructionsSelected();

      //  doctorFlows.approvalInstruction();



    }




}
