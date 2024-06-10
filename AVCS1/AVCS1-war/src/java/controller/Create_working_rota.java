/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSP_utils.DropDownMenus;
import model.Vet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Expertise;
import model.VetFacade;
import model.WorkingRota;
import model.WorkingRotaFacade;
import java.time.*;
import java.time.format.*;
import java.util.Collections;
/**
 *
 * @author lowsi
 */



@WebServlet(name = "Create_working_rota", urlPatterns = {"/Create_working_rota"})
public class Create_working_rota extends HttpServlet {

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
        String year_rotaStr = request.getParameter("year_rota");
        String month_rotaStr = request.getParameter("month_rota");
        String day_rotaStr = request.getParameter("day_rota");
        int year_rota;
        int month_rota;
        int day_rota;
        boolean invalidDate = false;
        boolean dateNotMonday = false;
        boolean duplicated = false;
        boolean min_expertise_not_reached = false;
        boolean duplicateStartingDateRota = false;
        LocalDateTime startingDateRota = null;
        try {
            year_rota = Integer.parseInt(year_rotaStr);
            month_rota = Integer.parseInt(month_rotaStr);
            day_rota = Integer.parseInt(day_rotaStr);
            startingDateRota = LocalDateTime.of(year_rota, month_rota, day_rota, 0, 0, 0, 0);
            if (!startingDateRota.getDayOfWeek().toString().equals("MONDAY")) {
                dateNotMonday = true;    
            }
            
            // to catch the duplicate monday date
            List<WorkingRota> allWorkingRotas = workingRotaFacade.findAll();
            for (WorkingRota rota: allWorkingRotas) {
                if (rota.getStartingDate().getYear() == startingDateRota.getYear() && rota.getStartingDate().getMonth() == startingDateRota.getMonth() && rota.getStartingDate().getDayOfMonth() == startingDateRota.getDayOfMonth()) {
                    duplicateStartingDateRota = true;
                    break;
                }
            }
        } catch (Exception e) {
            invalidDate = true;
        }
       
        
        
        String[][] rota = new String[3][7];
        // extract all the vets from the table, then put it into `rota` for further validation
        for (int row_index=0; row_index < 3; row_index++) {
            for (int col_index=0; col_index<7; col_index++) {
                String vet_name = request.getParameter(Integer.toString(row_index)+","+Integer.toString(col_index));
                rota[row_index][col_index] = vet_name;
            }
            
        }
        
        // for checking purposes (debugging)
//        for (int row_index=0; row_index < 3; row_index++) {
//            for (int col_index=0; col_index<7; col_index++) {
//                System.out.print(rota[row_index][col_index] + ",");
//            }
//            System.out.println("-");
//        }
        List<Vet> allVets = vetFacade.findAll();
        
        // to check if there is any duplicate vet on the same day
        // then check if the min expertise per day is not reached
        
        int col_num_error = 0; ///////////for notifying user about which day has nt reached the min expertises
        for (int col_index=0; col_index<7; col_index++) {
            int min_expertise_per_day = 0;
            if (duplicated || min_expertise_not_reached) {
                break;
            }
            String[] vets_on_same_day = new String[3];
            for (int row_index=0; row_index<3; row_index++) {
                String vet_name = rota[row_index][col_index];
                vets_on_same_day[row_index] = vet_name;
                for (Vet vet: allVets) {
                    if (vet.getUname().equals(vet_name)) {
                        //Note that once the registration of a vet is approved, his/her 2 expertises are initialized to -
                        for (Expertise exp: vet.getExpertises()) {
                            if (!exp.getExpertise_name().equals("-")) {
                                min_expertise_per_day = min_expertise_per_day + 1;
                            }
                        }
                        break;
                    }
                }
            }
            
            //check if the min expertise per day is not reached
            if (min_expertise_per_day < 5) {
                min_expertise_not_reached = true;
                col_num_error = col_index;
                break;
            }
            
            // to check if there is any duplicate vet on the same day
            for(int i = 0; i < vets_on_same_day.length; i++) {
                if (duplicated || min_expertise_not_reached) {
                    break;
                }
                for(int j = i + 1; j < vets_on_same_day.length; j++) {
                    // duplicate vet
                    if(vets_on_same_day[i].equals(vets_on_same_day[j])) {
                        duplicated = true;
                        col_num_error = col_index;
                        break;
                    } 
                }  
            }
            
            // to check if the min expertise per day is not reached
            
        }
        request.getRequestDispatcher("managing_staff/create_working_rota.jsp").include(request, response);
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
            
            
            if (invalidDate) {
                out.println("The starting date of the working rota is invalid, try again!");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                List<WorkingRota> allWorkingRotas = workingRotaFacade.findAll();
                
                // generate a list of updated working rotas after the staff has added a working rota
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
                return;
            }
            
            if (duplicateStartingDateRota) {
                out.println("The working rota for that week has been created before, try again!");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                List<WorkingRota> allWorkingRotas = workingRotaFacade.findAll();
                
                // generate a list of updated working rotas after the staff has added a working rota
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
                return;
                
            }
            
            if (dateNotMonday) {
                out.println("The starting date of the working rota is not on Monday, try again!");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                List<WorkingRota> allWorkingRotas = workingRotaFacade.findAll();
                
                // generate a list of updated working rotas after the staff has added a working rota
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
                return;
            }
            
            if (duplicated) {
                out.println("The vets are duplicated within the same day! The error happened at column " + col_num_error + "!");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                List<WorkingRota> allWorkingRotas = workingRotaFacade.findAll();
                
                // generate a list of updated working rotas after the staff has added a working rota
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
                return;
            } 
            
            if (min_expertise_not_reached) {
                out.println("The min number of expertise per day should be 5! The error happened at column " + col_num_error + "!");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                List<WorkingRota> allWorkingRotas = workingRotaFacade.findAll();
                
                // generate a list of updated working rotas after the staff has added a working rota
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
                return;
            }
            
            if (!duplicated && !min_expertise_not_reached) {
                // add a new working rota to the table workingRota
                
                WorkingRota newWorkingRota = new WorkingRota(startingDateRota, 
                        rota[0][0], rota[1][0], rota[2][0], 
                        rota[0][1], rota[1][1], rota[2][1], 
                        rota[0][2], rota[1][2], rota[2][2], 
                        rota[0][3], rota[1][3], rota[2][3],
                        rota[0][4], rota[1][4], rota[2][4],
                        rota[0][5], rota[1][5], rota[2][5],
                        rota[0][6], rota[1][6], rota[2][6]
                );
                workingRotaFacade.create(newWorkingRota);
                out.println("The working rota has been added successfully!");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                List<WorkingRota> allWorkingRotas = workingRotaFacade.findAll();
                
                // generate a list of updated working rotas after the staff has added a working rota
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
