package workflows.db;

import extensions.DBAction;
import extensions.UIActions;
import io.qameta.allure.Step;
import utilities.CommonOps;

import java.util.List;

public class GeneralWithDBFlow extends CommonOps {


    @Step(" Login to Emr2 with params from db")
    public static void loginWithDB(){
        String query = "SELECT userName,password FROM dbo.users WHERE id=1";
        List<String> list = DBAction.getRow(query);
        UIActions.updateText( emrLogin.txt_username ,list.get(0));
        UIActions.updateText( emrLogin.txt_password, list.get(1));
        UIActions.click(emrLogin.btn_submitBtn);

    }

//    @Step(" Remove Instruction from db by cpoeId")
//    public static void removeInstruction(String mispar_ishpuz){
//
//        String query;
//        query = "SELECT cpoeInstructionID FROM cpoe.cpoeInstructions WHERE mispar_ishpuz=" + mispar_ishpuz;
//        DBAction.
//        for(int i=0; i< )
//          query = "EXECUTE cpoe.deleteDrugInstruction @cpoeId = " + , @cHours = 0";
//
//
//    }


}
