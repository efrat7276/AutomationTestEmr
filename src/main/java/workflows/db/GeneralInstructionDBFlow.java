package workflows.db;

import extensions.DBAction;
import extensions.UIActions;
import io.qameta.allure.Step;
import utilities.CommonOps;

import java.util.List;

public class GeneralInstructionDBFlow extends CommonOps {


    @Step(" Login to Emr2 with params from db")
    public static void loginWithDB(){
        System.out.println("enter to function loginWithDB");
        String query = "SELECT userName,password FROM dbo.users WHERE id=1";
        List<String> list = DBAction.getRow(query);
        UIActions.updateText( emrLogin.txt_username ,list.get(0));
        UIActions.updateText( emrLogin.txt_password, list.get(1));
        UIActions.click(emrLogin.btn_submitBtn);

    }

}
