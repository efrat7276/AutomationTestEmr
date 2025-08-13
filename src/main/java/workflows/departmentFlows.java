package workflows;

import extensions.UIActions;
import extensions.Verifications;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.CommonOps;

public class departmentFlows extends CommonOps {

    @Step("choose department")
    public static void chooseDepartment(String dep) throws InterruptedException {

        boolean found=false;
        UIActions.click(chooseDepartmentListPage.btn_li_department);
        for (WebElement department : chooseDepartmentListPage.departmentList) {
            if (department.getText().contains(dep)) {
                wait.until(ExpectedConditions.visibilityOf(department));
                UIActions.click(department);
                found=true;
                break; // יציאה אחרי שמצאנו ולחצנו
            }
        }
        if(!found)
            System.out.println("Department not found: " + dep);

     //   Verifications.isElementDisplay(patientsList.menu_patientList);
    }
    }

