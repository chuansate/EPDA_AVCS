/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSP_utils.DropDownMenus;
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
import model.Expertise;
import model.ExpertiseFacade;
import model.Receptionist;
import model.ReceptionistFacade;
import model.Vet;
import model.VetFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Edit_profile", urlPatterns = {"/Edit_profile"})
public class Edit_profile extends HttpServlet {

    @EJB
    private ReceptionistFacade receptionistFacade;


    @EJB
    private VetFacade vetFacade;

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
        String uname = (String)s.getAttribute("uname");
        String userType = (String)s.getAttribute("userType");
        if (userType.equals("vet")) {
            request.getRequestDispatcher("vet/edit_vet_profile.jsp").include(request, response);
            try (PrintWriter out = response.getWriter()) {
                List<Vet> allVets = vetFacade.findAll();
                Vet found = null;
                for (Vet vet:allVets) {
                    if (uname.equals(vet.getUname())) {
                        found = vet;
                        break;
                    }
                }
                out.println("<form action=\"Save_changes_profile\"><input type=\"submit\" value=\"Save\">");
                out.println("<table>");
                out.println("<tr><td>Username: </td> <td>"+ found.getUname() +"</td></tr>");
                out.println("<tr><td>Password: </td> <td><input type=\"text\" name=\"pwd\" size=\"20\" placeholder=\""+ found.getPwd() +"\"></td></tr>");
                out.println("<tr><td>Email address: </td> <td><input type=\"text\" name=\"email\" size=\"20\" placeholder=\""+ found.getEmail_adr()+"\"></td></tr>");
                out.println("<tr><td>Contact number: </td> <td><input type=\"text\" name=\"contactNum\" size=\"20\" placeholder=\""+ found.getContact_num() +"\"></td></tr>");
                out.println("<tr><td>Nationality: </td> <td><input type=\"text\" name=\"nationality\" size=\"20\" placeholder=\""+ found.getNationality() +"\"></td></tr>");
                out.println("<tr><td>Age: </td> <td><input type=\"text\" name=\"age\" size=\"20\" placeholder=\""+ found.getAge() +"\"></td></tr>");
                if (found.getGender() == 'A') {
                    out.println("<tr><td>Gender (Either M or F): </td> <td><input type=\"text\" name=\"gender\" size=\"20\" placeholder=\"-\"></td></tr>");
                } else {
                    out.println("<tr><td>Gender (Either M or F): </td> <td><input type=\"text\" name=\"gender\" size=\"20\" placeholder=\""+ found.getGender() +"\"></td></tr>");
                }
                DropDownMenus d = new DropDownMenus();
                out.println("<tr><td>Expertise 1: </td> <td>"+ d.expertisesDropDown_JSP("expertise1", "expertise1") +"</td></tr>");
                out.println("<tr><td>Expertise 2: </td> <td>"+ d.expertisesDropDown_JSP("expertise2", "expertise2")+"</td></tr>");
                out.println("</table></form>");

            }
        } else if (userType.equals("receptionist")) {
            request.getRequestDispatcher("receptionist/edit_receptionist_profile.jsp").include(request, response);
            try (PrintWriter out = response.getWriter()) {
                List<Receptionist> allReceptionists = receptionistFacade.findAll();
                Receptionist found = null;
                for (Receptionist r:allReceptionists) {
                    if (uname.equals(r.getUname())) {
                        found = r;
                        break;
                    }
                }
                out.println("<form action=\"Save_changes_profile\"><input type=\"submit\" value=\"Save\">");
                out.println("<table>");
                out.println("<tr><td>Username: </td> <td>"+ found.getUname() +"</td></tr>");
                out.println("<tr><td>Password: </td> <td><input type=\"text\" name=\"pwd\" size=\"20\" placeholder=\""+ found.getPwd() +"\"></td></tr>");
                out.println("<tr><td>Email address: </td> <td><input type=\"text\" name=\"email\" size=\"20\" placeholder=\""+ found.getEmail_adr()+"\"></td></tr>");
                out.println("<tr><td>Contact number: </td> <td><input type=\"text\" name=\"contactNum\" size=\"20\" placeholder=\""+ found.getContact_num() +"\"></td></tr>");
                out.println("<tr><td>Nationality: </td> <td><input type=\"text\" name=\"nationality\" size=\"20\" placeholder=\""+ found.getNationality() +"\"></td></tr>");
                out.println("<tr><td>Age: </td> <td><input type=\"text\" name=\"age\" size=\"20\" placeholder=\""+ found.getAge() +"\"></td></tr>");
                if (found.getGender() == 'A') {
                    out.println("<tr><td>Gender (Either M or F): </td> <td><input type=\"text\" name=\"gender\" size=\"20\" placeholder=\"-\"></td></tr>");
                } else {
                    out.println("<tr><td>Gender (Either M or F): </td> <td><input type=\"text\" name=\"gender\" size=\"20\" placeholder=\""+ found.getGender() +"\"></td></tr>");
                }
                out.println("</table></form>");

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
