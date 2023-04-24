package utilities;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageDDT_int extends CommonOps{

//
//    @DataProvider(name ="patients")
//    public Object[] getDataProviderPatients(){
//        return  getDataFromCSV_array("./DDTFiles/patients.csv");
//    }


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

//
//    public static Object[] getDataFromCSV_array(String filePath) {
//
//        List<String> lines = readCSV(filePath);
//        Object[] data = new Object[lines.size()];
//        for (int i = 0; i < lines.size(); i++) {
//            data[i] = lines.get(i);
//        }
//        return data;
//    }

//    public static Object[] getDataFromCSV_array(String filePath) {
//
//        List<String> lines = readCSV(filePath);
//        Object[] data = new Object[lines.size()];
//        for (int i = 0; i < lines.size(); i++) {
//            data[i] = Integer.valueOf(lines.get(i));
//        }
//        return data;
//    }

}
