package workflows.patientSheet;
import extensions.UIActions;
import io.qameta.allure.Step;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.doctorFlows;
import workflows.nurse.nurseFlows;

public class preliminaryActionsFlows extends CommonOps {




    @Step("add tab drug")
    public static void addDrugs() throws InterruptedException {

        int patient_num = 1;
        String dep = "ט'נ' כללי";
        WebFlows.login('d');
        departmentFlows.chooseDepartment(dep);
        WebFlows.patientBoxEntry(patient_num);
        doctorFlows.stopAllActiveInstructionToPatient();

        doctorFlows.newDrug();
//        doctorFlows.drugFormAddDrugDaily("TAB DONEPEZIL 10MG (ASENTA)",100,1,null,false,false,false);
//        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
//        UIActions.updateText(drugForm.inp_selectDrug,"   ");
//        Thread.sleep(500);

        doctorFlows.drugFormAddLiquidDrug("INJ CARMUSTINE 100mg INJ. (BCNU)","dextrose 5% 100ml",11,false);
        drugForm.inp_selectDrug.equals(driver.switchTo().activeElement());
        UIActions.updateText(drugForm.inp_selectDrug,"   ");
        Thread.sleep(500);

        doctorFlows.drugFormAddLiquidDrug("INF dex 5%+0.9% sod chl 1000ml (STANDART)",null,12,true);
        doctorFlows.approvalInstruction();

        afterMethod();
        WebFlows.login('n');


        //todo add nakaz

        departmentFlows.chooseDepartment(dep);
        WebFlows.patientBoxEntry(patient_num);
        nurseFlows.approvalAllPossibilitiesIns(true,false);
        nurseFlows.approvalNurseSign();

        Thread.sleep(5000);
        NavigateFlows.goToCategory("cardex");

        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
        nurseFlows.executeAllLiquidAfterApprovalNurse();
        nurseFlows.executionNurseSign();

    }


}