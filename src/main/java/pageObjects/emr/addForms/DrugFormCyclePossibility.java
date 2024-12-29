package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DrugFormCyclePossibility {


    @FindBy(how=How.XPATH, using="//ul[@aria-labelledby='numberOfTimes_daily']/li")
    public List<WebElement> numberOfTimesCycle;

    @FindBy(how=How.ID, using="numberOfTimes_daily")
    public WebElement btn_numberOfTimesCycle;

}
