package pages.nurse.Execute;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class UpdateExecutionPage {


    @FindBy(how = How.TAG_NAME, using = "emr-datepicker")
    public WebElement datePicker;


    @FindBy(how = How.XPATH, using = "//p-table/div[@class='ui-table ui-widget']")
    public WebElement tableDrugExecuted;

    @FindBy(how = How.XPATH, using = "//p-table/div[@class='ui-table ui-widget']//tbody/tr")
    public WebElement rowDrugExecuted;

    @FindBy(how = How.XPATH, using = "//p-table/div[@class='ui-table ui-widget']//td[7]//button")
    public List<WebElement> btn_updateExecList;

    @FindBy(how = How.XPATH, using = "//p-table/div[@class='ui-table ui-widget']//execution-popover//button")
    public List<WebElement> btn_iconExecList;
}





