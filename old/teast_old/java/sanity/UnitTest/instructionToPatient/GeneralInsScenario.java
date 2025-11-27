package sanity.UnitTest.instructionToPatient;


import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.nurse.nurseFlows;


@Listeners(utilities.Listeners.class)
public class GeneralInsScenario extends CommonOps {

    @Test(description = "add and save generalIns daily")
    @Description("add and save generalIns daily ")
    public void test01_addAndSaveGeneralInsDailyToPatient() throws InterruptedException {

        WebFlows.login('d');
        WebFlows.patientBoxEntry(3);
        generalInstructionFlows.addAndSaveGeneralInsDailyToPatient(3, false);

    }

    @Test(description = "nurse approval and nurse execute generalIns daily")
    @Description("nurse approval and nurse execute generalIns daily ")
    public void test01_nurseApprovalGeneralInsDailyToPatient() throws InterruptedException {

        WebFlows.login('n');
        WebFlows.patientBoxEntry(3);
        nurseFlows.approvalGeneralInsDaily(3 , false);
       NavigateFlows.goToCategory("cardex");
       nurseFlows.executeAllGeneralInsAfterApprovalNurse();


    }


    @Test(description = "add and save generalIns daily")
    @Description("add and save generalIns daily ")
    public void test02_addAndSaveGeneralInsDailyFutureToPatient() throws InterruptedException {

        WebFlows.login('d');
        WebFlows.patientBoxEntry(3);
        generalInstructionFlows.addAndSaveGeneralInsDailyToPatient(3, true);

    }

    @Test(description = "nurse approval and nurse execute generalIns daily")
    @Description("nurse approval and nurse execute generalIns daily ")
    public void test02_nurseApprovalGeneralInsFutureToPatient() throws InterruptedException {

        WebFlows.login('n');
        WebFlows.patientBoxEntry(3);
        nurseFlows.approvalGeneralInsDaily(3 , true);


    }



}
