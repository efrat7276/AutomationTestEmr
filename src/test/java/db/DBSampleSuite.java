package db;

import base.BaseDBTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Example Database Test Suite
 * Demonstrates how to write tests using BaseDBTest
 */
@Slf4j
@Epic("Database Tests")
public class DBSampleSuite extends BaseDBTest {

    // Example constants - adjust to your actual database tables/columns
    private static final String PATIENTS_TABLE = "patients";
    private static final String USERS_TABLE = "users";
    private static final String DEPARTMENTS_TABLE = "departments";

    @Feature("Database Connectivity")
    @Story("Verify database connection is established")
    @Test
    public void test_01_databaseConnectionEstablished() {
        log.info("=== Test 01: Verify Database Connection ===");
        
        // Simple query to verify connection works
        String query = "SELECT COUNT(*) FROM " + DEPARTMENTS_TABLE;
        String result = querySingleValue(query);
        
        assertNotNull(result, "Query should return a result");
        assertTrue(Integer.parseInt(result) >= 0, "Row count should be >= 0");
        
        log.info("✓ Database connection verified");
    }

    @Feature("Data Retrieval")
    @Story("Query single value from database")
    @Test
    public void test_02_querySingleValue() {
        log.info("=== Test 02: Query Single Value ===");
        
        // Get first department name
        String query = "SELECT TOP 1 department_name FROM " + DEPARTMENTS_TABLE;
        String departmentName = querySingleValue(query);
        
        assertNotNull(departmentName, "Should return a department name");
        log.info("✓ Retrieved department: {}", departmentName);
    }

    @Feature("Data Retrieval")
    @Story("Query column values as list")
    @Test
    public void test_03_queryColumnValuesToList() {
        log.info("=== Test 03: Query Column Values to List ===");
        
        // Get all department names
        String query = "SELECT TOP 5 department_name FROM " + DEPARTMENTS_TABLE;
        List<String> departments = queryColumnValues(query);
        
        assertNotNull(departments, "Should return a list");
        assertTrue(departments.size() > 0, "List should contain at least one department");
        
        log.info("✓ Retrieved {} departments", departments.size());
        departments.forEach(dept -> log.info("  - {}", dept));
    }

    @Feature("Data Retrieval")
    @Story("Query first row with all columns")
    @Test
    public void test_04_queryFirstRow() {
        log.info("=== Test 04: Query First Row ===");
        
        // Get complete first row from patients table
        String query = "SELECT TOP 1 * FROM " + PATIENTS_TABLE;
        List<String> row = queryFirstRow(query);
        
        assertNotNull(row, "Should return a row");
        assertTrue(row.size() > 0, "Row should contain at least one column");
        
        log.info("✓ Retrieved row with {} column(s)", row.size());
    }

    @Feature("Row Count")
    @Story("Count rows in table")
    @Test
    public void test_05_getRowCount() {
        log.info("=== Test 05: Get Row Count ===");
        
        // Count all departments
        int departmentCount = getRowCount(DEPARTMENTS_TABLE, null);
        
        assertTrue(departmentCount >= 0, "Row count should be >= 0");
        log.info("✓ Total departments: {}", departmentCount);
    }

    @Feature("Row Count")
    @Story("Count rows with WHERE clause")
    @Test
    public void test_06_getRowCountWithCondition() {
        log.info("=== Test 06: Get Row Count with Condition ===");
        
        // Count active patients (example - adjust to your actual column names)
        String whereClause = "status = 'active'"; // Adjust based on your schema
        int activePatientCount = getRowCount(PATIENTS_TABLE, whereClause);
        
        assertTrue(activePatientCount >= 0, "Row count should be >= 0");
        log.info("✓ Active patients count: {}", activePatientCount);
    }

    @Feature("Record Existence")
    @Story("Check if record exists")
    @Test
    public void test_07_recordExists() {
        log.info("=== Test 07: Check Record Existence ===");
        
        // Check if specific department exists (adjust the condition)
        String query = "SELECT TOP 1 department_id FROM " + DEPARTMENTS_TABLE + 
                       " WHERE department_name = 'מיון נשים'";
        boolean exists = recordExists(query);
        
        log.info("✓ Record exists: {}", exists);
        // Note: May be true or false depending on your data
    }

