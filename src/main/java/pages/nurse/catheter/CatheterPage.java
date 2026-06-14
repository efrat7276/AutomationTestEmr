package pages.nurse.catheter;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import actionUtilies.UIActions;
import pages.UserSignModalPage;

public class CatheterPage {

    private final By accordionCatheterAddBy = By.xpath("//drain-catheter//div/span[text()='החדרת צנתר / נקז']");
    private final By buttonCatheterListBy = By.xpath("//label[contains(text(), 'סוג צנתר/נקז')]/..//input[@role='combobox']");
    private final By catheterListBy = By.xpath("//ngb-typeahead-window[@id=//input[@role='combobox']/@aria-owns]//button[@role='option']");
    private final By catheterBranolaBy = By.xpath("//button[@role='option']//ngb-highlight[contains(text(), 'ברנולה')]/..");
    private final By sideRightBy = By.xpath("//input[@id='side-1']");
    private final By sideLeftBy = By.xpath("//input[@id='side-0']");
    private final By submitButtonBy = By.xpath("//button[contains(@class, 'btn-submit')]");
    private final By cancelButtonBy = By.xpath("//button[contains(@class, 'btn-cancel')]");
    private final By activeCatheterTableBy = By.xpath("(//table[contains(@class, 'drain-catheter')])[1]");
    private final By nonActiveCatheterTableBy = By.xpath("(//table[contains(@class, 'drain-catheter')])[2]");
    private final UserSignModalPage userSignModalPage = new UserSignModalPage();
    
    public void addCatheterBranola(String nurseUsername, String nursePassword){
        UIActions.click(accordionCatheterAddBy);
        UIActions.click(buttonCatheterListBy);
        UIActions.click(catheterBranolaBy);
        chooseSide("right");
        saveCatheter(nurseUsername, nursePassword);
        isCatheterInActiveTable("ברנולה");
    }

    public void chooseSide(String side){
        if (side == null || side.equalsIgnoreCase("right")){
            UIActions.click(sideRightBy);
        } else if (side.equalsIgnoreCase("left")){
            UIActions.click(sideLeftBy);
        }
    }

    
      public void cancelCatheterForm(){
        UIActions.click(cancelButtonBy);
    }

    private void saveCatheter(String username, String password){
        UIActions.click(submitButtonBy);
        userSignModalPage.signModal(username, password);
        UIActions.waitForSpinnerToDisappear();
        UIActions.waitForInvisibility(submitButtonBy);
    }

    public void isCatheterInActiveTable(String catheterName){
        var element = UIActions.findElementWithWait(activeCatheterTableBy);
        assertTrue(element.findElement(By.xpath(".//td[1]")).getText().contains("ברנולה"), "הצנתר ברנולה לא נמצא בטבלה של נקזים פעילים!");
    }
}
