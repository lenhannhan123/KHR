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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "timeline", catalog = "khr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Timeline.findAll", query = "SELECT t FROM Timeline t"),
    @NamedQuery(name = "Timeline.findById", query = "SELECT t FROM Timeline t WHERE t.id = :id"),
    @NamedQuery(name = "Timeline.findByTimename", query = "SELECT t FROM Timeline t WHERE t.timename = :timename"),
    @NamedQuery(name = "Timeline.findByStartdate", query = "SELECT t FROM Timeline t WHERE t.startdate = :startdate"),
    @NamedQuery(name = "Timeline.findByEnddate", query = "SELECT t FROM Timeline t WHERE t.enddate = :enddate"),
    @NamedQuery(name = "Timeline.findByStatus", query = "SELECT t FROM Timeline t WHERE t.status = :status")})
public class Timeline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Time_name")
    private String timename;
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
    @Column(name = "Status")
    private short status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTimeline")
    private List<Shift> shiftList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTimeline")
    private List<TimelineDetail> timelineDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTimeline")
    private List<UserTimeline> userTimelineList;

    public Timeline() {
    }

    public Timeline(Integer id) {
        this.id = id;
    }

    public Timeline(Integer id, String timename, Date startdate, Date enddate, short status) {
        this.id = id;
        this.timename = timename;
        this.startdate = startdate;
        this.enddate = enddate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimename() {
        return timename;
    }

    public void setTimename(String timename) {
        this.timename = timename;
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

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    @XmlTransient
    public List<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    @XmlTransient
    public List<TimelineDetail> getTimelineDetailList() {
        return timelineDetailList;
    }

    public void setTimelineDetailList(List<TimelineDetail> timelineDetailList) {
        this.timelineDetailList = timelineDetailList;
    }

    @XmlTransient
    public List<UserTimeline> getUserTimelineList() {
        return userTimelineList;
    }

    public void setUserTimelineList(List<UserTimeline> userTimelineList) {
        this.userTimelineList = userTimelineList;
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
        if (!(object instanceof Timeline)) {
            return false;
        }
        Timeline other = (Timeline) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.Timeline[ id=" + id + " ]";
    }
    
}
