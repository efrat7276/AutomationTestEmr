package pages.nurse.wound;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class SurgicalWoundMoreFieldsToAddPage {

    @FindBy(how = How.ID, using = "situation-1")
    public WebElement inp_openedWound;

    @FindBy(how = How.ID, using = "situation-0")
    public WebElement inp_closedWound;

    @FindBy(how = How.XPATH, using = "//div//label[text()='מיקום הפצע']/following-sibling::div//input")
    public WebElement location;

    // פצע סגור
    @FindBy(how = How.ID, using = "closeOfEdges")
    public WebElement btn_closeOfEdges;
    @FindBy(how = How.XPATH, using = "//button[@id='closeOfEdges']/following-sibling::div//button")
    public List<WebElement> option_closeOfEdges;

    @FindBy(how = How.XPATH, using = "//button[@id='marginalCondition ']")
    public WebElement dropdown_marginalCondition;
    @FindBy(how = How.XPATH, using = "//button[@id='marginalCondition ']/following-sibling::div//button]")
    public List<WebElement> options_marginalCondition;

    @FindBy(how = How.ID, using = "provisionAmount")
    public WebElement dropdown_amountOfSecretion;
    @FindBy(how = How.XPATH, using = "//button[@id='provisionAmount']/following-sibling::div//button")
    public List<WebElement> options_amountOfSecretion;

    // smellsBad radio buttons

    @FindBy(how = How.XPATH, using = "//input[@formcontrolname='smellsBad']")
    public List<WebElement> radio_smellsBad;
    // holes radio buttons

    @FindBy(how = How.XPATH, using = "//input[@formcontrolname='holes']")
    public List<WebElement> radio_holes;
    // tissueColor dropdown button

    @FindBy(how = How.XPATH, using = "//button[@id='tissueColor']")
    public WebElement button_tissueColor;
    // marginalCondition dropdown button

    @FindBy(how = How.XPATH, using = "//textarea[@name='estimateComment']")
    public WebElement txt_comment;


    // חבישות

    @FindBy(how = How.XPATH, using = "//div/div[text()='חבישה']/following-sibling::div//input")
    public List<WebElement> inp_provisionList;

    @FindBy(how = How.XPATH, using = "//div/div[text()='חבישה']//following-sibling::div//div/div[2]//button[@id='provisionAmount']")
    public List<WebElement> dropdownList_provisionAmount;

    @FindBy(how = How.XPATH, using = "//div/div[text()='חבישה']//following-sibling::div//div/div//button[@id='provisionAmount']/following-sibling::div/button")
    public List<WebElement> option_provisionAmountForAllProvision;

    @FindBy(how = How.XPATH, using = "//div/div[text()='חבישה']/following-sibling::div//label[text()='כמות נוזל']/following-sibling::input")
    public WebElement inp_provisionAmount;
}
