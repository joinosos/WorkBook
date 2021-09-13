package page;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.Cookie;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LoginPage extends POBasePage {

    private String url = "https://work.weixin.qq.com/wework_admin/frame";


    public void saveCookie()  {
        try {
            //打开企业微信
            getUrl(url);
            sleepSeconds(10);
            refresh();
            Set<Cookie> cookies = driver.manage().getCookies();
            cookies.forEach(cookie -> System.out.println(cookie.getName() + " " + cookie.getValue()));
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.writeValue(new File("src/main/resources/cookie.yaml"), cookies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public IndexPage loginCookie()  {
        try {
            driver.get(url);
           sleepSeconds(5);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            TypeReference<List<HashMap<String, Object>>> typeReference = new TypeReference<List<HashMap<String, Object>>>() {
            };
            List<HashMap<String, Object>> cookies = mapper.readValue(new File("src/main/resources/cookie.yaml"), typeReference);
            cookies.forEach(cookie -> {
                driver.manage().addCookie(new Cookie(cookie.get("name").toString(), cookie.get("value").toString()));
            });
            sleepSeconds(5);
            driver.navigate().refresh();
        }catch (IOException e){
            e.printStackTrace();
        }
        return new IndexPage();
    }
}
