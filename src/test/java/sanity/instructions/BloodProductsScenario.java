package sanity.instructions;


import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.CommonOps;
import utilities.ManageDDT;
import workflows.WebFlows;
import workflows.db.GeneralWithDBFlow;
import workflows.doctor.bloodProductInstructionFlows;
import workflows.doctor.doctorFlows;

@Listeners(utilities.Listeners.class)
public class BloodProductsScenario extends CommonOps {



    @Test(description = "Verify additions 1 bloodProduct to Patient" , dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"} )
    @Description("bloodProductForm - addition 1 bloodProduct to patient")
    public void verify_AddAndSaveBloodProduct(String patient_num) {
        GeneralWithDBFlow.loginWithDB();
        WebFlows.chooseRole("רופא");
     //   WebFlows.patientBoxEntry(patient_num);
    //    doctorFlows.newBloodProduct();
   //     bloodProductInstructionFlows.
        doctorFlows.approvalInstruction();
    //    Verifications.isElementDisplay(doctorInstructionPage.btns_addDrug.get(0));

    }
}
