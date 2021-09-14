package web_po.page;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class POBasePage {

    protected static WebDriver driver=null;
    protected WebElement webElement = null;
    public static final String ID = "ID";
    public static final String XPATH = "XPATH";
    public static final String LINK_TEXT = "LINK_TEXT";
    public static final String PARTIAL_LINK_TEXT = "PARTIAL_LINK_TEXT";
    public static final String NAME = "NAME";
    public static final String TAG_NAME = "TAG_NAME";
    public static final String CLASS_NAME = "CLASS_NAME";
    public static final String CSS_SELECTOR = "CSS_SELECTOR";

    public static void init(String chrome_driver_path){
        //设置驱动位置
        if (! new File(chrome_driver_path).exists()){
            throw new IllegalStateException("驱动不存在 path ="+chrome_driver_path);
        }
        System.setProperty("webdriver.chrome.driver", chrome_driver_path);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    public void getUrl(String url){
        driver.get(url);
    }
    public void quit(){
        /***
         * 退出全部窗口
         */
        driver.quit();
    }
    public void close(){
        /**
         * 关闭当前窗口
         */
        driver.quit();
    }
    public void refresh(){
        driver.navigate().refresh();
    }
    public void click(String by,String msg){
        webElement = getElement(by,msg);
        if (webElement ==null){
            //todo 异常处理 处理异常情况
            System.out.println("元素无法点击");
            // todo 添加白名单
            clickList();
            return;
        }
        webElement.click();
    }
    public void sendKeys(String by,String msg,String text){
        webElement = getElement(by,msg);
        if (webElement ==null){
            //todo 异常处理 处理异常情况
            System.out.println("元素无法点击");
            return;
        }
        if (text.isEmpty() ){
            return;
        }
        webElement.sendKeys(text);
    }
    public String text(String by,String msg){
        webElement = getElement(by,msg);
        if (webElement ==null){
            //todo 异常处理 处理异常情况
            System.out.println("元素无法点击");
            return "";
        }
        return webElement.getText().trim();
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
    protected WebElement elementChild(WebElement element,String by,String msg){
        return element.findElement(getStr2By(by,msg));
    }
    protected WebElement getElement(String by,String msg){
        try{
        return new WebDriverWait(driver,10*1000,500)
                .until(ExpectedConditions.visibilityOfElementLocated(getStr2By(by,msg)));
        }catch (TimeoutException e){
            e.printStackTrace();
            System.out.println(by+" "+msg+" 元素查询超时");
        }
        return null;
    }
    protected List<WebElement> getElements(String by, String msg){
        if(getElement(by,msg)!=null){
            return driver.findElements(getStr2By(by,msg));
        }
        return null;
    }
    private By getStr2By(String by,String msg) {
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
