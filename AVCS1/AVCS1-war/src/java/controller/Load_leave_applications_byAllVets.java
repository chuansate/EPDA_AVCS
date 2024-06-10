/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSP_utils.ListsOfRecords;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.LeaveApplication;
import model.LeaveApplicationFacade;
import model.Staff;
import model.Vet;
import model.WorkingRota;
import model.WorkingRotaFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Load_leave_applications_byAllVets", urlPatterns = {"/Load_leave_applications_byAllVets"})
public class Load_leave_applications_byAllVets extends HttpServlet {

    @EJB
    private WorkingRotaFacade workingRotaFacade;

    @EJB
    private LeaveApplicationFacade leaveApplicationFacade;

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
        List<WorkingRota> rotas = workingRotaFacade.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        HttpSession s = request.getSession(false);
        Staff vet = (Staff)s.getAttribute("user");
        Collections.sort(rotas, (new RotaSortingComparator()).reversed());
        List<LeaveApplication> pendingLeaveApplications = leaveApplicationFacade.searchPendingLeaveApplications();

        try (PrintWriter out = response.getWriter()) {
            if (pendingLeaveApplications != null) {
                request.getRequestDispatcher("managing_staff/check_leave_applications_by_vets.jsp").include(request, response);
                out.println(new ListsOfRecords().listOfPendingLeaveApplicationsByAllVets(pendingLeaveApplications));
            
            
            } else {
                request.getRequestDispatcher("managing_staff/check_leave_applications_by_vets.jsp").include(request, response);
                out.println("There is no pending applications from vets so far!");
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
