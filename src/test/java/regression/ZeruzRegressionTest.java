package regression;

import base.BaseSuit;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import actionUtilies.UIActions;
import pages.mainPages.WomenEmergencyPatientsPage;
import pages.menu.InnerMenuPage;
import pages.midwife.GynecologyFollowupPage;
import pages.midwife.InductionsPage;
import pages.midwife.ZeruzModalPage;
import pages.PatientBoxPage;

@Slf4j
public class ZeruzRegressionTest extends BaseSuit {

    private WomenEmergencyPatientsPage womenEmergencyPatientsPage;
    private GynecologyFollowupPage gynecologyFollowupPage;
    private ZeruzModalPage zeruzModalPage;
    private InductionsPage inductionsPage;
    private InnerMenuPage innerMenuPage;
    private PatientBoxPage patientBoxPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        womenEmergencyPatientsPage = new WomenEmergencyPatientsPage();
        gynecologyFollowupPage = new GynecologyFollowupPage();
        zeruzModalPage = new ZeruzModalPage();
        inductionsPage = new InductionsPage();
        innerMenuPage = new InnerMenuPage();
        patientBoxPage = new PatientBoxPage();
    }

    @Test(description = "Zeruz transfer to delivery room and cross-screen verification")
    public void zeruzTransferToDeliveryRoom() throws Exception {
        loginAsDoctor();
        chooseDepartment("מיון נשים");

        // Open first patient
        womenEmergencyPatientsPage.choosePatient(1);
        UIActions.waitForSpinnerToDisappear();

        // Capture patient display name from header
        String first = patientBoxPage.getPatientFirstDisplay();
        String last = patientBoxPage.getPatientLastName();
        String patientDisplayName = (first + " " + last).trim();
        log.info("Captured patient display name: {}", patientDisplayName);

        // Open Zeruz modal and add the specific transfer directive
        gynecologyFollowupPage.clickZeruz();
        UIActions.waitForSpinnerToDisappear();
        ZeruzModalPage zeruz = new ZeruzModalPage();
        zeruz.fillAndSaveZeruz("זירוז בחדר לידה");

        // Verify modal closed
        UIActions.waitForSpinnerToDisappear();
        Assert.assertFalse(zeruz.isZeruzModalVisible(), "Zeruz modal should be closed after save");
   

        // Navigate to Inductions dashboard
        innerMenuPage.navigateToMenuEntry("רשימת מטופלות", false);

        innerMenuPage.navigateToMenuEntry("רשימת זרוזים", false);
        UIActions.waitForSpinnerToDisappear();

        // Look for patient under the target section
        String sectionTitle = "ממתינות להעברה לחדר לידה";
        Assert.assertNotNull(inductionsPage.findSectionByTitle(sectionTitle), "Section '" + sectionTitle + "' should exist");

        // Find patient's row inside the section and verify it shows the transfer directive
        WebElement patientRow = inductionsPage.findPatientRowInSection(sectionTitle, patientDisplayName);
        Assert.assertNotNull(patientRow, "Patient row should be present in section");
        String rowText = patientRow.getText();
        Assert.assertTrue(rowText.contains("זירוז בחדר לידה") , "Patient row should display the transfer directive summary");
    }

    @Test(description = "GF-08c: Add a new Zeruz and verify previous directive is marked canceled")
    public void gf08c_previousDirectiveCancelation() throws Exception {
        loginAsDoctor();
        chooseDepartment("מיון נשים");

        womenEmergencyPatientsPage.choosePatient(1);
        UIActions.waitForSpinnerToDisappear();

        // Add first directive (type A)
      //  gynecologyFollowupPage.clickZeruz();
        // UIActions.waitForSpinnerToDisappear();
      //   ZeruzModalPage modal = new ZeruzModalPage();
        // modal.addDirectiveKeepModalOpen("ציטוטק");

        // // Add second directive (type B) which should cause first to be marked canceled
        // modal.addDirectiveKeepModalOpen("סטריפינג");

        // Verify in-modal that the earlier directive is marked canceled
        // boolean prevCanceled = modal.isPreviousDirectiveMarkedCancelled("ציטוטק");
        // Assert.assertTrue(prevCanceled, "Previous directive ציטוטק should be marked canceled in the modal");

        // // Close modal and verify on Inductions dashboard (row may show canceled state or the new directive)
        // modal.closeZeruzModal();
         String patientName = patientBoxPage.getPatientFirstDisplay() + " " + patientBoxPage.getPatientLastName();

         innerMenuPage.navigateToMenuEntry("רשימת מטופלות", false);

        innerMenuPage.navigateToMenuEntry("רשימת זרוזים", false);
        //UIActions.waitForSpinnerToDisappear();

        Assert.assertNotNull(inductionsPage.findPatientRowInSection("זירוזים פעילים", patientName), "Patient row should exist on Inductions dashboard");

        // Optional: open previous inductions popup and assert the canceled entry exists
        inductionsPage.openPreviousInductionsPopup("זירוזים פעילים", patientName);
        Assert.assertTrue(inductionsPage.previousInductionsPopupContains("ציטוטק"), "Previous inductions popup should contain ציטוטק (canceled)");
    }
}
