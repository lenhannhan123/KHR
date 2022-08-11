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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "transfer_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransferData.findAll", query = "SELECT t FROM TransferData t"),
    @NamedQuery(name = "TransferData.findById", query = "SELECT t FROM TransferData t WHERE t.id = :id"),
    @NamedQuery(name = "TransferData.findByName", query = "SELECT t FROM TransferData t WHERE t.name = :name"),
    @NamedQuery(name = "TransferData.findByContent", query = "SELECT t FROM TransferData t WHERE t.content = :content"),
    @NamedQuery(name = "TransferData.findByShiftcodefrom", query = "SELECT t FROM TransferData t WHERE t.shiftcodefrom = :shiftcodefrom"),
    @NamedQuery(name = "TransferData.findByShiftcodeto", query = "SELECT t FROM TransferData t WHERE t.shiftcodeto = :shiftcodeto"),
    @NamedQuery(name = "TransferData.findByPositionfrom", query = "SELECT t FROM TransferData t WHERE t.positionfrom = :positionfrom"),
    @NamedQuery(name = "TransferData.findByPositionto", query = "SELECT t FROM TransferData t WHERE t.positionto = :positionto"),
    @NamedQuery(name = "TransferData.findByMailfrom", query = "SELECT t FROM TransferData t WHERE t.mailfrom = :mailfrom"),
    @NamedQuery(name = "TransferData.findByMailto", query = "SELECT t FROM TransferData t WHERE t.mailto = :mailto"),
    @NamedQuery(name = "TransferData.findByIdTimeline", query = "SELECT t FROM TransferData t WHERE t.idTimeline = :idTimeline"),
    @NamedQuery(name = "TransferData.findByResponse", query = "SELECT t FROM TransferData t WHERE t.response = :response"),
    @NamedQuery(name = "TransferData.findByStatus", query = "SELECT t FROM TransferData t WHERE t.status = :status")})
public class TransferData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "Name")
    private String name;
    @Size(max = 200)
    @Column(name = "Content")
    private String content;
    @Column(name = "Shiftcodefrom")
    private Integer shiftcodefrom;
    @Column(name = "Shiftcodeto")
    private Integer shiftcodeto;
    @Column(name = "Positionfrom")
    private Integer positionfrom;
    @Column(name = "Positionto")
    private Integer positionto;
    @Size(max = 100)
    @Column(name = "Mailfrom")
    private String mailfrom;
    @Size(max = 100)
    @Column(name = "Mailto")
    private String mailto;

    @Column(name = "IdTimeline")
    private int idTimeline;
    @Size(max = 200)
    @Column(name = "Response")
    private String response;
    @Column(name = "Status")
    private Integer status;

    public TransferData() {
    }

    public TransferData(Integer id) {
        this.id = id;
    }

    public TransferData(Integer id, int idTimeline) {
        this.id = id;
        this.idTimeline = idTimeline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getShiftcodefrom() {
        return shiftcodefrom;
    }

    public void setShiftcodefrom(Integer shiftcodefrom) {
        this.shiftcodefrom = shiftcodefrom;
    }

    public Integer getShiftcodeto() {
        return shiftcodeto;
    }

    public void setShiftcodeto(Integer shiftcodeto) {
        this.shiftcodeto = shiftcodeto;
    }

    public Integer getPositionfrom() {
        return positionfrom;
    }

    public void setPositionfrom(Integer positionfrom) {
        this.positionfrom = positionfrom;
    }

    public Integer getPositionto() {
        return positionto;
    }

    public void setPositionto(Integer positionto) {
        this.positionto = positionto;
    }

    public String getMailfrom() {
        return mailfrom;
    }

    public void setMailfrom(String mailfrom) {
        this.mailfrom = mailfrom;
    }

    public String getMailto() {
        return mailto;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }

    public int getIdTimeline() {
        return idTimeline;
    }

    public void setIdTimeline(int idTimeline) {
        this.idTimeline = idTimeline;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
        if (!(object instanceof TransferData)) {
            return false;
        }
        TransferData other = (TransferData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.TransferData[ id=" + id + " ]";
    }
    
}
