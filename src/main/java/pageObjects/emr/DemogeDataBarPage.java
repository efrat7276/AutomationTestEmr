package pageObjects.emr;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DemogeDataBarPage {

    @FindBy(how= How.XPATH , using = "//demog-data-bar/div/div[1]/div/div[2]/div/div[2]/div[2]/i")
    public WebElement btn_collapse_patient_deatiles;

    @FindBy(how= How.XPATH , using = "//span[@class='value-label']")
    public WebElement  sherut_label;

    @FindBy(how= How.XPATH , using = "//span[@class='value-label ']")
    public List<WebElement>  labels_value;

    //::following-sibling::brother
}
