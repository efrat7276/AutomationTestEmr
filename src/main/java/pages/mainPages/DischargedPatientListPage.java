package pages.mainPages;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import actionUtilies.UIActions;

public class DischargedPatientListPage {
  public DischargedPatientListPage() {
            UIActions.waitForSpinnerToDisappear();
    }
   private  By list_dischargedPatients = By.xpath("//p-table[contains(@class,'depMeushpazim-table patients-table')]");

 public boolean verifydischargedPatientsListVisible() {
       assertTrue(UIActions.isElementDisplayed(list_dischargedPatients), "Discharged patients list should be visible but it's not.");
        return true;
  
}
}
