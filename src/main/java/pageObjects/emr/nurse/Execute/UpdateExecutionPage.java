package pageObjects.emr.nurse.Execute;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class UpdateExecutionPage {


    @FindBy(how = How.TAG_NAME , using = "emr-datepicker")
    public WebElement datePicker;
}
