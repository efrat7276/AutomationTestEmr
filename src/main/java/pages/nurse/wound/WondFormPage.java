package pages.nurse.wound;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import pages.BasePage;
import pages.UserSignModalPage;

import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.By;

import static org.testng.Assert.assertTrue;

import javax.annotation.Nullable;
@Slf4j
public class WondFormPage extends BasePage {

public By button_addWound =By.id("btnAddMedicine");
   
    public By button_woundType = By.xpath("//button[@id='dropdownBasic1' and @name='kind']");
    public By options_woundType = By.xpath("//div[contains(@class,'kind-options dropdown-menu')]//button");                                                           // public By options_woundLocation = By.name("location");
    public By radio_woundSituation = By.name("situation");
    public By label_woundSituation = By.xpath("//label[contains(text(), 'מצב הפצע')]");
    public By dropdown_closeOfEdges = By.id("closeOfEdges");
    public By options_closeOfEdges = By.cssSelector("div[ngbDropdownMenu] button[ngbDropdownItem]");
    public By label_woundLocation = By.xpath("//label[@for='location']");
    public By dropdown_woundLocation = By.id("location");
    public By options_woundLocation = By.xpath("//button[@id='location']/following-sibling::div/button");
    public By option_woundLocationFirst = By.xpath("(//button[@id='location']/following-sibling::div/button)[1]");
    public By input_woundLocation = By.cssSelector("input[formControlName='location']");
    public By label_woundSide = By.xpath("//label[@for='side']");
    public By dropdown_woundSide = By.id("side");
    public By option_woundSide = By.xpath("//button[@id='side']/following-sibling::div/button");
    public By radio_faciaOpen = By.xpath("//input[@formcontrolname='facia' and @value='open']");
    public By radio_faciaClose = By.xpath("//input[@formcontrolname='facia' and @value='close']");
    public By label_faciaOpen = By.xpath("//label[contains(text(), 'facia פתוחה')]");
    public By label_faciaClose = By.xpath("//label[contains(text(), 'facia סגורה עם רשת')]");
    public By label_comments = By.xpath("//label[@for='definationComment']");
    public By textarea_comments = By.name("definationComment");
    public By button_provisionAmount = By.xpath("//button[@id='provisionAmount']");
    public By option_provisionAmount = By.xpath("//button[@id='provisionAmount']/following-sibling::div/button");
    public By button_provisionType = By.xpath("//*[@id='provisionType']");
    public By option_provisionType = By.xpath("//*[@id='provisionType']/following-sibling::div/button");
    public By input_provisionTypeComment = By.xpath("//input[@formcontrolname='provisionTypeComment']");
    public By button_woundColor = By.xpath("//button[@id='tissueColor ']");
    public By option_woundColor = By.xpath("//button[@id='tissueColor ']/following-sibling::div/button");
    public By button_skinAround = By.xpath("//button[@id='skinAround']");
    public By option_skinAround = By.xpath("//button[@id='skinAround']/following-sibling::div//input[@type='checkbox']");
    public By checkbox_closedInstruction = By.xpath("//input[@formcontrolname='closedInstruction']");
    public By button_save = By.xpath("//div[@class='action-bottom-bar']/button[contains(@class, 'ng-star-inserted')][3]");
    
    public void addNewWound(String woundDescription,
                            @Nullable Integer closeOrOpen,
                            @Nullable Integer degree,
                            @Nullable Integer treatmentInstructionCount) {

      log.info("Attempting to add new wound with description: {}, closeOrOpen: {}, degree: {}, treatmentInstructionCount: {}",
      woundDescription, closeOrOpen, degree, treatmentInstructionCount);
     //  UIActions.click(button_addWound);
       UIActions.waitForSpinnerToDisappear();
       UIActions.click(button_woundType);
        UIActions.selectFromList(options_woundType, woundDescription);

        if (UIActions.isElementDisplayed(dropdown_woundLocation)) {
            UIActions.click(dropdown_woundLocation);
            UIActions.click(option_woundLocationFirst);
        }
        else if (UIActions.isElementDisplayed(input_woundLocation)) {
            UIActions.typeText(input_woundLocation, "יד");
        }
        // if(UIActions.isElementDisplayed(radio_woundSituation)) {
        //     UIActions.click(radio_woundSituation);
        // }

        //  if (degree != null) {
        //      UIActions.click(button_provisionAmount);
        //      UIActions.selectFromList(option_provisionAmount, degree.toString());
        //  }

        //  if (treatmentInstructionCount != null) {
        //      UIActions.click(button_provisionType);
        //      UIActions.selectFromList(option_provisionType, "הוראות טיפול");
        //      UIActions.typeText(input_provisionTypeComment, "מספר הוראות טיפול: " + treatmentInstructionCount);
        //  }

        //  if (closeOrOpen != null) {
        //      UIActions.click(dropdown_closeOfEdges);
        //      UIActions.click(options_closeOfEdges);
        //  }


        if (UIActions.isElementDisplayed(dropdown_woundSide)) {
            UIActions.click(dropdown_woundSide);
            UIActions.click(option_woundSide);
        }

        if (closeOrOpen != null) {
            UIActions.click(dropdown_closeOfEdges);
            UIActions.click(options_closeOfEdges);
        }


    }

 private void clickSaveWound() {
     UIActions.click(button_save);
    }

    public void saveWound(String username, String password) {
        log.info("Saving wound and signing with username: {}", username);  
        clickSaveWound();
        
          userSignModalPage.signModal(username, password);
         assertTrue(verificationTitleIsDisplay("פצעים"), "Title 'פצעים' is not displayed after saving wound, expected to be on the wounds page");
          log.info("Wound saved and verified successfully");

    }
    // public void verifyWoundSaved() {
    //     log.info("Verifying wound was saved successfully");
    //     By savedWoundLocator = By.xpath("//div[contains(@class,'wound-list')]//div[contains(text(),'פצע איסכמי')]");
    //     UIActions.ver(savedWoundLocator);
    // }



   


}
