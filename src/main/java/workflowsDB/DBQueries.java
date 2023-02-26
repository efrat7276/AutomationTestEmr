package workflowsDB;

import utilities.CommonOps;

import java.sql.ResultSet;
import java.util.List;

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

    public static void clearPatientsDrug(List<String> patient_list){

        for (int i=0; i<patient_list.size() ; i++){
            DBQueries.clearDataForMisparIshpuz(patient_list.get(i));
        }
    }
}
