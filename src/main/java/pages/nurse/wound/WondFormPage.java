package pages.nurse.wound;

import actionUtilies.UIActions;
import org.openqa.selenium.By;

import javax.annotation.Nullable;
import java.util.List;

public class WondFormPage {

    //הגדרת הפצע

    // Dropdown button for selecting wound type (סוג הפצע)
    public By button_woundToAddList = By.id("dropdownBasic1");

    // Dynamic list of wound types (each wound option in the dropdown)
    public By options_woundType = By.xpath("//div[@class='kind-options dropdown-menu show']/button");

    // Radio buttons for wound situation (מצב הפצע)
    public By radio_woundSituation = By.name("situation");

    // Label for the wound situation radio buttons
    public By label_woundSituation = By.xpath("//label[contains(text(), 'מצב הפצע')]");

    // Dropdown button for closing wound edges (סגירת חתך)
    public By dropdown_closeOfEdges = By.id("closeOfEdges");

    // Dynamic list of closure options for wound edges (סגירת חתך)
    public By options_closeOfEdges = By.cssSelector("div[ngbDropdownMenu] button[ngbDropdownItem]");

    // ---- Wound Location (מיקום הפצע) ----

    // Label for Wound Location
    public By label_woundLocation = By.xpath("//label[@for='location']");

    // Dropdown button for selecting wound location (when wound type is not equal to 1)
    public By dropdown_woundLocation = By.id("location");

    // Wound location options (appears when dropdown is open)
    public By options_woundLocation = By.xpath("//button[@id='location']/following-sibling::div/button");

    // First option for wound location (helper for quick selection)
    public By option_woundLocationFirst = By.xpath("(//button[@id='location']/following-sibling::div/button)[1]");

    // Input field for wound location (when wound type is equal to 1)
    public By input_woundLocation = By.cssSelector("input[formControlName='location']");

    // ---- Wound Side (צד הפצע) ----

    // Label for Wound Side
    public By label_woundSide = By.xpath("//label[@for='side']");

    // Dropdown button for selecting wound side
    public By dropdown_woundSide = By.id("side");

    // Wound side options (appears when dropdown is open)
    public By option_woundSide = By.xpath("//button[@id='side']/following-sibling::div/button");

    // ---- Wound Facia (facia פתוחה / סגורה עם רשת) ----

    // Radio button for 'facia פתוחה' (Open Facia)
    public By radio_faciaOpen = By.xpath("//input[@formcontrolname='facia' and @value='open']");

    // Radio button for 'facia סגורה עם רשת' (Closed Facia with Mesh)
    public By radio_faciaClose = By.xpath("//input[@formcontrolname='facia' and @value='close']");

    // Label for 'facia פתוחה'
    public By label_faciaOpen = By.xpath("//label[contains(text(), 'facia פתוחה')]");

    // Label for 'facia סגורה עם רשת'
    public By label_faciaClose = By.xpath("//label[contains(text(), 'facia סגורה עם רשת')]");

    // Label for the Comment Textarea
    public By label_comments = By.xpath("//label[@for='definationComment']");

    // Textarea for entering comments
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


    public void addNewWound(String woundDescription,
                            @Nullable Integer closeOrOpen,
                            @Nullable Integer degree,
                            @Nullable Integer treatmentInstructionCount) {
        WoundPage woundPage = new WoundPage();
        woundPage.clickAddWound();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        UIActions.click(button_woundToAddList);
        UIActions.selectFromList(options_woundType, woundDescription);



        if (UIActions.isElementDisplayed(dropdown_woundLocation)) {
            UIActions.click(dropdown_woundLocation);
            UIActions.click(option_woundLocationFirst);
        }
        if (UIActions.isElementDisplayed(dropdown_woundSide)) {
            UIActions.click(dropdown_woundSide);
            UIActions.click(option_woundSide);
        }

        if (closeOrOpen != null) {
            UIActions.click(dropdown_closeOfEdges);
            UIActions.click(options_closeOfEdges);
        }

    ////יש להוסיף התיחסות לפצע ניתוחי ופצע לחץ 
    }

    public void addAnsSaveWound() {
        WoundPage woundPage = new WoundPage();
        woundPage.clickSaveWound();
    }

    public void addAnsSaveWound(String username, String password) {
        addAnsSaveWound();
    }


}
