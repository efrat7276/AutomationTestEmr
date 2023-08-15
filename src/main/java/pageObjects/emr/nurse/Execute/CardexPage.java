package pageObjects.emr.nurse.Execute;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class CardexPage {

    @FindBy(how = How.XPATH , using = "//div[@class=row oneDrugLine]")
    public List<WebElement> listDrug;


    //just for test user
    @FindBy(how = How.XPATH , using = "//div[@class='row oneDrugLine']/div/div/div[2]")
    public List< WebElement> topCpoeInCardex ;

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

    @FindBy(how= How.ID , using = "btnApproval")
    public WebElement btn_approval;

    @FindBy(how = How.XPATH , using = "//user-and-password//input[1]")
    public WebElement userName_input;

    @FindBy(how = How.XPATH , using = "//user-and-password//input[2]")
    public WebElement userSign_input;



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

// history - icons

   @FindBy(how=How.CLASS_NAME, using = "fa fa-history")
   public List<WebElement> icon_historyList ;

}
