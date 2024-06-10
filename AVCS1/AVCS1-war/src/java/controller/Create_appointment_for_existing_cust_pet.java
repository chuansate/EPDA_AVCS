/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSP_utils.ListsOfRecords;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Appointment;
import model.AppointmentFacade;
import model.Vet;
import model.VetFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Create_appointment_for_existing_cust_pet", urlPatterns = {"/Create_appointment_for_existing_cust_pet"})
public class Create_appointment_for_existing_cust_pet extends HttpServlet {

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
        String yearStr = request.getParameter("year");
        String monthStr = request.getParameter("month");
        String dayStr = request.getParameter("day");
        String hourStr = request.getParameter("hour");
        String minuteStr = request.getParameter("minute");
        String assigned_vet = request.getParameter("assigned_vet");
        String existingCustUname = request.getParameter("existing_cust_name");
        String existingPetName = request.getParameter("existing_pet_name");
        String existingSpecies = request.getParameter("existing_species");
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int age;
        Vet vetFound = null;
        LocalDateTime appointmentDateTime;
        Appointment aptFound = null;
        try (PrintWriter out = response.getWriter()) {
            try {
                // check if the customer username, pet name, and species exists
                List<Appointment> allApts = appointmentFacade.findAll();
                for (Appointment apt: allApts) {
                    if (apt.getCustomer_uname().equals(existingCustUname) && apt.getPet_name().equals(existingPetName) && apt.getSpecies().equals(existingSpecies)){
                        aptFound = apt;
                        break;
                    }
                }
                
                if (aptFound == null) {
                    throw new Exception();
                } 
                
                
                // check for the format of the appointment date time
                year = Integer.parseInt(yearStr);
                month = Integer.parseInt(monthStr);
                day = Integer.parseInt(dayStr);
                hour = Integer.parseInt(hourStr);
                minute = Integer.parseInt(minuteStr);
                appointmentDateTime = LocalDateTime.of(year, month, day, hour, minute, 0);
 
                // check if the assigned vet exists
                List<Vet> allVets = vetFacade.findAll();
                
                for (Vet v: allVets) {
                    if (v.getUname().equals(assigned_vet)) {
                        vetFound = v;
                        break;
                    }
                }
                if (vetFound == null) {
                    throw new Exception();
                }
                
                //update the database
                Appointment new_appointment = new Appointment(assigned_vet, existingCustUname, appointmentDateTime, "", "", false, 0, false, aptFound.getGender(), aptFound.getEmail_adr(), aptFound.getContact_num(), aptFound.getNationality(), aptFound.getAge(), existingPetName, existingSpecies, -1);                
                appointmentFacade.create(new_appointment);
                // rmb to manipulate the VET_APPOINTMENT, table for the one2many relationship betw vet and appointment
                vetFound.getAppointments().add(new_appointment);
                vetFacade.edit(vetFound);
                request.getRequestDispatcher("receptionist/manage_appointments.jsp").include(request, response);
                out.println("<br>The appointment has been successfully added!");
                List<Appointment> pendingAppointments = appointmentFacade.searchPendingAppointments();
                ListsOfRecords li = new ListsOfRecords();
                out.println("<br>" + li.listOfAppointments(pendingAppointments));
                
            } catch (Exception ex){
                request.getRequestDispatcher("receptionist/manage_appointments.jsp").include(request, response);
                if (aptFound == null) {
                    out.println("<br>There is no matching record for the customer and pet, try again!");
                }else if (vetFound == null){ 
                    out.println("<br>There is no matching vet to be assigned, try again!");
                }else {
                    out.println("<br>Invalid inputs while creating the appointments, try again!");
                }
                List<Appointment> pendingAppointments = appointmentFacade.searchPendingAppointments();
                ListsOfRecords li = new ListsOfRecords();
                out.println("<br>" + li.listOfAppointments(pendingAppointments));
                
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
