package pages;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import static org.testng.Assert.assertTrue;

@Slf4j
public class PatientBoxPage extends BasePage {
    public PatientBoxPage() {
     //   UIActions.waitForSpinnerToDisappear();
    }

    private By bar_deatails_patient = By.xpath("//app-patient-detail/div");

    // Demog-specific locators (from demog-data-bar)
    private By departmentBy = By.xpath("//demog-data-bar//span[contains(@class,'unitTopText')]");
    private By serviceNumberBy = By.xpath("//demog-data-bar//div[contains(@class,'line-number')]//span[not(contains(@class,'value-label'))]");
    private By patientFirstDisplayBy = By.xpath("//demog-data-bar//div[contains(@class,'col-3')]//strong[1]");
    private By patientLastNameBy = By.xpath("//demog-data-bar//div[contains(@class,'col-3')]//strong[2]");
    private By patientIdBy = By.xpath("//demog-data-bar//span[contains(@class,'data-label') and contains(normalize-space(.),'ת.ז')]/following-sibling::span[1]");
    private By patientAgeBy = By.xpath("//demog-data-bar//span[contains(@class,'data-label') and contains(normalize-space(.),'גיל')]/following-sibling::span[1]");
    private By patientFatherNameBy = By.xpath("//demog-data-bar//span[contains(@class,'data-label') and contains(normalize-space(.),'שם אב')]/following-sibling::span[1]");
    private By sensitiveAlertIconBy = By.xpath("//demog-data-bar//div[contains(@class,'alertDemog')]//i[contains(@class,'fa-exclamation')]");

    public void verifyPatientDetailsExisting(){
         assertTrue(UIActions.findElementWithWait(bar_deatails_patient).isDisplayed(),"patient details bar should be displayed but it's not.");
    }

    /**
     * Return the patient display name shown in the patient header (best-effort).
     * Tries specific header selectors first and falls back to the patient details bar.
     */
    public String getPatientDisplayNameFromHeader() {
        try {
            By headerBy = By.xpath("//app-patient-detail//h1 | //app-patient-detail//div[contains(@class,'patient-name')]");
            String txt = UIActions.getElementText(headerBy);
            if (txt == null || txt.isBlank()) {
                return UIActions.getElementText(bar_deatails_patient).trim();
            }
            return txt.trim();
        } catch (Exception e) {
            log.warn("Unable to read patient display name from header: {}", e.getMessage());
            try {
                return UIActions.getElementText(bar_deatails_patient).trim();
            } catch (Exception ex) {
                return "";
            }
        }
    }

    /**
     * Return the raw patient details bar text (useful for parsing IDs, ward, etc.)
     */
    public String getPatientDetailsBarText() {
        try {
            return UIActions.getElementText(bar_deatails_patient).trim();
        } catch (Exception e) {
            log.warn("Unable to read patient details bar text: {}", e.getMessage());
            return "";
        }
    }

    // ===== Demography getters =====
    public String getDepartment() {
        try { return UIActions.getElementText(departmentBy).trim(); } catch (Exception e) { return ""; }
    }

    public String getServiceNumber() {
        try { return UIActions.getElementText(serviceNumberBy).trim(); } catch (Exception e) { return ""; }
    }

    public String getPatientFirstDisplay() {
        try { return UIActions.getElementText(patientFirstDisplayBy).trim(); } catch (Exception e) { return ""; }
    }

    public String getPatientLastName() {
        try { return UIActions.getElementText(patientLastNameBy).trim(); } catch (Exception e) { return ""; }
    }

    public String getPatientId() {
        try { return UIActions.getElementText(patientIdBy).trim(); } catch (Exception e) { return ""; }
    }

    public String getPatientAge() {
        try { return UIActions.getElementText(patientAgeBy).trim(); } catch (Exception e) { return ""; }
    }

    public String getPatientFatherName() {
        try { return UIActions.getElementText(patientFatherNameBy).trim(); } catch (Exception e) { return ""; }
    }

    public boolean isSensitiveAlertPresent() {
        try { return UIActions.isElementPresentAndVisible(sensitiveAlertIconBy); } catch (Exception e) { return false; }
    }
}
