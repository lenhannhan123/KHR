/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Admin
 */
@Entity
@Table(name = "position")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Position.findAll", query = "SELECT p FROM Position p"),
        @NamedQuery(name = "Position.findById", query = "SELECT p FROM Position p WHERE p.id = :id"),
        @NamedQuery(name = "Position.findByPositionname", query = "SELECT p FROM Position p WHERE p.positionname = :positionname"),
        @NamedQuery(name = "Position.findBySalarydefault", query = "SELECT p FROM Position p WHERE p.salarydefault = :salarydefault")})
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Position_name")
    private String positionname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Salary_default")
    private int salarydefault;
//    @OneToMany(mappedBy = "idPosition")
//    private List<Shift> shiftList;

    public Position() {
    }

    public Position(Integer id) {
        this.id = id;
    }

    public Position(Integer id, String positionname, int salarydefault) {
        this.id = id;
        this.positionname = positionname;
        this.salarydefault = salarydefault;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPositionname() {
        return positionname;
    }

    public void setPositionname(String positionname) {
        this.positionname = positionname;
    }

    public int getSalarydefault() {
        return salarydefault;
    }

    public void setSalarydefault(int salarydefault) {
        this.salarydefault = salarydefault;
    }

//    @XmlTransient
//    public List<Shift> getShiftList() {
//        return shiftList;
//    }
//
//    public void setShiftList(List<Shift> shiftList) {
//        this.shiftList = shiftList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Position)) {
            return false;
        }
        Position other = (Position) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.Position[ id=" + id + " ]";
    }

}
