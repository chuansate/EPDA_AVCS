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
import java.time.LocalDateTime;
/**
 *
 * @author lowsi
 */
@Entity
public class WorkingRota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime startingDate;
    private String mondayVet1;
    private String mondayVet2;
    private String mondayVet3;
    private String tuesdayVet1;
    private String tuesdayVet2;
    private String tuesdayVet3;
    private String wednesdayVet1;
    private String wednesdayVet2;
    private String wednesdayVet3;
    private String thursdayVet1;
    private String thursdayVet2;
    private String thursdayVet3;
    private String fridayVet1;
    private String fridayVet2;
    private String fridayVet3;
    private String saturdayVet1;
    private String saturdayVet2;
    private String saturdayVet3;
    private String sundayVet1;
    private String sundayVet2;
    private String sundayVet3;

    public WorkingRota() {
    }

    public WorkingRota(LocalDateTime startingDate, String mondayVet1, String mondayVet2, String mondayVet3, String tuesdayVet1, String tuesdayVet2, String tuesdayVet3, String wednesdayVet1, String wednesdayVet2, String wednesdayVet3, String thursdayVet1, String thursdayVet2, String thursdayVet3, String fridayVet1, String fridayVet2, String fridayVet3, String saturdayVet1, String saturdayVet2, String saturdayVet3, String sundayVet1, String sundayVet2, String sundayVet3) {
        this.startingDate = startingDate;
        this.mondayVet1 = mondayVet1;
        this.mondayVet2 = mondayVet2;
        this.mondayVet3 = mondayVet3;
        this.tuesdayVet1 = tuesdayVet1;
        this.tuesdayVet2 = tuesdayVet2;
        this.tuesdayVet3 = tuesdayVet3;
        this.wednesdayVet1 = wednesdayVet1;
        this.wednesdayVet2 = wednesdayVet2;
        this.wednesdayVet3 = wednesdayVet3;
        this.thursdayVet1 = thursdayVet1;
        this.thursdayVet2 = thursdayVet2;
        this.thursdayVet3 = thursdayVet3;
        this.fridayVet1 = fridayVet1;
        this.fridayVet2 = fridayVet2;
        this.fridayVet3 = fridayVet3;
        this.saturdayVet1 = saturdayVet1;
        this.saturdayVet2 = saturdayVet2;
        this.saturdayVet3 = saturdayVet3;
        this.sundayVet1 = sundayVet1;
        this.sundayVet2 = sundayVet2;
        this.sundayVet3 = sundayVet3;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public String getMondayVet1() {
        return mondayVet1;
    }

    public void setMondayVet1(String mondayVet1) {
        this.mondayVet1 = mondayVet1;
    }

    public String getMondayVet2() {
        return mondayVet2;
    }

    public void setMondayVet2(String mondayVet2) {
        this.mondayVet2 = mondayVet2;
    }

    public String getMondayVet3() {
        return mondayVet3;
    }

    public void setMondayVet3(String mondayVet3) {
        this.mondayVet3 = mondayVet3;
    }

    public String getTuesdayVet1() {
        return tuesdayVet1;
    }

    public void setTuesdayVet1(String tuesdayVet1) {
        this.tuesdayVet1 = tuesdayVet1;
    }

    public String getTuesdayVet2() {
        return tuesdayVet2;
    }

    public void setTuesdayVet2(String tuesdayVet2) {
        this.tuesdayVet2 = tuesdayVet2;
    }

    public String getTuesdayVet3() {
        return tuesdayVet3;
    }

    public void setTuesdayVet3(String tuesdayVet3) {
        this.tuesdayVet3 = tuesdayVet3;
    }

    public String getWednesdayVet1() {
        return wednesdayVet1;
    }

    public void setWednesdayVet1(String wednesdayVet1) {
        this.wednesdayVet1 = wednesdayVet1;
    }

    public String getWednesdayVet2() {
        return wednesdayVet2;
    }

    public void setWednesdayVet2(String wednesdayVet2) {
        this.wednesdayVet2 = wednesdayVet2;
    }

    public String getWednesdayVet3() {
        return wednesdayVet3;
    }

    public void setWednesdayVet3(String wednesdayVet3) {
        this.wednesdayVet3 = wednesdayVet3;
    }

    public String getThursdayVet1() {
        return thursdayVet1;
    }

    public void setThursdayVet1(String thursdayVet1) {
        this.thursdayVet1 = thursdayVet1;
    }

    public String getThursdayVet2() {
        return thursdayVet2;
    }

    public void setThursdayVet2(String thursdayVet2) {
        this.thursdayVet2 = thursdayVet2;
    }

    public String getThursdayVet3() {
        return thursdayVet3;
    }

    public void setThursdayVet3(String thursdayVet3) {
        this.thursdayVet3 = thursdayVet3;
    }

    public String getFridayVet1() {
        return fridayVet1;
    }

    public void setFridayVet1(String fridayVet1) {
        this.fridayVet1 = fridayVet1;
    }

    public String getFridayVet2() {
        return fridayVet2;
    }

    public void setFridayVet2(String fridayVet2) {
        this.fridayVet2 = fridayVet2;
    }

    public String getFridayVet3() {
        return fridayVet3;
    }

    public void setFridayVet3(String fridayVet3) {
        this.fridayVet3 = fridayVet3;
    }

    public String getSaturdayVet1() {
        return saturdayVet1;
    }

    public void setSaturdayVet1(String saturdayVet1) {
        this.saturdayVet1 = saturdayVet1;
    }

    public String getSaturdayVet2() {
        return saturdayVet2;
    }

    public void setSaturdayVet2(String saturdayVet2) {
        this.saturdayVet2 = saturdayVet2;
    }

    public String getSaturdayVet3() {
        return saturdayVet3;
    }

    public void setSaturdayVet3(String saturdayVet3) {
        this.saturdayVet3 = saturdayVet3;
    }

    public String getSundayVet1() {
        return sundayVet1;
    }

    public void setSundayVet1(String sundayVet1) {
        this.sundayVet1 = sundayVet1;
    }

    public String getSundayVet2() {
        return sundayVet2;
    }

    public void setSundayVet2(String sundayVet2) {
        this.sundayVet2 = sundayVet2;
    }

    public String getSundayVet3() {
        return sundayVet3;
    }

    public void setSundayVet3(String sundayVet3) {
        this.sundayVet3 = sundayVet3;
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
        if (!(object instanceof WorkingRota)) {
            return false;
        }
        WorkingRota other = (WorkingRota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.WorkingRota[ startingDate=" + this.startingDate + ",  ]";
    }
    
}
