package fpt.aptech.khrmobile.Entities;

public class Image {
    private String mail;
    private String avatar;

    public Image(String mail, String avatar) {
        this.mail = mail;
        this.avatar = avatar;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
