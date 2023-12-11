package sanity.UnitTest.emergency;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;
import workflows.departmentFlows;
import workflows.doctor.doctorFlows;
import workflows.doctor.drugFlows;

@Listeners(utilities.Listeners.class)
public class DoctorEmergency extends CommonOps {

//    @Test(description="entry to emergency department")
//    @Description("entry to emergency department")
//    public void chooseEmergencyDepartment() throws InterruptedException {
//
//        WebFlows.login('d');
//        departmentFlows.chooseDepartment("חדר מיון");
//        Thread.sleep(1000);
//        Verifications.textIsContains(chooseDepartmentListPage.box_patientNumber , "11");
//
//    }
    @Test(description="check hospital stock checkbox")
    @Description("check hospital stock checkbox")
    public void test01_checkHospitalStockCheckbox() throws InterruptedException{
        WebFlows.login('d');
        departmentFlows.chooseDepartment("חדר מיון");
        WebFlows.patientBoxEntry(1);
        doctorFlows.newDrug();
        Verifications.isNotSelected(drugForm.checkbox_DepartmentalStandard);
    }

    @Test(description="check possibilities for emergency")
    @Description("check possibilities for emergency")
    public void test02_checkPossibilitiesInDrugForm() throws InterruptedException{
        WebFlows.login('d');
        departmentFlows.chooseDepartment("חדר מיון");
        WebFlows.patientBoxEntry(1);
        doctorFlows.newDrug();
        softAssert.assertTrue(drugForm.listRadioPossbility.size()==2);
        softAssert.assertTrue(drugForm.listRadioPossbility.get(2).isDisplayed());
        softAssert.assertTrue(drugForm.listRadioPossbility.get(3).isDisplayed());
        softAssert.assertAll();
    }

    @Test(description="add drug once-only emergency ")
    @Description("add drug once-only emergency ")
    public void test03_addDrugOnceOnly() throws InterruptedException{
        WebFlows.login('d');
        departmentFlows.chooseDepartment("חדר מיון");
        WebFlows.patientBoxEntry(1);
        drugFlows.addAndSaveDrugOnceOnlyToPatient();

    }

    @Test(description="add drug once-only and execute")
    @Description("add drug once-only and execute")
    public void test04_addDrugOnceOnlyAndExecute() throws InterruptedException{
        WebFlows.login('d');
        departmentFlows.chooseDepartment("חדר מיון");
        WebFlows.patientBoxEntry(1);
        drugFlows.addAndSaveDrugOnceOnlyWithExecuteToPatient();

    }
}
