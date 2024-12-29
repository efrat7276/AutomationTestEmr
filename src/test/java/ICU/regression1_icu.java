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
import workflows.patientSheet.deviceDataFlows;
import workflows.patientSheet.preliminaryActionsFlows;

import java.io.IOException;
import java.util.List;

import static workflows.patientSheet.deviceDataFlows.add_an_error_comment_device_data;

@Listeners(utilities.Listeners.class)
public class regression1_icu extends CommonOps {

   // String  patient_num = "24120811";
    int  patient_num = 2;

    String departmentICU = "ט'נ' כללי";
    @Test(description = "reception planning")
    @Description("reception planning")
    public void
    test01_icu_receptionPlanning() throws InterruptedException, IOException {

        WebFlows.login('d');
        departmentFlows.chooseDepartment(departmentICU);
        WebFlows.patientBoxEntry(patient_num);
      //  doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newDrug();
        //daily drug
    //    doctorFlows.drugFormAddDrugCycle("KCL", 20, 3, false,  true);
       planningReception.savePlanningReception();
//
//        Thread.sleep(1000);
//         ////חתימה על כל ההוראות מהקבלה
        doctorFlows.approvalInstruction();
        Thread.sleep(1000);
       afterMethod();


    //   CommonOps.reLogin();
        WebFlows.login('n');
        departmentFlows.chooseDepartment(departmentICU);
       WebFlows.patientBoxEntry(patient_num);
        nurseFlows.approvalAllPossibilitiesIns(true,false);
        nurseFlows.approvalNurseSign();
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAndSupervisionAllCardexIns();

    }

    @Test(description = "patient sheet")
    @Description("patient sheet")
    void check_patientSheet() throws InterruptedException {

       // preliminaryActionsFlows.addDrugs();
       // Thread.sleep(1000);
        WebFlows.login('d');
        Thread.sleep(1000);





       departmentFlows.chooseDepartment(departmentICU);
       WebFlows.patientBoxEntry(patient_num);
       NavigateFlows.goToCategory("patientSheet");
        Thread.sleep(1000);

       deviceDataFlows.confirm_device_data();
       deviceDataFlows.make_error_device_data();
       Thread.sleep(500);

       deviceDataFlows.add_a_comment_device_data();
        Thread.sleep(500);
        deviceDataFlows.add_an_error_comment_device_data();

    }



}
