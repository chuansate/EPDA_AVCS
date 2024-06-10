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
import model.LeaveApplication;
import model.LeaveApplicationFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Reject_leave_application_vet", urlPatterns = {"/Reject_leave_application_vet"})
public class Reject_leave_application_vet extends HttpServlet {

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
        List<LeaveApplication> leaveApplications = leaveApplicationFacade.findAll();
        long laIDToBeRejected = 0l;
        for (LeaveApplication la: leaveApplications) {
            // check if the Delete button of each record is clicked
            if (request.getParameter(Long.toString(la.getId())) != null) {
                // keep track of the index of the record to be deleted
                laIDToBeRejected = la.getId();
                System.out.println("laIDToBeRejected = " + laIDToBeRejected);
                break;
            }
        }
        
        LeaveApplication laFound = leaveApplicationFacade.find(laIDToBeRejected);
        try (PrintWriter out = response.getWriter()) {
            if (laFound != null) {
                request.getRequestDispatcher("managing_staff/check_leave_applications_by_vets.jsp").include(request, response);                
                try {
                    //do some backend here!
                    laFound.setStatus("Rejected");
                    leaveApplicationFacade.edit(laFound);
                    List<LeaveApplication> updatedPendingLeaveApplications = leaveApplicationFacade.searchPendingLeaveApplications();
                    out.println("The leave application has been rejected!");
                    if (updatedPendingLeaveApplications != null) {
                        out.println(new ListsOfRecords().listOfPendingLeaveApplicationsByAllVets(updatedPendingLeaveApplications));
                    } else {
                        out.println("There is no pending applications from vets so far!");
                    }
                    

                } catch(Exception ex) {
                    out.println("Failed to reject the leave application due to database issues!");
                    List<LeaveApplication> pendingLeaveApplications = leaveApplicationFacade.searchPendingLeaveApplications();
                    if (pendingLeaveApplications != null) {
                        out.println(new ListsOfRecords().listOfPendingLeaveApplicationsByAllVets(pendingLeaveApplications));
                    } else {
                        out.println("There is no pending applications from vets so far!");
                    }
                }
                
            } else {
                request.getRequestDispatcher("managing_staff/check_leave_applications_by_vets.jsp").include(request, response);
                out.println("The leave application could not be found in the database, try again!");
                List<LeaveApplication> pendingLeaveApplications = leaveApplicationFacade.searchPendingLeaveApplications();
                if (pendingLeaveApplications != null) {
                    out.println(new ListsOfRecords().listOfPendingLeaveApplicationsByAllVets(pendingLeaveApplications));
                } else {
                    out.println("There is no pending applications from vets so far!");
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
