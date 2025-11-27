package workflows.db;

import actionUtilies.DBAction;
import io.qameta.allure.Step;

import java.sql.SQLException;

public class InsertToDBFlows {

    @Step("insert to patient a catheter")
    public static String AddToPatientOrCatheter(int  mispar_ishpuz , int sug) throws SQLException {
    String sqlQuery = "INSERT INTO dbo.emr_tzantar (mispar_ishpuz,k_zantar,t_z_machdir,environmentSrc) VALUES(?,?,?,?)";
     int [] parameters = new int[4];
     parameters[0]=mispar_ishpuz;
     parameters[1]=sug;
     parameters[2]=1;
     parameters[3]=3;
     return DBAction.InsertQuery(sqlQuery);

    }



}
