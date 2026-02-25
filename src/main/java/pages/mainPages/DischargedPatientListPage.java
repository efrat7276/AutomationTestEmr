package pages.mainPages;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import actionUtilies.UIActions;

public class DischargedPatientListPage {

   private  By list_dischargedPatients = By.xpath("//p-table[contains(@class,'depMeushpazim-table patients-table')]");

public void verifyIsDischargedPatientsListVisible() {
      assertTrue(UIActions.isElementDisplayed(list_dischargedPatients),"Discharged patients list should be visible but it's not.");  
   
}
}
