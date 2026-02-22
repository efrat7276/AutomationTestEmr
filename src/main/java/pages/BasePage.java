package pages;

import actionUtilies.UIActions;
import drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
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
