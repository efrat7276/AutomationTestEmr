package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DrugsSection {


    @FindBy(how = How.ID, using="btnAddMedicine")
    public WebElement btns_addDrug;

    @FindBy(how = How.ID, using="btnImportMedicine")
    public WebElement btn_importDrug;

    @FindBy(how = How.ID, using="btnDilution")
    public WebElement btn_addDilution;


}