    @Feature("Column Retrieval")
    @Story("Get specific column value with WHERE clause")
    @Test
    public void test_08_getColumnValue() {
        log.info("=== Test 08: Get Column Value ===");
        
        // Get department ID for specific department
        String departmentId = getColumnValue(DEPARTMENTS_TABLE, "department_id", 
                                             "department_name = 'מיון נשים'");
        
        if (departmentId != null) {
            log.info("✓ Found department ID: {}", departmentId);
        } else {
            log.info("✓ No matching department found");
        }
    }

    @Feature("Query Validation")
    @Story("Verify SQL query is valid")
    @Test
    public void test_09_isQueryValid() {
        log.info("=== Test 09: Validate SQL Query ===");
        
        // Validate a SELECT query
        String validQuery = "SELECT TOP 1 * FROM " + DEPARTMENTS_TABLE;
        boolean isValid = isQueryValid(validQuery);
        
        assertTrue(isValid, "Valid query should pass validation");
        log.info("✓ Query validation passed");
    }

    @Feature("Query Validation")
    @Story("Invalid query detection")
    @Test(expectedExceptions = RuntimeException.class)
    public void test_10_invalidQueryDetection() {
        log.info("=== Test 10: Invalid Query Detection ===");
        
        // Attempt invalid query - should throw exception
        String invalidQuery = "SELECT * FROM NonExistentTable";
        isQueryValid(invalidQuery); // This should throw
        
        log.info("✓ Invalid query properly rejected");
    }

    // ============ DML Operations (Insert/Update/Delete) ============
    // Note: These are examples - adjust table names and values to match your schema

    @Feature("Data Insertion")
    @Story("Insert record using raw query")
    @Test(enabled = false) // Set to true only if you want to actually insert
    public void test_11_insertRecordRaw() {
        log.info("=== Test 11: Insert Record (Raw Query) ===");
        
        // Example: Insert a new department
        // IMPORTANT: Adjust this to match your actual table structure
        String query = "INSERT INTO " + DEPARTMENTS_TABLE + 
                       " (department_name, status) VALUES ('Test Dept', 'active')";
        
        int rowsInserted = executeUpdate(query);
        assertEquals(rowsInserted, 1, "Should insert exactly 1 row");
        
        log.info("✓ Record inserted successfully");
    }

    @Feature("Data Insertion")
    @Story("Insert record using helper method")
    @Test(enabled = false) // Set to true only if you want to actually insert
    public void test_12_insertRecordHelper() {
        log.info("=== Test 12: Insert Record (Helper Method) ===");
        
        // Example: Using helper method
        int rowsInserted = insertRecord(DEPARTMENTS_TABLE, 
                                       "department_name, status", 
                                       "'Test Dept', 'active'");
        
        assertEquals(rowsInserted, 1, "Should insert exactly 1 row");
        log.info("✓ Record inserted using helper method");
    }

    @Feature("Data Update")
    @Story("Update record")
    @Test(enabled = false) // Set to true only if you want to actually update
    public void test_13_updateRecord() {
        log.info("=== Test 13: Update Record ===");
        
        // Example: Update a department
        int rowsUpdated = updateRecord(DEPARTMENTS_TABLE, 
                                      "status = 'inactive'", 
                                      "department_name = 'Test Dept'");
        
        assertTrue(rowsUpdated > 0, "Should update at least 1 row");
        log.info("✓ {} record(s) updated", rowsUpdated);
    }

    @Feature("Data Deletion")
    @Story("Delete record")
    @Test(enabled = false) // Set to true only if you want to actually delete
    public void test_14_deleteRecord() {
        log.info("=== Test 14: Delete Record ===");
        
        // Example: Delete a test department
        int rowsDeleted = deleteRecord(DEPARTMENTS_TABLE, 
                                      "department_name = 'Test Dept'");
        
        assertTrue(rowsDeleted >= 0, "Deletion should complete without error");
        log.info("✓ {} record(s) deleted", rowsDeleted);
    }
}
