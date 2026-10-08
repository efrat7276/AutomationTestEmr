# Women Emergency Department - DB vs UI Reconciliation Testing

## Overview
Complete testing framework for validating data consistency between Database (via Stored Procedure) and UI (Women Emergency Department).

## Architecture

### Components

#### 1. **Patient.java** (Model)
Represents a single patient with fields:
- patientId
- name
- age
- room
- bed
- status
- doctor
- admissionDate
- condition

Use Lombok's `@Data`, `@Builder` for easy manipulation.

#### 2. **DBPatientService.java** (Service Layer)
Handles all database operations for Women Emergency Department:
```java
DBPatientService.getWomenEmergencyPatientsFromDB()     // Get all patients
DBPatientService.getPatientByIdFromDB(patientId)       // Get single patient
DBPatientService.getPatientsByStatusFromDB(status)     // Filter by status
DBPatientService.patientExistsInDB(patientId)          // Verify existence
DBPatientService.getTotalPatientCountFromDB()          // Count total
```

#### 3. **WomenEmergencyPatientsPage.java** (Page Object)
Enhanced with new methods to extract UI data:
```java
getUIPatientData()                           // Get all rows from all tables
getUIPatientDataByCategory(category)         // Get rows from specific category
getPatientCellValue(rowIndex, cellIndex)     // Get specific cell value
```

#### 4. **WomenDepartmentReconciliationSuite.java** (Test Suite)
10 test methods covering:
- DB data retrieval
- UI data extraction
- Count reconciliation
- Patient presence verification
- Detail comparison
- Data integrity checks
- Diagnostics/debugging

---

## Setup Instructions

### Step 1: Update DBPatientService with Actual Procedure Details

Open `DBPatientService.java` and update these constants:

```java
// Change this to your actual stored procedure name
private static final String PROCEDURE_NAME = "sp_GetWomenEmergencyDepartmentPatients";

// Change these to your actual column names returned by the procedure
private static final String COL_PATIENT_ID = "patient_id";
private static final String COL_NAME = "name";
private static final String COL_AGE = "age";
private static final String COL_ROOM = "room";
// ... etc for all columns
```

**Example:** If your procedure is named `proc_WomenDept_Patients` and returns columns `[mrn, patient_name, age_years, ward, bed_no, patient_status, assigned_doctor, admit_datetime, clinical_condition]`, then:

```java
private static final String PROCEDURE_NAME = "proc_WomenDept_Patients";
private static final String COL_PATIENT_ID = "mrn";
private static final String COL_NAME = "patient_name";
private static final String COL_AGE = "age_years";
private static final String COL_ROOM = "ward";
private static final String COL_BED = "bed_no";
private static final String COL_STATUS = "patient_status";
private static final String COL_DOCTOR = "assigned_doctor";
private static final String COL_ADMISSION = "admit_datetime";
private static final String COL_CONDITION = "clinical_condition";
```

### Step 2: Update Procedure Call (if needed)

If your procedure requires parameters:

```java
// Instead of:
String procedureCall = String.format("EXEC %s", PROCEDURE_NAME);

// Use:
String procedureCall = String.format("EXEC %s @departmentId=%s, @activeOnly=%s", 
                                     PROCEDURE_NAME, "55000", "1");
```

### Step 3: Update mapRowToPatient() (if needed)

The current implementation assumes the column names in the result set. If different, adjust the mapping:

```java
private static Patient mapRowToPatient(Map<String, Object> row) {
    // Get values with null safety
    String patientId = getMapValue(row, COL_PATIENT_ID);
    String name = getMapValue(row, COL_NAME);
    // ... etc
}
```

### Step 4: Adjust Assertions (if needed)

Some tests may need adjustment based on your business logic:

```java
// test_05_reconcilePatientCounts():
// If DB and UI counts should match exactly, keep:
assertEquals(dbCount, uiCount, "Counts should match");

// If UI filters data (e.g., only shows active patients), adjust:
assertTrue(uiCount <= dbCount, "UI count should be <= DB count");
```

---

## Running the Tests

### Run All Reconciliation Tests
```bash
mvn test -Dtest=WomenDepartmentReconciliationSuite
```

### Run Specific Test
```bash
mvn test -Dtest=WomenDepartmentReconciliationSuite#test_05_reconcilePatientCounts
```

### Run with Logging
```bash
mvn test -Dtest=WomenDepartmentReconciliationSuite -X
```

---

## Test Methods Explained

### test_01_fetchPatientsFromDB()
**Purpose:** Verify stored procedure executes and returns data
**What it does:** 
- Calls stored procedure
- Verifies list is not null
- Verifies list has at least 1 patient

### test_02_verifyDBPatientCount()
**Purpose:** Count total patients in database
**What it does:**
- Gets total count from DB
- Logs summary with status distribution

### test_03_extractPatientsFromUI()
**Purpose:** Extract patient data from UI tables
**What it does:**
- Logs in as doctor
- Selects Women Emergency Department
- Extracts all patient rows from UI

### test_04_verifyUIPatientCount()
**Purpose:** Count patients displayed in UI
**What it does:**
- Logs in as doctor
- Selects Women Emergency Department
- Gets total patient count from UI

