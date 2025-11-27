package pages;


import actionUtilies.UIActions;
import drivers.DriverManager;
import helpers.FilesHelper;
import org.openqa.selenium.By;

public class LoginPage extends BasePage{

    private By txt_username = By.id("user_name");
    private By txt_password = By.id("password");
    private By btn_submitBtn = By.id("submitBtn");
    private By btn_clearBtn = By.cssSelector("button.btn.btn-default.clear");
    private By spn_message = By.xpath("//form/div[2]/span");


    /**
     * navigate to url system
     */
    public void navigateToEMR(){
        String env =  System.getProperty("env" , "prod");
        driver= DriverManager.getInstance();
        driver.get(FilesHelper.getData(env));
    }

    public void login(String user , String pass , String role){
        navigateToEMR();
        insertUserAndPass(user,pass);
        if(user.equals("test")){
            ChooseRolePage chooseRolePage=new ChooseRolePage();
            chooseRolePage.chooseRole(role);
        }

    }
    private void insertUserAndPass(String user, String pass) {
        UIActions.clearText(txt_username);
        UIActions.typeText(txt_username,user);
        UIActions.typeText(txt_password,pass);
        UIActions.click(btn_submitBtn);
    }

}
