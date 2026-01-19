package helpers;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class ManageDB {

    private static ManageDB instance = null;
    private static java.sql.Connection con = null;

    /**
     * Returns the single instance of ManageDB (Singleton).
     */
    public static ManageDB getInstance() {
        if (instance == null) {
            instance = new ManageDB();
        }
        return instance;
    }

    /**
     * Returns the active database connection. Opens a new connection if none
     * exists.
     */
    public static Connection getConnections() {
        try {
            if (con == null || con.isClosed()) {
                // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection(FilesHelper.getData("DB-prod"), FilesHelper.getData("DBUserName"),
                        FilesHelper.getData("DBPassword"));
            }
        } catch (SQLException e) {
            // TODO add Allure fail step
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Returns a new Statement for executing SQL queries on the active connection.
     */
    public static Statement getStatement() {
        Statement stmt = null;
        try {
            stmt = getConnections().createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stmt;
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {

        try {
            con.close();
        } catch (Exception ex) {
            System.out.println("Error eccurred while exit to DB, see details: " + ex);

        }
    }
}
