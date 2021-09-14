package app_po.page;


public class SwitchTab extends POBaseWork {

    /**
     * 选择消息
     * @return
     */
//    public MsgPage switchMsg(){
//        msg="xpath=>//*[@resource-id=\"com.tencent.wework:id/dy5\" and @text=\"消息\"]";
//        click(driver,time,msg);
//        return new MsgPage();
//    }

    /**
     * 通讯录
     * @return
     */
    public AddressPage switchAdd (){
        click(XPATH,"//*[@resource-id=\"com.tencent.wework:id/dy5\" and @text=\"通讯录\"]");
        return new AddressPage();
    }

//    /**
//     * 工作台
//     * @return
//     */
//    public WorkPage switchWork (){
//        msg="xpath=>//*[@resource-id=\"com.tencent.wework:id/dy5\" and @text=\"工作台\"]";
//        click(driver,time,msg);
//        return new WorkPage();
//    }

//    /**
//     * 我
//     * @return
//     */
//    public MePage switchMe (){
//        click(XPATH,"//*[@resource-id=\"com.tencent.wework:id/dy5\" and @text=\"我\"]");
//        return new MePage ();
//    }
}
