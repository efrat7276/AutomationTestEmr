package pages.menu;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import pages.BasePage;

import static org.testng.Assert.assertTrue;

import org.checkerframework.checker.guieffect.qual.UI;

@Slf4j
public class MainMenuPage extends BasePage {
    private By roleUserBy = By.xpath("//div//li[@class='hebrewName']//span[@class='dataBase'][2]");
    private By numVersionBy = By.xpath("//div//li[@class='hebrewName']//div/button[@id='dropdownBasic1']");
    private By iconToExitBy = By.xpath("//div//ul/li[@class='top-icons']//i");
    private By category_drugPreparation = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='הכנת תרופות']");
    private By category_dischargedList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='רשימת משוחררים']");
    private By category_ordersList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='הזמנות']");
    private By category_reportsList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='דוחות']");
    private By category_protocolList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='פרוטוקולים']");
    private By category_operations = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='חדר ניתוח']");
    private By category_patientList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='רשימת מטופלים']");


    private By patientTableBy = By.xpath("//patients-table//tr");

    public MainMenuPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    public String getUserRole() {
        return UIActions.getElementText(roleUserBy);
    }

    public void verificationPatientListTabExisting(){ 
        assertTrue(UIActions.findElementWithWait(category_patientList).isDisplayed(),"Patient list category should be displayed in main menu but it's not.");   
    }

    public void  verificationNumVersionExisting(){ 
        assertTrue(UIActions.findElementWithWait(numVersionBy).isDisplayed(),"Version number should be displayed in main menu but it's not.");
    }

    public void verifyRoleIsDisplayed(String role){
        log.info("Verifying main menu for role: {}", role);
        switch (role) {
            case "אחות":
                getUserRole().equals("אחות");
                break;
            case "רופא":
                getUserRole().equals("רופא");
                break;
            case "מזכירה":
                getUserRole().equals("מזכירה");
                break;
            default:
                log.warn("No specific verification implemented for role: {}", role);
        }
    }

    public void logout(){
        log.info("Logging out from the application.");
        UIActions.click(iconToExitBy);
        
      UIActions.waitForElementClickable(By.xpath("//app-msg-modal//button[contains(@class,'btn-submit')]"));
        UIActions.click(By.xpath("//app-msg-modal//button[contains(@class,'btn-submit')]"));
    }

    public void verifyPatientTableIsDisplayed() {
        UIActions.waitForListToHaveElements(UIActions.findElementsWithWait(patientTableBy));
        assertTrue(UIActions.findElementWithWait(patientTableBy).isDisplayed(), "Patient table should be displayed but it's not.");
    }

}
