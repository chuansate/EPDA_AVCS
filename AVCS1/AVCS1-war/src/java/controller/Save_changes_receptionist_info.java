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
import model.Vet;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Save_changes_receptionist_info", urlPatterns = {"/Save_changes_receptionist_info"})
public class Save_changes_receptionist_info extends HttpServlet {

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
        String pwd = request.getParameter("pwd");
        String gender = request.getParameter("gender");
        String email_adr = request.getParameter("email_adr");
        String contact_num = request.getParameter("contact_num");
        String nationality = request.getParameter("nationality");
        String ageStr = request.getParameter("age");
        int age;
        HttpSession s = request.getSession(false);
        long receptionistIDTobeEdited = (long)s.getAttribute("receptionistIDTobeEdited");
        Receptionist receptionistToBeEdited = receptionistFacade.find(receptionistIDTobeEdited);
        request.getRequestDispatcher("managing_staff/manage_receptionist_info.jsp").include(request, response);
        try (PrintWriter out = response.getWriter()) {
            try {
                if (pwd.length() > 8 || pwd.length() == 0) {
                    throw new Exception();
                }

                if (gender.length() > 1 || gender.length() == 0) {
                    throw new Exception();
                }

                // check if the entered gender match any of the provided gender options
                char[] gender_options = {'M', 'F'};
                boolean gender_isValid = false;
                for (char gender_option: gender_options) {
                    if (gender.charAt(0) == gender_option) {
                        gender_isValid = true;
                        break;
                    }
                }

                if (!gender_isValid) {
                    throw new Exception();
                }

                age = Integer.parseInt(ageStr);
                if (age <= 0) {
                    throw new Exception();
                }
                
                receptionistToBeEdited.setPwd(pwd);
                receptionistToBeEdited.setGender(gender.charAt(0));
                receptionistToBeEdited.setEmail_adr(email_adr);
                receptionistToBeEdited.setContact_num(contact_num);
                receptionistToBeEdited.setNationality(nationality);
                receptionistToBeEdited.setAge(age);
                receptionistFacade.edit(receptionistToBeEdited);

                List<Receptionist> updatedAllReceptionists = receptionistFacade.findAll();
                if (updatedAllReceptionists != null) {
                    out.println("The changes on the receptionist have been saved successfully!");
                    out.println(new ListsOfRecords().listOfReceptionists(updatedAllReceptionists));
                } else {
                    out.println("There is no receptionist to display so far!");
                }
            } catch (Exception ex) {
                out.println("Invalid inputs, please try again!");
                System.out.println("stack trace:");
                ex.printStackTrace();
                List<Receptionist> allReceptionists = receptionistFacade.findAll();
                if (allReceptionists != null) {
                    out.println(new ListsOfRecords().listOfReceptionists(allReceptionists));
                } else {
                    out.println("There is no receptionist to display so far!");
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
