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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "over_time", catalog = "khr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OverTime.findAll", query = "SELECT o FROM OverTime o"),
    @NamedQuery(name = "OverTime.findById", query = "SELECT o FROM OverTime o WHERE o.id = :id"),
    @NamedQuery(name = "OverTime.findByContent", query = "SELECT o FROM OverTime o WHERE o.content = :content"),
    @NamedQuery(name = "OverTime.findByStatus", query = "SELECT o FROM OverTime o WHERE o.status = :status")})
public class OverTime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Content")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private short status;
    @JoinColumn(name = "Mail", referencedColumnName = "Mail")
    @ManyToOne(optional = false)
    private Account mail;
    @JoinColumn(name = "Id_Shift", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Shift idShift;

    public OverTime() {
    }

    public OverTime(Integer id) {
        this.id = id;
    }

    public OverTime(Integer id, String content, short status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Account getMail() {
        return mail;
    }

    public void setMail(Account mail) {
        this.mail = mail;
    }

    public Shift getIdShift() {
        return idShift;
    }

    public void setIdShift(Shift idShift) {
        this.idShift = idShift;
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
        if (!(object instanceof OverTime)) {
            return false;
        }
        OverTime other = (OverTime) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.OverTime[ id=" + id + " ]";
    }
    
}
