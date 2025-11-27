package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DrugConfirmation {

    @FindBy(how= How.ID , using = "confirmationKeyWithNoSelectesd")
    public WebElement confKey_inp;

    @FindBy(how= How.ID , using = "confirmationCommentDoctorTemporary")
    public WebElement comment_inp;

    @FindBy(how= How.ID , using = "confirmationCommentReceiverTemporary")
    public WebElement commentRecive_inp;

    @FindBy(how= How.ID , using = "drugConfirmationsClear")
    public WebElement clear_button;

    @FindBy(how= How.ID , using = "drugConfirmationsCancel")
    public WebElement cancel_button;

    @FindBy(how= How.ID , using = "drugConfirmationsSave")
    public WebElement save_button;

    @FindBy(how= How.ID , using = "drugConfirmationStatusID")
    public WebElement letter_buttonList;

    @FindBy(how= How.XPATH , using = "//*[@id='drugConfirmationStatusID']/following-sibling::ul/li")
    public List<WebElement> letter_List;
}
