package pages.nurse.Execute;
import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.checkerframework.checker.guieffect.qual.UI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.UserSignModalPage;
@Slf4j
public class CardexPageNew extends BasePage {

    public CardexPageNew() {
        UIActions.waitForSpinnerToDisappear();
       // userSignModalPage = new UserSignModalPage();
        }
        private final By drugCheckboxes = By.xpath("//input[@type='checkbox' and starts-with(@id, 'drugInsDayModeCheckbox')]");
        private final By generalCheckboxes = By.xpath("//input[@type='checkbox' and starts-with(@id, 'generalInsDayModeCheckbox')]");
        private final By solCheckboxes = By.xpath("//input[@type='checkbox' and starts-with(@id, 'solExec')  and not(@disabled)]");
        private final By bloodCheckboxes = By.xpath("//input[@type='checkbox' and starts-with(@id, 'bloodProductInsDayModeCheckbox')]");
        private final By allShiftCheckboxes = By.xpath("//input[@type='checkbox' and contains(@id, 'InsDayModeCheckbox')] | //input[@type='checkbox' and contains(@id, 'solExec') and not(@disabled)]");


private final By nav_cardex = By.xpath("//a[@id='ngb-nav-6']");
private final By nav_instructionForApproval = By.xpath("//a[@id='ngb-nav-0']ׁׁ");



   private final By btn_approvalDrug = By.xpath("//button[contains(@class, 'btn-submit')]");
     private final By i_arrow = By.xpath("//app-patient-detail/app-inner-menu/div/div/div[1]/span/i");
    private final By stickers = By.xpath("//button[3][contains(@class, 'btn-secondary')]");
   public void executeAllDrugsToThisShift() {
        log.info("Starting execution of all drug instructions to this shift.");
        List<WebElement> drugs = driver.findElements(By.xpath("//input[@type='checkbox' and starts-with(@id, 'drugInsDayModeCheckbox')]"));
        for (WebElement el : drugs) {
            UIActions.waitForElementClickable(el);
            UIActions.click(el);
        }
    }
    public void executeAllGeneralsToThisShift(){
        log.info("Starting execution of all general instructions to this shift.");
        List<WebElement> generals = UIActions.findElementsWithWait(generalCheckboxes);
        for (WebElement el : generals){
            UIActions.waitForElementClickable(el);
            UIActions.click(el);
        }
    }
    public void executeAllSolutionsToThisShift(){
        log.info("Starting execution of all solutions to this shift.");
        List<WebElement> sols = UIActions.findElementsWithWait(solCheckboxes);
        for (WebElement el : sols){
            UIActions.waitForElementClickable(el);
            UIActions.click(el);
        }
    }
    public void executeAllBloodsToThisShift(){
        log.info("Starting execution of all blood products to this shift.");
        List<WebElement> bloods = UIActions.findElementsWithWait(bloodCheckboxes);
        for (WebElement el : bloods){
            UIActions.waitForElementClickable(el);
            UIActions.click(el);
        }
    }
    public void executeAllToThisShift(){
        log.info("Starting execution of all instructions to this shift.");
        List<WebElement> all = UIActions.findElementsWithWait(allShiftCheckboxes);
        for (WebElement el : all){
            UIActions.waitForElementClickable(el);
            UIActions.click(el);
        }
    }
  

    public void executeAndApproveAllToThisShiftAndApproval(String username, String password) {
        log.info("Starting execution and approval of all instructions to this shift.");
        executeAllToThisShift();
         UIActions.click(btn_approvalDrug);
        userSignModalPage.signModal(username, password);
        verifyExecuted();
         UIActions.waitForText(btn_approvalDrug,"0" );
    }

    public void clickNavInstructionForApproval() {
         UIActions.click(nav_instructionForApproval);
    }

    // public void clickNavCardex() {
    //      log.info("Attempting to click on nav cardex.");
    //     try {
    //         UIActions.click(navCardex);
    //         log.info("Clicked on Cardex navigation successfully.");
    //     } catch (Exception e) {
    //         log.error("Failed to click on Cardex navigation. Error: " + e.getMessage());
    //         throw new RuntimeException("Failed to navigate to Cardex", e);
    //     }
    // }
    
    public void clickArrowForwardToInnerMenu() {
        UIActions.click(i_arrow);
    }

    public void printIVLabelForFirstFluidInCardex() {
        log.info("Attempting to print IV label for the first fluid in cardex.");
        UIActions.waitForSpinnerToDisappear();
      //  UIActions.waitForElementClickable(nav_cardex);
     //  clickNavCardex();
       UIActions.waitForElementClickable(stickers);
        UIActions.click(stickers);
       if (medicinePrep.isMedicinePrepDisplayed()) {
        log.info("IV label printed successfully.");
         } else {
        log.error("Failed to print IV label.");
         }
    }

    public void verifyExecuted() {
           assertTrue(UIActions.waitForText(btn_approvalDrug,"0" ),"Expected approval button text to contain '0' but got '" + UIActions.getText(btn_approvalDrug) + "'");
    }
}
