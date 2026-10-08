package pages.midwife;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import drivers.DriverManager;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MonitorNotesModalPage extends BasePage {

    public MonitorNotesModalPage() {
        super();
        UIActions.waitForSpinnerToDisappear();
        log.info("✅ MonitorNotesModalPage initialized");
    }

    // ============ Locators ============
    private final By modalContainerBy = By.xpath("//ngb-modal-window[contains(@class,'modal') and contains(@class,'show')]");
    private final By modalTitleBy = By.xpath("//ngb-modal-window//h5[contains(normalize-space(.),'הערות למוניטור')]");
    private final By noteInputBy = By.id("comment-typeahead");
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
        // Press Enter to accept the typeahead suggestion / submit text
     //   UIActions.sendKeys(noteInputBy, org.openqa.selenium.Keys.ENTER);
        UIActions.waitForSpinnerToDisappear();

        UIActions.click(submitButtonBy);
        UIActions.waitForSpinnerToDisappear();

        // If signing modal appears, use existing helper
        if (userSignModalPage != null && username != null && password != null) {
            userSignModalPage.signModal(username, password);
        }

        UIActions.waitForSpinnerToDisappear();
        log.info("✅ Monitor note submitted");
    }

    /**
     * Close the monitor notes modal
     */
    public void closeMonitorNotesModal() {
        UIActions.click(closeButtonBy);
        UIActions.waitForSpinnerToDisappear();
        log.info("✅ Monitor Notes modal closed");
    }

    /**
     * Return the list of visible monitor notes texts inside the modal (best-effort)
     */
    public List<String> getMonitorNotesHistory() {
        List<String> notes = new ArrayList<>();
        List<WebElement> rows = DriverManager.getInstance().findElements(notesTableRowsBy);
        for (WebElement row : rows) {
            try {
                WebElement td = row.findElement(org.openqa.selenium.By.xpath("./td[1]"));
                notes.add(td.getText().trim());
            } catch (Exception e) {
                notes.add(row.getText().trim());
            }
        }
        return notes;
    }
}
