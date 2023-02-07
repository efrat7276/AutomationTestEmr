package sanity.UnitTest.instructionToPatient;


import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.doctor.drugFlows;
import workflows.nurse.nurseFlows;


@Listeners(utilities.Listeners.class)
public class SolutionScenario extends CommonOps {



    @Test(description = "test add and save drug diluted" , groups = {"doctor"})
    @Description("test add and save drug diluted ")
    public void test06_addAndSaveDrugDilutedToPatient(){

        WebFlows.login('d');
        WebFlows.patientBoxEntry(3);
        drugFlows.addAndSaveDrugDilutedToPatient();


    }

    @Test(description = "test nurse-approval drug diluted" , groups = {"nurse"})
    @Description("test nurse-approval drug weekly")
    public void test06_nurseApprovalDilutedToPatient() throws InterruptedException {

        WebFlows.login('n');
        WebFlows.patientBoxEntry(3);
        nurseFlows.approvalSolution(true, false);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllLiquidAfterApprovalNurse();
    }

    @Test(description = "test add and save continues drug" , groups = {"doctor"})
    @Description("test add and save continues drug")
    public void test07_addAndSaveDrugContinuesToPatient(){

        WebFlows.login('d');
        WebFlows.patientBoxEntry(3);
        drugFlows.addAndSaveDrugContinuesToPatient();


    }

    @Test(description = "test nurse-approval drug weekly" , groups = {"nurse"})
    @Description("test nurse-approval drug weekly")
    public void test07_nurseApprovalContinuesToPatient() throws InterruptedException {

        WebFlows.login('n');
        WebFlows.patientBoxEntry(3);
        nurseFlows.approvalSolution(true , true);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllLiquidAfterApprovalNurse();
    }

}
