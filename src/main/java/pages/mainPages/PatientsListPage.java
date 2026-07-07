package pages.mainPages;
import actionUtilies.UIActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;


public class PatientsListPage {

    public PatientsListPage() {
        UIActions.waitForSpinnerToDisappear();
    }

   public  By list_patients = By.xpath("//p-table[contains(@class,'depMeushpazim-table patients-table')]//tr//td[1]");

    public List<WebElement> getPatientRows() {
        return UIActions.findElementsWithWait(list_patients);
    }
    public void choosePatient(int index){
        try {
            // Retry logic: attempt to click patient multiple times due to stale elements
            int maxAttempts = 3;
            int attemptCount = 0;
            Exception lastException = null;
            
            while (attemptCount < maxAttempts) {
                try {
                    attemptCount++;
                    
                    // Wait for the table to be visible and stable
                    UIActions.waitForVisible(list_patients);
                    
                    // Get fresh list of patient rows
                    List<WebElement> patientList = getPatientRows();
                    UIActions.waitForListToHaveElements(patientList);
                    
                    if (patientList.isEmpty()) {
                        throw new RuntimeException("Patient list is empty");
                    }
                    
                    if (index < 1 || index > patientList.size()) {
                        throw new RuntimeException("Patient index " + index + " out of bounds (total: " + patientList.size() + ")");
                    }
                    
                    // Small delay before clicking to let DOM settle
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    
                    // Get fresh element and click it
                    WebElement patientRow = patientList.get(index - 1);
                    patientRow.click();
                    
                    // If we get here, click was successful
                    return;
                    
                } catch (org.openqa.selenium.StaleElementReferenceException e) {
                    lastException = e;
                    if (attemptCount < maxAttempts) {
                        // Try again with fresh DOM acquisition
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
            
            // If we exhausted retries, throw the last exception
            if (lastException != null) {
                throw new RuntimeException("Failed to select patient after " + maxAttempts + " attempts due to stale elements", lastException);
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to select patient at index " + index + ": " + e.getMessage(), e);
        }
    }

    /**
     * Returns true if the patients list is visible and has at least one row.
     */
    public boolean verifyPatientsListVisible() {
        try {
            List<WebElement> rows = getPatientRows();
            return rows != null && !rows.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

}
