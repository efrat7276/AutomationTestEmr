package pages.menu;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pages.BasePage;

import static org.testng.Assert.assertTrue;

public class MainMenuPage extends BasePage {

    Logger logger = LoggerFactory.getLogger(MainMenuPage.class);
    private By category_drugPreparation = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='הכנת תרופות']");
    private By category_dischargedList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='רשימת משוחררים']");
    private By category_ordersList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='הזמנות']");
    private By category_reportsList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='דוחות']");
    private By category_protocolList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='פרוטוקולים']");
    private By category_operations = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='חדר ניתוח']");
    private By category_patientList = By.xpath("//ul[contains(@class,'flex-fill main-menu-list')]//span[text()='רשימת מטופלים']");



    public void verificationPatientListTabExisting(){ 
       if(UIActions.findElementWithWait (category_patientList).isDisplayed()){
        assertTrue(true);
        logger.info("Patient list category is displayed in main menu.");
       }
       else{
        logger.error("Patient list category is NOT displayed in main menu.");
        assertTrue(false);
       }    
    }

}
