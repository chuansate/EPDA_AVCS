/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSP_utils.ListsOfRecords;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Appointment;
import model.AppointmentFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Retrieve_medical_records", urlPatterns = {"/Retrieve_medical_records"})
public class Retrieve_medical_records extends HttpServlet {

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
        String custUname = request.getParameter("cust_uname");
        String petName = request.getParameter("pet_name");
        String species = request.getParameter("species");
        List<Appointment> allApts = appointmentFacade.findAll();
        ArrayList<Appointment> medicalRecords = new ArrayList<Appointment>();
        try (PrintWriter out = response.getWriter()) {
            boolean medicalRecordsFound = false;
            for (Appointment apt: allApts) {
                if (apt.getCustomer_uname().equals(custUname) && apt.getPet_name().equals(petName) && apt.getSpecies().equals(species) && apt.isCompleted()) {
                    medicalRecords.add(apt);
                    medicalRecordsFound = true;
                }
            }
            
            if (medicalRecordsFound) {
                request.getRequestDispatcher("receptionist/check_medical_records.jsp").include(request, response);
                out.println("Returning some medical records...");
                out.println(new ListsOfRecords().listOfMedicalRecords(medicalRecords));
                out.println("<p>Print the medical records here: <input type='submit' value='Print'></p>");
            } else {
                request.getRequestDispatcher("receptionist/check_medical_records.jsp").include(request, response);
                out.println("There is no medical records for the customer and the vet... Try again!");
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
