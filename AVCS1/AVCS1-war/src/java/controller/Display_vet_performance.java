/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSP_utils.PaySlipForVets;
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
import model.VetFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Display_vet_performance", urlPatterns = {"/Display_vet_performance"})
public class Display_vet_performance extends HttpServlet {

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
        String vetUname = request.getParameter("vet_uname");
        HttpSession s = request.getSession(false);
        
        String monthPaySlip = request.getParameter("month_pay_slip");
        boolean vetNotExist = false;
        boolean monthInvalid = true;
        try (PrintWriter out = response.getWriter()) {
            try {
                Vet vetFound = null;
                List<Vet> allVets = vetFacade.findAll();
                for (Vet v: allVets) {
                    if (v.getUname().equals(vetUname)) {
                        vetFound = v;
                        s.setAttribute("vetWhosePaySlipIsGenerated", vetFound);
                        
                        break;
                    }
                }
                
                if (vetFound == null) {
                    vetNotExist = true;
                    throw new Exception();
                }
                
                
                String[] monthOptions = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MARCH", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
                
                for (String m: monthOptions) {
                    if (m.equals(monthPaySlip)) {
                        monthInvalid = false;
                        break;
                    }
                }
                if (monthInvalid) {
                    throw new Exception();
                }
                s.setAttribute("month_pay_slip", monthPaySlip);
                
                
                request.getRequestDispatcher("managing_staff/generate_pay_slip.jsp").include(request, response);
                out.println("Displaying performance of the vet, "+ vetUname +": <br>");
                int sumCustomerRating = 0;
                int numCompletedAptByVet = 0;
                List<Appointment> allApts = appointmentFacade.findAll();
                for (Appointment apt: allApts) {
                    if (apt.getVet_uname().equals(vetUname) && apt.isCompleted() && apt.getScheduled_time().getMonth().toString().equals(monthPaySlip) && apt.isPaid()) {
                        sumCustomerRating = sumCustomerRating + apt.getCustomer_rating();
                        numCompletedAptByVet = numCompletedAptByVet + 1;
                    } 
                }
                if (numCompletedAptByVet != 0) {
                    out.println("Number of completed appointments by the vet = " + numCompletedAptByVet + "<br>");
                    out.println("Average customer rating by the vet = " + (sumCustomerRating*1.0)/numCompletedAptByVet +"<br>");
                } else {
                    out.println("The vet has not completed any appointment in " + monthPaySlip);
                }
                
                
                out.println(new PaySlipForVets().editPaySlipTemplate(String.valueOf(vetFound.getBase_salary())));
                
            } catch (Exception ex) {
                request.getRequestDispatcher("managing_staff/generate_pay_slip.jsp").include(request, response);
                if (vetNotExist) {
                    out.println("The vet doesn't exist, try again!");
                }else if (monthInvalid) {
                    out.println("The entered month is invalid, try again!");
                }else {
                    out.print("Some errors occured, try again!");
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
