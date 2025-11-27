package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class GeneralInstructionPage {

    @FindBy(how = How.TAG_NAME, using = "general-ins-menu")
    public WebElement generalInsMenu;

    @FindBy(how = How.XPATH, using = "//ngb-accordion/div//button")
    public List<WebElement> generalInstructionCategoryList;

    @FindBy(how = How.XPATH, using = "//ngb-accordion//input")
    public List <WebElement> generalInsSubList;

    //רשימת הוראות שנבחרו - קיימת רק אם יש משהו שנבחר
    @FindBy(how = How.XPATH, using = "//div[@class='main-box']/ul/li")
    public List<WebElement>  listSelectedGeneralIns;

    @FindBy(how = How.XPATH, using = "//div[@class='main-box']//label[text()='תדירות']/following-sibling::button[contains(@class,'dropdown-toggle')]")
    public WebElement btn_possbilities;

    @FindBy(how = How.XPATH, using = "//div[@class='main-box']//label[text()='תדירות']/following-sibling::div[contains(@class,'dropdown')]/button")
    public List<WebElement>  possbilityList;

    @FindBy(how = How.XPATH, using = "//div[@class='main-box']//label[text()='פעמים']/following-sibling::button")
    public WebElement btn_numberOfTime;

    @FindBy(how = How.XPATH, using = "//div[@class='main-box']//label[text()='פעמים']/following-sibling::div[contains(@class,'dropdown')]/button")
    public List<WebElement>  numberOfTimeList;


    @FindBy(how = How.XPATH, using = "//button[contains(@class,'btn-submit')]")
    public WebElement btn_save;

    @FindBy(how = How.XPATH, using = "//button[@class='btn btn-defualt btn-cancel btn-click '][0]")
    public WebElement btn_clear;

    @FindBy(how = How.XPATH, using = "//button[@class='btn btn-defualt btn-cancel btn-click '][1]")
    public WebElement btn_back;
}
