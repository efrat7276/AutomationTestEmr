package pageObjects.emr.nurse.wound;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class BedsoreMoreFieldsToAddPage {

    @FindBy(how = How.XPATH, using = "//button[@id='grade']")
    public WebElement button_grade;

    // grade options (appears when dropdown is open)
    @FindBy(how = How.XPATH, using = "//button[@id='grade']/following-sibling::div/button")
    public List<WebElement> option_woundDegree;


     @FindBy(how = How.XPATH, using = "//button[@id='tissueDescription']")
     public WebElement button_woundTissueDescription;

    @FindBy(how = How.XPATH, using = "//div[@class='form-box']//input[@type='checkbox']")
    public List<WebElement> option_woundMultiSelect;





    // smellsBad radio buttons

    @FindBy(how = How.XPATH, using = "//input[@formcontrolname='smellsBad']")
    public List<WebElement> radio_smellsBad;
    // holes radio buttons

    @FindBy(how = How.XPATH, using = "//input[@formcontrolname='holes']")
    public List<WebElement> radio_holes;
    // tissueColor dropdown button

    @FindBy(how = How.XPATH, using = "//button[@id='tissueColor']")
    public WebElement button_tissueColor;






}
