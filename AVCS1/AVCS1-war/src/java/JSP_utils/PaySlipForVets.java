/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSP_utils;

/**
 *
 * @author lowsi
 */
public class PaySlipForVets {
    public String editPaySlipTemplate (String vetBasicSalary){
        String str = "<form method='POST' action=\"Compute_gross_salary\"> <table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\">\n" +
"            <tr> <th colspan=\"2\"> EARNINGS </th> <th colspan=\"2\"> DEDUCTIONS </th> </tr>\n" +
"            <tr> <td>DESCRIPTION</td> <td>AMOUNT (RM)</td> <td>DESCRIPTION</td> <td>AMOUNT (RM)</td></tr>\n" +
"            <tr> <td>Basic Salary</td> <td>"+ vetBasicSalary +"</td> <td>Employee EPF</td> <td><input type=\"text\" name='employee_epf_rate' size='5'> %</td></tr>\n" +
"            <tr> <td>Incentives</td> <td><input type=\"text\" name='incentives_rate' size='5'>%</td> <td>Late for</td> <td><input type=\"text\" name='late_hours' size='5'>hours</td></tr>\n" +
"        </table> <br> <input type=\"submit\" value=\"Generate Pay Slip\"> </form>";
        
        return str;
    }
    
    public String finalPaySlip(String monthPaySlip, String vetBasicSalary, String employeeEPF_rate, String EPF_deductionAmount, String incentivesRate, String incentivesAmount, String late_hours, String lateHoursDeductionAmount, String grossEarnings, String grossDeductions){
        String str = " <table border=\"1\" width=\"80%\" style=\"margin-left: auto; margin-right: auto;\">\n" +
"            <tr> <th colspan=\"2\"> EARNINGS </th> <th colspan=\"2\"> DEDUCTIONS </th> </tr>\n" +
"            <tr> <td>DESCRIPTION</td> <td>AMOUNT (RM)</td> <td>DESCRIPTION</td> <td>AMOUNT (RM)</td></tr>\n" +
"            <tr> <td>Basic Salary</td> <td>"+ vetBasicSalary +"</td> <td>Employee EPF ("+ employeeEPF_rate +" %)</td> <td>"+ EPF_deductionAmount +"</td></tr>\n" +
"            <tr> <td>Incentives ("+ incentivesRate +" %)</td> <td>" + incentivesAmount + "</td> <td>Late for ("+ late_hours +" hours)</td> <td>"+ lateHoursDeductionAmount +"</td></tr>\n" +
"            <tr> <th> Gross Earnings </th> <th>"+ grossEarnings +"</th> <th> Gross Deductions </th> <th>"+ grossDeductions +"</th> </tr>\n" +
"            <tr> <th colspan=\"2\"> Nett Salary </th> <th colspan=\"2\"> "+ (Integer.parseInt(grossEarnings) - Integer.parseInt(grossDeductions)) +"</th>\n" +                
"        </table>  ";
        
        return str;
    }
}
