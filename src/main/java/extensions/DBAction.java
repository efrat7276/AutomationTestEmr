package extensions;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import io.qameta.allure.Step;
import utilities.CommonOps;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBAction extends CommonOps {

    @Step("get count list")
    public  static String countRow(String query) throws SQLException {
        try {
            rs = stmt.executeQuery(query);
        }
        catch (Exception ex){
            System.out.println("the data didn't reach . see details: "+ ex);
        }
        return rs.getString(1);
    }

    @Step("insert to table")
    public static String InsertQuery(String query) throws SQLException {
        try {
            rs = stmt.executeQuery(query);
            System.out.println( "the insert query succeed");
            return "succeed";

        }
        catch (Exception ex){
            System.out.println( "the insert query failed . see details: "+ ex);
            return "failed";
        }
    }

    @Step("get all rows from database to list ")
    public static List<String> getAllRows(String query){
        List<String> result = new ArrayList<String>();
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()){
                result.add(String.valueOf( rs.getRow()));
            }
        }
        catch (Exception ex){
            System.out.println("the data didn't reach . see details: "+ ex);
        }
        return result;
    }

  @Step("Get columns Row From database to list")
    public static List<String> getColumnsInRows(String query){
        List<String> result = new ArrayList<String>();
         try {
          rs = stmt.executeQuery(query);
          rs.next();
          result.add(rs.getString(1));
          result.add(rs.getString(2));

      }
      catch (Exception ex){
          System.out.println("the data didn't reach . see details: "+ ex);
      }
      return result;

    }

    @Step("Get result database")
    public static String getResultForQuery(String query){
        String result = "";
        try {
            rs = stmt.executeQuery(query);
            rs.next();
            result= rs.getString(1);
        }
        catch (Exception ex){
            System.out.println("the data didn't reach . see details: "+ ex);
        }
        return result;

    }

    @Step("Count rows From database")
    public static int getCountRows(String query){
        int countRows = 0;
        try {
            rs = stmt.executeQuery(query);

           while(rs.next()){

               countRows++;
               System.out.println(rs.getRow()+ "   "+rs.findColumn("mispar_ishpuz"));
            }
        }
        catch (Exception ex){
            System.out.println("the data didn't reach . see details: "+ ex);
        }
        return countRows;

    }

}
