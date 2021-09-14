package web_po.page;

import org.openqa.selenium.WebElement;

import java.util.List;

public class ContactsPage extends POBasePage {

    public ContactsPage addContactBut(){
        click(XPATH,"//*[@class=\"ww_operationBar\"]/a[text()=\"添加成员\"]");
        return this;
    }

    /**
     * 添加成员
     * @param userName
     * @param accountName
     * @param phoneNumber
     * @return
     */
    public ContactsPage addContact(String userName,String accountName,String phoneNumber){
        //姓名
        sendKeys(ID,"username",userName);
        //账号
        sendKeys(ID,"memberAdd_acctid",accountName);
        //手机号
        sendKeys(ID,"memberAdd_phone",phoneNumber);
        //保存
        click(LINK_TEXT,"保存");
        return this;
    }

    /**
     * 搜索成员
     * @param accountName
     * @return
     */
    public String searchContact(String accountName){
        //防止运行过快
        sleepSeconds(1);
        //搜索账号是否存在
        sendKeys(CSS_SELECTOR,"#memberSearchInput",accountName);
        //查找账号
        WebElement element = getElement(XPATH,"//*[@class=\"member_display_cover_detail\"]/div[3]");
        if(element==null){
            return "";
        }
        String text = element.getText();
        delContact();
        return text;
    }

    /**
     * 删除成员
     */
    public void delContact(){
        //防止运行过快
        sleepSeconds(1);
        //删除
        click(CSS_SELECTOR,"a.qui_btn.ww_btn.js_del_member");
        //确定
        click(XPATH,"//a[@class=\"qui_btn ww_btn ww_btn_Blue\" and text()=\"确认\"]");
        sleepSeconds(1);
    }

    /**
     * 添加部门
     * @param departName
     */
    public ContactsPage addDepartment(String departName){
        //点击加号
        click(CSS_SELECTOR,".member_colLeft_top_addBtn");
        //点击添加部门
        click(LINK_TEXT,"添加部门");
        //部门名称输入
        sendKeys(XPATH,"//*[text()=\"部门名称\"]/..//input",departName);
        //选择部门所属部门
        //mMsg="xpath=>//*[text()=\"所属部门\"]/..//a/span[1]";
        click(LINK_TEXT,"选择所属部门");
        //点击部门 多个元素默认点击第一个
        getElement(XPATH,"//*[text()=\"所属部门\"]/../div/div/ul/li/a").click();
        //点击确定
        click(LINK_TEXT,"确定");
        return this;
    }

    /**
     * 搜索部门
     * @param departName
     * @return
     */
    public String searchDepartment(String departName){
        //防止运行过快
        sleepSeconds(1);
        //搜索账号是否存在
        sendKeys(CSS_SELECTOR,"#memberSearchInput",departName);
        //查找账号
        WebElement element = getElement(CSS_SELECTOR,"#party_name");
        if(element==null){
            return "";
        }
        return element.getText();
    }

    /**
     * 删除部门
     * @param departName
     */
    public void delDepartment(String departName){
        //防止运行过快
        sleepSeconds(1);
        //搜索账号是否存在
        List<WebElement> webElements = getElements(XPATH,"//*[@class=\"jstree-children\"]/li");
        for (int i = 0; i < webElements.size(); i++) {
            WebElement element = webElements.get(i);
            element.click();
            String name = element.getText().trim();
            System.out.println(name.equals(departName));
            if (name.equals(departName)){
                System.out.println("删除");
                String id = element.getAttribute("id");
                //每行的更多
                elementChild(element,XPATH,"//*[@id="+id+"]/a/span").click();
                //弹窗删除
                click(XPATH,"//a[@href=\"#\" and text()=\"删除\"]");
                //确定删除
                click(LINK_TEXT,"确定");
                break;
            }
        }
        sleepSeconds(1);
    }


    /**
     * 添加标签
     * @param labelName
     * @return
     */
    public ContactsPage addLabel(String labelName){
        //防止运行过快
        sleepSeconds(1);
        //点击标签
        click(XPATH,"//ul[@class=\"ww_btnGroup\"]/li[2]/a");
        //点击加号
        click(CSS_SELECTOR,".member_colLeft_top_addBtn");
        sendKeys(XPATH,"//*[text()=\"标签名称 \"]/../input",labelName);
        //点击确定
        click(LINK_TEXT,"确定");
        return this;
    }

    /**
     * 搜索标签
     * @param labelName
     * @return
     */
    public String searchLabel(String labelName){
        //防止运行过快
        sleepSeconds(2);
        //TODO 可能存在多个，这里默认获取第一个
        //搜索标签
        return text(CLASS_NAME,"ww_commonCntHead_title_inner_text");
    }

    /**
     * 删除标签
     * @param labelName
     */
    public void delLabel(String labelName) {
        //点击标签
        click(CLASS_NAME, "a.qui_btn.ww_btn.ww_btn_Active");
        //查看所有标签
        List<WebElement> webElements = getElements(XPATH, "//div[text()=\"我的标签\"]/../ul/li");
        for (int i = 0; i < webElements.size(); i++) {
            WebElement element = webElements.get(i);
            element.click();
            String name = element.getText().trim();
            System.out.println(name.equals(labelName));
            if (name.equals(labelName)) {
                System.out.println("删除");
                String id = element.getAttribute("tag-id");
                //每行的更多
                elementChild(element, XPATH,"//*[@tag-id=\"" + id + "\"]/a/i").click();
                //弹窗删除
                click(XPATH, "//a[@on-click=\"removeTag\" and text()=\"删除\"]");
                //确定删除
                click(LINK_TEXT, "确定");
                break;
            }
        }
    }

}
