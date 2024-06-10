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
@WebServlet(name = "Search_staff_info", urlPatterns = {"/Search_staff_info"})
public class Search_staff_info extends HttpServlet {

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
        String uname = request.getParameter("uname");
        List<Staff> staffs = staffFacade.findAll();
        Staff found = null;
        for (Staff staff: staffs) {
            if (uname.equals(staff.getUname())) {
                found = staff;
                break;
            }
        }
        
        if (found == null) {
            request.getRequestDispatcher("managing_staff/manage_staff_info.jsp").include(request, response);
            try (PrintWriter out = response.getWriter()) {
                out.println("<br>There is no matched result!");
            }
        } else {
            request.getRequestDispatcher("managing_staff/manage_staff_info.jsp").include(request, response);
            try (PrintWriter out = response.getWriter()) {
                out.println("<br>Matched result:");
                out.println("<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr><th>Staff Username</th> <th>Password</th> <th>Gender</th> <th>Actions</th></tr>");
                // The staffs are not allowed to delete their respective accounts
                // So only EDIT button is open to them, DELETE button is not open
                if (found.getId() == user.getId()) {
                    out.println("<tr>");
                    out.println("<td>" + found.getUname() + "</td>");
                    out.println("<td>" + found.getPwd() + "</td>");
                    out.println("<td>" + found.getGender() + "</td>");
                    out.println("<td><form action=\"Edit_staff_info\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(found.getId()) + "\"></form> ");
                    out.println("</tr>");
                } else {
                    // The staffs are allowed to edit others' info, and even delete others' accounts
                    out.println("<tr>");
                    out.println("<td>" + found.getUname() + "</td>");
                    out.println("<td>" + found.getPwd() + "</td>");
                    out.println("<td>" + found.getGender() + "</td>");
                    out.println("<td><form action=\"Edit_staff_info\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(found.getId()) + "\"></form> ");
                    out.println("<form action=\"Delete_staff_info\" method=\"post\"><input type=\"submit\" value=\"Delete\" name=\"" + String.valueOf(found.getId()) + "\"></form></td>");
                    out.println("</tr>");
                }
                
                out.println("</table>");
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
