package pages.popUp;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import pages.BasePage;

@Slf4j
public class ConfirmationAlert extends BasePage {

    public ConfirmationAlert() {
        UIActions.waitForSpinnerToDisappear();
    }

    private final By btnOk = By.id("buttonImport");
    private final By btnCancel = By.id("buttonCancle");



}
