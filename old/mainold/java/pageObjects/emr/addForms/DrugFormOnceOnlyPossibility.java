package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DrugFormOnceOnlyPossibility {

    @FindBy(how= How.XPATH, using="//button[@id='btnHourToGive']/following-sibling::ul/li")
    public List<WebElement> hourList;

    @FindBy(how=How.ID, using="btnHourToGive")
    public WebElement btn_hour;
}
