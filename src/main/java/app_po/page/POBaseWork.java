package app_po.page;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class POBaseWork {

    protected static AppiumDriver<MobileElement> driver = null;
    protected static MobileElement mobileElement=null;
//    public static String appPackage = "com.tencent.wework";
//    public static String appActivity = "com.tencent.wework.launch.WwMainActivity";
//    public static String appiumServer = "http://localhost:4723/wd/hub";
    public static final String ID = "ID";
    public static final String XPATH = "XPATH";
    public static final String LINK_TEXT = "LINK_TEXT";
    public static final String PARTIAL_LINK_TEXT = "PARTIAL_LINK_TEXT";
    public static final String NAME = "NAME";
    public static final String TAG_NAME = "TAG_NAME";
    public static final String CLASS_NAME = "CLASS_NAME";
    public static final String CSS_SELECTOR = "CSS_SELECTOR";
    public static int mWidth;
    public static int mHeight;

    public static void init(String appPackage,String appActivity,String appiumServer) {
        try {
            //文件太大GitHub无法上传 error: File apk/WeCom_android_3.1.6.16398_100001.apk is 171.98 MB; this exceeds GitHub's file size limit of 100.00 MB
            //String file_path = System.getProperty("user.dir") + File.separator + "apk" + File.separator + "WeCom_android_3.1.6.16398_100001.apk";
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
            //配置apk文件
            //capabilities.setCapability(MobileCapabilityType.APP, file_path);
            capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
            capabilities.setCapability(AndroidMobileCapabilityType.DEVICE_READY_TIMEOUT, 60);
            driver = new AndroidDriver<>(new URL(appiumServer), capabilities);
            //设置隐试等待
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            mHeight=driver.manage().window().getSize().getHeight();
            mWidth=driver.manage().window().getSize().getWidth();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public static void afterTest() {
        driver.quit();
    }

    public void click(String by,String msg){
        mobileElement = getElement(by,msg);
        if (mobileElement ==null){
            //todo 异常处理 处理异常情况
            System.out.println("元素无法点击");
            // todo 添加白名单
            clickList();
            return;
        }
        mobileElement.click();
    }
    public boolean sendKeys(String by,String msg,String text){
        mobileElement = getElement(by,msg);
        if (mobileElement ==null){
            //todo 异常处理 处理异常情况
            System.out.println("元素无法点击");
            return false;
        }
        if (text.isEmpty() ){
            return false;
        }
        mobileElement.sendKeys(text);
        return true;
    }
    public String text(String by,String msg){
        mobileElement = getElement(by,msg);
        if (mobileElement ==null){
            //todo 异常处理 处理异常情况
            System.out.println("元素无法点击");
            return "";
        }
        return mobileElement.getText().trim();
    }
    public void sleepSeconds(int time){
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void clickList(){
        //异常白名单
    }
    protected MobileElement elementChild(MobileElement element, String by, String msg){
        return element.findElement(getStr2By(by,msg));
    }
    protected MobileElement getElement(String by,String msg){
        try{
            return (MobileElement) new WebDriverWait(driver,10*1000,500)
                    .until(ExpectedConditions.visibilityOfElementLocated(getStr2By(by,msg)));
        }catch (TimeoutException e){
            e.printStackTrace();
            System.out.println(by+" "+msg+" 元素查询超时");
        }
        return null;
    }
    /**
     * 获取toast的文本内容
     * @param driver
     * @param time
     * @return
     */
    public String toastText(AndroidDriver driver,long time){
        return new WebDriverWait(driver, time, 1000)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"android.widget.Toast\"]"))).getText();
    }

    /**
     * 滚动到当前元素
     * @param by
     * @param msg
     * @return
     */
    public WebElement swipeScreen(String by,String msg){
        String str = "new UiScrollable(new UiSelector().scrollable(true))";
            switch (by) {
                case ID:
                    str += ".scrollIntoView(new UiSelector().resourceId(\"" + msg + "\"))";
                    break;
                case LINK_TEXT:
                    str += ".scrollIntoView(new UiSelector().text(\"" + msg + "\"))";
                    break;
                default:
            }
        return driver.findElement(MobileBy.AndroidUIAutomator(str));
    }

    /**
     * 该滚动无效
     * @param driver
     * @param start_x
     * @param start_y
     * @param end_x
     * @param end_y
     * @param time
     */
    public void swipeScreen(AndroidDriver driver,int start_x,int start_y,int end_x,int end_y,long time){
        TouchAction action =new TouchAction(driver);
        action.press(PointOption.point(start_x,start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(time)))
                .moveTo(PointOption.point(end_x,end_y))
                .release().perform();
    }
    protected List<MobileElement> getElements(String by, String msg){
        if(getElement(by,msg)!=null){
            return driver.findElements(getStr2By(by,msg));
        }
        return null;
    }
    private By getStr2By(String by, String msg) {
        By var=null;
        switch (by) {
            case ID:
                var = By.id(msg);
                break;
            case XPATH:
                var = By.xpath(msg);
                break;
            case LINK_TEXT:
                var = By.linkText(msg);
                break;
            case PARTIAL_LINK_TEXT:
                var = By.partialLinkText(msg);
                break;
            case NAME:
                var = By.name(msg);
                break;
            case TAG_NAME:
                var = By.tagName(msg);
                break;
            case CLASS_NAME:
                var = By.className(msg);
                break;
            case CSS_SELECTOR:
                var = By.cssSelector(msg);
                break;
            default:
                System.out.println(msg);
                var = null;
                break;
        }
        return var;
    }
}
