package workflows;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Step;
import utilities.CommonOps;

public class departmentFlows extends CommonOps {

    @Step("choose department")
    public static void chooseDepartment(String dep) throws InterruptedException {

        UIActions.click(chooseDepartmentListPage.btn_li_department);
//        for (int i = 0; i < chooseDepartmentListPage.departmentList.size(); i++) {
//            if (chooseDepartmentListPage.departmentList.get(i).getText().contains(dep)) {
//                UIActions.click(chooseDepartmentListPage.departmentList.get(i));
//            }
//        }
        chooseDepartmentListPage.departmentList.stream().forEach(i ->
        {
            if(i.getText().contains(dep))
                {
                    i.click();
                }
        });
        try {
            Thread.sleep(6000);
        }
        catch (InterruptedException e){

        }
        Verifications.isElementDisplay(patientsList.menu_patientList);
    }
    }

