package pages.popUp;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import pages.BasePage;

@Slf4j
public class CardexDailyReport extends BasePage {

    public CardexDailyReport() {
        UIActions.waitForSpinnerToDisappear();
    }

    private final By btnExit = By.xpath("//cardex-daily-report//span");




}
