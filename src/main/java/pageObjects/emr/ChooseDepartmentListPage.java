package pageObjects.emr;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;


public class ChooseDepartmentListPage {

    @FindBy(how= How.XPATH , using = "//ngb-typeahead-window//button")
    public List<WebElement> departmentList;

    @FindBy(how= How.ID , using = "dropdownBasic1")
    public WebElement btn_li_department;

    @FindBy(how= How.XPATH , using = "//button[@class='btn btn-ok']")
    public WebElement btn_ok;

    @FindBy(how= How.XPATH , using = "//button[@class='btn btn-secondary']")
    public WebElement btn_cancel;

    @FindBy(how= How.XPATH , using = "//div[@class='patientsIcon']//span")
    public WebElement box_patientNumber;
}
