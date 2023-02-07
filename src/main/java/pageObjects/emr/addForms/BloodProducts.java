package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class BloodProducts {

    @FindBy(how= How.XPATH, using="//button[@id='btnbloodProductList']/following-sibling::ul/li")
    public List<WebElement> bloodProductList;

    @FindBy(how=How.ID, using="btnbloodProductList")
    public WebElement btn_bloodProductList;

    @FindBy(how= How.XPATH, using="//button[@id='solutionBagSizeCodeList']/following-sibling::ul/li")
    public List<WebElement> solutionBagSizeList;

    @FindBy(how=How.ID, using="solutionBagSizeCodeList")
    public WebElement btn_solutionBagSizeList;


    @FindBy(how= How.XPATH, using="//button[@id='dropdownDrugUnitMeasure']/following-sibling::ul/li")
    public List<WebElement> dropdownDrugUnitMeasureList;

    @FindBy(how=How.ID, using="dropdownDrugUnitMeasure")
    public WebElement btn_dropdownDrugUnitMeasure;

    @FindBy(how=How.ID, using="drugComment")
    public WebElement inp_comment;

}


