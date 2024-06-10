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
import javax.servlet.http.HttpSession;
import model.Receptionist;
import model.ReceptionistFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Edit_receptionist_info", urlPatterns = {"/Edit_receptionist_info"})
public class Edit_receptionist_info extends HttpServlet {

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
        long receptionistIDTobeEdited = 0l;
        List<Receptionist> allreceptionists = receptionistFacade.findAll();
        HttpSession s = request.getSession(false);
        
        for (Receptionist r: allreceptionists) {
            // check if the EDIT button of each record is clicked
            if (request.getParameter(Long.toString(r.getId())) != null) {
                // keep track of the index of the record to be edited
                receptionistIDTobeEdited = r.getId();
                s.setAttribute("receptionistIDTobeEdited", receptionistIDTobeEdited);
                break;
            }
        }
        
        request.getRequestDispatcher("managing_staff/manage_receptionist_info.jsp").include(request, response);
        try (PrintWriter out = response.getWriter()) {
            out.println(new ListsOfRecords().listOfReceptionistsToBeEdited(allreceptionists, receptionistIDTobeEdited));
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
