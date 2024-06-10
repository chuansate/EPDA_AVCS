/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import JSP_utils.ListsOfRecords;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpSession;
import model.Appointment;
import model.AppointmentFacade;
import model.Vet;
import model.VetFacade;
/**
 *
 * @author lowsi
 */
@WebServlet(name = "Save_edited_appointment", urlPatterns = {"/Save_edited_appointment"})
public class Save_edited_appointment extends HttpServlet {

    @EJB
    private VetFacade vetFacade;

    @EJB
    private AppointmentFacade appointmentFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String assigned_vet = request.getParameter("assigned_vet");
        String cust_uname = request.getParameter("cust_uname");
        String gender = request.getParameter("gender");
        String email_adr = request.getParameter("email_adr");
        String contact_num = request.getParameter("contact_num");
        String nationality = request.getParameter("nationality");
        String ageStr = request.getParameter("age");
        String pet_name = request.getParameter("pet_name");
        String species = request.getParameter("species");
        int age;
        
        HttpSession s = request.getSession(false);
        long aptID2beEdited = (long)s.getAttribute("appointmentID2BeEdited");
        
        Appointment apt2BeEdited = appointmentFacade.find(aptID2beEdited);
        
        try (PrintWriter out = response.getWriter()) { 
            try {
                age = Integer.parseInt(ageStr);
                // check if the assigned vet exists
                Vet newVet = vetFacade.searchByUname(assigned_vet).get(0);
                
                if (newVet == null) {
                    throw new Exception();
                }
                
                
                //check for the length of the customer username
                if (cust_uname.length() == 0 || cust_uname.length() > 8) {
                    throw new Exception();
                }
                
                //check for the length of the pet name
                if (pet_name.length() == 0 || pet_name.length() > 8) {
                    throw new Exception();
                }
                
                // check if the entered gender match any of the provieded gender options
                if (gender.length() > 1 || gender.length() == 0) {
                    throw new Exception();
                }
                char[] gender_options = {'M', 'F'};
                boolean gender_isValid = false;
                for (char gender_option: gender_options) {
                    if (gender.charAt(0) == gender_option) {
                        gender_isValid = true;
                        break;
                    }
                }
                
                if (!gender_isValid) {
                    throw new Exception();
                }
                
                if (age <= 0) {
                    throw new Exception();
                }
                
                request.getRequestDispatcher("receptionist/manage_appointments.jsp").include(request, response);
                
                try {
                    //update the database!     
                    // if the assigned vet does not change, then we need to update the table Appointment only
                    if (newVet.getUname().equals(apt2BeEdited.getVet_uname())) {                     
                        apt2BeEdited.setCustomer_uname(cust_uname);
                        apt2BeEdited.setGender(gender.charAt(0));
                        apt2BeEdited.setEmail_adr(email_adr);
                        apt2BeEdited.setContact_num(contact_num);
                        apt2BeEdited.setNationality(nationality);
                        apt2BeEdited.setAge(age);
                        apt2BeEdited.setPet_name(pet_name);
                        apt2BeEdited.setSpecies(species);
                        appointmentFacade.edit(apt2BeEdited);
                    } else {
                    // if the assigned vet changes, then we need to update both the tables Appointment and Vet_Appointment
                        Appointment new_apt = new Appointment(assigned_vet, cust_uname, apt2BeEdited.getScheduled_time(), apt2BeEdited.getDiagnosis(), apt2BeEdited.getPrognosis(), apt2BeEdited.isCompleted(), apt2BeEdited.getPrice(), apt2BeEdited.isPaid(), gender.charAt(0), email_adr, contact_num, nationality, age, pet_name, species, apt2BeEdited.getCustomer_rating());
                        //update the table APPOINTMENT
                        appointmentFacade.create(new_apt);

                        //Update the table VET_APPOINTMENT
                        List<Vet> oldVets = vetFacade.searchByUname(apt2BeEdited.getVet_uname());
                        if (oldVets != null) {
                            Vet oldVet = oldVets.get(0);
                            oldVet.getAppointments().remove(apt2BeEdited);
                            newVet.getAppointments().add(new_apt);
                            vetFacade.edit(oldVet);
                            vetFacade.edit(newVet);
                        } else {
                            // this could happen when a vet is deleted and his appointment is left assigned to "original_vet_uname (Deleted)"
                            // then if u update the assigned vet to a new one, oldVets will be null since you have ady deleted it from the table Vet
                            newVet.getAppointments().add(new_apt);
                            vetFacade.edit(newVet);
                        }
                        
                        //update the table APPOINTMENT
                        appointmentFacade.remove(apt2BeEdited);
                    }
                    
                    
                    out.println("The changes on the appointment have been saved successfully!");
                    List<Appointment> updatedAppointments = appointmentFacade.searchPendingAppointments();
                    ListsOfRecords li = new ListsOfRecords();
                    if (updatedAppointments != null) {
                        out.println("<br>" + li.listOfAppointments(updatedAppointments));
                    } else {
                        out.println("<p>There is no pending appointments for you to modify right now!</p>");
                    }
                } catch (Exception ex) {
                    out.println("Failed to save the changes on the appointment, try again!");
                    System.out.println("Failed to save the changes on the appointment, try again!");
                    ex.printStackTrace();
                    List<Appointment> updatedAppointments = appointmentFacade.searchPendingAppointments();
                    ListsOfRecords li = new ListsOfRecords();
                    if (updatedAppointments != null) {
                        out.println("<br>" + li.listOfAppointments(updatedAppointments));
                    } else {
                        out.println("<p>There is no pending appointments for you to modify right now!</p>");
                    }
                }     
            } catch (Exception e) {
                request.getRequestDispatcher("receptionist/manage_appointments.jsp").include(request, response);
                // Load some existing appointments from the database
                List<Appointment> appointments = appointmentFacade.searchPendingAppointments();
                out.println("<br>Invalid inputs while editing the appointment, try again!");
                ListsOfRecords li = new ListsOfRecords();
                if (appointments != null) {
                    out.println("<br>" + li.listOfAppointments(appointments));
                } else {
                    out.println("<p>There is no pending appointments for you to modify right now!</p>");
                }
            }
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
