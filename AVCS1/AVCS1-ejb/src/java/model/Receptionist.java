/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
    @NamedQuery(name="Receptionist.searchNotApproved", query="SELECT r FROM Receptionist r WHERE r.approved=0"),
    @NamedQuery(name="Receptionist.approveRegistration", query="UPDATE Receptionist r SET r.approved = :approved WHERE r.id = :id"),
    @NamedQuery(name="Receptionist.searchByUname", query="SELECT r FROM Receptionist r WHERE r.uname = :uname"),
})
public class Receptionist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uname;
    private String pwd;
    private char gender;
    private String email_adr;
    private String contact_num;
    private String nationality;
    private int age;
    private boolean approved;

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Receptionist() {
    }

    public Receptionist(String uname, String pwd, char gender, String email_adr, String contact_num, String nationality, int age, boolean approved) {
        this.uname = uname;
        this.pwd = pwd;
        this.gender = gender;
        this.email_adr = email_adr;
        this.contact_num = contact_num;
        this.nationality = nationality;
        this.age = age;
        this.approved = approved;
    }
      
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getEmail_adr() {
        return email_adr;
    }

    public void setEmail_adr(String email_adr) {
        this.email_adr = email_adr;
    }

    public String getContact_num() {
        return contact_num;
    }

    public void setContact_num(String contact_num) {
        this.contact_num = contact_num;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
        if (!(object instanceof Receptionist)) {
            return false;
        }
        Receptionist other = (Receptionist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Receptionist[ id=" + id + " ]";
    }
    
}
