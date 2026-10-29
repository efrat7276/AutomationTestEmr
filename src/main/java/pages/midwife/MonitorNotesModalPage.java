package pages.midwife;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import drivers.DriverManager;

import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class MonitorNotesModalPage extends BasePage {

    public MonitorNotesModalPage() {
        super();
        userSignModalPage = new pages.UserSignModalPage();
        UIActions.waitForSpinnerToDisappear();
        log.info("✅ MonitorNotesModalPage initialized");
    }

    // ============ Locators ============
    private final By modalContainerBy = By.xpath("//ngb-modal-window[contains(@class,'modal') and contains(@class,'show')]");
    private final By modalTitleBy = By.xpath("//ngb-modal-window//h5[contains(normalize-space(.),'הערות למוניטור')]");
    private final By noteInputBy = By.id("comment-typeahead");
    private final By usernameInputBy = By.id("user");
    private final By passwordInputBy = By.id("password");
    private final By submitButtonBy = By.xpath("//ngb-modal-window//div[contains(@class,'modal-body')]//button[contains(@class,'btn-primary')]");
    private final By closeButtonBy = By.xpath("//ngb-modal-window//button[@aria-label='Close']");
    private final By notesTableRowsBy = By.xpath("//ngb-modal-window//table[contains(@class,'history-table')]//tbody//tr");

    /**
     * Check if Monitor Notes modal is visible
     * @return true if visible
     */
    public boolean isMonitorNotesModalVisible() {
        return UIActions.isElementPresentAndVisible(modalTitleBy);
    }

    /**
     * Fill monitor note text area and submit. If a signature modal appears, signs it.
     * @param note the monitor note text to add
     * @param username username to sign (nullable if not required)
     * @param password password to sign (nullable if not required)
     */
    public void fillAndSaveMonitorNote(String note, String username, String password) {
        log.info("📝 Filling monitor note...");
        UIActions.waitForSpinnerToDisappear();

        // type into typeahead and press Enter to add
        UIActions.typeText(noteInputBy, note);
  
       //save
       UIActions.typeText(usernameInputBy, username);
       UIActions.typeText(passwordInputBy, password);

        UIActions.click(submitButtonBy);
     

        log.info("✅ Monitor note submitted");
    }

    /**
     * Close the monitor notes modal
     */
    public void verifyMonitorNotesVisibleAndClose() {
        assertTrue(verifyMonitorNoteSaved(), "Monitor note should be saved before closing the modal");
        UIActions.click(closeButtonBy);
        UIActions.waitForSpinnerToDisappear();
        log.info("✅ Monitor Notes modal closed");
    }

    /**
     * Verify that the latest monitor note contains today's date and a valid HH:mm time.
     */
    public boolean verifyMonitorNoteSaved() {
        UIActions.waitForSpinnerToDisappear();
        String latest = UIActions.findElementWithWait(notesTableRowsBy).getText();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        boolean containsTime = Pattern.compile("(?<!\\d)(?:[01]\\d|2[0-3]):[0-5]\\d(?!\\d)")
            .matcher(latest).find();
        return latest.contains(today) && containsTime;
    }

    /**
     * Return the list of visible monitor notes texts inside the modal (best-effort)
     */
    public List<String> getMonitorNotesHistory() {
        List<String> notes = new ArrayList<>();
        List<WebElement> rows = DriverManager.getInstance().findElements(notesTableRowsBy);
        for (WebElement row : rows) {
                WebElement td = row.findElement(org.openqa.selenium.By.xpath("./td[1]"));
                notes.add(td.getText().trim());
        }
        return notes;
    }
}
