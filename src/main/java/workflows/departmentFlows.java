package workflows;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import utilities.CommonOps;

public class departmentFlows extends CommonOps {

    @Step("choose department")
    public static void chooseDepartment(String dep) throws InterruptedException {

        //Thread.sleep(1000);
        UIActions.click(chooseDepartmentListPage.btn_li_department);

        for (int i = 0; i < chooseDepartmentListPage.departmentList.size(); i++) {
            if (chooseDepartmentListPage.departmentList.get(i).getText().contains(dep)) {
                UIActions.click(chooseDepartmentListPage.departmentList.get(i));
                return;
            }
        }
        Thread.sleep(3000);
    }
    }

