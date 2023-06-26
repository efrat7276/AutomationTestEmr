package pageObjects.emr.mainPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class OperationsPage {

    @FindBy(how = How.XPATH ,using="//operations")
    public WebElement operations_body;


}
