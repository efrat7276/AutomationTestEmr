package utilities;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOError;
import java.util.List;

public class Listeners extends CommonOps  implements ITestListener {


   public   void onStart(ITestContext result){
       System.out.println("--------------------Starting Execution--------------------");
    }

    public   void onFinish(ITestContext result){
        System.out.println("--------------------Ending Execution--------------------");
    }

    public   void onTestStart(ITestResult result){
        System.out.println("--------Starting Test: " + result.getName() + "------------");
    }

    public   void onTestSkipped(ITestResult result){
        System.out.println("--------Skipping Test: " + result.getName() + "------------");
    }

    public   void onTestSuccess(ITestResult result){
        System.out.println("-------- Test: " + result.getName() + " Passed -----------");
    }

    public   void onTestFailure(ITestResult result){
        System.out.println("-------- Test: " + result.getName() + " Failed -----------");
        LogEntries entry = driver.manage().logs().get(LogType.BROWSER);

        List<LogEntry> logs= entry.getAll();


//        if(logs.size() == 0)
//        {
//            System.out.println("No Console Logs");
//        }
//        for(LogEntry e: logs)
//        {
//           System.out.println("Message is: " +e.getMessage());
//            System.out.println("Level is: " +e.getLevel());
//            System.out.println(e);
//        }

        saveScreenshot();
    }

   @Attachment(value = "Screen-Shot", type = "image/png")
    public byte[] saveScreenshot(){


       return ((TakesScreenshot)driver).getScreenshotAs((OutputType.BYTES));
   }


    public static File saveScreenshotFile(){
        return ((TakesScreenshot)driver).getScreenshotAs((OutputType.FILE));
    }

}



