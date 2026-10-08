package pages.doctor;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import org.checkerframework.checker.guieffect.qual.UI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.UserSignModalPage;
import java.util.List;

@Slf4j
public class FollowupPage extends BasePage {

  UserSignModalPage userSignModalPage;
  public FollowupPage() {
        UIActions.waitForSpinnerToDisappear();
        userSignModalPage = new UserSignModalPage();
    }

    // SOAP Notes Elements
    private By textArea_subjective = By.id("subjective");
    private By textArea_objective = By.id("objective");
    private By textArea_aassesment = By.id("assesment");
    private By textArea_pplan = By.id("pplan");

    // Buttons
    private By saveButton = By.id("btn_save"); 
    private By clearButton = By.id("btn_clear");
    private By copy_lastButton = By.id("btn_copy_last");
    private By printLastButton = By.xpath("//button[contains(text(), 'הדפס אחרון')]");
    private By printAllButton = By.xpath("//button[contains(text(), 'הדפס הכל')]");
    private By showLast3DaysButton = By.xpath("//button[contains(text(), 'הצג שלושה ימים אחרונים')]");

    // Checkbox
    private By readyForDischargeCheckbox = By.xpath("//input[@type='checkbox'][ancestor::*[contains(text(), 'מיועד לשחרור')]]");

    // History Elements
    private By followupHistoryList = By.xpath("//followup-history-midwives//div[@class='history-line']");
    private By buttonTrash = By.xpath("//followup-history-midwives//div[@class='history-line']//i[contains(@class, 'trash')]");

    // Catheter Tracking Section
    private By catheterTrackingHeading = By.xpath("//heading[contains(text(), 'מעקב צנתרים ונקזים')]");
    private By catheterTrackingTable = By.xpath("//heading[contains(text(), 'מעקב צנתרים ונקזים')]/ancestor::*//table");
    private By catheterTableRows = By.xpath("//heading[contains(text(), 'מעקב צנתרים ונקזים')]/ancestor::*//table//tbody//tr");

    // Wound Tracking Section
    private By woundTrackingHeading = By.xpath("//heading[contains(text(), 'מעקב פצעים')]");
    private By woundTrackingTable = By.xpath("//heading[contains(text(), 'מעקב פצעים')]/ancestor::*//table");

    // Medical Follow-up Section
    private By medicalFollowupHeading = By.xpath("//heading[contains(text(), 'מעקב רפואי')]");
    private By followupHistoryHeading = By.xpath("//heading[contains(text(), 'היסטוריית מעקב רפואי')]");


    // ===== SOAP Notes Methods =====

    public void addFollowupAndVerify(String notes_Subjec , String notes_Objective ,
                            String notes_Aassesment , String notes_Plan, String username, String password) {
     UIActions.waitForSpinnerToDisappear();
     UIActions.typeText(textArea_subjective, notes_Subjec);
     UIActions.typeText(textArea_objective, notes_Objective);
     UIActions.typeText(textArea_aassesment, notes_Aassesment);
     UIActions.typeText(textArea_pplan, notes_Plan);
     UIActions.click(saveButton);
     userSignModalPage.signModal(username, password);
     verifyFollowupSaved();
  }

  public void verifyFollowupSaved() {
    assertTrue(UIActions.waitForVisible(buttonTrash), "❌ כשל בשמירת הפולו-אפ או בהצגתו בהיסטוריה.");
   log.info("✅ follow-up saved and displayed in history successfully.");
  }
  
  public void fillSOAPFields(String subjective, String objective, String assessment, String plan) {
    log.info("🔄 Filling SOAP Fields: Subjective, Objective, Assessment, Plan...");
    fillSubjective(subjective);
    fillObjective(objective);
    fillAssessment(assessment);
    fillPlan(plan);
  }

  public void fillSubjective(String text) {
    UIActions.typeText(textArea_subjective, text);
    log.info("✅ Subjective field filled: " + text);
  }

  public void fillObjective(String text) {
    UIActions.typeText(textArea_objective, text);
    log.info("✅ Objective field filled: " + text);
  }

  public void fillAssessment(String text) {
    UIActions.typeText(textArea_aassesment, text);
    log.info("✅ Assessment field filled: " + text);
  }

  public void fillPlan(String text) {
    UIActions.typeText(textArea_pplan, text);
    log.info("✅ Plan field filled: " + text);
  }

  public String getSubjectiveText() {
    return UIActions.getElementText(textArea_subjective);
  }

  public String getObjectiveText() {
    return UIActions.getElementText(textArea_objective);
  }

  public String getAssessmentText() {
    return UIActions.getElementText(textArea_aassesment);
  }

  public String getPlanText() {
    return UIActions.getElementText(textArea_pplan);
  }

  public void clearSubjective() {
    UIActions.clearText(textArea_subjective);
    log.info("✅ Subjective field cleared");
  }

  public void clearObjective() {
    UIActions.clearText(textArea_objective);
    log.info("✅ Objective field cleared");
  }

