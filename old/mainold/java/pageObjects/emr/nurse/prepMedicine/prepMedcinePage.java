package pageObjects.emr.nurse.prepMedicine;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class prepMedcinePage {

    @FindBy(how= How.XPATH , using = "//medicine-prep-filter//div/label[text()='שעה']/parent::div")
    public WebElement filterHourBtn;

    @FindBy(how= How.XPATH , using = "//medicine-prep-filter//div/label[text()='שעה']/parent::div//ul/p-dropdownitem/li")
    public List< WebElement>  filterHourList;

}
