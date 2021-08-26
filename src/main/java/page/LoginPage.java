package page;

public class LoginPage extends POBasePage {

    private String url = "https://work.weixin.qq.com/wework_admin/frame";

    public void saveCookie()  {
        /***
         * 打开企业微信，手动扫码后保存cookie
         */
    }


    public IndexPage loginCookie()  {
        /**
         * cookie未过期时，读取yaml跳过微信扫码
         */
        return new IndexPage();
    }
}
