package pageObjects.emr.generalComponents;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utilities.Base;

import java.util.List;

public class ListChoicesComponent extends Base {


    @FindBy(how= How.XPATH , using = "//typeahead-container/button")
    public List<WebElement>  listChoices;

//    @FindBy(how= How.XPATH , using = "//ul/li[1]")
//    public WebElement li_doctor;
//
//    @FindBy(how= How.XPATH , using = "//ul/li[2]")
//    public WebElement li_nurte;
//
//    @FindBy(how= How.XPATH , using = "//ul/li[3]")
//    public WebElement li_ibfectionologist;
//
//    @FindBy(how= How.XPATH , using = "//ul/li[4]")
//    public WebElement li3;
//
//    @FindBy(how= How.XPATH , using = "//ul/li[5]")
//    public WebElement li4;
//
//    @FindBy(how= How.XPATH , using = "//ul/li[6]")
//    public WebElement li5;
}
