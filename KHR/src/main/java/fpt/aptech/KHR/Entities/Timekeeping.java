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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "timekeeping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Timekeeping.findAll", query = "SELECT t FROM Timekeeping t"),
    @NamedQuery(name = "Timekeeping.findById", query = "SELECT t FROM Timekeeping t WHERE t.id = :id"),
    @NamedQuery(name = "Timekeeping.findByTimestart", query = "SELECT t FROM Timekeeping t WHERE t.timestart = :timestart"),
    @NamedQuery(name = "Timekeeping.findByTimeend", query = "SELECT t FROM Timekeeping t WHERE t.timeend = :timeend"),
    @NamedQuery(name = "Timekeeping.findByTime", query = "SELECT t FROM Timekeeping t WHERE t.time = :time")})
public class Timekeeping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Time_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Time_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeend;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Time")
    private int time;
    @JoinColumn(name = "Mail", referencedColumnName = "Mail")
    @ManyToOne(optional = false)
    private Account mail;
    @JoinColumn(name = "Id_shift", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Shift idshift;

    public Timekeeping() {
    }

    public Timekeeping(Integer id) {
        this.id = id;
    }

    public Timekeeping(Integer id, Date timestart, Date timeend, int time) {
        this.id = id;
        this.timestart = timestart;
        this.timeend = timeend;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimestart() {
        return timestart;
    }

    public void setTimestart(Date timestart) {
        this.timestart = timestart;
    }

    public Date getTimeend() {
        return timeend;
    }

    public void setTimeend(Date timeend) {
        this.timeend = timeend;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Account getMail() {
        return mail;
    }

    public void setMail(Account mail) {
        this.mail = mail;
    }

    public Shift getIdshift() {
        return idshift;
    }

    public void setIdshift(Shift idshift) {
        this.idshift = idshift;
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
        if (!(object instanceof Timekeeping)) {
            return false;
        }
        Timekeeping other = (Timekeeping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.Timekeeping[ id=" + id + " ]";
    }
    
}
