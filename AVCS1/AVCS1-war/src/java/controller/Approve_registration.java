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
@WebServlet(name = "Approve_registration", urlPatterns = {"/Approve_registration"})
public class Approve_registration extends HttpServlet {

    @EJB
    private ExpertiseFacade expertiseFacade;

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
        
        List<Vet> vetsNotApproved = vetFacade.searchNotApproved();
        if (vetsNotApproved != null) {
            for (Vet vet: vetsNotApproved) {
                // check if the Approve button of each record is clicked
                if (request.getParameter("vet " + Long.toString(vet.getId())) != null) {
                    // initialize the 2 expertises of the vet to be -
                    Expertise exp1 = new Expertise("-");
                    Expertise exp2 = new Expertise("-");
                    expertiseFacade.create(exp1);
                    expertiseFacade.create(exp2);
                    vet.getExpertises().add(exp1);
                    vet.getExpertises().add(exp2);
                    vetFacade.edit(vet);
                    // Approve the vet's registration
                    vetFacade.approveRegistration(vet.getId(), true);
                    break;
                }
            }
        }
        
        
        List<Receptionist> receptionistsNotApproved = receptionistFacade.searchNotApproved();
        if (receptionistsNotApproved != null) {
            for (Receptionist rec: receptionistsNotApproved) {
                // check if the Approve button of each record is clicked
                if (request.getParameter("rec " + Long.toString(rec.getId())) != null) {
                    // Approve the receptionist's registration
                    receptionistFacade.approveRegistration(rec.getId(), true);
                    break;
                }
            }
        }
        
        request.getRequestDispatcher("Load_registration_info").forward(request, response);
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
