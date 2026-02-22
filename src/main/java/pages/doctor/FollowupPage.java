package pages.doctor;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import pages.BasePage;
import pages.UserSignModalPage;

public class FollowupPage extends BasePage {

  UserSignModalPage userSignModalPage;
  public FollowupPage() {
        super();
        userSignModalPage = new UserSignModalPage();
    }

    private By textArea_subjective = By.id("subjective");
    private By textArea_objective = By.id("objective");
    private By textArea_aassesment = By.id("assesment");
    private By textArea_pplan = By.id("pplan");

    private By saveButton = By.id("btn_save"); 
    private By clearButton = By.id("btn_clear");
    private By copy_lastButton = By.id("btn_copy_last");

    private By followupHistoryList = By.xpath("//followup-history//div[@class='history-line']");
    private By buttonTrash = By.xpath("//followup-history//div[@class='history-line']//i[contains(@class, 'trash')]");



    public void addFollowup(String notes_Subjec , String notes_Objective ,
                            String notes_Aassesment , String notes_Plan, String username, String password) {
     logger.info("Attempting to add follow-up");
     UIActions.typeText(textArea_subjective, notes_Subjec);
     UIActions.typeText(textArea_objective, notes_Objective);
     UIActions.typeText(textArea_aassesment, notes_Aassesment);
     UIActions.typeText(textArea_pplan, notes_Plan);
     UIActions.click(saveButton);
     userSignModalPage.signModal(username, password);
     verifyFollowupSaved();
  }

  public void verifyFollowupSaved() {
    if(UIActions.waitForVisible(buttonTrash)) {
      logger.info("✔ הפולו-אפ נשמר בהצלחה והופיע בהיסטוריה.");
    } else {
      logger.error("❌ כשל בשמירת הפולו-אפ או בהצגתו בהיסטוריה.");
      throw new AssertionError("כשל בשמירת הפולו-אפ או בהצגתו בהיסטוריה.");
    }
  } 
}
