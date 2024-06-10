/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.LeaveApplication;
import model.LeaveApplicationFacade;
import model.Vet;
import model.WorkingRota;
import model.WorkingRotaFacade;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Apply_leave_vet", urlPatterns = {"/Apply_leave_vet"})
public class Apply_leave_vet extends HttpServlet {

    @EJB
    private WorkingRotaFacade workingRotaFacade;

    @EJB
    private LeaveApplicationFacade leaveApplicationFacade;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String reason = request.getParameter("reason");
        
        // the leaveDate is in the format of DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //search how to convert it back!
        String leaveDate = request.getParameter("leaveDate");
        int yyyy; int MM; int dd; LocalDateTime leaveDateObj;
        HttpSession s = request.getSession(false);
        Vet vetUsingSystem = (Vet)s.getAttribute("user");
        List<WorkingRota> rotas = workingRotaFacade.findAll();
        Collections.sort(rotas, (new RotaSortingComparator()).reversed());
        List<LeaveApplication> leaveApplicationsByVet = leaveApplicationFacade.searchLeaveApplicationsByVet(vetUsingSystem.getUname());        
        try (PrintWriter out = response.getWriter()) {
            try {
                String[] yyyy_MM_dd = leaveDate.split("-");
                yyyy = Integer.parseInt(yyyy_MM_dd[0]);
                MM = Integer.parseInt(yyyy_MM_dd[1]);
                dd = Integer.parseInt(yyyy_MM_dd[2]);
                leaveDateObj = LocalDateTime.of(yyyy, MM, dd, 0, 0, 0, 0);
            } catch (Exception ex) {
                request.getRequestDispatcher("vet/create_leave_applications_vet.jsp").include(request, response);
                out.println("Failed to convert the leave date from string to LocalDateTime object!");
                return;
            }
            if (!reason.equals("")) {
                
                // check if the leaving application exists:
                // if exists, then update the reason and status
                // else create a new record
                boolean leaveApplicationFound = false;
                if (leaveApplicationsByVet != null) {
                    for (LeaveApplication la: leaveApplicationsByVet) {
                        if (la.getLeaveDate().getYear() == yyyy &&  la.getLeaveDate().getMonthValue() == MM && la.getLeaveDate().getDayOfMonth() == dd) {
                            leaveApplicationFound = true;
                            la.setReason(reason);
                            la.setStatus("Pending");
                            leaveApplicationFacade.edit(la);
                            break;
                        }
                    }
                } else {
                    leaveApplicationFacade.create(new LeaveApplication(leaveDateObj, vetUsingSystem.getUname(), "vet", "Pending", reason));
                }
                
                
                if (!leaveApplicationFound && leaveApplicationsByVet != null) {
                    leaveApplicationFacade.create(new LeaveApplication(leaveDateObj, vetUsingSystem.getUname(), "vet", "Pending", reason));
                }
                
                request.getRequestDispatcher("vet/create_leave_applications_vet.jsp").include(request, response);
                out.println("The leave has been applied successfully!");
                //rendering the list of working days by the vet, allowing the vet to apply for leave
                for (WorkingRota r: rotas) {
                    String[][] vetNamesInWorkingRota = {
                        {r.getMondayVet1(), r.getTuesdayVet1(), r.getWednesdayVet1(), r.getThursdayVet1(), r.getFridayVet1(), r.getSaturdayVet1(), r.getSundayVet1()},
                        {r.getMondayVet2(), r.getTuesdayVet2(), r.getWednesdayVet2(), r.getThursdayVet2(), r.getFridayVet2(), r.getSaturdayVet2(), r.getSundayVet2()},
                        {r.getMondayVet3(), r.getTuesdayVet3(), r.getWednesdayVet3(), r.getThursdayVet3(), r.getFridayVet3(), r.getSaturdayVet3(), r.getSundayVet3()}
                    };

                    out.println("<br><h3>Week " + r.getStartingDate().format(formatter) + "</h3>");
                    out.println("<br><br><table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\">");
                    //out.println("<tr><th>Mon ("+ r.getStartingDate().format(formatter) +") </th> <th>Tue ("+ r.getStartingDate().plusDays(1).format(formatter) +") </th> <th>Wed ("+ r.getStartingDate().plusDays(2).format(formatter) +") </th> <th>Thu ("+ r.getStartingDate().plusDays(3).format(formatter) +") </th> <th>Fri ("+ r.getStartingDate().plusDays(4).format(formatter) +") </th> <th>Sat ("+ r.getStartingDate().plusDays(5).format(formatter) +")</th> <th>Sun ("+ r.getStartingDate().plusDays(6).format(formatter) +") </th></tr>");
                    out.println("<tr><th>Duty Information</th> <th>Reason</th> <th>Status</th></tr>");

                    for (String[] vetSameRow: vetNamesInWorkingRota) {
                        int indexDayOfWeek = 0;
                        for (String vetOnDuty: vetSameRow) {
                            if (vetOnDuty.equals(vetUsingSystem.getUname())) {
                                out.println("<form method=\"POST\" action=\"Apply_leave_vet\"><tr>");
                                out.println("<td><p>Vet on duty: "+ vetOnDuty +"</p><p>Duty Date: <input type=\"text\"  name=\"leaveDate\" value=\""+ r.getStartingDate().plusDays(indexDayOfWeek).format(formatter) +"\" readonly></p></td>");
                                
                                // Loop thru the table `LeaveApplications` to find the leave application
                                // the table will update the reason and status if vet applies again after staffs rejected
                                // instead of creating another new record
                                List<LeaveApplication> updatedLeaveApplicationsByVet = leaveApplicationFacade.searchLeaveApplicationsByVet(vetUsingSystem.getUname());
                                if (updatedLeaveApplicationsByVet == null) {
                                    out.println("<td>Ready to apply</td>");
                                } else {
                                    LeaveApplication laFound = null;    
                                    LocalDateTime dateTime = r.getStartingDate().plusDays(indexDayOfWeek);

                                    for (LeaveApplication la: updatedLeaveApplicationsByVet) {
                                        if (la.getLeaveDate().getYear() == dateTime.getYear() &&  la.getLeaveDate().getMonth() == dateTime.getMonth() && la.getLeaveDate().getDayOfMonth() == dateTime.getDayOfMonth()) {
                                            if (la.getStatus().equals("Approved")) {
                                                out.println("<td>Enjoy your holiday!</td>");
                                            } else {
                                                // if it is "Pending" or "Rejected", you can enter a reason to apply for leave
                                                out.println("<td><p>Enter reason to apply for leave:</p> <p><input type=\"text\" name=\"reason\" size=\"20\"></p> <p><input type=\"submit\" value=\"Apply\"></p></td>");
                                            }
                                            out.println("<td>"+ la.getStatus() +"</td>");
                                            laFound = la;
                                            break;
                                        }
                                    }
                                    // if not found, means it it has never been applied before
                                    if (laFound == null) {
                                        out.println("<td><p>Enter reason to apply for leave:</p> <p><input type=\"text\" name=\"reason\" size=\"20\"></p> <p><input type=\"submit\" value=\"Apply\"></p></td>");
                                        out.println("<td>Ready to apply</td>");
                                    }
                                }
                                
                                out.println("</tr></form>");
                            }
                            indexDayOfWeek++;
                        }
                        
                    }           

                    out.println("</table>");
                }
            } else {
                request.getRequestDispatcher("vet/create_leave_applications_vet.jsp").include(request, response);
                out.println("The reason cannot be empty, write a reason before applying for leave!");
                //rendering the list of working days by the vet, allowing the vet to apply for leave
                for (WorkingRota r: rotas) {
                    String[][] vetNamesInWorkingRota = {
                        {r.getMondayVet1(), r.getTuesdayVet1(), r.getWednesdayVet1(), r.getThursdayVet1(), r.getFridayVet1(), r.getSaturdayVet1(), r.getSundayVet1()},
                        {r.getMondayVet2(), r.getTuesdayVet2(), r.getWednesdayVet2(), r.getThursdayVet2(), r.getFridayVet2(), r.getSaturdayVet2(), r.getSundayVet2()},
                        {r.getMondayVet3(), r.getTuesdayVet3(), r.getWednesdayVet3(), r.getThursdayVet3(), r.getFridayVet3(), r.getSaturdayVet3(), r.getSundayVet3()}
                    };

                    out.println("<br><h3>Week " + r.getStartingDate().format(formatter) + "</h3>");
                    out.println("<br><br><table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\">");
                    //out.println("<tr><th>Mon ("+ r.getStartingDate().format(formatter) +") </th> <th>Tue ("+ r.getStartingDate().plusDays(1).format(formatter) +") </th> <th>Wed ("+ r.getStartingDate().plusDays(2).format(formatter) +") </th> <th>Thu ("+ r.getStartingDate().plusDays(3).format(formatter) +") </th> <th>Fri ("+ r.getStartingDate().plusDays(4).format(formatter) +") </th> <th>Sat ("+ r.getStartingDate().plusDays(5).format(formatter) +")</th> <th>Sun ("+ r.getStartingDate().plusDays(6).format(formatter) +") </th></tr>");
                    out.println("<tr><th>Duty Information</th> <th>Reason</th> <th>Status</th></tr>");

                    for (String[] vetSameRow: vetNamesInWorkingRota) {
                        int indexDayOfWeek = 0;
                        for (String vetOnDuty: vetSameRow) {
                            if (vetOnDuty.equals(vetUsingSystem.getUname())) {
                                out.println("<form method=\"POST\" action=\"Apply_leave_vet\"><tr>");
                                out.println("<td><p>Vet on duty: "+ vetOnDuty +"</p><p>Duty Date: <input type=\"text\"  name=\"leaveDate\" value=\""+ r.getStartingDate().plusDays(indexDayOfWeek).format(formatter) +"\" readonly></p></td>");
                                
                                // Loop thru the table `LeaveApplications` to find the leave application
                                // the table will update the reason and status if vet applies again after staffs rejected
                                // instead of creating another new record
                                List<LeaveApplication> updatedLeaveApplicationsByVet = leaveApplicationFacade.searchLeaveApplicationsByVet(vetUsingSystem.getUname());
                                if (updatedLeaveApplicationsByVet == null) {
                                    out.println("<td>Ready to apply</td>");
                                } else {
                                    LeaveApplication laFound = null;    
                                    LocalDateTime dateTime = r.getStartingDate().plusDays(indexDayOfWeek);

                                    for (LeaveApplication la: updatedLeaveApplicationsByVet) {
                                        if (la.getLeaveDate().getYear() == dateTime.getYear() &&  la.getLeaveDate().getMonth() == dateTime.getMonth() && la.getLeaveDate().getDayOfMonth() == dateTime.getDayOfMonth()) {
                                            if (la.getStatus().equals("Approved")) {
                                                out.println("<td>Enjoy your holiday!</td>");
                                            } else {
                                                // if it is "Pending" or "Rejected", you can enter a reason to apply for leave
                                                out.println("<td><p>Enter reason to apply for leave:</p> <p><input type=\"text\" name=\"reason\" size=\"20\"></p> <p><input type=\"submit\" value=\"Apply\"></p></td>");
                                            }
                                            out.println("<td>"+ la.getStatus() +"</td>");
                                            laFound = la;
                                            break;
                                        }
                                    }
                                    // if not found, means it it has never been applied before
                                    if (laFound == null) {
                                        out.println("<td><p>Enter reason to apply for leave:</p> <p><input type=\"text\" name=\"reason\" size=\"20\"></p> <p><input type=\"submit\" value=\"Apply\"></p></td>");
                                        out.println("<td>Ready to apply</td>");
                                    }
                                }
                                
                                out.println("</tr></form>");
                            }
                            indexDayOfWeek++;
                        }
                        
                    }           

                    out.println("</table>");
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
