package sanity.UnitTest.instructionToPatient;

import extensions.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.doctor.doctorFlows;

@Listeners(utilities.Listeners.class)
public class StopInstructionScenario extends CommonOps {

    @Test(description = "test stop all active instruction to patient " , groups = {"doctor"})
    @Description("test stop all active instruction to patient ")
    public void test01_stopAllActiveInstructionToPatient(){

        WebFlows.login('d');
        WebFlows.patientBoxEntry(3);
        doctorFlows.stopAllActiveInstructionToPatient();
        Verifications.textIsContains(doctorInstructionPage.title, "הוראות רפואיות");
    }

}
