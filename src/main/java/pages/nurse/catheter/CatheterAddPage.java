package pages.nurse.catheter;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pages.BasePage;

import java.util.List;

@Slf4j
public class CatheterAddPage extends BasePage {
    
    public CatheterAddPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    @FindBy(how = How.NAME , using = "//drug-liquidation//*[@name='drugRow1']//button[@class='btn-nurse-instructions']")
    public List<WebElement>  drugToApprovalList;

}
