package ICU;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.bloodProductInstructionFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.doctor.nutritionFlows;
import workflows.general.general;
import workflows.nurse.nurseFlows;
import workflows.icu_department.*;
import java.io.IOException;
import java.util.List;

@Listeners(utilities.Listeners.class)
public class regression1_icu extends CommonOps {

    @Test(description = "reception planning")
    @Description("reception planning")
    public void
    test01_icu_receptionPlanning() throws InterruptedException, IOException {
        int patient_num = 3;
        String departmentICU = "ט'נ' כללי";
        WebFlows.login('d');
        departmentFlows.chooseDepartment(departmentICU);
        WebFlows.patientBoxEntry(patient_num);
        doctorFlows.stopAllActiveInstructionToPatient();
       planningReception.savePlanningReception();

        Thread.sleep(1000);
         ////חתימה על כל ההוראות מהקבלה
        doctorFlows.approvalInstruction();
        Thread.sleep(1000);
        CommonOps.afterMethod();
        WebFlows.login('n');
        departmentFlows.chooseDepartment(departmentICU);
        WebFlows.patientBoxEntry(patient_num);
        nurseFlows.approvalAllPossibilitiesIns(true,false);
        nurseFlows.approvalNurseSign();
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAndSupervisionAllCardexIns();

    }


//
//void aa() throws InterruptedException, IOException {
//
//
//
//    }
//
//
//    @Test(description = "addToPatientTwoDrugsOneApprovedAndOneNotApproved")
//    @Description("add to patient two drugs one approved and one not approved")
//    public void test02_addToPatientTwoDrugsOneApprovedAndOneNotApproved(){
//        // add to patient one drug not approved and drug approved
//
//        WebFlows.login('d');
//        WebFlows.patientBoxEntry(1);
//        doctorFlows.newDrug();
//        doctorFlows.drugFormAddDrugDaily("TAB ALPRAZolam 0.5mg (XANAX)", 20 , 3 , null , false , false , true);
//        doctorFlows.approvalInstruction();
//        doctorFlows.newDrug();
//        doctorFlows.drugFormAddDrugDaily("TAB ALPRAZolam 0.5mg (XANAX)", 100 , 3 , null , false , false , true);
//    }
//
//



}
