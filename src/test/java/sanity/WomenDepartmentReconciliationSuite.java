package sanity;

import base.BaseSuit;
import db.services.DBPatientService;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import models.GynecologyPatient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.mainPages.WomenEmergencyPatientsPage;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Reconciliation Test Suite
 * Compares data from Database (Stored Procedure) with UI (Women Emergency Department)
 */
@Slf4j
@Epic("Women Emergency Department - DB vs UI Reconciliation")
public class WomenDepartmentReconciliationSuite extends BaseSuit {

    private WomenEmergencyPatientsPage womenEmergencyPatientsPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        womenEmergencyPatientsPage = new WomenEmergencyPatientsPage();
    }

    @Feature("Data Reconciliation")
    @Story("Verify DB patients are displayed on UI")
    @Test
    public void test_01_verifyDBPatientsViaUI() {
        log.info("=== Test 01: Verify DB Patients Displayed on UI ===");
        
        // Get DB gynecology patients
        List<GynecologyPatient> dbPatients = DBPatientService.getWomenEmergencyPatientsFromDB();
        log.info("Retrieved {} patients from database", dbPatients.size());
        
        if (dbPatients.isEmpty()) {
            log.warn("⚠️ No patients in database to verify");
            return;
        }
        
        // Get UI data
        loginAsDoctor();
        chooseDepartment("מיון נשים");
        List<List<String>> uiPatientData = womenEmergencyPatientsPage.getUIPatientData();
        log.info("Retrieved {} rows from UI", uiPatientData.size());
        
        // Flatten UI data for easier searching
        List<String> uiDataFlattened = new java.util.ArrayList<>();
        for (List<String> row : uiPatientData) {
            for (String cell : row) {
                uiDataFlattened.add(cell.toLowerCase());
            }
        }
        
        // Verify each DB patient appears somewhere in UI
        log.info("========== VERIFICATION RESULTS ==========");
        int foundCount = 0;
        int notFoundCount = 0;
        
        for (GynecologyPatient dbPatient : dbPatients) {
            String patientId = dbPatient.getMispar_ishpuz();
            String patientName = dbPatient.getFullName();
            
            boolean idFound = uiDataFlattened.stream()
                    .anyMatch(cell -> cell.contains(patientId.toLowerCase()));
            
            boolean nameFound = uiDataFlattened.stream()
                    .anyMatch(cell -> cell.contains(patientName.toLowerCase()));
            
            if (idFound || nameFound) {
                log.info("✓ Found patient {} ({}) on UI", patientId, patientName);
                foundCount++;
            } else {
                log.warn("✗ Patient {} ({}) NOT found on UI", patientId, patientName);
                notFoundCount++;
            }
        }
        
        log.info("========== SUMMARY ==========");
        log.info("Found on UI: {}", foundCount);
        log.info("Not found on UI: {}", notFoundCount);
        log.info("==============================");
        
        // Verify at least some patients match
        assertTrue(foundCount > 0, "At least some DB patients should be displayed on UI");
    }
}
