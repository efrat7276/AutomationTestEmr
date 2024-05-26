package pageObjects.emr.nurse.catheter;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class CatheterAddPage {

    @FindBy(how = How.NAME , using = "//drug-liquidation//*[@name='drugRow1']//button[@class='btn-nurse-instructions']")
    public List<WebElement>  drugToApprovalList;

}
