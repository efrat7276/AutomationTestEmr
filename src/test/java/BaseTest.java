import drivers.DriverManager;
import org.testng.annotations.AfterTest;

public class BaseTest {

    @AfterTest()
    public void closeBrowser(){
        DriverManager.getInstance().quit();
    }
}
