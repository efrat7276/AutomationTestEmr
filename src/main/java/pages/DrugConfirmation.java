package pages;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import pages.BasePage;

import java.util.List;

@Slf4j
public class DrugConfirmation extends BasePage {

    public DrugConfirmation() {
        UIActions.waitForSpinnerToDisappear();
    }

    private final By confKey_inp = By.id("confirmationKeyWithNoSelectesd");
    private final By comment_inp = By.id("confirmationCommentDoctorTemporary");
    private final By commentRecive_inp = By.id("confirmationCommentReceiverTemporary");
    private final By clear_button = By.id("drugConfirmationsClear");
    private final By cancel_button = By.id("drugConfirmationsCancel");
    private final By save_button = By.id("drugConfirmationsSave");
    private final By letter_buttonList = By.id("drugConfirmationStatusID");
    private final By letter_List = By.xpath("//*[@id='drugConfirmationStatusID']/following-sibling::ul/li");
}
