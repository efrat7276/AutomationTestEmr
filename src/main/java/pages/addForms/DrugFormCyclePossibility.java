package pages.addForms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DrugFormCyclePossibility {


    public By numberOfTimesCycle = By.xpath("//ul[@aria-labelledby='numberOfTimes_daily']/li");

    public By btn_numberOfTimesCycle = By.id("numberOfTimes_daily");
}
