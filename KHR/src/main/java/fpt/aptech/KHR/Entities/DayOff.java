/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "day_off", catalog = "khr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DayOff.findAll", query = "SELECT d FROM DayOff d"),
    @NamedQuery(name = "DayOff.findById", query = "SELECT d FROM DayOff d WHERE d.id = :id"),
    @NamedQuery(name = "DayOff.findByStartdate", query = "SELECT d FROM DayOff d WHERE d.startdate = :startdate"),
    @NamedQuery(name = "DayOff.findByEnddate", query = "SELECT d FROM DayOff d WHERE d.enddate = :enddate"),
    @NamedQuery(name = "DayOff.findByDaynumber", query = "SELECT d FROM DayOff d WHERE d.daynumber = :daynumber"),
    @NamedQuery(name = "DayOff.findByContent", query = "SELECT d FROM DayOff d WHERE d.content = :content"),
    @NamedQuery(name = "DayOff.findByStatus", query = "SELECT d FROM DayOff d WHERE d.status = :status")})
public class DayOff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Start_date")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "End_date")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Day_number")
    private int daynumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Content")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private short status;
    @JoinColumn(name = "Mail", referencedColumnName = "Mail")
    @ManyToOne(optional = false)
    private Account mail;

    public DayOff() {
    }

    public DayOff(Integer id) {
        this.id = id;
    }

    public DayOff(Integer id, Date startdate, Date enddate, int daynumber, String content, short status) {
        this.id = id;
        this.startdate = startdate;
        this.enddate = enddate;
        this.daynumber = daynumber;
        this.content = content;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public int getDaynumber() {
        return daynumber;
    }

    public void setDaynumber(int daynumber) {
        this.daynumber = daynumber;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DayOff)) {
            return false;
        }
        DayOff other = (DayOff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.DayOff[ id=" + id + " ]";
    }
    
}
