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
import javax.servlet.http.HttpSession;
import model.Appointment;
import model.AppointmentFacade;
import model.Vet;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Save_diagnosis_prognosis", urlPatterns = {"/Save_diagnosis_prognosis"})
public class Save_diagnosis_prognosis extends HttpServlet {

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
        String diagnosis = request.getParameter("diagnosis");
        String prognosis = request.getParameter("prognosis");
        HttpSession s = request.getSession(false);
        Vet vetUser = (Vet)s.getAttribute("user");
        List<Appointment> pendingAptsByVet = appointmentFacade.searchPendingAppointmentsByVet(vetUser.getUname());
        long aptID2beEdited = 0l;
        for (Appointment apt: pendingAptsByVet) {
            // check if the Save button of each pending appointment is clicked
            if (request.getParameter(Long.toString(apt.getId())) != null) {
                // keep track of the index of the record to be edited
                aptID2beEdited = apt.getId();
                break;
            }
        }
        Appointment aptWhoseDiagAndProgToBeUpdated = appointmentFacade.find(aptID2beEdited);
        try (PrintWriter out = response.getWriter()) {
            try {
                //do the database logic here
                aptWhoseDiagAndProgToBeUpdated.setDiagnosis(diagnosis);
                aptWhoseDiagAndProgToBeUpdated.setPrognosis(prognosis);
                aptWhoseDiagAndProgToBeUpdated.setCompleted(true);
                appointmentFacade.edit(aptWhoseDiagAndProgToBeUpdated);
                List<Appointment> updatedPendingAptsByVet = appointmentFacade.searchPendingAppointmentsByVet(vetUser.getUname());
                ListsOfRecords li = new ListsOfRecords();
                request.getRequestDispatcher("vet/manage_pending_appointments.jsp").include(request, response);
                out.println("Diagnosis and prognosis of the appointment have been saved successfully!");
                if (updatedPendingAptsByVet != null) {
                    out.println(li.listOfAppointmentsByVet(updatedPendingAptsByVet));
                } else {
                    out.println("<br>You have no pending appointments at the moment!");
                }
            } catch (Exception ex) {
                List<Appointment> updatedPendingAptsByVet = appointmentFacade.searchPendingAppointmentsByVet(vetUser.getUname());
                ListsOfRecords li = new ListsOfRecords();
                request.getRequestDispatcher("vet/manage_pending_appointments.jsp").include(request, response);
                out.println("Failed to save diagnosis and prognosis of the appointment, try again!");
                if (updatedPendingAptsByVet != null) {
                    out.println(li.listOfAppointmentsByVet(updatedPendingAptsByVet));
                } else {
                    out.println("<br>You have no pending appointments at the moment!");
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
