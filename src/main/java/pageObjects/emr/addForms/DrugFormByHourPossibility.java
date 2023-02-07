package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DrugFormByHourPossibility {


    @FindBy(how= How.XPATH, using="//button[@id='everyXtimeGivingPossibiltyDetail']/following-sibling::ul/li")
    public List<WebElement> everyXTimeList;

    @FindBy(how=How.ID, using="everyXtimeGivingPossibiltyDetail")
    public WebElement btn_everyXTime;

}
