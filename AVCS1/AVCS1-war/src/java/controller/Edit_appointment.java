/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AppointmentFacade;
import model.Appointment;
import JSP_utils.ListsOfRecords;
/**
 *
 * @author lowsi
 */
@WebServlet(name = "Edit_appointment", urlPatterns = {"/Edit_appointment"})
public class Edit_appointment extends HttpServlet {

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
        HttpSession s = request.getSession(false);
        long aptID2beEdited = 0l;
        List<Appointment> appointments = appointmentFacade.findAll();
        for (Appointment apt: appointments) {
            // check if the EDIT button of each record is clicked
            if (request.getParameter(Long.toString(apt.getId())) != null) {
                // keep track of the index of the record to be edited
                aptID2beEdited = apt.getId();
                s.setAttribute("appointmentID2BeEdited", aptID2beEdited);
                break;
            }
        }
        
        request.getRequestDispatcher("receptionist/manage_appointments.jsp").include(request, response);
        try (PrintWriter out = response.getWriter()) {
            ListsOfRecords li = new ListsOfRecords();
            List<Appointment> pendingAppointments = appointmentFacade.searchPendingAppointments();
            if (pendingAppointments != null) {
                out.println(li.listOfAppointmentsToBeEdited(pendingAppointments, aptID2beEdited));
            } else {
                out.println("<p>There is no pending appointments for you to modify right now!</p>");
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
