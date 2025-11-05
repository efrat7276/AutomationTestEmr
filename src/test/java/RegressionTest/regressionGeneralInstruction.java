package RegressionTest;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;

public class regressionGeneralInstruction extends CommonOps{


    @Test(description = "add to patient generalInstruction")
    public void test_addToPatientGeneralInstruction() throws InterruptedException {

        WebFlows.login('d');
        WebFlows.patientBoxEntry(2);
        generalInstructionFlows.addAndSaveGeneralInsDailyToPatient(2,false);
        doctorFlows.approvalInstruction();
    }
}
