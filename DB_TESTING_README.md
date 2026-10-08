# Database Testing Infrastructure

## Overview
Complete database testing infrastructure with utilities for executing SQL queries, managing connections, and validating data integrity.

## Architecture

### Components

#### 1. **ManageDB.java** (Singleton)
- Manages database connection lifecycle
- Reads credentials from `DataConfig.xml` 
- Uses environment variable to determine which DB to connect to
- Methods:
  - `getInstance()` - Get singleton instance
  - `getConnections()` - Get active connection
  - `getStatement()` - Get statement for executing queries
  - `closeConnection()` - Close connection

#### 2. **DBExecuter.java** (Static Utility)
- Executes all SQL queries against the database
- Handles result parsing and error handling
- Methods:
  - `executeSelect(query)` - Returns single String value
  - `executeQueryToList(query)` - Returns List<String> of first column
  - `executeSelectFirstRow(query)` - Returns List<String> of all columns of first row
  - `ecuteUpdateQuery(query)` - Returns affected rows count
  - `executeMultiDeleteProcedure(sql)` - Executes stored procedure with multiple deletes
  - `handleAnyQuery(sql)` - Handles any query type (SELECT/INSERT/UPDATE/DELETE/PROCEDURE)
  - `isExecutionSuccessful(sql)` - Validates query syntax

#### 3. **BaseDBTest.java** (Test Base Class)
- Extends all DB test classes
- Handles connection setup/teardown
- Provides convenient wrapper methods for all DBExecuter operations
- Provides helper methods for common DB operations

#### 4. **DBTestUtils.java** (Static Utility)
- Additional utility methods for common test scenarios
- Data analysis queries (COUNT, MAX, MIN, AVG, SUM)
- Data integrity checks (orphaned records, NULL values)
- Comparison operations

---

## Configuration

### DataConfig.xml
Add database connection details:
```xml
<root>
    <DB-PROD>Server=prod-db-server;Database=EMR;Port=1433</DB-PROD>
    <DB-STAGING>Server=staging-db-server;Database=EMR;Port=1433</DB-STAGING>
    <DB-DEV>Server=dev-db-server;Database=EMR;Port=1433</DB-DEV>
    <DBUserName>your-db-user</DBUserName>
    <DBPassword>your-db-password</DBPassword>
</root>
```

### Constants.java
Set current environment:
```java
public static final String CURRENT_ENV = "PROD"; // or "STAGING", "DEV"
```

---

## Usage

### Basic Test Class

```java
@Slf4j
public class MyDBTest extends BaseDBTest {
    
    @Test
    public void test_queryData() {
        // Single value query
        String name = querySingleValue("SELECT name FROM patients WHERE id = 1");
        
        // Column values as list
        List<String> names = queryColumnValues("SELECT name FROM patients LIMIT 10");
        
        // Get complete first row
        List<String> row = queryFirstRow("SELECT * FROM patients WHERE id = 1");
        
        // Execute update
        int affected = executeUpdate("UPDATE patients SET status = 'active' WHERE id = 1");
    }
}
```

---

## API Reference

### BaseDBTest Methods

#### Query Methods
```java
// Get single String value
String value = querySingleValue("SELECT name FROM table WHERE id = 1");

// Get list of first column values
List<String> values = queryColumnValues("SELECT name FROM table");

// Get all columns of first row
List<String> row = queryFirstRow("SELECT * FROM table");

// Execute any query type
Object result = executeAnyQuery("SELECT * FROM table");
```

#### DML Methods
```java
// Insert/Update/Delete
int rows = executeUpdate("INSERT INTO table (...) VALUES (...)");

// Stored procedure with multiple operations
List<Integer> counts = executeMultiDeleteProcedure("EXEC sp_DeleteData");

// Validate query syntax
boolean valid = isQueryValid("SELECT * FROM table");
```

#### Helper Methods
```java
// Count rows in table
int count = getRowCount("patients", null);
int count = getRowCount("patients", "status = 'active'");

// Check if record exists
boolean exists = recordExists("SELECT id FROM patients WHERE name = 'John'");

// Get specific column value
String dept = getColumnValue("patients", "department", "id = 1");

// Insert record
int rows = insertRecord("patients", "name,age", "'John',25");

// Update record
int rows = updateRecord("patients", "status = 'inactive'", "id = 1");

// Delete record
int rows = deleteRecord("patients", "id = 1");
```

---

### DBTestUtils Methods

#### Data Analysis
```java
// Check if values exist in column
boolean exists = DBTestUtils.verifyValuesExist("patients", "status", 
                                              Arrays.asList("active", "inactive"));

// Get distinct values
List<String> statuses = DBTestUtils.getDistinctValues("patients", "status", 10);

// Count occurrences
int count = DBTestUtils.countValueOccurrences("patients", "status", "active");

// Aggregate functions
String max = DBTestUtils.getMaxValue("patients", "age");
String min = DBTestUtils.getMinValue("patients", "age");
String avg = DBTestUtils.getAverageValue("patients", "age");
String sum = DBTestUtils.getSumValue("patients", "age");
```

