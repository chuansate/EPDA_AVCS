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
import model.Receptionist;
import model.ReceptionistFacade;
import model.Vet;
import model.VetFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Register_vets_receptionists", urlPatterns = {"/Register_vets_receptionists"})
public class Register_vets_receptionists extends HttpServlet {

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
        
        String userType = request.getParameter("userType");
        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        
        try (PrintWriter out = response.getWriter()) {
            if (userType.equals("vet")) {
                List<Vet> vets = vetFacade.findAll();
                Vet found = null;
                for (Vet vet: vets) {
                    if (vet.getUname().equals(uname)){
                        found = vet;
                        break;
                    }
                }

                if (found == null) {
                    Vet newVet = new Vet(uname, pwd, 'A', "", "", "", 0, false, -1);
                    vetFacade.create(newVet);
                    request.getRequestDispatcher("login.jsp").include(request, response);
                    out.println("The account has been registered, please wait for approvals from the managing staffs!");
                } else {
                    request.getRequestDispatcher("register.jsp").include(request, response);
                    out.println("The username has been registered, try again!");
                }

            } else {
                List<Receptionist> receptionists = receptionistFacade.findAll();
                Receptionist found = null;
                for (Receptionist receptionist: receptionists) {
                    if (receptionist.getUname().equals(uname)){
                        found = receptionist;
                        break;
                    }
                }

                if (found == null) {
                    Receptionist newReceptionist = new Receptionist(uname, pwd, 'A', "", "", "", 0, false);
                    receptionistFacade.create(newReceptionist);
                    request.getRequestDispatcher("login.jsp").include(request, response);
                    out.println("The account has been registered, please wait for approvals from the managing staffs!");
                } else {
                    request.getRequestDispatcher("register.jsp").include(request, response);
                    out.println("The username has been registered, try again!");
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
