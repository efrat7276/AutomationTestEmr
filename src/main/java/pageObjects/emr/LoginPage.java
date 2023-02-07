package pageObjects.emr;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage {

    @FindBy(how= How.ID , using = "user_name")
    public WebElement txt_username;

    @FindBy(how= How.ID , using = "password")
    public WebElement txt_password;

    @FindBy(how= How.ID , using = "submitBtn")
    public WebElement btn_submitBtn;

    @FindBy(how= How.CSS , using = "button[class='btn btn-default clear']")
    public WebElement btn_clearBtn;

    @FindBy(how= How.XPATH , using = "//form/div[2]/span")
    public WebElement spn_message;

}
