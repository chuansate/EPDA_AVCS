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

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Save_payment_info", urlPatterns = {"/Save_payment_info"})
public class Save_payment_info extends HttpServlet {

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
        String consultation_priceStr = request.getParameter("consultation_price");
        String customer_ratingStr = request.getParameter("customer_rating");
        int consultation_price;
        int customer_rating;
        List<Appointment> completedApts = appointmentFacade.searchCompletedAppointments();
        long aptID2beEdited = 0l;
        for (Appointment apt: completedApts) {
            // check if the Save button of each completed appointment is clicked
            if (request.getParameter(Long.toString(apt.getId())) != null) {
                aptID2beEdited = apt.getId();
                break;
            }
        }
        Appointment apt2beEdited = appointmentFacade.find(aptID2beEdited);
        try (PrintWriter out = response.getWriter()) {
            try {
                consultation_price = Integer.valueOf(consultation_priceStr);
                customer_rating = Integer.valueOf(customer_ratingStr);
                if (consultation_price <= 0) {
                    throw new Exception();
                }
                
                if (customer_rating < 1 || customer_rating > 5) {
                    throw new Exception();
                }
                
                request.getRequestDispatcher("receptionist/set_prices_completed_appointments.jsp").include(request, response);
                ListsOfRecords li = new ListsOfRecords();
                
                apt2beEdited.setPaid(true);
                apt2beEdited.setPrice(consultation_price);
                apt2beEdited.setCustomer_rating(customer_rating);
                appointmentFacade.edit(apt2beEdited);
                out.println("The appointment has been paid successfully!");
                List<Appointment> updatedCompletedApts = appointmentFacade.searchCompletedAppointments();
                if (updatedCompletedApts != null) {
                    out.println(li.setPricesRatingListOfCompletedAppointments(updatedCompletedApts));
                } else {
                    out.println("There is no appointment completed by vets so far!");
                }
            } catch (Exception ex) {
                request.getRequestDispatcher("receptionist/set_prices_completed_appointments.jsp").include(request, response);
                ListsOfRecords li = new ListsOfRecords();
                if (completedApts != null) {
                    out.println("Invalid inputs, try again!");
                    out.println(li.setPricesRatingListOfCompletedAppointments(completedApts));
                } else {
                    out.println("There is no appointment completed by vets so far!");
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
