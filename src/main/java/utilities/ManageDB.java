package utilities;

import java.sql.DriverManager;

public class ManageDB extends CommonOps {


    public static void openConnection(String dbURL, String user, String pass)  {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(dbURL, user , pass);
            stmt = con.createStatement();
        }
        catch (Exception ex){
            System.out.println("Error eccurred while connecting to DB, see details: "+ex);
        }
    }

    public static void closeConnection() {

        try{
            con.close();
        }
        catch (Exception ex){
            System.out.println("Error eccurred while exit to DB, see details: "+ex);

        }
    }
}
