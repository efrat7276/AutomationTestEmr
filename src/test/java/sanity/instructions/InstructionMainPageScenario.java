package sanity.instructions;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.db.GeneralWithDBFlow;
import workflows.nurse.nurseFlows;

public class InstructionMainPageScenario extends CommonOps {

    @Test(description = "verify a doctor's signature on unsigned instruction ")
    @Description("a doctor signs on doctor's instruction")
    public static void ApprovalAll() throws InterruptedException {

        GeneralWithDBFlow.loginWithDB();
        WebFlows.chooseRole("רופא");

        WebFlows.patientBoxEntry("2021159500");
       // Thread.sleep(1000);
        UIActions.click(doctorInstructionPage.btn_approvalDrug);
        WebFlows.userSignConfirm();
         Thread.sleep(1000);

   //     Verifications.visualElement("res_stamp.JPG");


    }


    @Test(description = "verify a doctor's signature on unsigned instruction ")
    @Description("a doctor signs on doctor's instruction")
    public static void NurseApprovalAll() throws InterruptedException {

        GeneralWithDBFlow.loginWithDB();
        WebFlows.chooseRole("אחות מוסמכת");

        WebFlows.patientBoxEntry("2021159500");
       // Thread.sleep(5000);
        nurseFlows.approvalDrugOnceOnlyList(4);

    }
}
