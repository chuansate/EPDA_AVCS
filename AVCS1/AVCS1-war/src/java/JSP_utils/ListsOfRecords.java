/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSP_utils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import model.LeaveApplication;
import model.Receptionist;
import model.Staff;
import model.Vet;

/**
 *
 * @author lowsi
 */
public class ListsOfRecords {
    public String listOfStaffs(List<Staff>staffs, long userID) {
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Staff Username</th> <th>Password</th> <th>Gender</th> <th>Actions</th></tr>";
        for (Staff stf: staffs) {
            // The staffs are not allowed to delete their respective accounts
            // So only EDIT button is open to them, DELETE button is not open
            if (stf.getId() == userID) {
                str = str + "<tr>";
                str = str + "<td>" + stf.getUname() + "</td>";
                str = str + "<td>" + stf.getPwd() + "</td>";
                str = str + "<td>" + stf.getGender() + "</td>";
                str = str + "<td><form action=\"Edit_staff_info\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(stf.getId()) + "\"></form> ";
                str = str + "</tr>";
            } else {
                // The staffs are allowed to edit others' info, and even delete others' accounts
                str = str + "<tr>";
                str = str + "<td>" + stf.getUname() + "</td>";
                str = str + "<td>" + stf.getPwd() + "</td>";
                str = str + "<td>" + stf.getGender() + "</td>";
                str = str + "<td><form action=\"Edit_staff_info\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(stf.getId()) + "\"></form> ";
                str = str + "<form action=\"Delete_staff_info\" method=\"post\"><input type=\"submit\" value=\"Delete\" name=\"" + String.valueOf(stf.getId()) + "\"></form></td>";
                str = str + "</tr>";
            }
            
        }
        
        str = str + "</table>";
        return str;
    }
    
    public String listOfVets(List<Vet>vets) {
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Vet Username</th> <th>Password</th> <th>Base Salary</th> <th>Gender</th> <th>Email address</th> <th>Contact number</th> <th>Nationality</th> <th>Age</th> <th>Actions</th></tr>";
        for (Vet v: vets) {
            str = str + "<tr>";
            str = str + "<td>" + v.getUname() + "</td>";
            str = str + "<td>" + v.getPwd() + "</td>";
            str = str + "<td>" + v.getBase_salary() + "</td>";
            str = str + "<td>" + v.getGender() + "</td>";
            str = str + "<td>" + v.getEmail_adr() + "</td>";
            str = str + "<td>" + v.getContact_num() + "</td>";
            str = str + "<td>" + v.getNationality() + "</td>";
            str = str + "<td>" + v.getAge() + "</td>";
            str = str + "<td><form action=\"Edit_vet_info\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(v.getId()) + "\"></form> ";
            str = str + "<form action=\"Delete_vet_info\" method=\"post\"><input type=\"submit\" value=\"Delete\" name=\"" + String.valueOf(v.getId()) + "\"></form></td>";
            str = str + "</tr>";
        }
        
        str = str + "</table>";
        return str;
    }
    
