package app_po.page;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class AddressPage extends POBaseWork {


    /**
     * 添加成员
     *
     * @param name
     * @param phoneNum
     */
    public boolean addContact(String name, String phoneNum) {
        //添加成员
        click(XPATH, "//android.widget.TextView[@text=\"添加成员\"]");
        //手动输入添加
        click(XPATH, "//android.widget.TextView[@text=\"手动输入添加\"]");
        //姓名
        boolean flag = sendKeys(ID,"com.tencent.wework:id/ays", name);
        if (flag) {
            //手机号码
            sendKeys(ID,"com.tencent.wework:id/f4m", phoneNum);
            //去掉不发送消息
            click(ID,"com.tencent.wework:id/esx");
            //保存
            click(ID, "com.tencent.wework:id/ac9");
            return true;
        } else {
            //点击返回
            click(ID, "com.tencent.wework:id/h86");
            //点击返回
            click(ID, "com.tencent.wework:id/h86");
            return false;
        }
    }

    /**
     * 搜索成员
     *
     * @param name
     * @return
     */
    public String searchContact(String name) {
        //点击返回
        click(ID, "com.tencent.wework:id/h86");
        //查询列表
        List<MobileElement> elements = getElements(XPATH, "//android.view.ViewGroup[@resource-id=\"com.tencent.wework:id/he1\"]/android.widget.TextView");
        String result = "";
        for (WebElement element : elements) {
            result = element.getText().trim();
            if (result.equals(name)) {
                //点击
                element.click();
                //删除账号
                delContact();
                break;
            }
        }
        return result;
    }

    /**
     * 删除成员
     */
    public void delContact() {
        //点击更多
        click(ID, "com.tencent.wework:id/h8g");
        //编辑成员
        click(ID, "com.tencent.wework:id/b49");
        //TODO滚动
        //滚动屏幕 互动到底部
        swipeScreen(ID,"com.tencent.wework:id/e37");
        //删除成员
        click(ID,"com.tencent.wework:id/e37");
        //确定
        click(ID, "com.tencent.wework:id/bei");
    }

    /**
     * 添加部门
     *
     * @param departName
     * @return
     */
    public AddressPage addDepartment(String departName) {
        //右上方设置
        click(ID, "com.tencent.wework:id/h8l");
        //添加子部门
        click(ID, "com.tencent.wework:id/eep");
        //输入名字
        sendKeys(ID,"com.tencent.wework:id/be7", departName);
        //确定
        click(ID, "com.tencent.wework:id/bei");
        return this;
    }

    /**
     * 搜索部门
     *
     * @param departName
     * @return
     */
    public String searchDepartment(String departName) {
        //查询列表
        List<MobileElement> elements = getElements(XPATH,"//android.view.ViewGroup[@resource-id=\"com.tencent.wework:id/he1\"]/android.widget.TextView");
        String result = "";
        for (WebElement element : elements) {
            result = element.getText().trim();
            if (result.equals(departName)) {
                //点击
                element.click();
                //删除部门
                delDepartment();
                break;
            }
        }
        return result;
    }

    /**
     * 删除部门
     */
    public void delDepartment() {
        //更多管理
        click(ID, "com.tencent.wework:id/fti");
        //删除部门
        click(XPATH, "//android.widget.TextView[@text=\"删除部门\"]");
        //确定
        click(ID, "com.tencent.wework:id/bei");
        //×
        click(ID, "com.tencent.wework:id/h8g");
    }
}
