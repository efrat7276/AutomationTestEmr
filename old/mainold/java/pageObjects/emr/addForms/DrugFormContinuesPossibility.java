package pageObjects.emr.addForms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DrugFormContinuesPossibility {

    @FindBy(how=How.ID, using="MinRate")
    public WebElement inp_rate;
}
