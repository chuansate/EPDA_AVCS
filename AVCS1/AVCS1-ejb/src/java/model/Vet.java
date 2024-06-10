/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author lowsi
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Vet.searchNotApproved", query="SELECT v FROM Vet v WHERE v.approved=0"),
    @NamedQuery(name="Vet.searchByUname", query="SELECT v FROM Vet v WHERE v.uname = :uname"),
    @NamedQuery(name="Vet.approveRegistration", query="UPDATE Vet v SET v.approved = :approved WHERE v.id = :id"),
    @NamedQuery(name="Vet.editProfile", query="UPDATE Vet v SET v.pwd = :pwd, v.email_adr = :email_adr, v.contact_num = :contact_num, v.nationality = :nationality, v.age = :age, v.gender = :gender WHERE v.uname = :uname"),
})
public class Vet implements Serializable {

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
    private int base_salary;
    @OneToMany
    private ArrayList<Expertise> expertises = new ArrayList<Expertise>();
    
    @OneToMany
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();

    @OneToMany
    private ArrayList<LeaveApplication> leaveApplications = new ArrayList<LeaveApplication>();
    public int getBase_salary() {
        return base_salary;
    }

    public ArrayList<LeaveApplication> getLeaveApplications() {
        return leaveApplications;
    }

    public void setLeaveApplications(ArrayList<LeaveApplication> leaveApplications) {
        this.leaveApplications = leaveApplications;
    }

    public void setBase_salary(int base_salary) {
        this.base_salary = base_salary;
    }

    public ArrayList<Expertise> getExpertises() {
        return expertises;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void setExpertises(ArrayList<Expertise> expertises) {
        this.expertises = expertises;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Vet(String uname, String pwd, char gender, String email_adr, String contact_num, String nationality, int age, boolean approved, int base_salary) {
        this.uname = uname;
        this.pwd = pwd;
        this.gender = gender;
        this.email_adr = email_adr;
        this.contact_num = contact_num;
        this.nationality = nationality;
        this.age = age;
        this.approved = approved;
        this.base_salary = base_salary;
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

    public Vet() {
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
        if (!(object instanceof Vet)) {
            return false;
        }
        Vet other = (Vet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Vet[ id=" + id + " ]";
    }
    
}
