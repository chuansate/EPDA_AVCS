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
import javax.servlet.http.HttpSession;
import model.Staff;
import model.StaffFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Delete_staff_info", urlPatterns = {"/Delete_staff_info"})
public class Delete_staff_info extends HttpServlet {

    @EJB
    private StaffFacade staffFacade;
    
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
        Staff user = (Staff)s.getAttribute("user");
        Long staffID2beDeleted = 0l;
        List<Staff> staffs = staffFacade.findAll();
        for (Staff staff: staffs) {
            // check if the DELETE button of each record is clicked
            if (request.getParameter(Long.toString(staff.getId())) != null) {
                // keep track of the index of the record to be edited
                staffID2beDeleted = staff.getId();                
                break;
            }
        }
        
        
        try (PrintWriter out = response.getWriter()) {
            try {
                staffFacade.deleteByID(staffID2beDeleted);
                List<Staff> updatedStaffs = staffFacade.findAll();
                
                request.getRequestDispatcher("managing_staff/manage_staff_info.jsp").include(request, response);
                out.println("The staff has been deleted successfully!");
                out.println(new ListsOfRecords().listOfStaffs(updatedStaffs, user.getId()));
            } catch (Exception ex) {
                ex.printStackTrace();
                
                request.getRequestDispatcher("managing_staff/manage_staff_info.jsp").include(request, response);
                out.println("Failed to delete the staff, try again!");
                out.println(new ListsOfRecords().listOfStaffs(staffs, user.getId()));
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
