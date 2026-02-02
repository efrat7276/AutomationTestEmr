package helpers;

public class QueriesUtils {

  
    public static final String any_update_query =
            "UPDATE cpoe.drugInstructions \n" +
            "SET dose = '500mg' \n" +
            "WHERE cpoeInstructionID = 3835935";
        
        
    public static final String getDetailsFirstPatient =
    "SELECT TOP 1 a.mispar_ishpuz, teudat_zeut FROM dbo.admission AS a " + 
                        "LEFT JOIN dbo.k_lan_dep_beds AS kldb " + 
                        "ON kldb.mispar_ishpuz = a.mispar_ishpuz " + 
                        "WHERE k_yechida_shichrur= "+ Constants.DEFAULT_DEPARTMENT_NUM_STRING + " "+
                        "AND tarich_shichrur IS NULL " + 
                        "AND a.bitul =0 " + 
                        "ORDER BY kldb.dep_bed_sort ASC ";
                        
    public static final String removePatient_from_tbl =
             "EXEC cpoe.delete_Instruction_Efrat @mispar_ishpuz = %s, @typeid = NULL";
             // "DELETE FROM cpoe.drugInstructionExecution WHERE cpoeInstructionID = 3835541";
}
