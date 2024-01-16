package sanity.instructions;
import com.google.common.util.concurrent.Uninterruptibles;
import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import org.apache.commons.lang3.ObjectUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.emr.DoctorInstructionPage;
import utilities.CommonOps;
import utilities.ManageDDT;
import utilities.ManageDDT;
import workflows.NavigateFlows;
import workflows.WebFlows;
import workflows.db.GeneralWithDBFlow;
import workflows.doctor.doctorFlows;
import workflows.nurse.nurseFlows;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Listeners(utilities.Listeners.class)
public class DrugScenarios extends CommonOps {

   private static final String doctor_role = "רופא";
   private static final String nurse_role = "אחות מוסמכת";

    private static final String user = "test";
    private static final String password = "Te081219";

//
//
//
//    @Test(description = "Verify Click on AddDrug Button" , dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"UI"})
//    @Description("drugForm - verify input 'Select Drug' is display in drugForm")
//    public void test01_verify_AddDrugFormPage_inputSelectDrugIsEmpty(int patient_num) {
//
//        WebFlows.login(user,password);
//        WebFlows.chooseRole("רופא");
//        WebFlows.patientBoxEntry(patient_num);
//        doctorFlows.newDrug();
//        Verifications.isElementDisplay(drugForm.inp_selectDrug);
//    }
//
//    @Test(description = "Verify Fulled Field After Selection Drug" , dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"UI"})
//    @Description("drugForm - verify field 'drug dosage' is full the selected drug's dosage")
//    public void test02_verify_AddDrugFormPage_AfterSelectDrugTheDosageFieldIsFull(int patient_num) {
//
//        WebFlows.login(user,password);
//        WebFlows.chooseRole("רופא");
//        WebFlows.patientBoxEntry(patient_num);
//        doctorFlows.newDrug();
//        UIActions.updateText(drugForm.inp_selectDrug , "TAB QUEtiapine fumarate 100mg (SEROQUEL)");
//        drugForm.inp_selectDrugTopList.click();
//        Verifications.textInputIsNotEmpty(drugForm.input_drugDosage);
//        // 2 וראציות נוספות לבדיקות
//         //  Verifications.textInputIsNotEmpty(drugForm.input_unitMeasure);
//        //Verifications.textIsDifferentFrom(drugForm.span_btn_dropdownRouteAdministration , "Select");
//    }

//    @Test(description = "Verify additions 5 drugs daily to Patient", dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"})
//    @Description("drugForm - additions 5 drugs daily to Patient")
//    public void test03_1_verifyAddDailyDrugListToPatient(int patient_num){
//
//        String str; List<String> details;
//        WebFlows.login(user, password, doctor_role);
//        WebFlows.patientBoxEntry(patient_num);
//        doctorFlows.newDrug();
//        for(int i=0; i<5 ;i++) {
//            str = ManageDDT.getLineFromCSV("./DDTFiles/drugsDailyNew.csv", i);
//            details = Arrays.asList(str.split(","));
//            doctorFlows.drugFormAddDrugDaily(details.get(1), 1, details.get(2), details.get(3), false);
//        }
//        doctorFlows.clickReturnAndApproval();
//        WebFlows.userSignConfirm();
//    }
//
//    @Test(description = "verify a doctor's signature on unsigned instruction ", dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"} )
//    @Description("a doctor signs on doctor's instruction")
//    public void  test03_2_NurseApprovalAllAndExecuteDailyDrug(int patient_num){
//
//        GeneralWithDBFlow.loginWithDB();
//        WebFlows.chooseRole("אחות מוסמכת");
//        WebFlows.patientBoxEntry(patient_num);
//        nurseFlows.approvalOnceOnlyDrugs(5);
//        UIActions.click(innerMenuPage.category_cardex);
//        nurseFlows.CardexPage_executeOnceOnlyDrugs();
//   }