#### Data Integrity
```java
// Check if table is empty
boolean empty = DBTestUtils.isTableEmpty("patients");

// Count NULL values
int nullCount = DBTestUtils.countNullValues("patients", "middle_name");

// Count non-NULL values
int nonNullCount = DBTestUtils.countNonNullValues("patients", "middle_name");

// Check for orphaned records (foreign key validation)
int orphaned = DBTestUtils.checkOrphanedRecords("appointments", "patient_id", 
                                               "patients", "id");

// Compare two columns
int differences = DBTestUtils.compareTwoColumns("patients", "email", "email_backup");
```

#### Utility
```java
// Execute query with error handling
boolean success = DBTestUtils.executeSafeQuery("SELECT * FROM patients");

// Get all table data
List<String> data = DBTestUtils.getAllTableData("patients", 100); // limit to 100 rows
```

---

## Example Test Scenarios

### Scenario 1: Verify Patient Data Exists
```java
@Test
public void test_patientDataExists() {
    // Verify specific patient exists
    boolean exists = recordExists("SELECT id FROM patients WHERE name = 'יוחנן כהן'");
    assertTrue(exists, "Patient should exist in database");
    
    // Get patient ID
    String patientId = getColumnValue("patients", "id", "name = 'יוחנן כהן'");
    assertNotNull(patientId, "Patient ID should be found");
}
```

### Scenario 2: Verify Department Sanity
```java
@Test
public void test_departmentSanity() {
    // Count departments
    int deptCount = getRowCount("departments", null);
    assertTrue(deptCount > 0, "Should have at least one department");
    
    // Verify Women Emergency Department exists
    String womenDeptId = getColumnValue("departments", "id", 
                                       "department_name = 'מיון נשים'");
    assertNotNull(womenDeptId, "Women Emergency Department should exist");
    
    // Get patient count in Women Emergency
    int womenPatients = getRowCount("patients", "department_id = " + womenDeptId);
    log.info("Women Emergency Department has {} patients", womenPatients);
}
```

### Scenario 3: Data Integrity Check
```java
@Test
public void test_dataIntegrity() {
    // Check for orphaned appointment records
    int orphaned = DBTestUtils.checkOrphanedRecords("appointments", "patient_id", 
                                                   "patients", "id");
    assertEquals(orphaned, 0, "No orphaned appointment records should exist");
    
    // Check for NULL values in required fields
    int nullIds = DBTestUtils.countNullValues("patients", "patient_id");
    assertEquals(nullIds, 0, "patient_id field should not have NULL values");
}
```

### Scenario 4: Performance Monitoring
```java
@Test
public void test_recordCounts() {
    // Get count summary
    int totalPatients = getRowCount("patients", null);
    int activePatients = getRowCount("patients", "status = 'active'");
    int inactivePatients = getRowCount("patients", "status = 'inactive'");
    
    log.info("Patient Statistics:");
    log.info("  Total: {}", totalPatients);
    log.info("  Active: {}", activePatients);
    log.info("  Inactive: {}", inactivePatients);
    
    // Verify counts add up
    assertEquals(totalPatients, activePatients + inactivePatients, 
                "Patient counts should match");
}
```

---

## Best Practices

### 1. Connection Management
- Connections are automatically opened in `@BeforeClass` and closed in `@AfterClass`
- No need to manually manage connections in test methods

### 2. Query Writing
- Use table/column names from your actual schema
- Always include table aliases for complex queries
- Use proper escaping for special characters in WHERE clauses

### 3. Test Organization
- One test file per logical domain (e.g., DBPatientTests, DBDepartmentTests)
- Use descriptive test method names following `test_XX_description` pattern
- Group related assertions together

### 4. Logging
- All methods use `@Slf4j` for logging
- Check logs to understand query execution flow
- Use `log.info()` for important test milestones

### 5. Error Handling
- Methods throw `RuntimeException` with descriptive messages
- Use `try-catch` for optional queries
- Validate results before assertions

### 6. Test Data
- Use `@Test(enabled = false)` for tests that modify data
- Enable only when needed for data setup/cleanup
- Always cleanup test data after tests complete

---

## File Structure

```
src/
├── main/
│   ├── java/
│   │   ├── actionUtilies/
│   │   │   └── DBExecuter.java
│   │   ├── db/
│   │   │   └── DBTestUtils.java
│   │   └── helpers/
│   │       └── ManageDB.java
│   └── resources/
│       └── Configuration/
│           └── DataConfig.xml
└── test/
    └── java/
        ├── base/
        │   └── BaseDBTest.java
        └── db/
            └── DBSampleSuite.java
```

---

## Troubleshooting

### Connection Refused
- Verify database server is running
- Check connection string in DataConfig.xml
- Verify credentials in DataConfig.xml
- Check firewall/network connectivity

### Query Timeout
- Verify query performance
- Check database indexes
- Limit result set size with LIMIT clause
- Check for locking/blocking

### NULL Pointer Exception
- Verify query returns results before calling rs.next()
- Use `executeSelectFirstRow()` which returns empty list if no rows
- Check column types match expected values

### Encoding Issues
- Verify DataConfig.xml is UTF-8 encoded
- Check database collation settings
- Use proper escaping for special characters

---

## Future Enhancements
- [ ] Connection pooling for better performance
- [ ] Query builder for safer SQL generation
- [ ] Data comparison utilities
- [ ] Performance profiling decorators
- [ ] Test data factories
- [ ] Mock database implementations

---

## Contact
For questions or issues, refer to the inline code documentation in each class.
