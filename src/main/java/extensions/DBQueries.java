package extensions;

import utilities.CommonOps;

import java.sql.ResultSet;

public class DBQueries extends CommonOps {

    public static String clearDataForMisparIshpuz(String mispar_ishpuz){

        try {
           ResultSet str=  stmt.executeQuery("EXECUTE cpoe.deleteDrugInstruction @cpoeId = NULL ,@cHours = NULL ,@mispar_ishpuz = "+ mispar_ishpuz);
        }
        catch (Exception ex){
            System.out.println("the query didn't success for mispar_ishpuz " + mispar_ishpuz +". see details "+ ex);
        }
        return  "success";
    }
}
