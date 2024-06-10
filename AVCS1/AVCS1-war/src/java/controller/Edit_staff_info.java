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
import model.Staff;
import model.StaffFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Edit_staff_info", urlPatterns = {"/Edit_staff_info"})
public class Edit_staff_info extends HttpServlet {

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
        
        Long staffID2beEdited = 0l;
        List<Staff> staffs = staffFacade.findAll();
        for (Staff staff: staffs) {
            // check if the EDIT button of each record is clicked
            if (request.getParameter(Long.toString(staff.getId())) != null) {
                // keep track of the index of the record to be edited
                staffID2beEdited = staff.getId();
                s.setAttribute("staffID2BeEdited", staffID2beEdited);
                break;
            }
        }
        request.getRequestDispatcher("managing_staff/manage_staff_info.jsp").include(request, response);
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<br><table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"><tr>\n" +
            "    <th>Username</th>\n" +
            "    <th>Password</th>\n" +
            "    <th>Gender</th>\n" +
            "    <th>Actions</th>\n" +
            "  </tr>");
            for (Staff staff: staffs) {
                if (staff.getId() == staffID2beEdited) {
                    out.println("<tr>");
                    out.println("<form action=\"Save_staff_info\" method=\"post\">");
                    out.println("<td>" + staff.getUname() + "</td>");
                    out.println("<td><input type=\"text\" name=\"pwd\" size=\"10\" value=" + staff.getPwd() + "></td>");
                    out.println("<td><input type=\"text\" name=\"gender\" size=\"10\" value=" + staff.getGender() + "></td>");
                    out.println("<td><input type=\"submit\" value=\"Save\"> </td>");
                    out.println("</form>");
                    out.println("</tr>");
                } else {
                    out.println("<tr>");
                    out.println("<td>" + staff.getUname() + "</td>");
                    out.println("<td>" + staff.getPwd() + "</td>");
                    out.println("<td>" + staff.getGender() + "</td>");
                    out.println("<td><form action=\"Edit_staff_info\" method=\"post\"><input type=\"submit\" value=\"Edit\" name=\"" + String.valueOf(staff.getId()) + "\"></form> ");
                    out.println("<form action=\"Delete_staff_info\" method=\"post\"><input type=\"submit\" value=\"Delete\" name=\"" + String.valueOf(staff.getId()) + "\"></form></td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
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
