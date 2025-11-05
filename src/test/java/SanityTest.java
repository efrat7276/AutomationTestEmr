import extensions.DBAction;
import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pageObjects.emr.nurse.Execute.CardexPage;
import utilities.CommonOps;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.db.GeneralWithDBFlow;
import workflows.doctor.doctorFlows;
import workflows.nurse.nurseFlows;

public class SanityTest extends CommonOps {

    @Test(description = "application login confirmation")
    public void test01_loginToEMR_withTestUser() {
        WebFlows.login("test", "Te231121");
        Verifications.isElementDisplay(chooseRole.list.get(0));
    }

    @Test(description = "application login with doctor role confirmation")
    public void test02_loginToEMR_withDoctorRole() {
        WebFlows.login("", "");
        Verifications.isElementDisplay(patientsList.list_patients.get(0));
    }

    @Test(description = "successful medication addition")
    public void test03_addMedicationToPatient() {
        WebFlows.login('d');
        //except :  מופיע רשימת מטופלים של מחלקה דיפולטיבית
        //entering to patient chart
        WebFlows.patientBoxEntry(1);
        // click on add drug button
        doctorFlows.newDrug();
        // fill all mandatory fields to add drug daily and click the button 'add and close'
        doctorFlows.drugFormAddDrugDaily("TAB ALPRAZolam 0.5mg (ALPRALID)", null, 1, null, false, false, true);
        // doctor's signature on the added or updated instructions
        doctorFlows.approvalInstruction();
        Verifications.isElementDisplay(doctorInstructionPage.title);
    }

    @Test(description = "successful medication approval")
    public void test04_approvalMedicationToPatient() throws InterruptedException {
        WebFlows.login('n');
        //except :  מופיע רשימת מטופלים של מחלקה דיפולטיבית
        //entering to patient chart
        WebFlows.patientBoxEntry(1);
        //except : מופיע מסך אישור הוראות
        // approval the medication - select hour and click 'אישור'
        nurseFlows.approvalDrugsDaily(1, false);
        ////todo להוסיף המתנה
        Verifications.isElementDisplay(approvalInstructionPage.possibilityDescDrug.get(0));
    }

    @Test(description = "successful medication execute")
    public void test05_executeMedicationToPatient() throws InterruptedException {
        WebFlows.login('n');
        //except :  מופיע רשימת מטופלים של מחלקה דיפולטיבית
        //entering to patient chart
        WebFlows.patientBoxEntry(1);
        //except : מופיע מסך קרדקס
        // execute the medication - put a checkmark in the box
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
        nurseFlows.executionNurseSign();
         //todo check if V appears
        Verifications.textIsContains(cardexPage.btn_approval, "0");
    }

    //todo לעשות בדיקה לכול המחלקות כרגע שליפה עבור פנימית ב' (ברירת מחדל)

//    @Test(description = "Verify count of patients list is mach to DB")
//    public void test06_verifyListPatientsCount() {
//     //   String query = "SELECT * FROM dbo.admission WHERE k_yechida_shichrur=10012 AND tarich_shichrur IS NULL";
//       // GeneralWithDBFlow.loginWithDB();
//      //  int patientCount = DBAction.getCountRows(query);
//        int x = 30;
//        WebFlows.login('d');
//        //todo כרגע size()-1 בגלל שהשורות ברשימת מטופלים מחזיר+1 כולל הכותרת
//        Verifications.numberOfElement(patientsList.list_patients.size() - 1, x);
//    }

    @Test(description = "discharged patients list appears")
    public void test07_verifyLisDischargedPatientsIsDisplayed() throws InterruptedException {
        WebFlows.login('d');
        UIActions.click(mainMenuPage.category_dischargedList);
        Verifications.isDisplay(dischargedListPage.discharged_body);

    }
}
