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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "time_accept")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TimeAccept.findAll", query = "SELECT t FROM TimeAccept t"),
    @NamedQuery(name = "TimeAccept.findById", query = "SELECT t FROM TimeAccept t WHERE t.id = :id"),
    @NamedQuery(name = "TimeAccept.findByIdtimeline", query = "SELECT t FROM TimeAccept t WHERE t.idtimeline = :idtimeline"),
    @NamedQuery(name = "TimeAccept.findByIduser", query = "SELECT t FROM TimeAccept t WHERE t.iduser = :iduser")})
public class TimeAccept implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idtimeline")
    private Integer idtimeline;
    @Column(name = "iduser")
    private String iduser;

    public TimeAccept() {
    }

    public TimeAccept(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdtimeline() {
        return idtimeline;
    }

    public void setIdtimeline(Integer idtimeline) {
        this.idtimeline = idtimeline;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
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
        if (!(object instanceof TimeAccept)) {
            return false;
        }
        TimeAccept other = (TimeAccept) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.TimeAccept[ id=" + id + " ]";
    }
    
}
