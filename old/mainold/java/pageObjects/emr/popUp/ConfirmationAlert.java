package pageObjects.emr.popUp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ConfirmationAlert {

    @FindBy(how= How.ID , using = "buttonImport")
    public WebElement btnOk;

    @FindBy(how= How.ID , using = "buttonCancle")
    public WebElement btnCancel;



}
