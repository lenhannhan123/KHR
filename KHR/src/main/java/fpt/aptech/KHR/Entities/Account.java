/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author jthie
 */
@Entity
@Table(name = "account")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
        @NamedQuery(name = "Account.findByMail", query = "SELECT a FROM Account a WHERE a.mail = :mail"),
        @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
        @NamedQuery(name = "Account.findByFullname", query = "SELECT a FROM Account a WHERE a.fullname = :fullname"),
        @NamedQuery(name = "Account.findByPhone", query = "SELECT a FROM Account a WHERE a.phone = :phone"),
        @NamedQuery(name = "Account.findByBirthdate", query = "SELECT a FROM Account a WHERE a.birthdate = :birthdate"),
        @NamedQuery(name = "Account.findByGender", query = "SELECT a FROM Account a WHERE a.gender = :gender"),
        @NamedQuery(name = "Account.findByCode", query = "SELECT a FROM Account a WHERE a.code = :code"),
        @NamedQuery(name = "Account.findByRole", query = "SELECT a FROM Account a WHERE a.role = :role"),
        @NamedQuery(name = "Account.findByRecoverycode", query = "SELECT a FROM Account a WHERE a.recoverycode = :recoverycode"),
        @NamedQuery(name = "Account.findByStatus", query = "SELECT a FROM Account a WHERE a.status = :status"),
        @NamedQuery(name = "Account.findByAvatar", query = "SELECT a FROM Account a WHERE a.avatar = :avatar")})
public class Account implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 50)
    @Column(name = "Fullname")
    private String fullname;

    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Phone")
    private String phone;

    @Basic(optional = false)
    @NotNull()
    @Column(name = "Birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Gender")
    private boolean gender;
    @Size(max = 250)
    @Column(name = "Code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Role")
    private String role;
    @Size(max = 6)
    @Column(name = "Recovery_code")
    private String recoverycode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private boolean status;
    @Size(max = 100)
    @Column(name = "Avatar")
    private String avatar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mail")
    private List<TimelineDetail> timelineDetailList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Mail")
    private String mail;

    public Account() {
    }

    public Account(String mail) {
        this.mail = mail;
    }

    public Account(String mail, String password, String fullname, String phone, Date birthdate, boolean gender, String code, String role, boolean status, String avatar) {
        this.mail = mail;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.birthdate = birthdate;
        this.gender = gender;
        this.code = code;
        this.role = role;
        this.status = status;
        this.avatar = avatar;
    }

    public Account(String mail, String password, String fullname, String phone, Date birthdate, boolean gender, String code, String role, boolean status) {
        this.mail = mail;
        this.fullname = fullname;
        this.phone = phone;
        this.birthdate = birthdate;
        this.gender = gender;
        this.code = code;
        this.role = role;
        this.status = status;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRecoverycode() {
        return recoverycode;
    }

    public void setRecoverycode(String recoverycode) {
        this.recoverycode = recoverycode;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mail != null ? mail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.mail == null && other.mail != null) || (this.mail != null && !this.mail.equals(other.mail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.Account[ mail=" + mail + " ]";
    }

    @JoinColumn(name = "Id_Store", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Store idStore;

    public Store getIdStore() {
        return idStore;
    }

    public void setIdStore(Store idStore) {
        this.idStore = idStore;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public boolean getGender() {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRecoverycode() {
        return recoverycode;
    }

    public void setRecoverycode(String recoverycode) {
        this.recoverycode = recoverycode;
    }

    public boolean getStatus() {
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

    @XmlTransient
    public List<TimelineDetail> getTimelineDetailList() {
        return timelineDetailList;
    }

    public void setTimelineDetailList(List<TimelineDetail> timelineDetailList) {
        this.timelineDetailList = timelineDetailList;
    }


}
