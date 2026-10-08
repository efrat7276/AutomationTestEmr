package db;

import actionUtilies.DBExecuter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Utility class for database testing operations
 * Provides reusable methods for common DB test scenarios
 */
@Slf4j
public class DBTestUtils {

    /**
     * Verify that expected values exist in database column
     * @param tableName Table to query
     * @param columnName Column to check
     * @param expectedValues List of values that should exist
     * @return true if all values exist, false otherwise
     */
    public static boolean verifyValuesExist(String tableName, String columnName, List<String> expectedValues) {
        log.info("Verifying values exist in {}:{}", tableName, columnName);
        
        for (String value : expectedValues) {
            String query = String.format("SELECT COUNT(*) FROM %s WHERE %s = '%s'", 
                                        tableName, columnName, value);
            try {
                String result = DBExecuter.executeSelect(query);
                int count = Integer.parseInt(result != null ? result : "0");
                if (count == 0) {
                    log.warn("Value '{}' NOT found in {}:{}", value, tableName, columnName);
                    return false;
                }
                log.info("✓ Value '{}' found", value);
            } catch (Exception e) {
                log.error("Error checking value '{}': {}", value, e.getMessage());
                return false;
            }
        }
        
        log.info("✓ All values verified successfully");
        return true;
    }

    /**
     * Get distinct values from a column
     * @param tableName Table to query
     * @param columnName Column to retrieve
     * @param limit Optional limit (pass null for no limit)
     * @return List of distinct values
     */
    public static List<String> getDistinctValues(String tableName, String columnName, Integer limit) {
        String query = String.format("SELECT DISTINCT %s FROM %s", columnName, tableName);
        if (limit != null && limit > 0) {
            query += " LIMIT " + limit;
        }
        log.info("Getting distinct values: {}", query);
        return DBExecuter.executeQueryToList(query);
    }

