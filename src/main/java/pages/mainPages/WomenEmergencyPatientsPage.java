package pages.mainPages;

import actionUtilies.UIActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.PatientBoxPage;

import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertTrue;

@Slf4j
public class WomenEmergencyPatientsPage extends BasePage {

       private PatientBoxPage patientBoxPage = new PatientBoxPage();

    public WomenEmergencyPatientsPage() {
      //  UIActions.waitForSpinnerToDisappear();
    }

    // Women Emergency Department has 3 separate tables with delivery-table class:
    // 1. לא משובצות (Not hospitalized)
    // 2. מיילדותי (Delivery/Obstetric)
    // 3. הריון עודף (High-risk pregnancy)
    


    private By allDeliveryTables = By.xpath("//p-table[contains(@class,'delivery-table')]");
    private By allPatientRows = By.xpath("//p-table[contains(@class,'delivery-table')]//tbody//tr");
    
    // Locators for specific categories (using header text to identify)
    private By notHospitalizedSection = By.xpath("//div[contains(text(),'לא משובצות')]//following-sibling::div//p-table[contains(@class,'delivery-table')]");
    private By deliverySection = By.xpath("//div[contains(text(),'מיילדותי')]//following-sibling::div//p-table[contains(@class,'delivery-table')]");
    private By highRiskSection = By.xpath("//div[contains(text(),'הריון עודף')]//following-sibling::div//p-table[contains(@class,'delivery-table')]");

    /**
     * Verify all delivery tables are loaded on the page
     */
    public void verifyPatientsTablesAreLoaded() {
        log.info("Verifying all Women Emergency Department tables are loaded");
        List<WebElement> tables = UIActions.findElementsWithWait(allDeliveryTables);
        assertTrue(tables.size() >= 1,
                "At least one delivery table should be displayed for Women Emergency Department");
        log.info("✓ Found {} Women Emergency Department table(s)", tables.size());
    }

