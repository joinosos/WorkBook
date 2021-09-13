package page;


public class IndexPage extends POBasePage {

    public ContactsPage addMember(){
        click(LINK_TEXT,"添加成员");
        return new ContactsPage();
    }

    public ContactsPage menuIndex(){
        click(ID,"menu_index");
        return new ContactsPage();
    }

    public ContactsPage menContacts(){
        click(ID,"menu_contacts");
        return new ContactsPage();
    }

}
