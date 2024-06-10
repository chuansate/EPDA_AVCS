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
import model.Staff;
import model.Vet;
import model.VetFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Save_changes_profile", urlPatterns = {"/Save_changes_profile"})
public class Save_changes_profile extends HttpServlet {

    @EJB
    private ReceptionistFacade receptionistFacade;

    @EJB
    private ExpertiseFacade expertiseFacade;

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
        String userType = (String)s.getAttribute("userType");
        if (userType.equals("vet")) {
            String pwd = request.getParameter("pwd");
            String email = request.getParameter("email");
            String contactNum = request.getParameter("contactNum");
            String nationality = request.getParameter("nationality");
            String age = request.getParameter("age");
            String gender = request.getParameter("gender");
            String uname = (String)s.getAttribute("uname");
            String expertise1 = request.getParameter("expertise1");
            String expertise2 = request.getParameter("expertise2");
            Vet user = (Vet)s.getAttribute("user");
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

                    int age_num = Integer.parseInt(age);
                    if (age_num <= 0) {
                        throw new Exception();
                    }
                    // UPDATE THE DATABASE!
                    user.setPwd(pwd);
                    user.setEmail_adr(email);
                    user.setContact_num(contactNum);
                    user.setNationality(nationality);
                    user.setAge(age_num);
                    user.setGender(gender.charAt(0));
                    s.setAttribute("user", user);
                    vetFacade.edit(user);
                    
                    
                    // the SQL statement below didnt update the Vet's profile information
                    // becoz we are storing a Vet inside the session, if we change the table but not the Vet inside the session, then there might be a conflict
                    // so the table won't update the Vet for us!
                    //vetFacade.editProfile(uname, pwd, email, contactNum, nationality, age_num, gender.charAt(0));
                    try {
                        // When staff approves the registration, Do the initialization of 2 expertises of each vet to be "-"
                        // then when vet edits the profile, what we need to do is just to set it!
                        Expertise exp1 = new Expertise(expertise1);
                        Expertise exp2 = new Expertise(expertise2);
                        List<Expertise> expertises = expertiseFacade.findAll();
                                              
                        expertiseFacade.create(exp1);
                        expertiseFacade.create(exp2);
                        
                        Expertise old_expertise1 = user.getExpertises().get(0);
                        Expertise old_expertise2 = user.getExpertises().get(1);
                        user.getExpertises().set(0, exp1);
                        user.getExpertises().set(1, exp2);
                        //update the table VET_EXPERTISE
                        vetFacade.edit(user);
                        //update the table EXPERTISE
                        expertiseFacade.remove(old_expertise1);
                        expertiseFacade.remove(old_expertise2);
                        
                    }catch (Exception e) {
                        e.printStackTrace();
                    }


                    s.setAttribute("invalidInputEditProfile", false);
                    request.getRequestDispatcher("Load_profile").forward(request, response);
                } catch (Exception e){
                    s.setAttribute("invalidInputEditProfile", true);
                    request.getRequestDispatcher("Load_profile").forward(request, response);
                }
            }
        } else if (userType.equals("receptionist")) {
            String pwd = request.getParameter("pwd");
            String email = request.getParameter("email");
            String contactNum = request.getParameter("contactNum");
            String nationality = request.getParameter("nationality");
            String age = request.getParameter("age");
            String gender = request.getParameter("gender");
            String uname = (String)s.getAttribute("uname");
            Receptionist user = (Receptionist)s.getAttribute("user");
            
            try (PrintWriter out = response.getWriter()) {
                try {
                    //All these validations below keep failing
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

                    int age_num = Integer.parseInt(age);
                    if (age_num <= 0) {
                        throw new Exception();
                    }
                    // UPDATE THE DATABASE!
                    user.setPwd(pwd);
                    user.setEmail_adr(email);
                    user.setContact_num(contactNum);
                    user.setNationality(nationality);
                    user.setAge(age_num);
                    user.setGender(gender.charAt(0));
                    s.setAttribute("user", user);
                    receptionistFacade.edit(user);
                    s.setAttribute("invalidInputEditProfile", false);
                    request.getRequestDispatcher("Load_profile").forward(request, response);
                } catch (Exception e){
                    s.setAttribute("invalidInputEditProfile", true);
                    request.getRequestDispatcher("Load_profile").forward(request, response);
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
