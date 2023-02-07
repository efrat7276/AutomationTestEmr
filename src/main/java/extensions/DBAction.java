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

  @Step("Get List From database")
    public static List<String> getRow(String query){
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

    @Step("Get List From database")
    public static boolean getListToCSV(String query, String fileCsvName){
      //  List<String> result = new ArrayList<String>();
        try {

            //open new csv file
            rs = stmt.executeQuery(query);
            String csv = "./DDTFiles/"  + fileCsvName + ".csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv));

             writer.writeAll(rs,false);

            writer.close();


//            CSVReader reader = new CSVReader(new FileReader("./DDTFiles/data.csv"));
//
//            List<String[]> allRows = reader.readAll();
//
//            System.out.println(allRows.get(0)[0]);

        }
        catch (SQLException | IOException ex){
            System.out.println("the data didn't reach . see details: "+ ex);
        }
        return true;

    }

    @Step("Count rows From database")
    public static int getCountRows(String query){
        List<String> result = new ArrayList<String>();
        int countRows = 0;
        try {
            rs = stmt.executeQuery(query);

           while(rs.next()){
               countRows++;
            }
        }
        catch (Exception ex){
            System.out.println("the data didn't reach . see details: "+ ex);
        }
        return countRows;

    }

}
