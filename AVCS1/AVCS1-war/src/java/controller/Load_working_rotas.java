/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Vet;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import JSP_utils.DropDownMenus;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import model.VetFacade;
import model.WorkingRota;
import model.WorkingRotaFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Load_working_rotas", urlPatterns = {"/Load_working_rotas"})
public class Load_working_rotas extends HttpServlet {

    @EJB
    private WorkingRotaFacade workingRotaFacade;

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
 
        List<Vet> allVets = vetFacade.findAll();
        request.getRequestDispatcher("managing_staff/create_working_rota.jsp").include(request, response);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<WorkingRota> allWorkingRotas = workingRotaFacade.findAll();
        
        try (PrintWriter out = response.getWriter()) {
            // for generating working rota with dropdown list of available vets
            // allowing staffs to create working rotas for vets
            out.println("<form action=\"Create_working_rota\" action='POST'>\n" +
"            <p>Enter the starting date of the working rota:</p>\n" +
"            <table>\n" +
"                <tr><td> Year: </td> <td> <input type=\"text\" name=\"year_rota\" size=\"5\"></td></tr>\n" +
"                <tr><td> Month: </td> <td><input type=\"text\" name=\"month_rota\" size=\"5\"> </td></tr>\n" +
"                <tr><td> Day: </td> <td> <input type=\"text\" name=\"day_rota\" size=\"5\"></td></tr>\n" +
"            </table>");
            out.println("<table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\"> \n");
            out.println("<tr><th>Mon</th> <th>Tue</th> <th>Wed</th> <th>Thu</th> <th>Fri</th> <th>Sat</th> <th>Sun</th></tr>\n");
            
            for (int row_index=0; row_index < 3; row_index++) {
                out.println("<tr>");
                for (int col_index=0; col_index<7; col_index++) {
                    out.println("<td>");
                    DropDownMenus d = new DropDownMenus();
                    out.println(d.vetsDropDown_JSP(row_index, col_index, allVets));
                    out.println("</td>");
                }
                out.println("</tr>");
            }
            out.println("<tr><td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td align =\"right\"><input type=\"submit\" value=\"Create\"></td><tr>");
            out.println("</table>");
            out.println("</form>");
            out.println("<br><br><br>");
            
            // generate a list of existing working rotas 
            if (allWorkingRotas != null) {
                Collections.sort(allWorkingRotas, (new RotaSortingComparator()).reversed());
                for (WorkingRota r: allWorkingRotas) {
                    out.println("<br><br><table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\">");
                    out.println("<tr><th>Mon ("+ r.getStartingDate().format(formatter) +") </th> <th>Tue ("+ r.getStartingDate().plusDays(1).format(formatter) +") </th> <th>Wed ("+ r.getStartingDate().plusDays(2).format(formatter) +") </th> <th>Thu ("+ r.getStartingDate().plusDays(3).format(formatter) +") </th> <th>Fri ("+ r.getStartingDate().plusDays(4).format(formatter) +") </th> <th>Sat ("+ r.getStartingDate().plusDays(5).format(formatter) +")</th> <th>Sun ("+ r.getStartingDate().plusDays(6).format(formatter) +") </th></tr>");
                    out.println("<tr><td>"+ r.getMondayVet1() +"</td><td>"+ r.getTuesdayVet1() +"</td><td>"+ r.getWednesdayVet1() +"</td><td>"+ r.getThursdayVet1() +"</td><td>"+ r.getFridayVet1()+"</td><td>"+ r.getSaturdayVet1() +"</td><td>"+ r.getSundayVet1() +"</td></tr>");
                    out.println("<tr><td>"+ r.getMondayVet2() +"</td><td>"+ r.getTuesdayVet2() +"</td><td>"+ r.getWednesdayVet2() +"</td><td>"+ r.getThursdayVet2() +"</td><td>"+ r.getFridayVet2()+"</td><td>"+ r.getSaturdayVet2() +"</td><td>"+ r.getSundayVet2() +"</td></tr>");
                    out.println("<tr><td>"+ r.getMondayVet3() +"</td><td>"+ r.getTuesdayVet3() +"</td><td>"+ r.getWednesdayVet3() +"</td><td>"+ r.getThursdayVet3() +"</td><td>"+ r.getFridayVet3()+"</td><td>"+ r.getSaturdayVet3() +"</td><td>"+ r.getSundayVet3() +"</td></tr>");
                    out.println("</table>");
                }
            } else {
                out.println("There is no working rota so far! Start creating some!");
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
