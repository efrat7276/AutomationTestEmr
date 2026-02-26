package pages.menu;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import pages.BasePage;

import static org.testng.Assert.assertTrue;

@Slf4j
public class MainMenuPage extends BasePage {
    private By category_drugPreparation = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='הכנת תרופות']");
    private By category_dischargedList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='רשימת משוחררים']");
    private By category_ordersList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='הזמנות']");
    private By category_reportsList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='דוחות']");
    private By category_protocolList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='פרוטוקולים']");
    private By category_operations = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='חדר ניתוח']");
    private By category_patientList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='רשימת מטופלים']");


    public MainMenuPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    public void verificationPatientListTabExisting(){ 
        assertTrue(UIActions.findElementWithWait(category_patientList).isDisplayed(),"Patient list category should be displayed in main menu but it's not.");   
    }

}