    /**
     * Get total patient count across all delivery tables
     */
    public int getTotalPatientsCount() {
        try {
            List<WebElement> rows = UIActions.findElementsWithWait(allPatientRows);
            log.info("Total patients across all delivery tables: {}", rows.size());
            return rows.size();
        } catch (Exception e) {
            log.warn("Could not get total patients count: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Get patient count for a specific category
     */
    public int getPatientsCountByCategory(String category) {
        try {
            By categoryLocator = getCategoryLocator(category);
            WebElement table = UIActions.findElementWithWait(categoryLocator);
            List<WebElement> rows = table.findElements(By.xpath(".//tbody//tr"));
            int count = rows.size();
            log.info("Patients count in '{}' category: {}", category, count);
            return count;
        } catch (Exception e) {
            log.warn("Could not get patients count for category '{}': {}", category, e.getMessage());
            return 0;
        }
    }

    /**
     * Verify a specific category table has data
     */
    public void verifyTableHasDataByCategory(String category) {
        log.info("Verifying '{}' category table contains data", category);
        int count = getPatientsCountByCategory(category);
        assertTrue(count > 0,
                String.format("'%s' category table should contain at least one patient", category));
        log.info("✓ '{}' category table contains {} patient(s)", category, count);
    }

    /**
     * Get category locator based on category name
     */
    private By getCategoryLocator(String category) {
        String cat = category.toLowerCase();
        if (cat.equals("לא משובצות") || cat.equals("not hospitalized")) {
            return notHospitalizedSection;
        } else if (cat.equals("מיילדותי") || cat.equals("delivery") || cat.equals("obstetric")) {
            return deliverySection;
        } else if (cat.equals("הריון עודף") || cat.equals("high-risk") || cat.equals("excess pregnancy")) {
            return highRiskSection;
        } else {
            throw new IllegalArgumentException("Unknown category: " + category);
        }
    }

    /**
     * Verify all three categories are present
     */
    public void verifyAllCategoriesPresent() {
        log.info("Verifying all 3 Women Emergency Department categories are present");
        verifyTableHasDataByCategory("לא משובצות");
        verifyTableHasDataByCategory("מיילדותי");
        verifyTableHasDataByCategory("הריון עודף");
        log.info("✓ All 3 categories verified successfully");
    }

    /**
     * Get patient count for all three categories and log summary
     */
    public void printCategorySummary() {
        int notHospitalized = getPatientsCountByCategory("לא משובצות");
        int delivery = getPatientsCountByCategory("מיילדותי");
        int highRisk = getPatientsCountByCategory("הריון עודף");
        int total = getTotalPatientsCount();
        
        log.info("========== Women Emergency Department Summary ==========");
        log.info("לא משובצות (Not Hospitalized): {}", notHospitalized);
        log.info("מיילדותי (Delivery/Obstetric): {}", delivery);
        log.info("הריון עודף (High-Risk Pregnancy): {}", highRisk);
        log.info("TOTAL: {}", total);
        log.info("======================================================");
    }

    /**
     * Extract all patient data from UI tables
     * Returns a list of patient data rows from all delivery tables
     * @return List of lists, where each inner list contains cell values of one patient row
     */
    public List<List<String>> getUIPatientData() {
        log.info("Extracting all patient data from Women Emergency Department tables");
        List<List<String>> allPatientData = new ArrayList<>();
        
        try {
            List<WebElement> rows = UIActions.findElementsWithWait(allPatientRows);
            log.info("Found {} patient rows in UI", rows.size());
            
            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                List<String> cellValues = new ArrayList<>();
                
                // Get all cells (td) in this row
                List<WebElement> cells = row.findElements(By.xpath(".//td"));
                
                for (WebElement cell : cells) {
                    String cellText = cell.getText().trim();
                    cellValues.add(cellText);
                }
                
                if (!cellValues.isEmpty()) {
                    allPatientData.add(cellValues);
                    log.debug("Row {}: {} cells -> {}", i + 1, cellValues.size(), cellValues);
                }
            }
            
            log.info("✓ Extracted {} patient rows from UI", allPatientData.size());
            return allPatientData;
            
        } catch (Exception e) {
            log.error("Error extracting patient data from UI: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Extract patient data from a specific category only
     * @param category Category name (לא משובצות, מיילדותי, הריון עודף)
     * @return List of patient data rows for the specific category
     */
    public List<List<String>> getUIPatientDataByCategory(String category) {
        log.info("Extracting patient data from '{}' category", category);
        List<List<String>> categoryPatientData = new ArrayList<>();
        
        try {
            By categoryLocator = getCategoryLocator(category);
            WebElement table = UIActions.findElementWithWait(categoryLocator);
            List<WebElement> rows = table.findElements(By.xpath(".//tbody//tr"));
            
            log.info("Found {} patient rows in '{}' category", rows.size(), category);
            
            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                List<String> cellValues = new ArrayList<>();
                
                // Get all cells (td) in this row
                List<WebElement> cells = row.findElements(By.xpath(".//td"));
                
                for (WebElement cell : cells) {
                    String cellText = cell.getText().trim();
                    cellValues.add(cellText);
                }
                
                if (!cellValues.isEmpty()) {
                    categoryPatientData.add(cellValues);
                    log.debug("Row {}: {} cells -> {}", i + 1, cellValues.size(), cellValues);
                }
            }
            
            log.info("✓ Extracted {} patient rows from '{}' category", categoryPatientData.size(), category);
            return categoryPatientData;
            
        } catch (Exception e) {
            log.error("Error extracting patient data from category '{}': {}", category, e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Get specific cell value from patient row
     * @param rowIndex Row index (0-based)
     * @param cellIndex Cell index (0-based)
     * @return Cell text value or empty string if not found
     */
    public String getPatientCellValue(int rowIndex, int cellIndex) {
        try {
            List<WebElement> rows = UIActions.findElementsWithWait(allPatientRows);
            if (rowIndex >= rows.size()) {
                log.warn("Row index {} is out of bounds", rowIndex);
                return "";
            }
            
            WebElement row = rows.get(rowIndex);
            List<WebElement> cells = row.findElements(By.xpath(".//td"));
            
            if (cellIndex >= cells.size()) {
                log.warn("Cell index {} is out of bounds for row {}", cellIndex, rowIndex);
                return "";
            }
            
            return cells.get(cellIndex).getText().trim();
            
        } catch (Exception e) {
            log.error("Error getting cell value at row {}, cell {}: {}", rowIndex, cellIndex, e.getMessage());
            return "";
        }
    }

    /**
     * Choose a patient by index from Women Emergency Department tables
     * @param patientIndex 0-based index of the patient row to select
     */
    public void choosePatientByIndex(int patientIndex) {
        try {
            log.info("Attempting to choose patient at index: {}", patientIndex);
            
            List<WebElement> rows = UIActions.findElementsWithWait(allPatientRows);
            
            if (patientIndex >= rows.size()) {
                throw new IndexOutOfBoundsException(
                    String.format("Patient index %d is out of bounds. Total patients: %d", 
                    patientIndex, rows.size()));
            }
            
            WebElement selectedRow = rows.get(patientIndex);
            
            // Try to find a clickable link/button in the first cell of the row
            List<WebElement> clickableElements = selectedRow.findElements(By.xpath(".//td//a | .//td//button"));
            
            if (!clickableElements.isEmpty()) {
                // Click on the first clickable element (usually the patient name link)
                UIActions.click(clickableElements.get(0));
                log.info("✓ Selected patient at index {} successfully", patientIndex);
                UIActions.waitForSpinnerToDisappear();
            } else {
                // If no clickable element, try clicking the entire first cell
                List<WebElement> firstCells = selectedRow.findElements(By.xpath(".//td[1]"));
                if (!firstCells.isEmpty()) {
                    UIActions.click(firstCells.get(0));
                    log.info("✓ Selected patient at index {} by clicking first cell", patientIndex);
                    UIActions.waitForSpinnerToDisappear();
                } else {
                    throw new Exception("No clickable element found in patient row at index " + patientIndex);
                }
            }
            
        } catch (IndexOutOfBoundsException e) {
            log.error("❌ Index out of bounds: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("❌ Error selecting patient at index {}: {}", patientIndex, e.getMessage());
            throw new RuntimeException("Failed to select patient at index " + patientIndex, e);
        }

        UIActions.waitForSpinnerToDisappear();
       // assertTrue(gynecologyFollowupPage.getPageStatus(), "Gynecology Followup page did not load after selecting patient");
    }

    /**
     * Choose a patient by 1-based index (wrapper for convenience)
     * Converts 1-based index to 0-based and calls choosePatientByIndex
     * @param patientNumber 1-based patient number (1, 2, 3, etc.)
     */
    public void choosePatient(int patientNumber) {
        if (patientNumber <= 0) {
            throw new IllegalArgumentException("Patient number must be >= 1");
        }
        choosePatientByIndex(patientNumber - 1);
        patientBoxPage.verifyPatientDetailsExisting();

    }

   
}
