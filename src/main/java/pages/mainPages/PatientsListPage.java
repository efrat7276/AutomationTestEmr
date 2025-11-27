package pages.mainPages;
import actionUtilies.UIActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PatientsListPage {

   private  By list_patients = By.xpath("//p-table[contains(@class,'depMeushpazim-table patients-table')]//tr//td[1]");

    public List<WebElement> getPatientRows() {
        return UIActions.findElementsWithWait(list_patients);
    }
    public void choosePatient(int index){

        List<WebElement> list = getPatientRows();
        list.get(index).click();
    }

}
