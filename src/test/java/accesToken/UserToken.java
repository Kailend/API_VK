package accesToken;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import users.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kailend on 17.12.2016.
 */
public class UserToken {

    WebDriver driver;

    @Test
    public void reciveToken(){
            User user=new User();
            driver=new JBrowserDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
            WebDriverWait wait=new WebDriverWait(driver,20);
            driver.get("https://vk.com/login");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
            driver.findElement(By.id("email")).sendKeys(user.mail);
            driver.findElement(By.id("pass")).sendKeys(user.password);
            driver.findElement(By.id("login_button")).click();
            wait.until(ExpectedConditions.urlContains(String.valueOf(user.userID)));
            driver.get("https://oauth.vk.com/authorize?client_id=1&redirect_uri=https://oauth.vk.com/access_token&scope=photos,offline&response_type=token&v=5.60");
            if(isElement(By.id("oauth_wrap_content"),10)){
                driver.findElement(By.xpath(".//*[@id='oauth_wrap_content']//button[1]")).click();
            }
            wait.until(ExpectedConditions.urlContains("id="+user.userID));
            String str= driver.getCurrentUrl();
            str=str.substring(str.indexOf("access"));
            System.out.println(str);

        }

    public boolean isElement(By by, int time){
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        List<WebElement> elementList=driver.findElements(by);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        if (elementList.size()>0) {
            return true;
        }
        else{
            return false;
        }
    }

    }
