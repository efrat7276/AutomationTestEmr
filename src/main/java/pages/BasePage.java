package pages;

import actionUtilies.UIActions;
import drivers.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

@Slf4j
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected UserSignModalPage userSignModalPage;
    protected MedicinePrep medicinePrep;

    private final String TITLE_TEMPLATE = "//span[normalize-space(text())='%s']";

    public BasePage() {
        this.driver = DriverManager.getInstance();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.userSignModalPage = new UserSignModalPage();
        this.medicinePrep = new MedicinePrep();
    }

    public Boolean verificationTitleIsDisplay(String titleText){
        String dynamicXpath = String.format(TITLE_TEMPLATE, titleText);
        By targetLocator = By.xpath(dynamicXpath);
        return driver.findElement(targetLocator).isDisplayed();
    }

}
