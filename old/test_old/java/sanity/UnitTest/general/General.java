package sanity.UnitTest.general;

import actionUtilies.Verifications;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import utilities.Helpers;
import workflows.WebFlows;


@Listeners(utilities.Listeners.class)
public class General extends CommonOps {


    String doctor_role = "רופא";

  //  @Test(description = "Verify Logo Image")
  //  @Description("verify logo image Using Sikuli")
//    public void test06_verifyLogo() {
//      //  String file_name = "aaaaa";
//     //   System.out.println("./DDTFiles/tests/"+file_name+".csv");
//        Verifications.visualElement("logo.JPG");
//    }

    @Test(description = "Verify List Roles")
    @Description("login with db and verify list roles - how many roles in list")
    public void test01_verifyListRoles(){
       // GeneralWithDBFlow.loginWithDB();
        WebFlows.login(Helpers.getData("userTest"), Helpers.getData("userPassword"));
        Verifications.numberOfElementList(chooseRole.list , 6);
    }

    @Test(description = "Verify changing department")
    @Description("login with db and verify changing department")
    public void test02_verifyChooseDepartment() throws InterruptedException {
        WebFlows.login( 'd');
        //chooseDepartmentFlow.chooseDepartment("פנימית א");
        //Verifications.textIsVisible(patientsList.span_nameDepartment, "פנימית א");
        Verifications.isElementDisplay(patientsList.menu_patientList);

    }
//
//    //todo לעשות בדיקה לכול המחלקות כרגע שליפה עבור פנימית ב' (ברירת מחדל)
//    @Test(description = "Verify count of patients list")
//    @Description("login with db and verify count of patients list")
//    public void test09_verifyListPatients(){
//        String query = "SELECT * FROM dbo.admission WHERE k_yechida_shichrur=10012 AND tarich_shichrur IS NULL";
//
//     //   GeneralWithDBFlow.loginWithDB();
//        UIActions.selectFromList(chooseRole.list , "רופא");
        //    WebFlows.login(CommonOps.getData("userTest"), CommonOps.getData("userPassword"), doctor_role);

//        //todo כרגע size()-1 בגלל שהשורות ברשימת מטופלים מחזיר+1 כולל הכותרת
//        Verifications.numberOfElement( patientsList.list_patients.size()-1 , DBAction.getCountRows(query));
//    }
//
//
//
//    @Test(description = "go to screen")
//    public void goToScreen() throws InterruptedException {
//      //  GeneralWithDBFlow.loginWithDB();
//      //  WebFlows.chooseRole("אחות מוסמכת");
//        //        WebFlows.login(CommonOps.getData("userTest"), CommonOps.getData("userPassword"), doctor_role);
//        WebFlows.patientBoxEntry("2021159500");
//        UIActions.click(cardexPage.i_arrow);
//        NavigateFlows.goToCategory("nurseConfirmation");
//        NavigateFlows.goToSubCategory("updateExecution");
//        Verifications.isElementDisplay(updateExecutionPage.datePicker);
//    }

}
