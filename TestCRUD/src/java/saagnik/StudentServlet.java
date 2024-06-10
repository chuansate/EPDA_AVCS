/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saagnik;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "StudentServlet", urlPatterns = {"/StudentServlet"})
public class StudentServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>"); 
            out.println("<html>"); 
            out.println("<head>"); 
            out.println("<title>Servlet StudentServlet</title>"); 
            out.println("</head>"); 
            out.println("<body>"); 

            // List to hold Student objects 
            ArrayList<Student> std = new ArrayList<>(); 

            // Adding members to the list. Here we are  
            // using the parameterized constructor of  
            // class "Student.java" 
            std.add(new Student("Roxy Willard", 22, "B.D.S")); 
            std.add(new Student("Todd Lanz", 22, "B.Tech")); 
            std.add(new Student("Varlene Lade", 21, "B.B.A")); 
            std.add(new Student("Julio Fairley", 22, "B.Tech")); 
            std.add(new Student("Helena Carlow", 24, "M.B.B.S")); 
  
            // Setting the attribute of the request object 
            // which will be later fetched by a JSP page 
            request.setAttribute("data", std); 

            // Creating a RequestDispatcher object to dispatch 
            // the request the request to another resource 
            RequestDispatcher rd =  
                  request.getRequestDispatcher("stdlist.jsp"); 

            // The request will be forwarded to the resource  
            // specified, here the resource is a JSP named, 
            // "stdlist.jsp" 
            rd.forward(request, response); 
            out.println("</body>"); 
            out.println("</html>"); 
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