    public String listOfVetsToBeEdited(List<Vet>vets, long vetIDToBeEdited) {
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Vet Username</th> <th>Password</th> <th>Base Salary</th> <th>Gender</th> <th>Email address</th> <th>Contact number</th> <th>Nationality</th> <th>Age</th> <th>Actions</th></tr>";
        for (Vet v: vets) {
            if (v.getId() == vetIDToBeEdited) {
                str = str + "<tr>";
                str = str + "<td>" + v.getUname() + "</td>";
                str = str + "<form action=\"Save_changes_vet_info\" method=\"post\">";
                str = str + "<td><= 8 characters:<input type=\"text\" name=\"pwd\" size=\"20\" value=\""+ v.getPwd() +"\"></td>";
                str = str + "<td> <input type=\"text\" name=\"base_salary\" size=\"20\" value=\""+ v.getBase_salary() +"\"> </td>";
                str = str + "<td>Either M or F: <input type=\"text\" name=\"gender\" size=\"20\" value=\""+ v.getGender() +"\"></td>";
                str = str + "<td><input type=\"text\" name=\"email_adr\" size=\"20\" value=\""+ v.getEmail_adr() +"\"></td>";
                str = str + "<td><input type=\"text\" name=\"contact_num\" size=\"20\" value=\""+ v.getContact_num() +"\"></td>";
                str = str + "<td><input type=\"text\" name=\"nationality\" size=\"20\" value=\""+ v.getNationality() +"\"></td>";
                str = str + "<td><input type=\"text\" name=\"age\" size=\"20\" value=\""+ v.getAge() +"\"></td>";
                str = str + "<td><input type=\"submit\" value=\"Save\">";
                str = str + "</form>";
                str = str + "</tr>";
            } else {
                str = str + "<tr>";
                str = str + "<td>" + v.getUname() + "</td>";
                str = str + "<td>" + v.getPwd() + "</td>";
                str = str + "<td>" + v.getBase_salary() + "</td>";
                str = str + "<td>" + v.getGender() + "</td>";
                str = str + "<td>" + v.getEmail_adr() + "</td>";
                str = str + "<td>" + v.getContact_num() + "</td>";
                str = str + "<td>" + v.getNationality() + "</td>";
                str = str + "<td>" + v.getAge() + "</td>";
                str = str + "<td><form action=\"Edit_vet_info\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(v.getId()) + "\"></form> ";
                str = str + "<form action=\"Delete_vet_info\" method=\"post\"><input type=\"submit\" value=\"Delete\" name=\"" + String.valueOf(v.getId()) + "\"></form></td>";
                str = str + "</tr>";
            }
            
        }
        
        str = str + "</table>";
        return str;
    }
    
    public String listOfReceptionists(List<Receptionist>receptionists) {
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Receptionist Username</th> <th>Password</th> <th>Gender</th> <th>Email address</th> <th>Contact number</th> <th>Nationality</th> <th>Age</th> <th>Actions</th></tr>";
        for (Receptionist r: receptionists) {
            str = str + "<tr>";
            str = str + "<td>" + r.getUname() + "</td>";
            str = str + "<td>" + r.getPwd() + "</td>";
            str = str + "<td>" + r.getGender() + "</td>";
            str = str + "<td>" + r.getEmail_adr() + "</td>";
            str = str + "<td>" + r.getContact_num() + "</td>";
            str = str + "<td>" + r.getNationality() + "</td>";
            str = str + "<td>" + r.getAge() + "</td>";
            str = str + "<td><form action=\"Edit_receptionist_info\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(r.getId()) + "\"></form> ";
            str = str + "<form action=\"Delete_receptionist_info\" method=\"post\"><input type=\"submit\" value=\"Delete\" name=\"" + String.valueOf(r.getId()) + "\"></form></td>";
            str = str + "</tr>";
        }
        
        str = str + "</table>";
        return str;
    }
    
    public String listOfReceptionistsToBeEdited(List<Receptionist>receptionists, long receptionistIDToBeEdited) {
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Vet Username</th> <th>Password</th> <th>Gender</th> <th>Email address</th> <th>Contact number</th> <th>Nationality</th> <th>Age</th> <th>Actions</th></tr>";
        for (Receptionist r: receptionists) {
            if (r.getId() == receptionistIDToBeEdited) {
                str = str + "<tr>";
                str = str + "<td>" + r.getUname() + "</td>";
                str = str + "<form action=\"Save_changes_receptionist_info\" method=\"post\">";
                str = str + "<td><= 8 characters:<input type=\"text\" name=\"pwd\" size=\"20\" value=\""+ r.getPwd() +"\"></td>";
                str = str + "<td>Either M or F: <input type=\"text\" name=\"gender\" size=\"20\" value=\""+ r.getGender() +"\"></td>";
                str = str + "<td><input type=\"text\" name=\"email_adr\" size=\"20\" value=\""+ r.getEmail_adr() +"\"></td>";
                str = str + "<td><input type=\"text\" name=\"contact_num\" size=\"20\" value=\""+ r.getContact_num() +"\"></td>";
                str = str + "<td><input type=\"text\" name=\"nationality\" size=\"20\" value=\""+ r.getNationality() +"\"></td>";
                str = str + "<td><input type=\"text\" name=\"age\" size=\"20\" value=\""+ r.getAge() +"\"></td>";
                str = str + "<td><input type=\"submit\" value=\"Save\">";
                str = str + "</form>";
                str = str + "</tr>";
            } else {
                str = str + "<tr>";
                str = str + "<td>" + r.getUname() + "</td>";
                str = str + "<td>" + r.getPwd() + "</td>";
                str = str + "<td>" + r.getGender() + "</td>";
                str = str + "<td>" + r.getEmail_adr() + "</td>";
                str = str + "<td>" + r.getContact_num() + "</td>";
                str = str + "<td>" + r.getNationality() + "</td>";
                str = str + "<td>" + r.getAge() + "</td>";
                str = str + "<td><form action=\"Edit_receptionist_info\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(r.getId()) + "\"></form> ";
                str = str + "<form action=\"Delete_receptionist_info\" method=\"post\"><input type=\"submit\" value=\"Delete\" name=\"" + String.valueOf(r.getId()) + "\"></form></td>";
                str = str + "</tr>";
            }
            
        }
        
        str = str + "</table>";
        return str;
    }
    
