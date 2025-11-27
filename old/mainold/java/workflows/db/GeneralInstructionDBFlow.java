package workflows.db;

import extensions.UIActions;
import io.qameta.allure.Step;
import utilities.CommonOps;

public class GeneralInstructionDBFlow extends CommonOps {


    @Step(" Login to Emr2 with params from db")
    public static void loginWithDB(){
        System.out.println("enter to function loginWithDB");
//        String query = "SELECT userName,password FROM dbo.users WHERE id=1";
//        List<String> list = DBAction.getColumnsInRows(query);
//        UIActions.updateText( emrLogin.txt_username ,list.get(0));
//        UIActions.updateText( emrLogin.txt_password, list.get(1));

        UIActions.updateText( emrLogin.txt_username ,"test");
        UIActions.updateText( emrLogin.txt_password, "Te231121");
        UIActions.click(emrLogin.btn_submitBtn);

    }

}
