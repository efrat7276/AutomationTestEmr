package pages.nurse.wound;

import actionUtilies.UIActions;
import org.openqa.selenium.By;

public class WoundPage {
 
    private final By button_addWound = By.id("btnAddMedicine");
    private final By button_saveWound = By.xpath("//div[@class='action-bottom-bar']/button[contains(@class, 'ng-star-inserted')][3]");

    public void clickAddWound() {
        UIActions.click(button_addWound);
    }

    public void clickSaveWound() {
        UIActions.click(button_saveWound);
    }
}



