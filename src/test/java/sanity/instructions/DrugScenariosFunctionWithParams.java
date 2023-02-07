package sanity.instructions;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import utilities.ManageDDT;
import workflows.WebFlows;
import workflows.db.GeneralWithDBFlow;
import workflows.doctor.doctorFlows;


import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Listeners(utilities.Listeners.class)
public class DrugScenariosFunctionWithParams extends CommonOps {

   private static final String doctor_role = "רופא";
   private static final String nurse_role = "אחות מוסמכת";
//
//    private static final String user = "test";
//    private static final String password = "Te081219";


    @Test(description = "verify drugs daily added to patient's instructions parameters")
    @Description("verify adding daily drugs to patient from parameters")
    public void AddAndSavaDrugsWithParameters(String patientIndex , String[] drugDescriptionList , int[] numberOfTimeList, @Nullable String[] dosage , @Nullable String[] routeAdministration ) {

        GeneralWithDBFlow.loginWithDB();
        WebFlows.chooseRole("רופא");
        WebFlows.patientBoxEntry(patientIndex);
        doctorFlows.newDrug();
        for (int i=0; i<drugDescriptionList.length ; i++) {
            doctorFlows.drugFormAddDrugDaily(drugDescriptionList[i],  2 ,2 , null , false, false, false);
        }
        UIActions.click(drugForm.btn_undo);
        doctorInstructionPage.btn_approvalDrug.click();
        WebFlows.userSignConfirm();
        Verifications.textIsContains(doctorInstructionPage.span_numberForApproval, "0");
    }


    @Test(description = "verify drugs daily added to patient's instructions parameters")
    @Description("verify adding daily drugs to patient from parameters")
    public void LiquidationDrugsWithParameters(int patient_num) throws InterruptedException {

//
//        GeneralWithDBFlow.loginWithDB();
//        WebFlows.chooseRole("אחות מוסמכת");
//        WebFlows.patientBoxEntry(patient_num);
//        for (int i=0; i<numberOfTimeList.size() ; i++) {
//            nurseFlows.approvalDailyDrugs(countOfUl , numberOfTimeList.get(i), i);
//            countOfUl += numberOfTimeList.get(i);
//
//        }
//        approvalInstructionPage.btn_approvalDrug.click();
//        WebFlows.userSignConfirm();
//        Verifications.isElementDisplay(approvalInstructionPage.span_numberForApproval);
    }

    @Test
    public void executeLiquidationDrugsWithParameters() throws InterruptedException {
        List<String> drugsNameList = new ArrayList<String>();
        drugsNameList.add("TAB QUEtiapine fumarate 100mg");
        drugsNameList.add("TAB acarbose 50mg");

        List<Integer> numberOfTimesList = new ArrayList<Integer>();
        numberOfTimesList.add(1);
        numberOfTimesList.add(1);
        numberOfTimesList.add(1);
     //   LiquidationDrugsWithParameters(1, drugsNameList, numberOfTimesList, null, null );
    }



}