    public String listOfAppointments(List<Appointment>appointments) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Appointment Info</th> <th>Customer Profile</th> <th>Vet Profile</th> <th>Actions</th></tr>";
        for (Appointment apt: appointments) {
            str = str + "<tr>";
            str = str + "<td> <p>Scheduled Time:" + apt.getScheduled_time().format(formatter) + "</p> <p>Assigned Vet: "+ apt.getVet_uname() +"</p></td>";
            str = str + "<td><p>Customer username: " + apt.getCustomer_uname() + "</p>  <p>Gender: "+ apt.getGender() + "</p>  <p>Email address: "+ apt.getEmail_adr() + "</p>  <p>Contact Number: "+ apt.getContact_num() + "</p>  <p>Nationality: "+ apt.getNationality() + "</p>  <p>Age: "+ apt.getAge() + "</p></td>";
            str = str + "<td><p>Pet name: " + apt.getPet_name() + "</p>  <p>Species: "+ apt.getSpecies() + "</p></td>";
            str = str + "<td><form action=\"Edit_appointment\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(apt.getId()) + "\"></form> ";
            str = str + "<form action=\"Delete_appointment\" method=\"post\"><input type=\"submit\" value=\"Delete\" name=\"" + String.valueOf(apt.getId()) + "\"></form></td>";
            str = str + "</tr>";
        }
        