  public void clearAssessment() {
    UIActions.clearText(textArea_aassesment);
    log.info("✅ Assessment field cleared");
  }

  public void clearPlan() {
    UIActions.clearText(textArea_pplan);
    log.info("✅ Plan field cleared");
  }

  public void saveAndApproveFollowup(String username, String password) {
    UIActions.click(saveButton);
    userSignModalPage.signModal(username, password); 
    verifyFollowupSaved();
  }

  public void clearAllFields() {
    UIActions.click(clearButton);
    log.info("✅ Clear button clicked - all SOAP fields cleared");
  }

  public void copyLastFollowup() {
    UIActions.click(copy_lastButton);
    log.info("✅ Copy last follow-up clicked");
  }



  // ===== Print Methods =====

  public void printLastFollowup() {
    UIActions.click(printLastButton);
    log.info("✅ Print Last button clicked");
  }

  public void printAllFollowups() {
    UIActions.click(printAllButton);
    log.info("✅ Print All button clicked");
  }

  // ===== Discharge Checkbox Methods =====

  public void checkReadyForDischarge() {
    WebElement checkbox = UIActions.findElementWithWait(readyForDischargeCheckbox);
    if (!checkbox.isSelected()) {
      UIActions.click(readyForDischargeCheckbox);
      log.info("✅ Ready for Discharge checkbox checked");
    }
  }

  public void uncheckReadyForDischarge() {
    WebElement checkbox = UIActions.findElementWithWait(readyForDischargeCheckbox);
    if (checkbox.isSelected()) {
      UIActions.click(readyForDischargeCheckbox);
      log.info("✅ Ready for Discharge checkbox unchecked");
    }
  }

  public boolean isReadyForDischargeChecked() {
    return UIActions.findElementWithWait(readyForDischargeCheckbox).isSelected();
  }

  // ===== Catheter Tracking Methods =====

  public boolean isCatheterTrackingVisible() {
    return UIActions.isElementDisplayed(catheterTrackingHeading);
  }

  public int getCatheterTrackingRowCount() {
    List<WebElement> rows = UIActions.findElementsWithWait(catheterTableRows);
    return rows.size();
  }

  public List<WebElement> getCatheterTrackingRows() {
    return UIActions.findElementsWithWait(catheterTableRows);
  }

  public String getCatheterNameAtRow(int rowIndex) {
    List<WebElement> rows = UIActions.findElementsWithWait(catheterTableRows);
    if (rowIndex < rows.size()) {
      // צנתר is in the 2nd column (index 1)
      return rows.get(rowIndex).findElements(By.tagName("td")).get(1).getText();
    }
    return "";
  }

  // ===== Wound Tracking Methods =====

  public boolean isWoundTrackingVisible() {
    return UIActions.isElementDisplayed(woundTrackingHeading);
  }

  public int getWoundTrackingRowCount() {
    try {
      List<WebElement> rows = UIActions.findElementsWithWait(By.xpath("//heading[contains(text(), 'מעקב פצעים')]/ancestor::*//table//tbody//tr"));
      return rows.size();
    } catch (Exception e) {
      log.info("No wounds tracked for this patient");
      return 0;
    }
  }

  // ===== History Methods =====

  public boolean isFollowupHistoryVisible() {
    return UIActions.isElementDisplayed(followupHistoryHeading);
  }

  public void showLast3DaysHistory() {
    UIActions.click(showLast3DaysButton);
    log.info("✅ Show last 3 days button clicked");
  }

  public int getFollowupHistoryCount() {
    List<WebElement> historyLines = UIActions.findElementsWithWait(followupHistoryList);
    return historyLines.size();
  }

  /**
   * Return the text content of the most recent followup history entry (first in the list)
   */
  public String getLatestFollowupHistoryText() {
    try {
      List<WebElement> historyLines = UIActions.findElementsWithWait(followupHistoryList);
      if (historyLines != null && !historyLines.isEmpty()) {
        WebElement latest = historyLines.get(0);
        String txt = latest.getAttribute("textContent");
        return txt != null ? txt.trim() : "";
      }
    } catch (Exception e) {
      log.warn("Could not read latest followup history text: {}", e.getMessage());
    }
    return "";
  }

  public void deleteFollowupAtIndex(int index) {
    List<WebElement> trashButtons = UIActions.findElementsWithWait(buttonTrash);
    if (index < trashButtons.size()) {
      UIActions.click(trashButtons.get(index));
      log.info("✅ Deleted follow-up at index: " + index);
    }
  }

  // ===== Full Flow Methods =====

  public void addFullFollowupWithDischarge(String subjective, String objective, String assessment, 
                                           String plan, String username, String password, boolean readyForDischarge) {
    fillSubjective(subjective);
    fillObjective(objective);
    fillAssessment(assessment);
    fillPlan(plan);
    
    if (readyForDischarge) {
      checkReadyForDischarge();
    }
    
    saveAndApproveFollowup(username, password);
    log.info("✅ Full follow-up with discharge status added and saved");
  }
}
