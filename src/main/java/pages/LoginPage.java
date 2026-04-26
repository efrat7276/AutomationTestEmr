package pages;

import actionUtilies.UIActions;
import drivers.DriverManager;
import helpers.Constants;
import helpers.FilesHelper;
import lombok.extern.slf4j.Slf4j;
import pages.mainPages.PatientsListPage;
import org.openqa.selenium.By;

@Slf4j
public class LoginPage extends BasePage {

    private By txt_username = By.id("user_name");
    private By txt_password = By.id("password");
    private By btn_submitBtn = By.id("submitBtn");
    PatientsListPage patientsListPage = new PatientsListPage();

    /**
     * navigate to url system
     */
    public void navigateToEMR(String env){
    driver= DriverManager.getInstance();
    String envKey = System.getProperty("env", env);
    String actualUrl = FilesHelper.getData(envKey);
    driver.get(actualUrl);
    }

    public void login(String user , String pass , String role){

    String env = Constants.CURRENT_ENV;
    log.info("Environment: {}", env);
     navigateToEMR(env);
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
