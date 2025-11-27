package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;


public class ChooseDepartmentListPage {

    @FindBy(how= How.XPATH , using = "//ngb-typeahead-window[contains(@id,'ngb-typeahead')]/button/ngb-highlight")
    public List<WebElement> departmentList;

    @FindBy(how= How.XPATH , using = "//input[@id='dropdownBasic1']")
    public WebElement btn_li_department;

}
