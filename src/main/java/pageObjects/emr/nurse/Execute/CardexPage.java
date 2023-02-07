package pageObjects.emr.nurse.Execute;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class CardexPage {

 // inputList to execute drugs

    @FindBy(how = How.XPATH , using = "//input[contains(@id, 'drugInsDayModeCheckbox')]")
    public List<WebElement> checkBoxListDrug;

    // inputList to execute generalIns

    @FindBy(how = How.XPATH , using = "//input[contains(@id, 'generalInsDayModeCheckbox')]")
    public List<WebElement> checkBoxListGeneralIns;

 // inputList to execute solutions

    @FindBy(how = How.XPATH , using = "//input[contains(@id, 'solExec')][not(@disabled)]")
    public List<WebElement> checkBoxListSol;

// inputList to execute bloodProduct

    @FindBy(how = How.XPATH , using = "//input[contains(@id, 'bloodExec')][not(@disabled)]")
    public List<WebElement> checkBoxListBlood;


// arrowList to open popover exec

    @FindBy(how = How.XPATH , using = "//*[@id='Div1']/div/button/i")
    public List<WebElement> popover_execArrowList;



 // popover execute

    @FindBy(how = How.XPATH , using = "//input[@id='supervisionCbox']/following-sibling::label")
    public WebElement inputSupervision;

    @FindBy(how = How.XPATH , using = "//*[contains(@id,'ngb-popover')]//button[1]")
    public WebElement btn_ok;

    @FindBy(how = How.XPATH , using = "//*[contains(@id,'ngb-popover')]//button[2]")
    public WebElement btn_cancle;

    @FindBy(how = How.NAME , using = "notGivenCbox")
    public WebElement inputNotGiven;

    @FindBy(how = How.NAME , using = "given")
    public WebElement inputGiven;


    @FindBy(how= How.ID , using = "btnApproval")
    public WebElement btn_approval;


 // button to exit from cardex page

    @FindBy(how= How.XPATH, using = "//i[contains(@class ,'icon ion-ios-arrow-back toggle-bt-off')]")
    public WebElement i_arrow;




// after execute

    @FindBy(how = How.XPATH , using = "//img[@src='assets/images/cardexIcons/checkmark-24.png']")
    public List<WebElement> drugsSignGaveList;

}