    @Test(description = "Verify additions 5 drugs daily to Patient", dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"})
    @Description("drugForm - additions 5 drugs daily to Patient")
    public void test03_1_verifyAddDailyDrugListToPatient(int patient_num){

        String str; List<String> details;
        WebFlows.login('d');
        WebFlows.patientBoxEntry(patient_num);
        doctorFlows.newDrug();
        for(int i=0; i<5 ;i++) {
            str = ManageDDT.getLineFromCSV("./DDTFiles/drugsDailyNew.csv", i);
            details = Arrays.asList(str.split(","));
            doctorFlows.drugFormAddDrugDaily(details.get(1), 3,4, details.get(2),  false,false,false);
        }
        doctorFlows.clickReturnAndApproval();
        WebFlows.userSignConfirm();
   }

    @Test(description = "verify a doctor's signature on unsigned instruction ", dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"} )
    @Description("a doctor signs on doctor's instruction")
    public void  test03_2_NurseApprovalAllAndExecuteDailyDrug(int patient_num) throws InterruptedException {

        GeneralWithDBFlow.loginWithDB();
        WebFlows.chooseRole("אחות מוסמכת");
        WebFlows.patientBoxEntry(patient_num);
        nurseFlows.approvalDrugDailyList(5 , 3);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
   }

    @Test(description = "Verify additions 3 drugs daily Infectious_Disease_Specialto to Patient" , dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"})
    @Description("drugForm - additions 3 drugs daily Infectious_Disease_Specialto Patient")
    public void test04_1_verifyAddDrugInfectiousDiseaseSpecialListToPatient(int patient_num) {

        String str; List<String> details;
        WebFlows.login('d');
        WebFlows.patientBoxEntry(patient_num);
        doctorFlows.newDrug();
        for(int i=0; i<3 ;i++) {
            str = ManageDDT.getLineFromCSV("./DDTFiles/DrugsInfectiousDiseaseSpecial.csv", i);
            details = Arrays.asList(str.split(","));
            doctorFlows.drugFormAddDrugDaily(details.get(1), 60,1, details.get(2),true, true,false);
        }
        doctorFlows.clickReturnAndApproval();
        WebFlows.userSignConfirm();
    }

    public void test04_2_(int patient_num){

        // אישור זיהומולוג
        GeneralWithDBFlow.loginWithDB();
        WebFlows.chooseRole("אחות מוסמכת");
        WebFlows.patientBoxEntry(patient_num);

    }

    @Test(description = "verify a doctor's signature on unsigned instruction ", dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"} )
    @Description("a doctor signs on doctor's instruction")
    public void  test04_3_NurseApprovalAllAndExecuteDailyInfectiousDiseaseSpecialDrug(int patient_num) throws InterruptedException {

        GeneralWithDBFlow.loginWithDB();
        WebFlows.chooseRole("אחות מוסמכת");
        WebFlows.patientBoxEntry(patient_num);
        nurseFlows.approvalDrugOnceOnlyList(3);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
    }

    @Test(description = "Verify additions 3 drugs daily As Risk to Patient" , dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"})
    @Description("drugForm - additions 3 drugs daily As Risk to Patient")
    public void test05_1_verifyAddDrugAsRiskListToPatient(int patient_num){

        String str; List<String> details;
        WebFlows.login('d');
        WebFlows.patientBoxEntry(patient_num);
        doctorFlows.newDrug();
        for(int i=0; i<2 ;i++) {
            str = ManageDDT.getLineFromCSV("./DDTFiles/drugsDailyDrugAtRiskNew.csv", i);
            details = Arrays.asList(str.split(","));
            doctorFlows.drugFormAddDrugDaily(details.get(1), 20,1, details.get(3), false,false, true);
        }
        doctorFlows.clickReturnAndApproval();
        WebFlows.userSignConfirm();
    }


    @Test(description = "Verify additions 3 drugs daily As Risk to Patient" , dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"})
    public void test05_2_NurseApprovalAllAndExecuteAsRiskDrug(int patient_num) throws InterruptedException {

        GeneralWithDBFlow.loginWithDB();
        WebFlows.chooseRole("אחות מוסמכת");
        WebFlows.patientBoxEntry(patient_num);
        nurseFlows.approvalDrugOnceOnlyList(2);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
    }

     @Test(description = "Verify additions 2 sos drugs to Patient" , dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"})
    @Description("drugForm - additions 2 SOS drugs to Patient")
    public void test06_1_verifyAddDrugSOSListToPatient(int patient_num){

        String str; List<String> details;
        WebFlows.login('d');
        WebFlows.patientBoxEntry(patient_num);
        doctorFlows.newDrug();
        for(int i=0; i<2 ;i++) {
            str = ManageDDT.getLineFromCSV("./DDTFiles/drugsSOS.csv", i);
            details = Arrays.asList(str.split(","));
            doctorFlows.drugFormAddDrugSOS(details.get(1), details.get(2), details.get(3),4,5,false);
        }
        doctorFlows.clickReturnAndApproval();
        WebFlows.userSignConfirm();
    }

    @Test(description = "verify a doctor's signature on unsigned instruction ", dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"} )
    @Description("a doctor signs on doctor's instruction")
    public void  test06_2_NurseApprovalAllAndExecuteSOSDrug(int patient_num) throws InterruptedException {

        GeneralWithDBFlow.loginWithDB();
        WebFlows.chooseRole("אחות מוסמכת");
        WebFlows.patientBoxEntry(patient_num);
        nurseFlows.approvalSOSDrugList();
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();

    }

    //
    @Test(description = "Verify addition 1 unknown drugs to Patient" , dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"})
    @Description("drugForm - additions 1 unknown drug to Patient")
    public void test07_1_verifyAddAndSaveUnknownDrug()  {

        WebFlows.login('d');
        WebFlows.patientBoxEntry(1);
        doctorFlows.newDrug();
        doctorFlows.drugFormAddUnknownDrug(true);
        doctorFlows.approvalInstruction();

        //Verifications.isElementDisplay(doctorInstructionPage.btns_addDrug.get(0));
    }

    @Test(description = "verify a doctor's signature on unsigned instruction ", dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"} )
    @Description("a doctor signs on doctor's instruction")
    public void  test07_2_NurseApprovalAllAndExecuteDailyDrug(int patient_num) throws InterruptedException {

        GeneralWithDBFlow.loginWithDB();
        WebFlows.chooseRole("אחות מוסמכת");
        WebFlows.patientBoxEntry(patient_num);
        nurseFlows.approvalDrugOnceOnlyList(1);
        NavigateFlows.goToCategory("cardex");
        nurseFlows.executeAllToCurrentHourFor_daily_onceOnly_sos_weekly_byHourAfterApprovalNurse();
    }



//    @Test(description = "verify a doctor's signature on unsigned instruction ", dataProvider = "patients" , dataProviderClass = ManageDDT.class , groups = {"sanity"} )
//    @Description("a doctor signs on doctor's instruction")
//    public static void NurseApprovalAll(int patient_num){
//
//        GeneralWithDBFlow.loginWithDB();
//        WebFlows.chooseRole("אחות מוסמכת");
//        WebFlows.patientBoxEntry(patient_num);
//        nurseFlows.approvalOnceOnlyDrugs(4);
//    }

}
