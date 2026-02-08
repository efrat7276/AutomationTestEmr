package pages.nurse.Execute;
import actionUtilies.UIActions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;
public class CardexPageNew extends BasePage {


        private final By drugCheckboxes = By.xpath("//input[@type='checkbox' and starts-with(@id, 'drugInsDayModeCheckbox')]");
        private final By generalCheckboxes = By.xpath("//input[@type='checkbox' and starts-with(@id, 'generalInsDayModeCheckbox')]");
        private final By solCheckboxes = By.xpath("//input[@type='checkbox' and starts-with(@id, 'solExec')  and not(@disabled)]");
        private final By bloodCheckboxes = By.xpath("//input[@type='checkbox' and starts-with(@id, 'bloodProductInsDayModeCheckbox')]");
        private final By allShiftCheckboxes = By.xpath("//input[@type='checkbox' and contains(@id, 'InsDayModeCheckbox')] | //input[@type='checkbox' and contains(@id, 'solExec') and not(@disabled)]");

   private final By btn_approvalDrug = By.xpath("//button[contains(@class, 'btn-submit')]");
    
   public void executeAllDrugsToThisShift() {
        List<WebElement> drugs = driver.findElements(By.xpath("//input[@type='checkbox' and starts-with(@id, 'drugInsDayModeCheckbox')]"));
        for (WebElement el : drugs) {
            UIActions.waitForElementClickable(el);
            el.click();
        }
    }
    public void executeAllGeneralsToThisShift(){
        List<WebElement> generals = UIActions.findElementsWithWait(generalCheckboxes);
        for (WebElement el : generals){
            UIActions.waitForElementClickable(el);
            el.click();
        }
    }
    public void executeAllSolutionsToThisShift(){
        List<WebElement> sols = UIActions.findElementsWithWait(solCheckboxes);
        for (WebElement el : sols){
            UIActions.waitForElementClickable(el);
            el.click();
        }
    }
    public void executeAllBloodsToThisShift(){
        List<WebElement> bloods = UIActions.findElementsWithWait(bloodCheckboxes);
        for (WebElement el : bloods){
            UIActions.waitForElementClickable(el);
            el.click();
        }
    }
    public void executeAllToThisShift(){
        List<WebElement> all = UIActions.findElementsWithWait(allShiftCheckboxes);
        for (WebElement el : all){
            UIActions.waitForElementClickable(el);
            el.click();
        }
    }
  
  
    public void approveAll(String username, String password) {
        UIActions.click(btn_approvalDrug);
        userSignModalPage.signModal(username, password);
        UIActions.waitForVisible(btn_approvalDrug);
    }

    public void executeAndApproveAllToThisShift(String username, String password) {
        executeAllToThisShift();
        approveAll(username, password);
    }
    
}
