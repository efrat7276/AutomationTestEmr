package pages.nurse.Execute;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.UserSignModalPage;

import java.util.List;

public class CardexPage extends BasePage {

    UserSignModalPage userSignModalPage;
    public CardexPage() {
        super();
        userSignModalPage = new UserSignModalPage();
     }
    // Navigation arrow to inner menu
    private final By arrowForward = By.xpath("//div[@class='oneDayLine col-sm-12 row blue-color']//button[@class='indication']/i");

    // Just for test user - cells in cardex rows
    private final By topCpoeInCardex = By.xpath("//div[@class='row oneDrugLine']/div/div/div[2]");

    // inputList to execute drugs
    private final By checkBoxListDrug = By.xpath("//input[contains(@id, 'drugInsDayModeCheckbox')]");
    private final By btn_approval = By.id("btnApproval");

    // inputList to execute generalIns
    private final By checkBoxListGeneralIns = By.xpath("//input[contains(@id, 'generalInsDayModeCheckbox')]");

    // inputList to execute solutions
    private final By checkBoxListSol = By.xpath("//input[contains(@id, 'solExec')][not(@disabled)]");

    // inputList to execute bloodProduct
    private final By checkBoxListBlood = By.xpath("//input[contains(@id, 'bloodExec')][not(@disabled)]");

    // arrowList to open popover exec
    private final By drug_popover_execArrowList = By.xpath("//input[contains(@id, 'drugInsDayModeCheckbox')][not(@disabled)]/parent::label/parent::div/parent::div/parent::div//following-sibling::div//div/button/i");
    private final By sol_popover_execArrowList = By.xpath("//input[contains(@id, 'solExec')][not(@disabled)]/parent::label/parent::div/parent::div/parent::div//following-sibling::div//div/button/i");
    private final By general_popover_execArrowList = By.xpath("//input[contains(@id, 'generalInsDayModeCheckbox')][not(@disabled)]/parent::label/parent::div/parent::div/parent::div//following-sibling::div//div/button/i");
    private final By blood_popover_execArrowList = By.xpath("//input[contains(@id, 'bloodExec')][not(@disabled)]/parent::label/parent::div/parent::div/parent::div//following-sibling::div//div/button/i");

    // popover execute
    private final By checkboxVInput = By.xpath("//div[@class='lineRow custom-control custom-checkbox heb-rtl input-group'][2]");
    private final By checkboxXInput = By.id("checkbox2");
    private final By inputSupervision = By.xpath("//input[@id='supervisionCbox']/following-sibling::label");
    private final By btn_ok = By.xpath("//*[contains(@id,'ngb-popover')]//button[1]");
    private final By btn_cancle = By.xpath("//*[contains(@id,'ngb-popover')]//button[2]");

    private final By dropDownReason = By.xpath("//execution-popover//p-dropdown");
    private final By reasonList = By.xpath("//execution-popover//p-dropdownitem");

    private final By userName_input = By.xpath("//user-and-password//input[1]");
    private final By userSign_input = By.xpath("//user-and-password//input[2]");

    // daily report
    private final By dailyReportIcon = By.xpath("//div[contains(@class,'oneDayLineIcon cursorPointer')]//i");

    // button to exit from cardex page
    private final By i_arrow = By.xpath("//i[contains(@class ,'icon ion-ios-arrow-back toggle-bt-off')]");

    // print stickers
    private final By btn_printStickers = By.xpath("//button[text()='הדפסת מדבקות']");
    private final By exit_printStickers = By.xpath("//app-pdf-modal/div[1]/button/span");

    // after execute
    private final By drugsSignGaveList = By.xpath("//img[@src='assets/images/cardexIcons/checkmark-24.png']");

    public void executeAllDrugsToThisShift(){
        List<WebElement> allCheckBoxDrug = UIActions.findElementsWithWait(checkBoxListDrug);
        for (WebElement el : allCheckBoxDrug){
            el.click();
        }
    }

    public void approvalAllExecution(String username, String password){
        UIActions.click(btn_approval);
        userSignModalPage.signModal(username,password);
    }

    public void clickArrowForwardToInnerMenu() {
        UIActions.click(i_arrow);
    }

    public void printIVLabelForFirstFluidInCardex() {
       UIActions.click(arrowForward);
    }

}
