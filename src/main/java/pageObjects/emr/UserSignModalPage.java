package pageObjects.emr;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UserSignModalPage {

    @FindBy(how= How.ID, using = "user")
    public WebElement input_userName;

    @FindBy(how= How.ID, using = "password")
    public WebElement input_password;

    @FindBy(how= How.XPATH, using = "//form//button[2]")
    public WebElement btn_confirm;

}
