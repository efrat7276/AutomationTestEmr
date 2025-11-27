package sanity.UnitTest.drugRequireApprovalFromInfectologist;

import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.drugFlows;
import workflows.nurse.nurseFlows;

import java.util.Calendar;

@Listeners(utilities.Listeners.class)
public class DrugWithConfirmationScenario extends CommonOps {

    @Test(description = "test add and save drug daily", groups = {"doctor"})
    @Description("test add and save drug daily ")
    public void test01_addAndSaveWithConfirmationDrugRequireConfirmationDailyToPatient(){

        WebFlows.login('d');
        WebFlows.patientBoxEntry(3);
        drugFlows.addAndSaveDrugDailyToPatient("TAB acyclovir 400mg (ZOVIRAX)",100 , 1 , true , false);
        doctorFlows.confirmationInfectionToDrugFromInstructionPage();

    }

    @Test(description = "test nurse-approval drug daily" , groups = {"nurse"})
    @Description("test nurse-approval drug daily ")
    public void test01_nurseApprovalDrugDailyToPatient() throws InterruptedException {

        WebFlows.login('n');
         WebFlows.patientBoxEntry(3);
        nurseFlows.approvalDrugsDaily(1, false);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();

    }

}
