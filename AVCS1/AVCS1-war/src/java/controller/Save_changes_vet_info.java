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
import model.Vet;
import model.VetFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Save_changes_vet_info", urlPatterns = {"/Save_changes_vet_info"})
public class Save_changes_vet_info extends HttpServlet {

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
        String pwd = request.getParameter("pwd");
        String gender = request.getParameter("gender");
        String email_adr = request.getParameter("email_adr");
        String base_salaryStr = request.getParameter("base_salary");
        String contact_num = request.getParameter("contact_num");
        String nationality = request.getParameter("nationality");
        String ageStr = request.getParameter("age");
        int age;
        int base_salary;
        HttpSession s = request.getSession(false);
        long vetIDTobeEdited = (long)s.getAttribute("vetIDTobeEdited");
        Vet vetToBeEdited = vetFacade.find(vetIDTobeEdited);
        request.getRequestDispatcher("managing_staff/manage_vet_info.jsp").include(request, response);
        try (PrintWriter out = response.getWriter()) {
            try {
                base_salary = Integer.parseInt(base_salaryStr);
                if (base_salary <= 0) {
                    throw new Exception();
                }
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
                
                vetToBeEdited.setPwd(pwd);
                vetToBeEdited.setGender(gender.charAt(0));
                vetToBeEdited.setBase_salary(base_salary);
                vetToBeEdited.setEmail_adr(email_adr);
                vetToBeEdited.setContact_num(contact_num);
                vetToBeEdited.setNationality(nationality);
                vetToBeEdited.setAge(age);
                vetFacade.edit(vetToBeEdited);

                List<Vet> updatedAllVets = vetFacade.findAll();
                if (updatedAllVets != null) {
                    out.println("The changes on the vet have been saved successfully!");
                    out.println(new ListsOfRecords().listOfVets(updatedAllVets));
                } else {
                    out.println("There is no vet to display so far!");
                }
            } catch (Exception ex) {
                out.println("Invalid inputs, please try again!");
                System.out.println("stack trace:");
                ex.printStackTrace();
                List<Vet> allVets = vetFacade.findAll();
                if (allVets != null) {
                    out.println(new ListsOfRecords().listOfVets(allVets));
                } else {
                    out.println("There is no vet to display so far!");
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
