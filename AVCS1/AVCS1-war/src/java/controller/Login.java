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
import model.Receptionist;
import model.ReceptionistFacade;
import model.Staff;
import model.StaffFacade;
import model.Vet;
import model.VetFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    @EJB
    private ReceptionistFacade receptionistFacade;

    @EJB
    private VetFacade vetFacade;

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
        String userType = request.getParameter("userType");
        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        //Returns the current session associated with this request, 
        //or if the request does not have a session, creates one.
        HttpSession s = request.getSession();
        String userTypeFromSession = (String)s.getAttribute("userType");
        if (s.getAttribute("invalidInputEditProfile") != null) {
            s.removeAttribute("invalidInputEditProfile");
        }
        System.out.println("User Type = "+ userType + " is trying to login... (From Login.java). ");
        System.out.println("If the User Type = null, it means the user is logging in for the first time!");
        // For staffs, vets, and receptionists, their home pages got access to different pages
        // The following `if` block are for handling them to redirect from the pages to their respective home pages
        // after clicking `Return Home Page` it will redirect to currect servlet file (Login.java), is to render the message:
        // "Welcome back, username!"
        if (userTypeFromSession != null) {
            try (PrintWriter out = response.getWriter()) {
                if (userTypeFromSession.equals("managingStaff")) {
                    request.getRequestDispatcher("managing_staff/home.jsp").include(request, response);
                    out.println("<br>Welcome back, " + s.getAttribute("uname") + "!");
                    return;
                } else if (userTypeFromSession.equals("vet")) {
                    request.getRequestDispatcher("vet/home.jsp").include(request, response);
                    out.println("<br>Welcome back, " + s.getAttribute("uname") + "!");
                    return;
                } else if (userTypeFromSession.equals("receptionist")) {
                    request.getRequestDispatcher("receptionist/home.jsp").include(request, response);
                    out.println("<br>Welcome back, " + s.getAttribute("uname") + "!");
                    return;
                }   
            }
        }
        
        
        
        // this is for when staff is redirected from login.jsp to managing_staff/home.jsp, vet/home.jsp and receptionist/home.jsp
        try (PrintWriter out = response.getWriter()) {
            try {
                if (userType.equals("managingStaff")) {
                    //out.println("<br><br><br> Welcome back, staff!");
                    List<Staff> allstaffs = staffFacade.findAll();
                    Staff found = null;
                    for(Staff staff: allstaffs){
                        if(uname.equals(staff.getUname()) && pwd.equals(staff.getPwd())){
                            found = staff;
                            break;
                        }
                    }

                    if(found == null){
                        throw new Exception();      
                    }
                    s.setAttribute("user", found);
                    s.setAttribute("uname", found.getUname());
                    s.setAttribute("userType", userType);
                    request.getRequestDispatcher("managing_staff/home.jsp").include(request, response);
                    out.println("<br>Welcome back, " + found.getUname() + "!");

                } else if (userType.equals("vet")) {
                    List<Vet> allvets = vetFacade.findAll();
                    Vet found = null;
                    for(Vet vet: allvets){
                        if(uname.equals(vet.getUname()) && pwd.equals(vet.getPwd())){
                            found = vet;
                            break;
                        }
                    }
                    
                    if(found == null){
                        throw new Exception();      
                    }
                    
                    if (found.isApproved()) {
                        s.setAttribute("user", found);
                        s.setAttribute("uname", found.getUname());
                        s.setAttribute("userType", userType);
                        request.getRequestDispatcher("vet/home.jsp").include(request, response);
                        out.println("<br>Welcome back, " + found.getUname() + "!");
                    } else {
                        s.invalidate();
                        request.getRequestDispatcher("login.jsp").include(request, response);
                        out.println("<br><br><br><p style=\"text-align:center\">Registration is still pending for approval from managing staffs!</p>");
                    }    
                } else {
                    List<Receptionist> allreceptionists = receptionistFacade.findAll();
                    Receptionist found = null;
                    for(Receptionist r: allreceptionists){
                        if(uname.equals(r.getUname()) && pwd.equals(r.getPwd())){
                            found = r;
                            break;
                        }
                    }
                    
                    if(found == null){
                        throw new Exception();      
                    }
                    
                    if (found.isApproved()) {
                        s.setAttribute("user", found);
                        s.setAttribute("uname", found.getUname());
                        s.setAttribute("userType", userType);
                        request.getRequestDispatcher("receptionist/home.jsp").include(request, response);
                        out.println("<br>Welcome back, " + found.getUname() + "!");
                    } else {
                        s.invalidate();
                        request.getRequestDispatcher("login.jsp").include(request, response);
                        out.println("<br><br><br><p style=\"text-align:center\">Registration is still pending for approval from managing staffs!</p>");
                    }  
                }
            } catch(Exception e){
                s.invalidate();
                request.getRequestDispatcher("login.jsp").include(request, response);
                out.println("<br><br><br><p style=\"text-align:center\">Invalid credentials!</p>");
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
