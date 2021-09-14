package app_po;

import app_po.javabean.ContactBean;
import app_po.page.AddressPage;
import app_po.page.SwitchTab;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WechatTest {

    public static boolean isFirst = true;
    public AddressPage addressPage;

    @Test
    void test(){
        String name ="%$!16188339005";
        String phoneNum ="13610015309";
        if (isFirst){
            addressPage = new SwitchTab().switchAdd();
        }
        boolean flag = addressPage.addContact(name,phoneNum);
        if(flag){
            System.out.println("成功");
            String result =addressPage .searchContact(name);
            System.out.println(result);
        }else {
            System.out.println("失败");
        }


//        String name = "depart13610015309";
//        String result = new SwitchTab().switchAdd().addDepartment(name).searchDepartment(name);
//        System.out.println(result);
    }



    /**
     * 添加部门
     * @param departName
     */
    @ParameterizedTest()
    @ValueSource(strings = {"depart1612835760","depart1611834760","depart1613833760"})
    void addDepartResult(String departName){
        String result = new SwitchTab().switchAdd().addDepartment(departName).searchDepartment(departName);
        assertEquals(departName, result);
    }

    /**
     * 添加成员
     * @param contactBean
     */
    @ParameterizedTest()
    @MethodSource()
    void addContactResult(ContactBean contactBean){
        System.out.println(contactBean.toString());
        String name = contactBean.getUserName();
        String phoneNum =contactBean.getPhoneNumber();
        if (isFirst){
            addressPage = new SwitchTab().switchAdd();
        }
        boolean flag = addressPage.addContact(name,phoneNum);
        if(flag){
            String result =addressPage .searchContact(name);
            assertEquals(name, result);
        }else {
            assertEquals(name, "失败");
        }
        System.out.println(contactBean.toString());

    }

    public static List<ContactBean> addContactResult(){
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            TypeReference<List<ContactBean>> typeReference = new TypeReference<List<ContactBean>>(){};
            return objectMapper.readValue(new File("src/main/resources/app_po/contacts.yaml"),typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