        str = str + "</table>";
        return str;
    }
    
    public String listOfAppointmentsToBeEdited(List<Appointment>appointments, long appointmentID2BeEdited) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Appointment Info</th> <th>Customer Profile</th> <th>Vet Profile</th> <th>Actions</th></tr>";
        for (Appointment apt: appointments) {
            if (apt.getId() == appointmentID2BeEdited) {
                str = str + "<tr>";
                str = str + "<form action=\"Save_edited_appointment\" method=\"post\">";
                str = str + "<td> <p>Scheduled Time : " + apt.getScheduled_time().format(formatter) + "</p> <p>Assigned Vet: <input type=\"text\" name=\"assigned_vet\" size=\"20\" value=\""+ apt.getVet_uname() +"\"></p></td>";
                str = str + "<td><p>Customer username: <input type=\"text\" name=\"cust_uname\" size=\"20\" value=\"" + apt.getCustomer_uname()+ "\"></p>  <p>Gender: <input type=\"text\" name=\"gender\" size=\"20\" value=\""+ apt.getGender() + "\"></p>  <p>Email address: <input type=\"text\" name=\"email_adr\" size=\"20\" value=\""+ apt.getEmail_adr() + "\"></p>  <p>Contact Number: <input type=\"text\" name=\"contact_num\" size=\"20\" value=\""+ apt.getContact_num() + "\"></p>  <p>Nationality: <input type=\"text\" name=\"nationality\" size=\"20\" value=\""+ apt.getNationality() + "\"></p>  <p>Age: <input type=\"text\" name=\"age\" size=\"20\" value=\""+ apt.getAge() + "\"></p></td>";
                str = str + "<td><p>Pet name: <input type=\"text\" name=\"pet_name\" size=\"20\" value=\"" + apt.getPet_name() + "\"></p>  <p>Species: " + new DropDownMenus().expertisesDropDown_JSP("species", "species") + " </p></td>";
                str = str + "<td><input type=\"submit\" value=\"Save\">";
                str = str + "</form>";
                str = str + "</tr>";
            } else {
                str = str + "<tr>";
                str = str + "<td> <p>Scheduled Time:" + apt.getScheduled_time().format(formatter) + "</p> <p>Assigned Vet: "+ apt.getVet_uname() +"</p></td>";
                str = str + "<td><p>Customer username: " + apt.getCustomer_uname()+ "</p>  <p>Gender: "+ apt.getGender() + "</p>  <p>Email address: "+ apt.getEmail_adr() + "</p>  <p>Contact Number: "+ apt.getContact_num() + "</p>  <p>Nationality: "+ apt.getNationality() + "</p>  <p>Age: "+ apt.getAge() + "</p></td>";
                str = str + "<td><p>Pet name: " + apt.getPet_name() + "</p>  <p>Species: "+ apt.getSpecies() + "</p></td>";
                str = str + "<td><form action=\"Edit_appointment\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(apt.getId()) + "\"></form> ";
                str = str + "<form action=\"Delete_appointment\" method=\"post\"><input type=\"submit\" value=\"Delete\" name=\"" + String.valueOf(apt.getId()) + "\"></form></td>";
                str = str + "</tr>";
            }
            
        }
        
        str = str + "</table>";
        return str;
    }
    
    // allowing a vet to enter the diagnosis and prognosis after completing an appointment
    public String listOfAppointmentsByVet(List<Appointment>appointments) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Appointment Info</th> <th>Customer Profile</th> <th>Vet Profile</th></tr>";
        for (Appointment apt: appointments) {
            str = str + "<tr>";
            str = str + "<form action=\"Save_diagnosis_prognosis\" method=\"post\">";
            str = str + "<td> <p>Scheduled Time:" + apt.getScheduled_time().format(formatter) + "</p> <p>Assigned Vet: "+ apt.getVet_uname() +"</p> <h3>Enter Diagnosis and Prognosis</h3> <p>Diagnosis: <input type=\"text\" name=\"diagnosis\" size=\"20\"></p> <p>Prognosis: <input type=\"text\" name=\"prognosis\" size=\"20\"></p> <input type=\"submit\" value=\"Save\" name=\"" + String.valueOf(apt.getId()) + "\"></td>";
            str = str + "<td><p>Customer username: " + apt.getCustomer_uname() + "</p>  <p>Gender: "+ apt.getGender() + "</p>  <p>Email address: "+ apt.getEmail_adr() + "</p>  <p>Contact Number: "+ apt.getContact_num() + "</p>  <p>Nationality: "+ apt.getNationality() + "</p>  <p>Age: "+ apt.getAge() + "</p></td>";
            str = str + "<td><p>Pet name: " + apt.getPet_name() + "</p>  <p>Species: "+ apt.getSpecies() + "</p></td>";
            str = str + "</form>";
            str = str + "</tr>";
        }
        
        str = str + "</table>";
        return str;
    }
    
    //to show the diagnosis and prognosis of completed appointments to a vet
    public String listOfCompletedAppointmentsByVet(List<Appointment>appointments) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Appointment Info</th> <th>Customer Profile</th> <th>Vet Profile</th></tr>";
        for (Appointment apt: appointments) {
            str = str + "<tr>";
            str = str + "<form action=\"Save_diagnosis_prognosis\" method=\"post\">";
            str = str + "<td> <p>Scheduled Time:" + apt.getScheduled_time().format(formatter) + "</p> <p>Assigned Vet: "+ apt.getVet_uname() +"</p> <p>Diagnosis: "+ apt.getDiagnosis() +"</p> <p>Prognosis: "+ apt.getPrognosis() +"</p> </td>";
            str = str + "<td><p>Customer username: " + apt.getCustomer_uname() + "</p>  <p>Gender: "+ apt.getGender() + "</p>  <p>Email address: "+ apt.getEmail_adr() + "</p>  <p>Contact Number: "+ apt.getContact_num() + "</p>  <p>Nationality: "+ apt.getNationality() + "</p>  <p>Age: "+ apt.getAge() + "</p></td>";
            str = str + "<td><p>Pet name: " + apt.getPet_name() + "</p>  <p>Species: "+ apt.getSpecies() + "</p></td>";
            str = str + "</form>";
            str = str + "</tr>";
        }
        
        str = str + "</table>";
        return str;
    }
    
    //to show the list of medical records by a pet, owned by a customer
    public String listOfMedicalRecords(ArrayList<Appointment>medicalRecordsBy_a_pet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Datetime</th> <th>Diagnosis</th> <th>Prognosis</th></tr>";
        for (Appointment apt: medicalRecordsBy_a_pet) {
            str = str + "<tr>";
            str = str + "<td> <p>" + apt.getScheduled_time().format(formatter) + "</p></td>";
            str = str + "<td> <p>"+ apt.getDiagnosis() +"</p></td>";
            str = str + "<td><p>"+ apt.getPrognosis() +"</p></td>";
            str = str + "</tr>";
        }
        
        str = str + "</table>";
        return str;
    }
    
    //the list of pending leave applications by all vets, staffs can either approve or reject
    public String listOfPendingLeaveApplicationsByAllVets(List<LeaveApplication>leaveApplicationsByAllVets) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Leave Application Date</th> <th>Vet Username</th> <th>Reason</th> <th>Action</th></tr>";
        for (LeaveApplication la: leaveApplicationsByAllVets) {
            str = str + "<tr>";
            str = str + "<td> <p>" + la.getLeaveDate().format(formatter) + "</p></td>";
            str = str + "<td> <p>"+ la.getUname() +"</p></td>";
            str = str + "<td><p>"+ la.getReason() +"</p></td>";
            str = str + "<td> <form action=\"Approve_leave_application_vet\" method=\"post\"><input type=\"submit\" value=\"Approve\" name=\"" + String.valueOf(la.getId()) + "\"></form> <form action=\"Reject_leave_application_vet\" method=\"post\"><input type=\"submit\" value=\"Reject\" name=\"" + String.valueOf(la.getId()) + "\"></form> </td>";
            str = str + "</tr>";
        }
        
        str = str + "</table>";
        return str;
    }
    
    
    public String setPricesRatingListOfCompletedAppointments(List<Appointment>appointments) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = "<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Appointment Info</th> <th>Customer Profile</th> <th>Vet Profile</th></tr>";
        for (Appointment apt: appointments) {
            str = str + "<tr>";
            str = str + "<form action=\"Save_payment_info\" method=\"post\">";
            str = str + "<td> <p>Scheduled Time:" + apt.getScheduled_time().format(formatter) + "</p> <p>Assigned Vet: "+ apt.getVet_uname() +"</p> <h3>Enter Payment Information</h3> <p>Consultation Price: <input type=\"text\" name=\"consultation_price\" size=\"20\"></p> " ;
            str = str + "<h3>Customer Rating for the Vet</h3> <p>From 1 to 5: <input type=\"text\" name=\"customer_rating\" size=\"20\"></p>";
            str = str + "<input type=\"submit\" value=\"Save\" name=\"" + String.valueOf(apt.getId()) + "\"></td>";
            str = str + "<td><p>Customer username: " + apt.getCustomer_uname() + "</p>  <p>Gender: "+ apt.getGender() + "</p>  <p>Email address: "+ apt.getEmail_adr() + "</p>  <p>Contact Number: "+ apt.getContact_num() + "</p>  <p>Nationality: "+ apt.getNationality() + "</p>  <p>Age: "+ apt.getAge() + "</p></td>";
            str = str + "<td><p>Pet name: " + apt.getPet_name() + "</p>  <p>Species: "+ apt.getSpecies() + "</p></td>";
            str = str + "</form>";
            str = str + "</tr>";
        }
        
        str = str + "</table>";
        return str;
    }
    
    
}
