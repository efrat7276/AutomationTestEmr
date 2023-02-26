package utilities;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ManageDDT extends CommonOps{


    @DataProvider(name ="patients")
    public Object[] getDataProviderPatients(){
        return  getDataFromCSV_array("./DDTFiles/patients.csv");
    }

    @DataProvider(name ="drugsDaily")
    public Object[][] getDataProviderDrugsDaily(){
       return  getDataFromCSV("./DDTFiles/drugsDaily.csv");
    }

    @DataProvider(name ="drugsOnceOnly")
    public Object[][] getDataProviderDrugsOnceOnly(){
        return getDataFromCSV("./DDTFiles/drugsOnceOnly.csv");
    }

    @DataProvider(name ="drugsSOS")
    public Object[][] getDataProviderDrugsSOS(){
        return getDataFromCSV("./DDTFiles/drugsSOS.csv");
    }

    @DataProvider(name ="drugsFromDB")
    public Object[][] getDataProviderDrugsList(){
        return getDataFromCSV("./DDTFiles/drugsDailyNew.csv");
    }

    @DataProvider(name ="drugsDrugsAtRiskFromDB")
    public Object[][] getDataProviderDrugsAtRisklist(){
        return getDataFromCSV("./DDTFiles/drugsDailyDrugAtRiskNew.csv");
    }

    @DataProvider(name ="drugsDrugsInfectiousDiseaseSpecialFromDB")
    public Object[][] getDataProviderDrugsInfectiousDiseaseSpecialist(){
        return getDataFromCSV("./DDTFiles/DrugsInfectiousDiseaseSpecial.csv");
    }

    @DataProvider(name ="nutritionFromDB")
    public Object[][] getDataProviderNutritionList(){
        return getDataFromCSV("./DDTFiles/NutritionList.csv");
    }

    public static Object[][] getDataFromCSV(String filePath) {

        List<String> lines = readCSV(filePath);
        List<String> columns = new ArrayList<String>(Arrays.asList(lines.get(0).split(",")));
        Object [][] data = new Object[lines.size()][columns.size()];
        String str;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < columns.size(); j++) {
                data[i][j] = lines.get(i).split(",")[j];
                str = data[i][j].toString();
                data[i][j] = str.replace("\"" , "");
            }
        }

        return data;
    }

    public static String getLineFromCSV(String filePath, int line) {

        List<String> lines = readCSV(filePath);
        List<String> columns = new ArrayList<String>(Arrays.asList(lines.get(0).split(",")));
        String data , str ;
        data = lines.get(line);
        str = data.toString();
        data = str.replace("\"" , "");

        return data;
    }



    public static List<String> readCSV (String csvFile){

        List<String> lines = null;
        File file = new File(csvFile);
        try{
            lines = Files.readAllLines(file.toPath() , StandardCharsets.UTF_8);
        }
        catch (IOException e){
            System.out.println("Some Error , See Deatails, " + e);
        }
        return lines;
    }



    public static Object[] getDataFromCSV_array(String filePath) {

        List<String> lines = readCSV(filePath);
        Object[] data = new Object[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            data[i] = lines.get(i);
        }
        return data;
    }


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



//מקורי
//    public static Object[][] getDataFromCSV(String filePath, int lines , int colums) {
//
//        List<String> csvData = readCSV(filePath);
//        Object[][] data = new Object[lines][colums];
//        for (int i = 0; i < csvData.size(); i++) {
//            for (int j = 0; j < colums; j++) {
//                data[i][j] = csvData.get(i).split(",")[j];
//            }
//        }
//        for (int i = 0; i < lines; i++) {
//            for (int k = 0; k < colums; k++)
//                System.out.println(data[i][k]);
//
//        }
//
//       return data;
//    }
}


