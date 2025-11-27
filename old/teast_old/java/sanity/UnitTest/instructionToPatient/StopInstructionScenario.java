package sanity.UnitTest.instructionToPatient;

import actionUtilies.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.doctor.doctorFlows;

@Listeners(utilities.Listeners.class)
public class StopInstructionScenario extends CommonOps {

    @Test(description = "test stop all active instruction to patient " , groups = {"doctor"})
    @Description("test stop all active instruction to patient ")
    @Parameters("patient_num")
    public void test01_stopAllActiveInstructionToPatient(int patient_num){

        WebFlows.login('d');
        WebFlows.patientBoxEntry(patient_num);
        doctorFlows.stopAllActiveInstructionToPatient();
        Verifications.textIsContains(doctorInstructionPage.title, "הוראות רפואיות");
    }

}
