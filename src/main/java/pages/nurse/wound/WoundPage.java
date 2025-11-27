package pages.nurse.wound;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WoundPage {

    @FindBy(how = How.ID , using = "btnAddMedicine")
    public WebElement button_addWound;

    @FindBy(how = How.XPATH , using = "//div[@class='action-bottom-bar']/button[contains(@class, 'ng-star-inserted')][3]")
    public WebElement  button_saveWound;


}
