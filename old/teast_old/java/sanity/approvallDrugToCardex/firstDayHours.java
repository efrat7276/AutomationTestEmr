package sanity.approvallDrugToCardex;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.doctorFlows;


@Listeners(utilities.Listeners.class)
public class firstDayHours extends CommonOps {
    @Test(description = "check hour list in first day to drug")
    public void check_hour_list_allDepartment() {

        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newDrug();
        doctorFlows.drugFormAddLiquidDrug("TAB ALPRAZolam 0.5mg (ALPRALID)",null,1,true);
        doctorFlows.approvalInstruction();
        afterMethod();

        WebFlows.login('n');
        WebFlows.patientBoxEntry(1);
        approvalInstructionPage.btn_drug.get(0).click();
        approvalInstructionPage.btn_drug.get(0).findElement(By.id("lll"));

    }

    @Test(description = "check hour list in first day to drug")
    public void check_hour_list_ICU() throws InterruptedException {

        WebFlows.login('d');
        departmentFlows.chooseDepartment("ט'נ' כללי");
        WebFlows.patientBoxEntry(1);
        doctorFlows.stopAllActiveInstructionToPatient();
        doctorFlows.newDrug();
        doctorFlows.drugFormAddLiquidDrug("INJ CARBOplatin 450mg",null,1,true);
        doctorFlows.approvalInstruction();
        afterMethod();

        WebFlows.login('n');
        departmentFlows.chooseDepartment("ט'נ' כללי");
        WebFlows.patientBoxEntry(1);
        approvalInstructionPage.btn_drug.get(0).click();
        approvalInstructionPage.btn_drug.get(0).findElement(By.id("lll"));

    }

}
