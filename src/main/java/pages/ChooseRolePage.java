package pages;

import actionUtilies.UIActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class ChooseRolePage {


    private By listRole = By.xpath("//ul[@class='list-group']/li");

    public void chooseRole(String roleDescription){


         UIActions.selectFromList(listRole, roleDescription);
    }
}
