package pages;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;

@Slf4j
public class ChooseDepartmentListPage {

  
    private By departmentList = By.xpath("//ngb-typeahead-window[contains(@id,'ngb-typeahead')]/button");

    private By btn_li_department = By.xpath("//input[@id='dropdownBasic1']");

    /**
     * Opens the department dropdown and selects the given department name.
     * @param departmentName the visible text of the department to select
     */
    public void selectDepartment(String departmentName) {
        log.info("Selecting department: {}", departmentName);
        UIActions.click(btn_li_department);
        UIActions.selectFromList(departmentList, departmentName);
    }

    
}