    /**
     * Count occurrences of a specific value in a column
     * @param tableName Table to query
     * @param columnName Column to check
     * @param value Value to count
     * @return Count of occurrences
     */
    public static int countValueOccurrences(String tableName, String columnName, String value) {
        String query = String.format("SELECT COUNT(*) FROM %s WHERE %s = '%s'", 
                                    tableName, columnName, value);
        log.info("Counting occurrences of '{}': {}", value, query);
        try {
            String result = DBExecuter.executeSelect(query);
            return Integer.parseInt(result != null ? result : "0");
        } catch (Exception e) {
            log.error("Error counting occurrences: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Get maximum value from a column
     * @param tableName Table to query
     * @param columnName Column to find max
     * @return Maximum value or null
     */
    public static String getMaxValue(String tableName, String columnName) {
        String query = String.format("SELECT MAX(%s) FROM %s", columnName, tableName);
        log.info("Getting max value: {}", query);
        try {
            return DBExecuter.executeSelect(query);
        } catch (Exception e) {
            log.error("Error getting max value: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Get minimum value from a column
     * @param tableName Table to query
     * @param columnName Column to find min
     * @return Minimum value or null
     */
    public static String getMinValue(String tableName, String columnName) {
        String query = String.format("SELECT MIN(%s) FROM %s", columnName, tableName);
        log.info("Getting min value: {}", query);
        try {
            return DBExecuter.executeSelect(query);
        } catch (Exception e) {
            log.error("Error getting min value: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Get average value from a numeric column
     * @param tableName Table to query
     * @param columnName Column to average
     * @return Average value or null
     */
    public static String getAverageValue(String tableName, String columnName) {
        String query = String.format("SELECT AVG(%s) FROM %s", columnName, tableName);
        log.info("Getting average value: {}", query);
        try {
            return DBExecuter.executeSelect(query);
        } catch (Exception e) {
            log.error("Error getting average value: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Get sum of a numeric column
     * @param tableName Table to query
     * @param columnName Column to sum
     * @return Sum value or null
     */
    public static String getSumValue(String tableName, String columnName) {
        String query = String.format("SELECT SUM(%s) FROM %s", columnName, tableName);
        log.info("Getting sum value: {}", query);
        try {
            return DBExecuter.executeSelect(query);
        } catch (Exception e) {
            log.error("Error getting sum value: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Check if table is empty
     * @param tableName Table to check
     * @return true if table has no rows, false if it has data
     */
    public static boolean isTableEmpty(String tableName) {
        String query = String.format("SELECT COUNT(*) FROM %s", tableName);
        log.info("Checking if table is empty: {}", tableName);
        try {
            String result = DBExecuter.executeSelect(query);
            int count = Integer.parseInt(result != null ? result : "0");
            boolean isEmpty = count == 0;
            log.info("✓ Table is {}", isEmpty ? "EMPTY" : "NOT EMPTY");
            return isEmpty;
        } catch (Exception e) {
            log.error("Error checking table: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get all data from table with optional limit
     * @param tableName Table to query
     * @param limit Optional row limit (pass null for no limit)
     * @return List of rows (each row as List of values)
     */
    public static List<String> getAllTableData(String tableName, Integer limit) {
        String query = String.format("SELECT * FROM %s", tableName);
        if (limit != null && limit > 0) {
            query += " LIMIT " + limit;
        }
        log.info("Getting all table data: {}", query);
        return DBExecuter.executeQueryToList(query);
    }

    /**
     * Compare two columns for data integrity
     * @param tableName Table to check
     * @param column1 First column
     * @param column2 Second column
     * @return Count of rows where columns differ
     */
    public static int compareTwoColumns(String tableName, String column1, String column2) {
        String query = String.format("SELECT COUNT(*) FROM %s WHERE %s != %s", 
                                    tableName, column1, column2);
        log.info("Comparing columns: {}", query);
        try {
            String result = DBExecuter.executeSelect(query);
            return Integer.parseInt(result != null ? result : "0");
        } catch (Exception e) {
            log.error("Error comparing columns: {}", e.getMessage());
            return -1;
        }
    }

    /**
     * Get rows where a specific column is NULL
     * @param tableName Table to query
     * @param columnName Column to check for NULL
     * @return Count of rows with NULL in specified column
     */
    public static int countNullValues(String tableName, String columnName) {
        String query = String.format("SELECT COUNT(*) FROM %s WHERE %s IS NULL", 
                                    tableName, columnName);
        log.info("Counting NULL values in {}: {}", columnName, query);
        try {
            String result = DBExecuter.executeSelect(query);
            return Integer.parseInt(result != null ? result : "0");
        } catch (Exception e) {
            log.error("Error counting NULL values: {}", e.getMessage());
            return -1;
        }
    }

    /**
     * Get rows where a specific column is NOT NULL
     * @param tableName Table to query
     * @param columnName Column to check for non-NULL values
     * @return Count of rows with non-NULL values in specified column
     */
    public static int countNonNullValues(String tableName, String columnName) {
        String query = String.format("SELECT COUNT(*) FROM %s WHERE %s IS NOT NULL", 
                                    tableName, columnName);
        log.info("Counting NON-NULL values in {}: {}", columnName, query);
        try {
            String result = DBExecuter.executeSelect(query);
            return Integer.parseInt(result != null ? result : "0");
        } catch (Exception e) {
            log.error("Error counting non-NULL values: {}", e.getMessage());
            return -1;
        }
    }

    /**
     * Verify data integrity: Check if foreign key references exist
     * @param mainTable Main table name
     * @param mainColumn Column in main table
     * @param referenceTable Reference table name
     * @param referenceColumn Column in reference table
     * @return Count of orphaned records (references that don't exist)
     */
    public static int checkOrphanedRecords(String mainTable, String mainColumn, 
                                          String referenceTable, String referenceColumn) {
        String query = String.format(
            "SELECT COUNT(*) FROM %s WHERE %s NOT IN (SELECT %s FROM %s)",
            mainTable, mainColumn, referenceColumn, referenceTable);
        
        log.info("Checking for orphaned records: {}", query);
        try {
            String result = DBExecuter.executeSelect(query);
            int orphanCount = Integer.parseInt(result != null ? result : "0");
            log.info("✓ Found {} orphaned record(s)", orphanCount);
            return orphanCount;
        } catch (Exception e) {
            log.error("Error checking orphaned records: {}", e.getMessage());
            return -1;
        }
    }

    /**
     * Execute raw SQL with error handling
     * @param sql SQL query to execute
     * @return true if execution successful, false otherwise
     */
    public static boolean executeSafeQuery(String sql) {
        log.info("Executing safe query: {}", sql);
        try {
            DBExecuter.isExecutionSuccessful(sql);
            log.info("✓ Query executed successfully");
            return true;
        } catch (Exception e) {
            log.error("✗ Query execution failed: {}", e.getMessage());
            return false;
        }
    }
}
