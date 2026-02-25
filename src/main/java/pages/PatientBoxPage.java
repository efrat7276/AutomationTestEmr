package pages;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import static org.testng.Assert.assertTrue;

@Slf4j
public class PatientBoxPage {
    public PatientBoxPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    private By bar_deatails_patient = By.xpath("//app-patient-detail/div");

    public void verifyPatientDetailsExisting(){
         assertTrue(UIActions.findElementWithWait(bar_deatails_patient).isDisplayed(),"patient details bar should be displayed but it's not.");
    }
}
