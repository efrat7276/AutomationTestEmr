package actionUtilies;

import io.qameta.allure.Step;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    public static String executeQuery(String query) {
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
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
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
