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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "shift", catalog = "khr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shift.findAll", query = "SELECT s FROM Shift s"),
    @NamedQuery(name = "Shift.findById", query = "SELECT s FROM Shift s WHERE s.id = :id"),
    @NamedQuery(name = "Shift.findByNumber", query = "SELECT s FROM Shift s WHERE s.number = :number"),
    @NamedQuery(name = "Shift.findByTimestart", query = "SELECT s FROM Shift s WHERE s.timestart = :timestart"),
    @NamedQuery(name = "Shift.findByIsOT", query = "SELECT s FROM Shift s WHERE s.isOT = :isOT"),
    @NamedQuery(name = "Shift.findByTimeend", query = "SELECT s FROM Shift s WHERE s.timeend = :timeend"),
    @NamedQuery(name = "Shift.findByShiftcode", query = "SELECT s FROM Shift s WHERE s.shiftcode = :shiftcode")})
public class Shift implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Number")
    private int number;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Time_start")
    @Temporal(TemporalType.TIME)
    private Date timestart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Is_OT")
    private boolean isOT;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Time_end")
    @Temporal(TemporalType.TIME)
    private Date timeend;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Shift_code")
    private int shiftcode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idshift")
    private List<Timekeeping> timekeepingList;
    @JoinColumn(name = "Id_Position", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Position idPosition;
    @JoinColumn(name = "Id_Timeline", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Timeline idTimeline;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idShift")
    private List<TimelineDetail> timelineDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idShift")
    private List<OverTime> overTimeList;

    public Shift() {
    }

    public Shift(Integer id) {
        this.id = id;
    }

    public Shift(Integer id, int number, Date timestart, boolean isOT, Date timeend, int shiftcode) {
        this.id = id;
        this.number = number;
        this.timestart = timestart;
        this.isOT = isOT;
        this.timeend = timeend;
        this.shiftcode = shiftcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getTimestart() {
        return timestart;
    }

    public void setTimestart(Date timestart) {
        this.timestart = timestart;
    }

    public boolean getIsOT() {
        return isOT;
    }

    public void setIsOT(boolean isOT) {
        this.isOT = isOT;
    }

    public Date getTimeend() {
        return timeend;
    }

    public void setTimeend(Date timeend) {
        this.timeend = timeend;
    }

    public int getShiftcode() {
        return shiftcode;
    }

    public void setShiftcode(int shiftcode) {
        this.shiftcode = shiftcode;
    }

    @XmlTransient
    public List<Timekeeping> getTimekeepingList() {
        return timekeepingList;
    }

    public void setTimekeepingList(List<Timekeeping> timekeepingList) {
        this.timekeepingList = timekeepingList;
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

    @XmlTransient
    public List<TimelineDetail> getTimelineDetailList() {
        return timelineDetailList;
    }

    public void setTimelineDetailList(List<TimelineDetail> timelineDetailList) {
        this.timelineDetailList = timelineDetailList;
    }

    @XmlTransient
    public List<OverTime> getOverTimeList() {
        return overTimeList;
    }

    public void setOverTimeList(List<OverTime> overTimeList) {
        this.overTimeList = overTimeList;
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
        if (!(object instanceof Shift)) {
            return false;
        }
        Shift other = (Shift) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.Shift[ id=" + id + " ]";
    }
    
}
