import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import javabean.ContactBean;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import page.ContactsPage;
import page.LoginPage;
import page.POBasePage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WechatTest {
    //驱动
    public static String driver_path="driver/chromedriver_mac";
    private static boolean isFirst = true;
    private static ContactsPage contactsPage=null;
    public  static POBasePage poBasePage = new POBasePage();
    //断言
    private List<Executable> executableList = new ArrayList<>();
    @BeforeAll
    public static void beforeTest(){
       POBasePage.init(driver_path);
    }

    @AfterAll
    public static void tearDown(){
        poBasePage.quit();
    }

    /**
     * 获取微信登录CooKie
     */
    @Test
    void getCookie() {
        new LoginPage().saveCookie();
    }

    /**
     * 部门的添加及删除 参数化
     * @param departName
     */
    @ParameterizedTest()
    @ValueSource(strings = {"depart1612835760","depart1611834760","depart1613833760"})
    void addDepartmentResult(String departName) {
        System.out.println("参数："+departName);
        if(isFirst) {
            contactsPage = new LoginPage().loginCookie().addMember();
            isFirst = false;
        }
        String result = contactsPage.addDepartment(departName).searchDepartment(departName);
        assertEquals(departName, result);
        poBasePage.refresh();
        contactsPage.delDepartment(departName);
        poBasePage.refresh();
    }

    /**
     * 添加标签 参数化
     * @param departName
     */
    @ParameterizedTest()
    @ValueSource(strings = {"label1612835760","label1611834760","label1613833760"})
    void addLabelResult(String departName) {
        System.out.println("参数："+departName);
        if(isFirst) {
            contactsPage = new LoginPage().loginCookie().menContacts();
            isFirst = false;
        }
        String result = contactsPage.addLabel(departName).searchLabel(departName);
        assertEquals(departName, result);
        poBasePage.refresh();
        contactsPage.delLabel(departName);
        poBasePage.refresh();
    }

    /**
     * 添加成员验证账号、姓名、手机号等字段
     * @param contactBean
     */
    @ParameterizedTest()
    @MethodSource()
//    @Ignore
    void addContactResult(ContactBean contactBean) {
        System.out.println("参数："+contactBean.toString());
        String userName = contactBean.getUserName();
        String accountName = contactBean.getAccountName();
        String phoneNumber = contactBean.getPhoneNumber();
        System.out.println(contactBean.toString());
        if(isFirst) {
            contactsPage = new LoginPage().loginCookie().menContacts();
            isFirst = false;
        }
        String result =contactsPage.addContactBut().addContact(userName, accountName, phoneNumber).searchContact(accountName);
        if(!result.equals("")) {
            String finalResult  = result.substring(result.indexOf("：") + 1);
            executableList.add(()->assertEquals(accountName, finalResult));
        }else {
            String finalResult = result;
            executableList.add(()->assertEquals(accountName, finalResult));
        }

    }

    /**
     * 读取账号配置参数
     * @return
     */
    static List<ContactBean> addContactResult(){
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            TypeReference<List<ContactBean>> typeReference = new TypeReference<List<ContactBean>>() {};
            return mapper.readValue(new File("src/main/resources/contacts.yaml"),typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
