/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author lowsi
 */
@Entity
@NamedQueries({
    @NamedQuery(name="LeaveApplication.searchPendingLeaveApplications", query="SELECT a FROM LeaveApplication a WHERE a.status = \"Pending\""),
    @NamedQuery(name="LeaveApplication.searchLeaveApplicationsByVet", query="SELECT a FROM LeaveApplication a WHERE a.uname = :uname and a.userType = \"vet\""),
})
public class LeaveApplication implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime leaveDate;
    private String uname;
    private String userType;
    private String reason;
    private String status;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LeaveApplication() {
    }

    public LeaveApplication(LocalDateTime leaveDate, String uname, String userType, String status, String reason) {
        this.leaveDate = leaveDate;
        this.reason = reason;
        this.uname = uname;
        this.userType = userType;
        this.status = status;
    }

    public LocalDateTime getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDateTime leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof LeaveApplication)) {
            return false;
        }
        LeaveApplication other = (LeaveApplication) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.LeaveApplication[ id=" + id + " ]";
    }
    
}
