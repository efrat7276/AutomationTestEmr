package pages.nurse.wound;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class WondFormPage {

    //הגדרת הפצע

    // Dropdown button for selecting wound type (סוג הפצע)
    @FindBy(how = How.ID, using = "dropdownBasic1")
    public WebElement button_woundToAddList;

    // Dynamic list of wound types (each wound option in the dropdown)
    @FindBy(how = How. XPATH,  using = "//div[@class='kind-options dropdown-menu show']/button")
    public List<WebElement> options_woundType;

    // Radio buttons for wound situation (מצב הפצע)
    @FindBy(how = How.NAME, using = "situation")
    public List<WebElement> radio_woundSituation;

    // Label for the wound situation radio buttons
    @FindBy(how = How.XPATH, using = "//label[contains(text(), 'מצב הפצע')]")
    public WebElement label_woundSituation;

    // Dropdown button for closing wound edges (סגירת חתך)
    @FindBy(how = How.ID, using = "closeOfEdges")
    public WebElement dropdown_closeOfEdges;

    // Dynamic list of closure options for wound edges (סגירת חתך)
    @FindBy(how = How.CSS, using = "div[ngbDropdownMenu] button[ngbDropdownItem]")
    public List<WebElement> options_closeOfEdges;

    // ---- Wound Location (מיקום הפצע) ----

    // Label for Wound Location
    @FindBy(how = How.XPATH, using = "//label[@for='location']")
    public WebElement label_woundLocation;

    // Dropdown button for selecting wound location (when wound type is not equal to 1)
    @FindBy(how = How.ID, using = "location")
    public WebElement dropdown_woundLocation;

    // Wound location options (appears when dropdown is open)
    @FindBy(how = How.XPATH, using = "//button[@id='location']/following-sibling::div/button")
    public List<WebElement> options_woundLocation;

    // Input field for wound location (when wound type is equal to 1)
    @FindBy(how = How.CSS, using = "input[formControlName='location']")
    public WebElement input_woundLocation;

    // ---- Wound Side (צד הפצע) ----

    // Label for Wound Side
    @FindBy(how = How.XPATH, using = "//label[@for='side']")
    public WebElement label_woundSide;

    // Dropdown button for selecting wound side
    @FindBy(how = How.ID, using = "side")
    public WebElement dropdown_woundSide;

    // Wound side options (appears when dropdown is open)
    @FindBy(how = How.XPATH, using = "//button[@id='side']/following-sibling::div/button")
    public WebElement option_woundSide;

    // ---- Wound Facia (facia פתוחה / סגורה עם רשת) ----

    // Radio button for 'facia פתוחה' (Open Facia)
    @FindBy(how = How.XPATH, using = "//input[@formcontrolname='facia' and @value='open']")
    public WebElement radio_faciaOpen;

    // Radio button for 'facia סגורה עם רשת' (Closed Facia with Mesh)
    @FindBy(how = How.XPATH, using = "//input[@formcontrolname='facia' and @value='close']")
    public WebElement radio_faciaClose;

    // Label for 'facia פתוחה'
    @FindBy(how = How.XPATH, using = "//label[contains(text(), 'facia פתוחה')]")
    public WebElement label_faciaOpen;

    // Label for 'facia סגורה עם רשת'
    @FindBy(how = How.XPATH, using = "//label[contains(text(), 'facia סגורה עם רשת')]")
    public WebElement label_faciaClose;

    // Label for the Comment Textarea
    @FindBy(how = How.XPATH, using = "//label[@for='definationComment']")
    public WebElement label_comments;

    // Textarea for entering comments
    @FindBy(how = How.NAME, using = "definationComment")
    public WebElement textarea_comments;

    @FindBy(how = How.XPATH, using = "//button[@id='provisionAmount']")
    public WebElement button_provisionAmount;

    @FindBy(how = How.XPATH, using = "//button[@id='provisionAmount']/following-sibling::div/button")
    public List<WebElement> option_provisionAmount;

    @FindBy(how = How.XPATH, using = "//*[@id='provisionType']")
    public WebElement button_provisionType;

    @FindBy(how = How.XPATH, using = "//*[@id='provisionType']/following-sibling::div/button")
    public List<WebElement> option_provisionType;

    @FindBy(how = How.XPATH, using = "//input[@formcontrolname='provisionTypeComment']")
    public WebElement input_provisionTypeComment;


    @FindBy(how = How.XPATH, using = "//button[@id='tissueColor ']")
    public WebElement button_woundColor;

    @FindBy(how = How.XPATH, using = "//button[@id='tissueColor ']/following-sibling::div/button")
    public List<WebElement> option_woundColor;

    @FindBy(how = How.XPATH, using = "//button[@id='skinAround']")
    public WebElement button_skinAround;

    @FindBy(how = How.XPATH, using = "//button[@id='skinAround']/following-sibling::div//input[@type='checkbox']")
    public List<WebElement> option_skinAround;


    @FindBy(how = How.XPATH, using = "//input[@formcontrolname='closedInstruction']")
    public WebElement checkbox_closedInstruction;




}
