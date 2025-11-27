package sanity.UnitTest.addAndEditInstructionToPatient;

import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.drugFlows;

@Listeners(utilities.Listeners.class)
public class EditDrugIns extends CommonOps {


    @Test(description = "edit dosage to drug daily ", groups = {"doctor"})
    @Description("edit drug daily")
    public void test01_editDrugDailyDosage() throws InterruptedException {

        WebFlows.login('d');
        WebFlows.patientBoxEntry(12);
        drugFlows.editDrugDailyDosage();

    }


    @Test(description = "edit timePerDay in drug daily", groups = {"doctor"})
    @Description("edit drug daily")
    public void test01_editDrugDailyNumberOfTime() throws InterruptedException {

        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        drugFlows.editDrugDailyNumberOfTime(3);

    }
}
