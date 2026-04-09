package pages.mainPages;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DischargedPatientListPage {
  public DischargedPatientListPage() {
            UIActions.waitForSpinnerToDisappear();
    }
   private  By list_dischargedPatients = By.xpath("//p-table[contains(@class,'depMeushpazim-table patients-table')]");

 public boolean verifydischargedPatientsListVisible() {
      log.info("Checking if discharged patients list is visible");
       assertTrue(UIActions.isElementDisplayed(list_dischargedPatients), "Discharged patients list should be visible but it's not.");
        return true;
  
}
}
