/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "account_notification", catalog = "khr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountNotification.findAll", query = "SELECT a FROM AccountNotification a"),
    @NamedQuery(name = "AccountNotification.findById", query = "SELECT a FROM AccountNotification a WHERE a.id = :id"),
    @NamedQuery(name = "AccountNotification.findByStatus", query = "SELECT a FROM AccountNotification a WHERE a.status = :status")})
public class AccountNotification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private boolean status;
    @JoinColumn(name = "Mail", referencedColumnName = "Mail")
    @ManyToOne(optional = false)
//    @JsonBackReference
    private Account mail;
    @JoinColumn(name = "Id_notification", referencedColumnName = "Id")
    @ManyToOne(optional = false)
//    @JsonBackReference
    private Notification idnotification;

    public AccountNotification() {
    }

    public AccountNotification(Integer id) {
        this.id = id;
    }

    public AccountNotification(Integer id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Account getMail() {
        return mail;
    }

    public void setMail(Account mail) {
        this.mail = mail;
    }

    public Notification getIdnotification() {
        return idnotification;
    }

    public void setIdnotification(Notification idnotification) {
        this.idnotification = idnotification;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountNotification)) {
            return false;
        }
        AccountNotification other = (AccountNotification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.AccountNotification[ id=" + id + " ]";
    }
    
}
