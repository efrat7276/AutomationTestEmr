package actionUtilies;

import io.qameta.allure.Step;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helpers.ManageDB;

public class DBExecuter {

    /**
     * מבצע query למסד הנתונים ומחזיר את התוצאה כ-String.
     * במקרה של תוצאה מרובה שורות, מחזיר את העמודה הראשונה של השורה הראשונה.
     * 
     * @param query שאילתת SQL לביצוע
     * @return תוצאת ה-query כ-String, או null במקרה של שגיאה
     */
    @Step("Execute query: {query}")
    public static String executeSelect(String query) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = ManageDB.getStatement();
            rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
            
        } catch (Exception ex) {
            System.out.println("Query execution failed. Query: " + query + " | Error: " + ex.getMessage());
           throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    /**
     * Executes a SELECT query and returns the first column of all rows as a list.
     * If there are no rows, returns an empty list (never null).
     *
     * @param query SQL SELECT to execute
     * @return List of first-column values for all rows; empty when no rows
     */
    @Step("Execute select-to-list query: {query}")
    public static List<String> executeQueryToList(String query) {
        Statement stmt = null;
        ResultSet rs = null;
        List<String> results = new ArrayList<>();
        try {
            stmt = ManageDB.getStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String val = rs.getString(1);
                results.add(val != null ? val : "");
            }
            return results;
        } catch (Exception ex) {
            System.out.println("Query-to-list execution failed. Query: " + query + " | Error: " + ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    /**
     * Executes a SELECT query and returns all column values from the FIRST row as a list.
     * If there are no rows, returns an empty list (never null).
     * Useful when you want row data but the query may return 0 rows without failing on rs.next().
     *
     * @param query SQL SELECT to execute
     * @return List of values for the first row; empty when no rows
     */
    @Step("Execute select-first-row query: {query}")
    public static List<String> executeSelectFirstRow(String query) {
        Statement stmt = null;
        ResultSet rs = null;
        List<String> row = new ArrayList<>();
        try {
            stmt = ManageDB.getStatement();
            rs = stmt.executeQuery(query);
            if (!rs.next()) {
                return row; // empty
            }
            ResultSetMetaData md = rs.getMetaData();
            int colCount = md.getColumnCount();
            for (int i = 1; i <= colCount; i++) {
                String val = rs.getString(i);
                row.add(val != null ? val : "");
            }
            return row;
        } catch (Exception ex) {
            System.out.println("Select-first-row execution failed. Query: " + query + " | Error: " + ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    /**
     * For update/delete queries
     * @param query
     * @return
     */
    public static int ecuteUpdateQuery(String query) {
        Statement stmt = null;
        try {
            stmt = ManageDB.getStatement();
            int affectedRows = stmt.executeUpdate(query); // מתאים ל-INSERT/UPDATE/DELETE
            return affectedRows;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing statement: " + e.getMessage());
            }
        }
    }
    @Step("Execute multi-delete procedure: {sql}")
    public static List<Integer> executeMultiDeleteProcedure(String sql) throws SQLException {
    List<Integer> deleteCounts = new ArrayList<>();

    try  {
        Statement stmt = ManageDB.getStatement();
    
        boolean isResultSet = stmt.execute(sql);

        //  לולאה שעוברת על כל "קרונות" התוצאות
        do {
            if (!isResultSet) {
                int count = stmt.getUpdateCount();
                
                // אם זה לא -1, סימן שיש כאן מספר שורות שנמחקו
                if (count != -1) {
                    deleteCounts.add(count);
                }
            }
            // כאן קורה המעבר לתוצאה הבאה ובדיקת התנאי המורכב
        } while (stmt.getMoreResults() || stmt.getUpdateCount() != -1);
    }
    catch (Exception ex) {
        System.out.println("Procedure execution failed. Query: " + sql + " | Error: " + ex.getMessage());
        throw new RuntimeException(ex);
    }
    
    return deleteCounts;
}
    @Step("Execute any query: {sql}")
public static Object handleAnyQuery(String sql) throws SQLException {
    List<Object> allResults = new ArrayList<>();
     Statement stmt = null;

    try {
        stmt = ManageDB.getStatement();
        boolean isResultSet = stmt.execute(sql);

        while (true) {
            if (isResultSet) {
                // טיפול ב-SELECT
                allResults.add(parseResultSet(stmt.getResultSet()));
            } else {
                // טיפול ב-UPDATE/INSERT/DELETE
                int updateCount = stmt.getUpdateCount();
                if (updateCount == -1) { // אין יותר תוצאות
                    break;
                }
                allResults.add(updateCount);
            }
            
            // מעבר לתוצאה הבאה
            isResultSet = stmt.getMoreResults();
        }
         } catch (SQLException ex) {
         throw new RuntimeException(ex);
        }
     finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing statement: " + e.getMessage());
            }
        }
    
    return allResults.size() == 1 ? allResults.get(0) : allResults;
    }

    // פונקציית עזר להמרת ResultSet לרשימה קריאה
    private static List<Map<String, Object>> parseResultSet(ResultSet rs) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                row.put(metaData.getColumnName(i), rs.getObject(i));
            }
            results.add(row);
        }
        return results;
    }


    public static boolean isExecutionSuccessful(String sql) {
         Statement stmt = null;
    try { stmt =ManageDB.getStatement(); 
        // If this line finishes without an exception, the SQL was valid 
        // and the server accepted it.
        stmt.execute(sql); 
        System.out.println("Execution succeeded for SQL: " + sql);
        return true; 
        
    } catch (SQLException ex) {
        // If we are here, it failed (syntax error, connection loss, etc.)
        System.err.println("Execution failed: " + ex.getMessage());
         throw new RuntimeException(ex);
    } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing statement: " + e.getMessage());
            }
    } 
}
}






