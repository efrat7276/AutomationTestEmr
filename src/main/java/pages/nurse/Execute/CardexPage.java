package pages.nurse.Execute;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pages.BasePage;

import java.util.List;

public class CardexPage extends BasePage {

    @FindBy(how = How.XPATH , using =  "//div[@class='oneDayLine col-sm-12 row blue-color']//button[@class='indication']/i")
    public WebElement arrowForward;


    //just for test user
    @FindBy(how = How.XPATH , using = "//div[@class='row oneDrugLine']/div/div/div[2]")
    public List< WebElement> topCpoeInCardex ;

 // inputList to execute drugs

    private final By checkBoxListDrug = By.xpath("//input[contains(@id, 'drugInsDayModeCheckbox')]");
//
private final By btn_approval = By.id("btnApproval");

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

    @FindBy(how = How.XPATH , using = "//input[contains(@id, 'drugInsDayModeCheckbox')][not(@disabled)]/parent::label/parent::div/parent::div/parent::div//following-sibling::div//div/button/i")
    public List<WebElement> drug_popover_execArrowList;


    @FindBy(how = How.XPATH , using = "//input[contains(@id, 'solExec')][not(@disabled)]/parent::label/parent::div/parent::div/parent::div//following-sibling::div//div/button/i")
    public List<WebElement> sol_popover_execArrowList;


    @FindBy(how = How.XPATH , using = "//input[contains(@id, 'generalInsDayModeCheckbox')][not(@disabled)]/parent::label/parent::div/parent::div/parent::div//following-sibling::div//div/button/i")
    public List<WebElement> general_popover_execArrowList;


    @FindBy(how = How.XPATH , using = "//input[contains(@id, 'bloodExec')][not(@disabled)]/parent::label/parent::div/parent::div/parent::div//following-sibling::div//div/button/i")
    public List<WebElement> blood_popover_execArrowList;


 // popover execute
//
    @FindBy(how = How.XPATH , using = "//div[@class='lineRow custom-control custom-checkbox heb-rtl input-group'][2]")
    public WebElement checkboxVInput;

    @FindBy(how = How.ID , using = "checkbox2")
    public WebElement checkboxXInput;

    @FindBy(how = How.XPATH , using = "//input[@id='supervisionCbox']/following-sibling::label")
    public WebElement inputSupervision;

    @FindBy(how = How.XPATH , using = "//*[contains(@id,'ngb-popover')]//button[1]")
    public WebElement btn_ok;

    @FindBy(how = How.XPATH , using = "//*[contains(@id,'ngb-popover')]//button[2]")
    public WebElement btn_cancle;

//    @FindBy(how = How.NAME , using = "notGivenCbox")
//    public WebElement inputNotGiven;
//
//    @FindBy(how = How.NAME , using = "given")
//    public WebElement inputGiven;

    @FindBy(how = How.XPATH , using = "//execution-popover//p-dropdown")
    public WebElement dropDownReason;

    @FindBy(how = How.XPATH , using = "//execution-popover//p-dropdownitem")
    public List<WebElement> reasonList;

//    @FindBy(how = How.XPATH , using = "//execution-popover//input")
//    public WebElement comment;

//    @FindBy(how= How.ID , using = "btnApproval")
//    public WebElement btn_approval;

    @FindBy(how = How.XPATH , using = "//user-and-password//input[1]")
    public WebElement userName_input;

    @FindBy(how = How.XPATH , using = "//user-and-password//input[2]")
    public WebElement userSign_input;
// daily report

    @FindBy(how = How.XPATH , using = "//div[contains(@class,'oneDayLineIcon cursorPointer')]//i")
    public WebElement dailyReportIcon;


 // button to exit from cardex page

    @FindBy(how= How.XPATH, using = "//i[contains(@class ,'icon ion-ios-arrow-back toggle-bt-off')]")
    public WebElement i_arrow;


// print stickers

    @FindBy(how= How.XPATH, using = "//button[text()='הדפסת מדבקות']")
    public WebElement btn_printStickers;



    @FindBy(how= How.XPATH, using = "//app-pdf-modal/div[1]/button/span")
    public WebElement exit_printStickers;


// after execute

    @FindBy(how = How.XPATH , using = "//img[@src='assets/images/cardexIcons/checkmark-24.png']")
    public List<WebElement> drugsSignGaveList;


    public void executeAllDrugsToThisShift(){

        List<WebElement> allCheckBoxDrug = UIActions.findElementsWithWait(checkBoxListDrug);
        for (int i=0 ; i<allCheckBoxDrug.size(); i++){
           allCheckBoxDrug.get(i).click();
        }


    }

    public void approvalAllExecution(String username, String password){
        UIActions.click(btn_approval);
        userSignModalPage.signModal(username,password);

    }

}