### test_05_reconcilePatientCounts()
**Purpose:** Compare DB and UI patient counts
**What it does:**
- Gets DB count
- Gets UI count
- Logs comparison (may not match if UI filters)

### test_06_verifyDBPatientsOnUI()
**Purpose:** Verify each DB patient appears on UI
**What it does:**
- Gets all DB patients
- Gets all UI data
- Searches for patient ID/name in UI
- Reports found/not found

### test_07_comparePatientDetails()
**Purpose:** Compare specific patient details between DB and UI
**What it does:**
- Gets DB patients
- Gets UI data
- Logs first 5 patients side-by-side
- **Note:** Requires manual column mapping once you know UI structure

### test_08_verifyDataIntegrity()
**Purpose:** Check for missing required fields
**What it does:**
- Verifies all patients have ID
- Verifies all patients have name
- Logs counts of empty fields

### test_09_printDBPatientDetails()
**Purpose:** Diagnostic - print all DB patient objects
**What it does:**
- Fetches all patients from DB
- Pretty-prints each patient
- Useful for debugging column mapping

### test_10_printUIPatientDetails()
**Purpose:** Diagnostic - print all UI patient rows
**What it does:**
- Logs in and navigates to Women Emergency Department
- Extracts all UI rows
- Prints each row with cell count and values
- Useful for understanding UI table structure

---

## Workflow Example

### Initial Setup Phase
1. Run test_09_printDBPatientDetails() → See what DB returns
2. Update column mappings in DBPatientService based on output
3. Run test_01_fetchPatientsFromDB() → Verify parsing works
4. Run test_10_printUIPatientDetails() → See UI table structure

### Validation Phase
5. Run test_03_extractPatientsFromUI() → Verify UI extraction works
6. Run test_05_reconcilePatientCounts() → Compare counts
7. Run test_06_verifyDBPatientsOnUI() → Verify all DB patients on UI

### Analysis Phase
8. Run test_07_comparePatientDetails() → Manual review of details
9. Adjust assertions/business logic based on findings
10. Run all tests → Final validation

---

## Debugging Tips

### Issue: "Stored procedure not found"
- Verify procedure name is exact (case-sensitive)
- Verify database connection is working
- Run test with additional logging enabled

### Issue: "Column names don't match"
- Run test_09 to see actual column names returned
- Update COL_* constants in DBPatientService
- Verify spelling (databases are often case-insensitive but maps are not)

### Issue: "Patient counts don't match"
- Run test_10 to see UI structure
- Check if UI filters data (e.g., only active patients)
- Verify UI loads all data (check scrolling/pagination)
- Adjust assertion to match business logic

### Issue: "Patient data not matching"
- Run test_09 and test_10 side-by-side
- Verify column order in UI table
- Update mapRowToPatient() if column positions differ
- Check for data transformations (formatting, truncation)

---

## Advanced Scenarios

### Scenario 1: Add Department Filter
If you want to reconcile only specific departments:

```java
private static final String PROCEDURE_NAME = "sp_GetWomenEmergencyDepartmentPatients";
private static final String DEPARTMENT_FILTER = "55000"; // Women Emergency ID

public static List<Patient> getWomenEmergencyPatientsFromDB() {
    String procedureCall = String.format("EXEC %s @departmentId='%s'", 
                                        PROCEDURE_NAME, DEPARTMENT_FILTER);
    // ... rest of code
}
```

### Scenario 2: Add Date Range Filter
```java
public static List<Patient> getWomenEmergencyPatientsFromDB(String startDate, String endDate) {
    String procedureCall = String.format(
        "EXEC %s @departmentId='%s', @startDate='%s', @endDate='%s'", 
        PROCEDURE_NAME, "55000", startDate, endDate);
    // ... rest of code
}
```

### Scenario 3: Compare Specific Fields Only
```java
// In test_07_comparePatientDetails():
// Compare only critical fields
assertEquals(dbPatient.getPatientId(), uiPatientId);
assertEquals(dbPatient.getName(), uiPatientName);
assertEquals(dbPatient.getRoom(), uiRoom);
// Skip less critical fields like admission date (may be formatted differently)
```

---

## File Structure

```
src/
├── main/java/
│   ├── db/services/
│   │   └── DBPatientService.java           ← UPDATE: procedure name & columns
│   ├── models/
│   │   └── Patient.java
│   └── pages/mainPages/
│       └── WomenEmergencyPatientsPage.java ← NEW: getUIPatientData() methods
└── test/java/
    └── sanity/
        └── WomenDepartmentReconciliationSuite.java
```

---

## Next Steps

1. **Provide Procedure Details** 
   - Procedure name
   - Parameter names/types (if any)
   - Column names and order returned

2. **Run Diagnostic Tests**
   - test_09: See DB data structure
   - test_10: See UI data structure

3. **Update Configuration**
   - Update DBPatientService constants
   - Adjust procedure call if parameters needed
   - Verify data types/formatting

4. **Adjust Assertions**
   - test_05: Update count comparison logic
   - test_07: Add field-by-field comparison

5. **Add Custom Validations**
   - Status-specific checks
   - Date/time validation
   - Room/bed assignment validation

---

## Support

For issues or questions:
1. Check "Debugging Tips" section above
2. Run diagnostic tests (test_09, test_10)
3. Review logs for column name mismatches
4. Consult your stored procedure documentation for parameter details
