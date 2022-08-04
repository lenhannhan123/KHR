/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Admin
 */
@Entity
@Table(name = "timeline")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Timeline.findAll", query = "SELECT t FROM Timeline t"),
        @NamedQuery(name = "Timeline.findById", query = "SELECT t FROM Timeline t WHERE t.id = :id"),
        @NamedQuery(name = "Timeline.findByTimename", query = "SELECT t FROM Timeline t WHERE t.timename = :timename"),
        @NamedQuery(name = "Timeline.findByStartdate", query = "SELECT t FROM Timeline t WHERE t.startdate = :startdate"),
        @NamedQuery(name = "Timeline.findByEnddate", query = "SELECT t FROM Timeline t WHERE t.enddate = :enddate"),
        @NamedQuery(name = "Timeline.findByStatus", query = "SELECT t FROM Timeline t WHERE t.status = :status"),
        @NamedQuery(name = "Timeline.findByIsDelete", query = "SELECT t FROM Timeline t WHERE t.isDelete = :isDelete")})
public class Timeline implements Serializable {

    @Size(max = 50)
    @Column(name = "Time_name")
    private String timename;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsDelete")
    private short isDelete;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTimeline")
    private List<TimelineDetail> timelineDetailList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Start_date")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Column(name = "End_date")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @Column(name = "Status")
    private Short status;

    public Timeline() {
    }

    public Timeline(Integer id) {
        this.id = id;
    }

    public Timeline(Integer id, short isDelete) {
        this.id = id;
        this.isDelete = isDelete;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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


  

    @XmlTransient
    public List<TimelineDetail> getTimelineDetailList() {
        return timelineDetailList;
    }

    public void setTimelineDetailList(List<TimelineDetail> timelineDetailList) {
        this.timelineDetailList = timelineDetailList;
    }



    public short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(short isDelete) {
        this.isDelete = isDelete;
    }


}
