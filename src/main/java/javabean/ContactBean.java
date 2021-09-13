package javabean;

public class ContactBean {
    private String userName;
    private String accountName;
    private String phoneNumber;
    private boolean hasImg;

    public ContactBean(){

    }

    public ContactBean(String userName, String accountName, String phoneNumber, boolean hasImg) {
        this.userName = userName;
        this.accountName = accountName;
        this.phoneNumber = phoneNumber;
        this.hasImg = hasImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isHasImg() {
        return hasImg;
    }

    public void setHasImg(boolean hasImg) {
        this.hasImg = hasImg;
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "userName='" + userName + '\'' +
                ", accountName='" + accountName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hasImg=" + hasImg +
                '}';
    }
}
