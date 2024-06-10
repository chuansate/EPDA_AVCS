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
import model.ReceptionistFacade;
import model.Vet;
import model.VetFacade;
import JSP_utils.DropDownMenus;
import java.util.ArrayList;
import model.Expertise;
import model.ExpertiseFacade;
import model.Receptionist;
/**
 *
 * @author lowsi
 */
@WebServlet(name = "Load_profile", urlPatterns = {"/Load_profile"})
public class Load_profile extends HttpServlet {


    @EJB
    private ExpertiseFacade expertiseFacade;

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
            List<Vet> allVets = vetFacade.findAll();
            Vet found = null;
            for (Vet vet:allVets) {
                if (uname.equals(vet.getUname())) {
                    found = vet;
                    break;
                }
            }
            request.getRequestDispatcher("vet/edit_vet_profile.jsp").include(request, response);
            try (PrintWriter out = response.getWriter()) {
                out.println("<form action=\"Edit_profile\"><input type=\"submit\" value=\"Edit\"></form>");
                out.println("<table>");
                out.println("<tr><td>Username: </td> <td>"+ found.getUname() +"</td></tr>");
                out.println("<tr><td>Password: </td> <td>"+ found.getPwd()+"</td></tr>");
                out.println("<tr><td>Email address: </td> <td>"+ found.getEmail_adr()+"</td></tr>");
                out.println("<tr><td>Contact number: </td> <td>"+ found.getContact_num()+"</td></tr>");
                out.println("<tr><td>Nationality: </td> <td>"+ found.getNationality()+"</td></tr>");
                out.println("<tr><td>Age: </td> <td>"+ found.getAge()+"</td></tr>");
                if (found.getGender() == 'A') {
                    out.println("<tr><td>Gender (Either M or F): </td> <td>-</td></tr>");
                } else {
                    out.println("<tr><td>Gender (Either M or F): </td> <td>"+ found.getGender()+"</td></tr>");
                }
                if (!found.getExpertises().isEmpty()) {
                    out.println("<tr><td>Expertise 1: </td> <td>"+ found.getExpertises().get(0).getExpertise_name() +"</td></tr>");
                    out.println("<tr><td>Expertise 2: </td> <td>"+ found.getExpertises().get(1).getExpertise_name() +"</td></tr>");
                } else {
                    out.println("<tr><td>Expertise 1: </td> <td>-</td></tr>");
                    out.println("<tr><td>Expertise 2: </td> <td>-</td></tr>");
                }
                
                out.println("</table>");
                // this is from Save_changes_profile.java, handling the moment when the user enters invalid inputs
                if (s.getAttribute("invalidInputEditProfile") != null) {
                    if ((boolean)s.getAttribute("invalidInputEditProfile")) {
                        out.println("Invalid inputs while editing the profile, try again!");
                    } else {
                        out.println("Updated the profile successfully!");
                    }
                }
                
            }
        } else if (userType.equals("receptionist")) {
            // come up with an interface for receptionists to edit their profile
            // then implement the part of making appointments
            List<Receptionist> allReceptionists = receptionistFacade.findAll();
            Receptionist found = null;
            for (Receptionist r:allReceptionists) {
                if (uname.equals(r.getUname())) {
                    found = r;
                    break;
                }
            }
            request.getRequestDispatcher("receptionist/edit_receptionist_profile.jsp").include(request, response);
            try (PrintWriter out = response.getWriter()) {
                out.println("<form action=\"Edit_profile\"><input type=\"submit\" value=\"Edit\"></form>");
                out.println("<table>");
                out.println("<tr><td>Username: </td> <td>"+ found.getUname() +"</td></tr>");
                out.println("<tr><td>Password: </td> <td>"+ found.getPwd()+"</td></tr>");
                out.println("<tr><td>Email address: </td> <td>"+ found.getEmail_adr()+"</td></tr>");
                out.println("<tr><td>Contact number: </td> <td>"+ found.getContact_num()+"</td></tr>");
                out.println("<tr><td>Nationality: </td> <td>"+ found.getNationality()+"</td></tr>");
                out.println("<tr><td>Age: </td> <td>"+ found.getAge()+"</td></tr>");
                if (found.getGender() == 'A') {
                    out.println("<tr><td>Gender (Either M or F): </td> <td>-</td></tr>");
                } else {
                    out.println("<tr><td>Gender (Either M or F): </td> <td>"+ found.getGender()+"</td></tr>");
                }
                
                
                out.println("</table>");
                // this is from Save_changes_profile.java, handling the moment when the user enters invalid inputs
                if (s.getAttribute("invalidInputEditProfile") != null) {
                    if ((boolean)s.getAttribute("invalidInputEditProfile")) {
                        out.println("Invalid inputs while editing the profile, try again!");
                    } else {
                        out.println("Updated the profile successfully!");
                    }
                }
                
            }
            System.out.println("(From Load_profile.java) " + userType + " is trying to login...");
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
