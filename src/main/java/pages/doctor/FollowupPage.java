package pages.doctor;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import pages.BasePage;

public class FollowupPage extends BasePage {

    private By textArea_subjective = By.id("subjective");
    private By textArea_objective = By.id("objective");
    private By textArea_aassesment = By.id("assesment");
    private By textArea_pplan = By.id("pplan");

    private By saveButton = By.id("btn_save"); 
    private By clearButton = By.id("btn_clear");
    private By copy_lastButton = By.id("btn_copy_last");


    public void addFollowup(String notes_Subjec , String notes_Objective ,
                            String notes_Aassesment , String notes_Plan, String username, String password) {
     UIActions.typeText(textArea_subjective, notes_Subjec);
     UIActions.typeText(textArea_objective, notes_Objective);
     UIActions.typeText(textArea_aassesment, notes_Aassesment);
     UIActions.typeText(textArea_pplan, notes_Plan);
     UIActions.click(saveButton);
    userSignModalPage.signModal(username, password);
  }
}
