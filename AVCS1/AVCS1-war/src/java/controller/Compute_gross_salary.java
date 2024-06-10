/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import JSP_utils.PaySlipForVets;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Vet;

/**
 *
 * @author lowsi
 */
@WebServlet(name = "Compute_gross_salary", urlPatterns = {"/Compute_gross_salary"})
public class Compute_gross_salary extends HttpServlet {

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
        Vet vetWhosePaySlipIsGenerated = (Vet)s.getAttribute("vetWhosePaySlipIsGenerated");
        String monthPaySlip = (String) s.getAttribute("month_pay_slip");
        String employeeEPF_rateStr = request.getParameter("employee_epf_rate");
        String incentives_rateStr = request.getParameter("incentives_rate");
        String late_hoursStr = request.getParameter("late_hours");
        int employeeEPF_rate;
        int incentivesRate;
        int lateHours;
        try (PrintWriter out = response.getWriter()) {
            try {
                employeeEPF_rate =  Integer.parseInt(employeeEPF_rateStr);
                incentivesRate =  Integer.parseInt(incentives_rateStr);
                lateHours =  Integer.parseInt(late_hoursStr);
                if (employeeEPF_rate <= 0 || employeeEPF_rate > 100) {
                    throw new Exception();
                }
                if (incentivesRate < 0) {
                    throw new Exception();
                }
                if (lateHours < 0) {
                    throw new Exception();
                }
                
                int incentivesAmount = vetWhosePaySlipIsGenerated.getBase_salary() * incentivesRate/100;
                int EPF_deductionAmount = vetWhosePaySlipIsGenerated.getBase_salary() * employeeEPF_rate/100;
                int lateHoursDeductionAmount = lateHours * 5;
                int grossEarnings = vetWhosePaySlipIsGenerated.getBase_salary() + incentivesAmount;
                int grossDeductions = EPF_deductionAmount + lateHoursDeductionAmount;
                int nettSalary = grossEarnings - grossDeductions;
                request.getRequestDispatcher("managing_staff/generate_pay_slip.jsp").include(request, response);
                out.println("<h3>Pay Slip for vet, "+vetWhosePaySlipIsGenerated.getUname() + ", on " + monthPaySlip + ":</h3>");
                out.println(new PaySlipForVets().finalPaySlip(
                        monthPaySlip, 
                        String.valueOf(vetWhosePaySlipIsGenerated.getBase_salary()), 
                        employeeEPF_rateStr, 
                        String.valueOf(EPF_deductionAmount), 
                        incentives_rateStr, 
                        String.valueOf(incentivesAmount), 
                        late_hoursStr, 
                        String.valueOf(lateHoursDeductionAmount), 
                        String.valueOf(grossEarnings), 
                        String.valueOf(grossDeductions)
                    )
                );
                
            } catch (Exception ex){                
                request.getRequestDispatcher("managing_staff/generate_pay_slip.jsp").include(request, response);
                out.println("Invalid inputs while generating payslip for the vet, try again!");
                out.println(new PaySlipForVets().editPaySlipTemplate(String.valueOf(vetWhosePaySlipIsGenerated.getBase_salary())));
                
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
