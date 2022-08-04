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
 *
 * @author Admin
 */
@Entity
@Table(name = "timeline_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TimelineDetail.findAll", query = "SELECT t FROM TimelineDetail t"),
    @NamedQuery(name = "TimelineDetail.findById", query = "SELECT t FROM TimelineDetail t WHERE t.id = :id"),
    @NamedQuery(name = "TimelineDetail.findByShiftCode", query = "SELECT t FROM TimelineDetail t WHERE t.shiftCode = :shiftCode")})
public class TimelineDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Shift_Code")
    private int shiftCode;
       @JoinColumn(name = "Mail", referencedColumnName = "Mail")
    @ManyToOne(optional = false)
    private Account mail;
    @JoinColumn(name = "Id_Position", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Position idPosition;
    @JoinColumn(name = "Id_Timeline", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Timeline idTimeline;

    public TimelineDetail() {
    }

    public TimelineDetail(Integer id) {
        this.id = id;
    }

    public TimelineDetail(Integer id, int shiftCode) {
        this.id = id;
        this.shiftCode = shiftCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(int shiftCode) {
        this.shiftCode = shiftCode;
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

    public Timeline getIdTimeline() {
        return idTimeline;
    }

    public void setIdTimeline(Timeline idTimeline) {
        this.idTimeline = idTimeline;
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
        if (!(object instanceof TimelineDetail)) {
            return false;
        }
        TimelineDetail other = (TimelineDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.TimelineDetail[ id=" + id + " ]";
    }
    
}
