package workflows.doctor;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Step;
import pageObjects.emr.ChooseDepartmentListPage;
import utilities.CommonOps;

public class chooseDepartmentFlow extends CommonOps {


    @Step(" choose department")
    public static void chooseDepartment(String departmentName) throws InterruptedException {


//
//        Thread.sleep(3000);
//        UIActions.click(patientsList.btn_listDepartment);
//        for(int i=0; i< chooseDepartmentListPage.li_department.size();i++){
//           if(chooseDepartmentListPage.li_department.get(i).getText().equals(departmentName)) {
//               UIActions.click(chooseDepartmentListPage.li_department.get(i));
//               UIActions.click(chooseDepartmentListPage.btn_ok);
//               break;
//           }
//
//        }
    }
}
