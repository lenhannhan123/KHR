/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Entities;

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
 * @author Admin
 */
@Entity
@Table(name = "account_position", catalog = "khr", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "AccountPosition.findAll", query = "SELECT a FROM AccountPosition a"),
        @NamedQuery(name = "AccountPosition.findById", query = "SELECT a FROM AccountPosition a WHERE a.id = :id"),
        @NamedQuery(name = "AccountPosition.findByMail", query = "SELECT a FROM AccountPosition a WHERE a.mail = :mail"),
        @NamedQuery(name = "AccountPosition.findBySalary", query = "SELECT a FROM AccountPosition a WHERE a.salary = :salary")})
public class AccountPosition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Salary")
    private int salary;
    @JoinColumn(name = "Mail", referencedColumnName = "Mail")
    @ManyToOne(optional = false)
    private Account mail;
    @JoinColumn(name = "Id_Position", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Position idPosition;

    public AccountPosition() {
    }

    public AccountPosition(Integer id) {
        this.id = id;
    }

    public AccountPosition(Integer id, int salary) {
        this.id = id;
        this.salary = salary;
    }

    public AccountPosition(Integer id, int salary, Account Mail, Position position) {
        this.id = id;
        this.salary = salary;
        this.mail = Mail;
        this.idPosition = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Account getMail() {
        return mail;
    }

    public void setMail(Account mail) {
        this.mail = mail;
    }

    public Position getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(Position idPosition) {
        this.idPosition = idPosition;
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
        if (!(object instanceof AccountPosition)) {
            return false;
        }
        AccountPosition other = (AccountPosition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.AccountPosition[ id=" + id + " ]";
    }

}
