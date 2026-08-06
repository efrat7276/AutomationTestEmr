package pages.nurse.Execute;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pages.BasePage;

import java.util.List;

@Slf4j
public class UpdateExecutionPage extends BasePage {
    
    public UpdateExecutionPage() {
        UIActions.waitForSpinnerToDisappear();
    }


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





