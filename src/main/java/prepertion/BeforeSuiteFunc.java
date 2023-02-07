package prepertion;

import com.opencsv.CSVWriter;
import extensions.DBQueries;
import utilities.CommonOps;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BeforeSuiteFunc extends CommonOps {

//    public String clearTables(String query){
//
//        try {
//            rs = stmt.executeQuery(query);
//        }
//        catch (Exception ex){
//
//        }
//        return "success";
//
//
//
//    }

    public static List<String> getPatientList (String department_num, int amountPatient){

        String query = "SELECT top " + amountPatient + " mispar_ishpuz FROM dbo.admission WHERE k_yechida_shichrur=" + department_num + " AND tarich_shichrur IS NULL";
        List<String> patients_list = new ArrayList<>();
         try {
           rs = stmt.executeQuery(query);
           while (rs.next()){
               patients_list.add(rs.getString("mispar_ishpuz"));
           }
       }
       catch (Exception ex){
           System.out.println("");
       }
         return  patients_list;
    }

    public static void writePatientListToCSV(List<String> patient_list) throws IOException {

        String patientCsv = "./DDTFiles/patients" + ".csv";
        List<String> details;
        FileWriter writer = null;
        try {
            writer = new FileWriter(patientCsv);
        }

        catch (IOException e) {
            System.out.println("error in write to csv file. see details: "+ e);
        }

        String collect=  patient_list.stream().collect(Collectors.joining("\n"));
        writer.write(collect);

       writer.close();
    }

    public static void clearPatientsDrug(List<String>  patient_list){

        for (int i=0; i<patient_list.size() ; i++){
             DBQueries.clearDataForMisparIshpuz(patient_list.get(i));
        }
    }
}
