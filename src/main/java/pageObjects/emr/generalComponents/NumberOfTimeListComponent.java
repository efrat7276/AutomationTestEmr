package pageObjects.emr.generalComponents;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utilities.Base;

import java.util.List;

public class NumberOfTimeListComponent extends Base {


    @FindBy(how= How.NAME , using = "numberOfTimes_daily")
    public WebElement  btn_open;

    @FindBy(how= How.XPATH , using = "//daily//li")
    public List<WebElement> list;

    @FindBy(how= How.XPATH , using = "//daily")
    public List<WebElement> listNumberOfTimes;


}
