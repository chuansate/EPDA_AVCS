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
    @NamedQuery(name="Appointment.searchPendingAppointmentsByVet", query="SELECT a FROM Appointment a WHERE a.vet_uname = :vet_uname AND a.completed = 0"),
    @NamedQuery(name="Appointment.searchCompletedAppointmentsByVet", query="SELECT a FROM Appointment a WHERE a.vet_uname = :vet_uname AND a.completed = 1"),
    @NamedQuery(name="Appointment.searchCompletedAppointments", query="SELECT a FROM Appointment a WHERE a.completed = 1 AND a.paid = 0"),
    @NamedQuery(name="Appointment.searchPendingAppointments", query="SELECT a FROM Appointment a WHERE a.completed = 0"),
})
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String vet_uname;
    private String customer_uname;
    private LocalDateTime scheduled_time;
    private String diagnosis;
    private String prognosis;
    private boolean completed;
    private int price;
    private boolean paid;
    private char gender;
    private String email_adr;
    private String contact_num;
    private String nationality;
    private int age;
    private String pet_name;
    private String species;
    private int customer_rating;

    public Appointment() {
    }

    public Appointment(String vet_uname, String customer_uname, LocalDateTime scheduled_time, String diagnosis, String prognosis, boolean completed, int price, boolean paid, char gender, String email_adr, String contact_num, String nationality, int age, String pet_name, String species, int customer_rating) {
        this.vet_uname = vet_uname;
        this.customer_uname = customer_uname;
        this.scheduled_time = scheduled_time;
        this.diagnosis = diagnosis;
        this.prognosis = prognosis;
        this.completed = completed;
        this.price = price;
        this.paid = paid;
        this.gender = gender;
        this.email_adr = email_adr;
        this.contact_num = contact_num;
        this.nationality = nationality;
        this.age = age;
        this.pet_name = pet_name;
        this.species = species;
        this.customer_rating = customer_rating;
    }

    public int getCustomer_rating() {
        return customer_rating;
    }

    public void setCustomer_rating(int customer_rating) {
        this.customer_rating = customer_rating;
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

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    

    public String getVet_uname() {
        return vet_uname;
    }

    public void setVet_uname(String vet_uname) {
        this.vet_uname = vet_uname;
    }

    public String getCustomer_uname() {
        return customer_uname;
    }

    public void setCustomer_uname(String customer_uname) {
        this.customer_uname = customer_uname;
    }

    public LocalDateTime getScheduled_time() {
        return scheduled_time;
    }

    public void setScheduled_time(LocalDateTime scheduled_time) {
        this.scheduled_time = scheduled_time;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrognosis() {
        return prognosis;
    }

    public void setPrognosis(String prognosis) {
        this.prognosis = prognosis;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
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
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Appointment[ id=" + id + " ]";
    }
    
}
