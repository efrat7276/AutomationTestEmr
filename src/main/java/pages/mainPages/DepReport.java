package pages.mainPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DepReport {

    @FindBy(how = How.XPATH ,using="//app-dep-report")
    public WebElement depReport_body;


}
