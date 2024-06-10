/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSP_utils.ListsOfRecords;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "Delete_appointment", urlPatterns = {"/Delete_appointment"})
public class Delete_appointment extends HttpServlet {

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
        List<Appointment> appointments = appointmentFacade.findAll();
        Appointment apt2BeDeleted = null;
        for (Appointment apt: appointments) {
            // check if the EDIT button of each record is clicked
            if (request.getParameter(Long.toString(apt.getId())) != null) {
                // keep track of the index of the record to be deleted
                apt2BeDeleted = apt;
                break;
            }
        }
        
        List<Vet> vets = vetFacade.findAll();
        Vet vetWhoseApt2BeDeleted = null;
        for (Vet v: vets) {
            if (v.getUname().equals(apt2BeDeleted.getVet_uname())) {
                vetWhoseApt2BeDeleted = v;
                break;
            }
        }
        
        
        
        request.getRequestDispatcher("receptionist/manage_appointments.jsp").include(request, response);
        try (PrintWriter out = response.getWriter()) {
            if (apt2BeDeleted != null && vetWhoseApt2BeDeleted != null) {
                //delete the appointment here from the database!
                
                //update the table VET_APPOINTMENT
                vetWhoseApt2BeDeleted.getAppointments().remove(apt2BeDeleted);
                vetFacade.edit(vetWhoseApt2BeDeleted);
                
                //update the table APPOINTMENT
                appointmentFacade.remove(apt2BeDeleted);
                
                out.println("The appointment has been deleted successfully!");
                List<Appointment> updatedAppointments = appointmentFacade.searchPendingAppointments();
                ListsOfRecords li = new ListsOfRecords();
                if (updatedAppointments != null) {
                    out.println("<br>" + li.listOfAppointments(updatedAppointments));
                } else {
                    out.println("<p>There is no pending appointments for you to modify right now!</p>");
                }
            } else {
                // this could happen when staffs delete the vets' accounts
                // the system only remove the vet from the table Vet, but forgot to update the table Appointment (maybe we should replace it with Unknown?)
                out.println("The appointment can't be deleted! Appointment or the vet can't be found inside the database!");
                List<Appointment> updatedAppointments = appointmentFacade.searchPendingAppointments();
                ListsOfRecords li = new ListsOfRecords();
                if (updatedAppointments != null) {
                    out.println("<br>" + li.listOfAppointments(updatedAppointments));
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