//     @Step("insert to table")
//     public static String InsertQuery(String query) throws SQLException {
//         try
//                 (PreparedStatement p = con.prepareStatement(query))
//             {
//                 p.setInt(1, 20202);
//                 p.setInt(2, 1);
//                 p.setInt(3, 1);
//                 p.setInt(4, 3);

//                 p.executeUpdate();
//                 return "insert succeed";
//             }


//         catch (Exception ex){
//             System.out.println( "the insert query failed . see details: "+ ex);
//             return "failed";
//         }
//     }

//     @Step("get all rows from database to list ")
//     public static List<String> getAllRows(String query){
//         List<String> result = new ArrayList<String>();
//         try {
//             rs = stmt.executeQuery(query);
//             while (rs.next()){
//                 result.add(String.valueOf( rs.getRow()));
//             }
//         }
//         catch (Exception ex){
//             System.out.println("the data didn't reach . see details: "+ ex);
//         }
//         return result;
//     }

//   @Step("Get columns Row From database to list")
//     public static List<String> getColumnsInRows(String query){
//         List<String> result = new ArrayList<String>();
//          try {
//           rs = stmt.executeQuery(query);
//           rs.next();
//           result.add(rs.getString(1));
//           result.add(rs.getString(2));

//       }
//       catch (Exception ex){
//           System.out.println("the data didn't reach . see details: "+ ex);
//       }
//       return result;

//     }

//     @Step("Get result database")
//     public static String getResultForQuery(String query){
//         String result = "";
//         try {
//             rs = stmt.executeQuery(query);
//             rs.next();
//             result= rs.getString(1);
//         }
//         catch (Exception ex){
//             System.out.println("the data didn't reach . see details: "+ ex);
//         }
//         return result;

//     }

//     @Step("Count rows From database")
//     public static int getCountRows(String query){
//         int countRows = 0;
//         try {
//             rs = stmt.executeQuery(query);

//            while(rs.next()){

//                countRows++;
//             //   System.out.println(rs.getRow()+ "   "+rs.findColumn("mispar_ishpuz"));
//             }
//         }
//         catch (Exception ex){
//             System.out.println("the data didn't reach . see details: "+ ex);
//         }
//         return countRows;

//     }

//}
