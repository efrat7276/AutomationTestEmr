package base;

import actionUtilies.DBExecuter;
import helpers.ManageDB;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.Map;

/**
 * Base class for all database tests.
 * Handles database connection lifecycle and provides utility methods.
 */
@Slf4j
public class BaseDBTest {

    protected ManageDB dbManager;

    /**
     * Setup database connection before running tests
     */
    @BeforeClass
    public void setupDB() {
        log.info("========== Setting up Database Connection ==========");
        try {
            dbManager = ManageDB.getInstance();
            ManageDB.getConnections(); // Initialize connection
            log.info("✓ Database connection established successfully");
        } catch (Exception e) {
            log.error("✗ Failed to establish database connection: {}", e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    /**
     * Close database connection after tests complete
     */
    @AfterClass
    public void teardownDB() {
        log.info("========== Closing Database Connection ==========");
        try {
            ManageDB.closeConnection();
            log.info("✓ Database connection closed successfully");
        } catch (Exception e) {
            log.error("✗ Error closing database connection: {}", e.getMessage());
        }
    }

    /**
     * Execute a SELECT query and return single String value
     * @param query SQL SELECT query
     * @return First column of first row, or null
     */
    protected String querySingleValue(String query) {
        log.info("Executing query: {}", query);
        try {
            String result = DBExecuter.executeSelect(query);
            log.info("✓ Query result: {}", result);
            return result;
        } catch (Exception e) {
            log.error("✗ Query failed: {}", e.getMessage());
            throw new RuntimeException("Query execution failed", e);
        }
    }

    /**
     * Execute a SELECT query and return list of values from first column
     * @param query SQL SELECT query
     * @return List of first column values
     */
    protected List<String> queryColumnValues(String query) {
        log.info("Executing query to list: {}", query);
        try {
            List<String> results = DBExecuter.executeQueryToList(query);
            log.info("✓ Query returned {} row(s)", results.size());
            return results;
        } catch (Exception e) {
            log.error("✗ Query failed: {}", e.getMessage());
            throw new RuntimeException("Query execution failed", e);
        }
    }

    /**
     * Execute a SELECT query and return all columns of first row
     * @param query SQL SELECT query
     * @return List of all column values for first row
     */
    protected List<String> queryFirstRow(String query) {
        log.info("Executing query to get first row: {}", query);
        try {
            List<String> row = DBExecuter.executeSelectFirstRow(query);
            log.info("✓ Query returned row with {} column(s)", row.size());
            return row;
        } catch (Exception e) {
            log.error("✗ Query failed: {}", e.getMessage());
            throw new RuntimeException("Query execution failed", e);
        }
    }

    /**
     * Execute an INSERT/UPDATE/DELETE query
     * @param query SQL DML query
     * @return Number of affected rows
     */
    protected int executeUpdate(String query) {
        log.info("Executing update query: {}", query);
        try {
            int affectedRows = DBExecuter.ecuteUpdateQuery(query);
            log.info("✓ Update query affected {} row(s)", affectedRows);
            return affectedRows;
        } catch (Exception e) {
            log.error("✗ Update query failed: {}", e.getMessage());
            throw new RuntimeException("Update execution failed", e);
        }
    }

    /**
     * Execute a stored procedure that performs multiple deletes
     * @param sql Procedure call SQL
     * @return List of delete counts for each statement
     */
    protected List<Integer> executeMultiDeleteProcedure(String sql) {
        log.info("Executing multi-delete procedure: {}", sql);
        try {
            List<Integer> deleteCounts = DBExecuter.executeMultiDeleteProcedure(sql);
            log.info("✓ Procedure executed with {} delete statement(s)", deleteCounts.size());
            for (int i = 0; i < deleteCounts.size(); i++) {
                log.info("  Delete statement {} affected {} row(s)", i + 1, deleteCounts.get(i));
            }
            return deleteCounts;
        } catch (Exception e) {
            log.error("✗ Procedure execution failed: {}", e.getMessage());
            throw new RuntimeException("Procedure execution failed", e);
        }
    }

    /**
     * Execute any SQL query (SELECT/INSERT/UPDATE/DELETE/PROCEDURE)
     * @param sql SQL query
     * @return Object result (List<Map<String,Object>> for SELECT, Integer for DML, or List of mixed)
     */
    protected Object executeAnyQuery(String sql) {
        log.info("Executing any query: {}", sql);
        try {
            Object result = DBExecuter.handleAnyQuery(sql);
            log.info("✓ Query executed successfully");
            return result;
        } catch (Exception e) {
            log.error("✗ Query execution failed: {}", e.getMessage());
            throw new RuntimeException("Query execution failed", e);
        }
    }

    /**
     * Verify that a SQL query executes without errors
     * @param sql SQL query to validate
     * @return true if execution was successful
     */
    protected boolean isQueryValid(String sql) {
        log.info("Validating query: {}", sql);
        try {
            boolean isValid = DBExecuter.isExecutionSuccessful(sql);
            log.info("✓ Query validation: {}", isValid ? "VALID" : "INVALID");
            return isValid;
        } catch (Exception e) {
            log.error("✗ Query validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Helper: Count rows in a table
     * @param tableName Table name
     * @param whereClause Optional WHERE clause (without WHERE keyword). Pass null for all rows.
     * @return Row count
     */
    protected int getRowCount(String tableName, String whereClause) {
        String query = "SELECT COUNT(*) FROM " + tableName;
        if (whereClause != null && !whereClause.isEmpty()) {
            query += " WHERE " + whereClause;
        }
        log.info("Getting row count: {}", query);
        try {
            String result = querySingleValue(query);
            int count = Integer.parseInt(result);
            log.info("✓ Row count: {}", count);
            return count;
        } catch (Exception e) {
            log.error("✗ Failed to get row count: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Helper: Check if a record exists
     * @param query SELECT query that should return 1 row if record exists
     * @return true if record exists, false otherwise
     */
    protected boolean recordExists(String query) {
        log.info("Checking if record exists: {}", query);
        try {
            String result = querySingleValue(query);
            boolean exists = result != null && !result.isEmpty();
            log.info("✓ Record exists: {}", exists);
            return exists;
        } catch (Exception e) {
            log.error("✗ Failed to check record existence: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Helper: Get value from specific column where condition
     * @param tableName Table name
     * @param columnName Column to retrieve
     * @param whereClause WHERE clause condition
     * @return Column value or null
     */
    protected String getColumnValue(String tableName, String columnName, String whereClause) {
        String query = String.format("SELECT %s FROM %s WHERE %s", columnName, tableName, whereClause);
        log.info("Getting column value: {}", query);
        return querySingleValue(query);
    }

    /**
     * Helper: Insert record and return affected rows
     * @param tableName Table name
     * @param columns Comma-separated column names
     * @param values Comma-separated values (with appropriate quotes/numbers)
     * @return Number of inserted rows
     */
    protected int insertRecord(String tableName, String columns, String values) {
        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
        log.info("Inserting record: {}", query);
        return executeUpdate(query);
    }

    /**
     * Helper: Update record
     * @param tableName Table name
     * @param setClause SET clause (e.g., "column1='value1', column2=123")
     * @param whereClause WHERE clause condition
     * @return Number of updated rows
     */
    protected int updateRecord(String tableName, String setClause, String whereClause) {
        String query = String.format("UPDATE %s SET %s WHERE %s", tableName, setClause, whereClause);
        log.info("Updating record: {}", query);
        return executeUpdate(query);
    }

    /**
     * Helper: Delete record
     * @param tableName Table name
     * @param whereClause WHERE clause condition
     * @return Number of deleted rows
     */
    protected int deleteRecord(String tableName, String whereClause) {
        String query = String.format("DELETE FROM %s WHERE %s", tableName, whereClause);
        log.info("Deleting record: {}", query);
        return executeUpdate(query);
    }
}
