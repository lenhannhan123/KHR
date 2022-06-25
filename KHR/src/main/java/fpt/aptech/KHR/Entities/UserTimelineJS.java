package fpt.aptech.KHR.Entities;

public class UserTimelineJS {

    public UserTimelineJS(String mail, String name, boolean isAdd) {
        this.mail = mail;
        this.name = name;
        this.isAdd = isAdd;
    }

    public UserTimelineJS() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    private String mail;
    private String name;
    private boolean isAdd;
}
