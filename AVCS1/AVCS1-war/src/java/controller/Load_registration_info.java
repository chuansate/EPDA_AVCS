/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.ejb.EJB;
import model.ReceptionistFacade;
import model.VetFacade;
import model.Vet;
import model.Receptionist;
/**
 *
 * @author lowsi
 */
@WebServlet(name = "Load_registration_info", urlPatterns = {"/Load_registration_info"})
public class Load_registration_info extends HttpServlet {

    @EJB
    private VetFacade vetFacade;

    @EJB
    private ReceptionistFacade receptionistFacade;

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
        // pretend that you are loading the data from database thru facade
        
        request.getRequestDispatcher("managing_staff/registration_info.jsp").include(request, response);
        
        try (PrintWriter out = response.getWriter()) {
            List<Vet> vetsNotApproved = vetFacade.searchNotApproved();
            List<Receptionist> receptionistsNotApproved = receptionistFacade.searchNotApproved();
            out.println("<a href=\"Login\">Return Home Page</a> <br><br>");
            out.println("<br><br>Pending registrations by vets:<br>");
            if (vetsNotApproved != null) {
                out.println("<br><br><br><table border=\"1\" width=\"80%\"><tr>\n" +
                "    <th>User Type</th>\n" +
                "    <th>Username</th>\n" +
                "    <th>Action</th>\n" +
                "  </tr>");
                for (Vet vet: vetsNotApproved) {
                    out.println("<tr>");
                    out.println("<td>Vet</td>");
                    out.println("<td>" + vet.getUname() + "</td>");
                    out.println("<td><form action=\"Approve_registration\" method=\"post\"><input type=\"submit\" value=\"Approve\" name=\"" + "vet " + String.valueOf(vet.getId()) + "\"></form> ");
                    out.println("</tr>");
                }
                out.println("</table>");
            } 
            
            out.println("<br><br>Pending registrations by receptionists:<br>");
            if (receptionistsNotApproved != null) {
                out.println("<br><br><br><table border=\"1\" width=\"80%\"><tr>\n" +
                "    <th>User Type</th>\n" +
                "    <th>Username</th>\n" +
                "    <th>Action</th>\n" +
                "  </tr>");
                for (Receptionist recep: receptionistsNotApproved) {
                    out.println("<tr>");
                    out.println("<td>Receptionist</td>");
                    out.println("<td>" + recep.getUname() + "</td>");
                    out.println("<td><form action=\"Approve_registration\" method=\"post\"><input type=\"submit\" value=\"Approve\" name=\"" + "rec " +String.valueOf(recep.getId()) + "\"></form> ");
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
