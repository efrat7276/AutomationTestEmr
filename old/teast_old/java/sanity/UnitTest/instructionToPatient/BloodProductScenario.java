package sanity.UnitTest.instructionToPatient;


import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.doctor.bloodProductInstructionFlows;
import workflows.nurse.nurseFlows;


@Listeners(utilities.Listeners.class)
public class BloodProductScenario extends CommonOps {

    @Test(description = "add and save blood-product" , groups = {"doctor"})
    @Description("add and save blood-product to patient ")
    public void test01_addAndSaveBloodProductToPatient()  {

        WebFlows.login('d');
        WebFlows.patientBoxEntry(3);
     //   bloodProductInstructionFlows.addAndCloseBloodProductToPatient();


    }

    @Test(description = "approval blood-product" , groups = {"nurse"})
    @Description("nurse approval and nurse execute blood-product ")
    public void test01_nurseApprovalBloodProductToPatient() throws InterruptedException {

        WebFlows.login('n');
        WebFlows.patientBoxEntry(3);
        nurseFlows.approvalSolution(false ,false);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllLiquidAfterApprovalNurse();

    }


}
