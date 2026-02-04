package pages;

import actionUtilies.UIActions;
import org.openqa.selenium.By;

import static org.testng.Assert.assertTrue;


public class PatientBoxPage {

    public PatientBoxPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    private By bar_deatails_patient = By.xpath("//app-patient-detail/div");

    public void verifyPatientDetailsExisting(){
        assertTrue(UIActions.findElementWithWait(bar_deatails_patient).isDisplayed());
    }
}
