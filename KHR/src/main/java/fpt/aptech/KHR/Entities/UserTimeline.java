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
@Table(name = "user_timeline")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserTimeline.findAll", query = "SELECT u FROM UserTimeline u"),
    @NamedQuery(name = "UserTimeline.findById", query = "SELECT u FROM UserTimeline u WHERE u.id = :id"),
    @NamedQuery(name = "UserTimeline.findByShiftcode", query = "SELECT u FROM UserTimeline u WHERE u.shiftcode = :shiftcode")})
public class UserTimeline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Shift_code")
    private short shiftcode;
    @JoinColumn(name = "Mail", referencedColumnName = "Mail")
    @ManyToOne(optional = false)
    private Account mail;
    @JoinColumn(name = "Id_Timeline", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Timeline idTimeline;

    public UserTimeline() {
    }

    public UserTimeline(Integer id) {
        this.id = id;
    }

    public UserTimeline(Integer id, short shiftcode) {
        this.id = id;
        this.shiftcode = shiftcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getShiftcode() {
        return shiftcode;
    }

    public void setShiftcode(short shiftcode) {
        this.shiftcode = shiftcode;
    }

    public Account getMail() {
        return mail;
    }

    public void setMail(Account mail) {
        this.mail = mail;
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
        if (!(object instanceof UserTimeline)) {
            return false;
        }
        UserTimeline other = (UserTimeline) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.UserTimeline[ id=" + id + " ]";
    }
    
}
