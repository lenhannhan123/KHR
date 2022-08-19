package fpt.aptech.khrmobile.Entities;

import java.io.Serializable;

public class Account  implements Serializable {
    private String mail;
    private String password;
    private String fullname;
    private String phone;
    private String birthdate;
    private boolean gender;
    private String code;
    private short role;
    private String recoverycode;
    private boolean status;
    private String avatar;
    private String googleid;

    public Account(String mail, String password, String fullname, String phone, String birthdate, boolean gender, String code, short role, String recoverycode, boolean status, String avatar) {
        this.mail = mail;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.birthdate = birthdate;
        this.gender = gender;
        this.code = code;
        this.role = role;
        this.recoverycode = recoverycode;
        this.status = status;
        this.avatar = avatar;
    }

    public Account() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public String getRecoverycode() {
        return recoverycode;
    }

    public void setRecoverycode(String recoverycode) {
        this.recoverycode = recoverycode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGoogleid() {
        return googleid;
    }

    public void setGoogleid(String googleid) {
        this.googleid = googleid;
    }


}
