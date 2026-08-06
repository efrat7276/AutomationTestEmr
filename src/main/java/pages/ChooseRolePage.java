package pages;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class ChooseRolePage extends BasePage {

    public ChooseRolePage() {
        UIActions.waitForSpinnerToDisappear();
    }

    private By listRole = By.xpath("//ul[@class='list-group']/li");

    public void chooseRole(String roleDescription){


         UIActions.selectFromList(listRole, roleDescription);
    }
}
