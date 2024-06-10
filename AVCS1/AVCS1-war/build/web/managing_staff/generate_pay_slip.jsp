<%-- 
    Document   : generate_pay_slip
    Created on : Mar 3, 2024, 12:20:10 PM
    Author     : lowsi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Generate Pay Slip Page</title>
    </head>
    <body>
        <a href="Login">Return Home Page</a>
        <p>As a staff, you can generate pay slip for vets based on EPF, customer rating, number of appointment completed.</p>
        <p>A vet's salary will be deducted by RM 5 for every hour the vet is late to work.</p>
        <form method='POST' action="Display_vet_performance">
            <p>Enter the vet's username: <input type="text" name='vet_uname' size='20'> </p>
            <p>Enter the month of pay slip (all uppercase letters): <input type="text" name='month_pay_slip' size='20'> </p>
            <p><input type="submit" value="Search"></p>
        </form>
        <%-- A template for staffs to edit the payslip
        <table border="1" width="80%" style="margin-left: auto; margin-right: auto;">
            <tr> <th colspan="2"> EARNINGS </th> <th colspan="2"> DEDUCTIONS </th> </tr>
            <tr> <td>DESCRIPTION</td> <td>AMOUNT (RM)</td> <td>DESCRIPTION</td> <td>AMOUNT (RM)</td></tr>
            <tr> <td>Basic Salary</td> <td></td> <td>Employee EPF</td> <td><input type="text" name='employee_epf_rate' size='5'> %</td></tr>
            <tr> <td>Incentives</td> <td><input type="text" name='incentives_rate' size='5'>%</td> <td>Late for</td> <td><input type="text" name='late_hours' size='5'>hours</td></tr>
        </table>
        --%>
        
        
    </body>
</html>
