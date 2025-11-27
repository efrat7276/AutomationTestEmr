package workflows.db;

import io.qameta.allure.Step;
import utilities.CommonOps;
import utilities.ManageDDT;


public class DrugsWithDBFlow extends CommonOps {

    @Step(" get drug list from db")
    public static void getDrugsList(){
        String query = "SELECT TOP 5 dc.item_no, dc.drugDescription, dc.dosageTafnit , ra.routeAdministrationEN\n" +
                "FROM cpoe.drugsCode AS dc \n" +
                "INNER JOIN cpoe.drugUnitMeasureCode AS dum\n" +
                "ON dum.drugUnitMeasureID = dc.drugUnitMeasureID \n" +
                "INNER JOIN cpoe.drugFormOfGivingDefault AS dfg\n" +
                "ON dc.drugFormOfGivingID = dfg.drugFactoryFormOfGivingID\n" +
                "INNER JOIN cpoe.routeAdministrationCode AS ra\n" +
                "ON ra.routeAdministrationID = dfg.routeAdministrationID\n" +
                "WHERE dc.drugStatusID=2\n" +
                "AND dc.drugFormOfGivingID=1\n" +
                "AND dfg.isDefault = 1\n" +
                "AND dc.drugAtRisk = 0\n" +
                "AND dc.infectiousDiseaseSpecialist = 0\n" +
                "AND dc.isAntibiotics = 0\n" +
                "AND dc.dosageTafnit  IS NOT NULL  \n" +
                "ORDER BY dc.item_no\n";
                 ManageDDT.writeToCSV(query, "drugsDailyNew");
    }


    @Step(" get drug list from db")
    public static void getDrugsSOSList(){
        String query = "SELECT TOP 2 dc.item_no, dc.drugDescription, dc.dosageTafnit , ra.routeAdministrationEN\n" +
                "FROM cpoe.drugsCode AS dc \n" +
                "INNER JOIN cpoe.drugUnitMeasureCode AS dum\n" +
                "ON dum.drugUnitMeasureID = dc.drugUnitMeasureID \n" +
                "INNER JOIN cpoe.drugFormOfGivingDefault AS dfg\n" +
                "ON dc.drugFormOfGivingID = dfg.drugFactoryFormOfGivingID\n" +
                "INNER JOIN cpoe.routeAdministrationCode AS ra\n" +
                "ON ra.routeAdministrationID = dfg.routeAdministrationID\n" +
                "WHERE dc.drugStatusID=2\n" +
                "AND dc.drugFormOfGivingID=1\n" +
                "AND dfg.isDefault = 1\n" +
                "AND dc.drugAtRisk = 0\n" +
                "AND dc.infectiousDiseaseSpecialist = 0\n" +
                "AND dc.isAntibiotics = 0\n" +
                "AND dc.dosageTafnit  IS NOT NULL  \n" +
                "ORDER BY dc.item_no desc\n";
        ManageDDT.writeToCSV(query, "drugsSOS");
    }

    @Step(" get drugInfectiousDiseaseSpecial list from db")
    public static void getDrugsInfectiousDiseaseSpecialList(){
        String query = "SELECT TOP 3 dc.item_no, dc.drugDescription, dc.dosageTafnit , ra.routeAdministrationEN\n" +
                "FROM cpoe.drugsCode AS dc \n" +
                "INNER JOIN cpoe.drugUnitMeasureCode AS dum\n" +
                "ON dum.drugUnitMeasureID = dc.drugUnitMeasureID \n" +
                "INNER JOIN cpoe.drugFormOfGivingDefault AS dfg\n" +
                "ON dc.drugFormOfGivingID = dfg.drugFactoryFormOfGivingID\n" +
                "INNER JOIN cpoe.routeAdministrationCode AS ra\n" +
                "ON ra.routeAdministrationID = dfg.routeAdministrationID\n" +
                "WHERE dc.drugStatusID=2\n" +
                "AND dc.drugFormOfGivingID=1\n" +
                "AND dfg.isDefault = 1\n" +
                "AND dc.infectiousDiseaseSpecialist = 1\n" +
                "ORDER BY dc.item_no\n";
        ManageDDT.writeToCSV(query, "DrugsInfectiousDiseaseSpecial");
    }

    @Step(" get drugAsRisk list from db")
    public static void getDrugsAsRiskList(){
        String query = "SELECT TOP 3 dc.item_no, dc.drugDescription, dc.dosageTafnit , ra.routeAdministrationEN\n" +
                "FROM cpoe.drugsCode AS dc \n" +
                "INNER JOIN cpoe.drugUnitMeasureCode AS dum\n" +
                "ON dum.drugUnitMeasureID = dc.drugUnitMeasureID \n" +
                "INNER JOIN cpoe.drugFormOfGivingDefault AS dfg\n" +
                "ON dc.drugFormOfGivingID = dfg.drugFactoryFormOfGivingID\n" +
                "INNER JOIN cpoe.routeAdministrationCode AS ra\n" +
                "ON ra.routeAdministrationID = dfg.routeAdministrationID\n" +
                "WHERE dc.drugStatusID=2\n" +
                "AND dc.drugFormOfGivingID IN (1,2)\n" +
                "AND dc.infectiousDiseaseSpecialist = 0\n" +
                "AND dc.drugAtRisk = 1\n" +
                "AND dfg.isDefault=1\n" +
                "ORDER BY dc.item_no";
        ManageDDT.writeToCSV(query, "drugsDailyDrugAtRiskNew");
    }


    @Step(" get nutrition list from db")
    public static void getNutritionList(){
        String query = "SELECT TOP 3  dc.item_no, dc.drugDescription, dc.dosageTafnit , dc.drugUnitMeasureID\n" +
                "FROM cpoe.drugsCode AS dc\n" +
                "INNER JOIN cpoe.drugUnitMeasureCode AS dum\n" +
                "ON dum.drugUnitMeasureID = dc.drugUnitMeasureID\n" +
                "INNER JOIN cpoe.drugFormOfGivingDefault AS dfg\n" +
                "ON dc.drugFormOfGivingID = dfg.drugFactoryFormOfGivingID\n" +
                "INNER JOIN cpoe.routeAdministrationCode AS ra\n" +
                "ON ra.routeAdministrationID = dfg.routeAdministrationID\n" +
                "WHERE dc.drugStatusID=2\n" +
                "AND dfg.isDefault = 1\n" +
                "AND (dc.drugFormOfGivingID=10\n" +
                "OR dc.nutritionApprovedSolution = 1 )\n" +
                "ORDER BY dc.item_no DESC";
        ManageDDT.writeToCSV(query, "NutritionList");
    }






}
