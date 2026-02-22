package pages;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.assertTrue;




public class PatientBoxPage {

    private static final Logger logger = LoggerFactory.getLogger(PatientBoxPage.class);
    public PatientBoxPage() {
        UIActions.waitForSpinnerToDisappear();
    }

    private By bar_deatails_patient = By.xpath("//app-patient-detail/div");

    public void verifyPatientDetailsExisting(){
       if(UIActions.findElementWithWait(bar_deatails_patient).isDisplayed()){
           logger.info("Patient details bar is displayed as expected.");
         assertTrue(true);
       } else {
           logger.error("Patient details bar is NOT displayed.");
           assertTrue(false);
       }
    }
}
