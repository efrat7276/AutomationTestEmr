package pages.addForms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DrugFormTimeLimitPossibility {


    public By durationList = By.xpath("//button[@id='solutionDurationList']/following-sibling::ul/li");

    public By btn_duration = By.id("solutionDurationList");
}
