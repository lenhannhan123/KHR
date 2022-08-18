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
@Table(name = "message_account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessageAccount.findAll", query = "SELECT m FROM MessageAccount m"),
    @NamedQuery(name = "MessageAccount.findByMesssageId", query = "SELECT m FROM MessageAccount m WHERE m.messsageId = :messsageId"),
    @NamedQuery(name = "MessageAccount.findByMesssageContent", query = "SELECT m FROM MessageAccount m WHERE m.messsageContent = :messsageContent"),
    @NamedQuery(name = "MessageAccount.findByMailSend", query = "SELECT m FROM MessageAccount m WHERE m.mailSend = :mailSend"),
    @NamedQuery(name = "MessageAccount.findByIdReceive", query = "SELECT m FROM MessageAccount m WHERE m.idReceive = :idReceive"),
    @NamedQuery(name = "MessageAccount.findByCreateDate", query = "SELECT m FROM MessageAccount m WHERE m.createDate = :createDate")})
public class MessageAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "messsage_id")
    private Integer messsageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "messsage_content")
    private String messsageContent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mail_send")
    private String mailSend;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "id_receive")
    private String idReceive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    public MessageAccount() {
    }

    public MessageAccount(Integer messsageId) {
        this.messsageId = messsageId;
    }

    public MessageAccount(Integer messsageId, String messsageContent, String mailSend, String idReceive, Date createDate) {
        this.messsageId = messsageId;
        this.messsageContent = messsageContent;
        this.mailSend = mailSend;
        this.idReceive = idReceive;
        this.createDate = createDate;
    }

    public Integer getMesssageId() {
        return messsageId;
    }

    public void setMesssageId(Integer messsageId) {
        this.messsageId = messsageId;
    }

    public String getMesssageContent() {
        return messsageContent;
    }

    public void setMesssageContent(String messsageContent) {
        this.messsageContent = messsageContent;
    }

    public String getMailSend() {
        return mailSend;
    }

    public void setMailSend(String mailSend) {
        this.mailSend = mailSend;
    }

    public String getIdReceive() {
        return idReceive;
    }

    public void setIdReceive(String idReceive) {
        this.idReceive = idReceive;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messsageId != null ? messsageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageAccount)) {
            return false;
        }
        MessageAccount other = (MessageAccount) object;
        if ((this.messsageId == null && other.messsageId != null) || (this.messsageId != null && !this.messsageId.equals(other.messsageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.KHR.Entities.MessageAccount[ messsageId=" + messsageId + " ]";
    }
    
}
