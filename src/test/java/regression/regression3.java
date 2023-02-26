package regression;

import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.generalInstructionFlows;
import workflows.nurse.nurseFlows;

@Listeners(utilities.Listeners.class)
public class regression3 extends CommonOps {


    @Test(description = "add one daily drug patient")
    @Description("add drug daily to patient")
    public void addDrugDailyToPatient() throws InterruptedException{

        WebFlows.login('d');
     //   departmentFlows.chooseDepartment("חדר מיון");
        WebFlows.patientBoxEntry(6);
        doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newDrug();
        doctorFlows.drugFormAddDrugDaily("TAB acetylcysteine 200mg efferv (REOLIN )", 20 ,1 , null ,false,false,true);
        doctorFlows.approvalInstruction();
        CommonOps.afterMethod();
        WebFlows.login('n');
        WebFlows.patientBoxEntry(6);
        nurseFlows.approvalAllPossibilitiesIns(true,false);
        nurseFlows.approvalNurseSign();





        //  doctorFlows.approvalInstruction();



    }




}
