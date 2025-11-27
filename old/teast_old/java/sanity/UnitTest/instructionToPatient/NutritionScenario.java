package sanity.UnitTest.instructionToPatient;


import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.doctor.nutritionFlows;
import workflows.nurse.nurseFlows;

@Listeners(utilities.Listeners.class)
public class NutritionScenario extends CommonOps {

    @Test(description = "test add and save nutrition daily")
    @Description("test add and save nutrition daily ")
    public void test01_addAndSaveNutritionDailyToPatient() throws InterruptedException {

        WebFlows.login('d');
        WebFlows.patientBoxEntry(3);
        nutritionFlows.addAndSaveNutritionToPatient("protein water",100,1,false);
    }

    @Test(description = "test nurse approval and nurse execute nutrition daily")
    @Description("test nurse approval and nurse execute nutrition daily ")
    public void test01_nurseApprovalNutritionDailyToPatient() throws InterruptedException {

        WebFlows.login('n');
        WebFlows.patientBoxEntry(3);
        nurseFlows.approvalDrugsDaily(1, false);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
    }
}
