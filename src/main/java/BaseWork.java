
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import page.LoginPage;

public class BaseWork {


    @BeforeAll
    public static void beforeAll(){

    }

    @AfterAll
    public static void afterAll(){
    }

    /**
     * 获取web cookie
     */
    @Test
    void getWebCookie(){
        new LoginPage().saveCookie();
    }


    /**
     * 添加web 部门
     * @param departName
     */
    @ParameterizedTest
    @ValueSource(strings = {"depart1612835760","depart1611834760","depart1613833760"})
    void addWebDepartmentResult(String departName) {
        System.out.println("参数："+departName);
    }

}
