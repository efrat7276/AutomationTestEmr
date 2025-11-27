package pageObjects.emr.mainPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DischargedListPage {

    @FindBy(how = How.XPATH ,using="//app-discharged")
    public WebElement discharged_body;


}
