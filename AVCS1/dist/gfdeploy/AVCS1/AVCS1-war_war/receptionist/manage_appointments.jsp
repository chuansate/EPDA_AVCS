<%-- 
    Document   : manage_appointments
    Created on : Feb 13, 2024, 10:18:02 PM
    Author     : lowsi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="JSP_utils.DropDownMenus" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Appointments Page</title>
    </head>
    <body>
        <a href="Login">Return Home Page</a>
        <p>You can manage appointments here! You can create, edit, and cancel appointments here. For each appointment created, you can create a customer profile and a vet profile, then assign a vet to the appointment:</p>
        <p>Fill in this form when you are creating an appointment for a new customer and the customer's new pet:</p>
        <form method="POST" action="Create_appointment">
            <table>
                <tr>
                    <td>
                        <h3>Create appointment</h3>
                        <table>
                            <tr><td>Year (Date):</td><td><input type="text" name="year" size="20"></td></tr>
                            <tr><td>Month (Date):</td><td><input type="text" name="month" size="20"></td></tr>
                            <tr><td>Day (Date):</td><td><input type="text" name="day" size="20"></td></tr>
                            <tr><td>Hour (Time):</td><td><input type="text" name="hour" size="20"></td></tr>
                            <tr><td>Minute (Time):</td><td><input type="text" name="minute" size="20"></td></tr>
                            <tr><td>Assigned Vet:</td><td><input type="text" name="assigned_vet" size="20"></td></tr>
                        </table>
                    </td>
                    <td>
                        <h3>Create customer profile</h3>
                        <table>
                            <tr><td>Customer Username (<= 8 characters):</td><td><input type="text" name="cust_uname" size="20"></td></tr>
                            <tr><td>Gender (M or F):</td><td><input type="text" name="gender" size="20"></td></tr>
                            <tr><td>Email address:</td><td><input type="text" name="email_adr" size="20"></td></tr>
                            <tr><td>Contact Number:</td><td><input type="text" name="contact_num" size="20"></td></tr>
                            <tr><td>Nationality: </td><td><input type="text" name="nationality" size="20"></td></tr>
                            <tr><td>Age: </td><td><input type="text" name="age" size="20"></td></tr>
                        </table>
                    </td>
                    <td>
                        <h3>Create pet profile</h3>
                        <table>
                            <tr><td>Pet name (<= 8 characters):</td><td><input type="text" name="pet_name" size="20"></td></tr>
                            <tr><td>Species: </td> <td><%= new DropDownMenus().expertisesDropDown_JSP("species", "species") %></td></tr>
                        </table>            
                    </td>
                    
                </tr>
            </table>
            <p><input type="submit" value="Create"></p>
        </form>
        <br>
        <p>Fill in this form when you are creating an appointment for an existing customer profile and the customer's existing pet profile:</p>
        <form method="POST" action="Create_appointment_for_existing_cust_pet">
            <table>
                <tr>
                    <td>
                        <h3>Create appointment</h3>
                        <table>
                            <tr><td>Year (Date):</td><td><input type="text" name="year" size="20"></td></tr>
                            <tr><td>Month (Date):</td><td><input type="text" name="month" size="20"></td></tr>
                            <tr><td>Day (Date):</td><td><input type="text" name="day" size="20"></td></tr>
                            <tr><td>Hour (Time):</td><td><input type="text" name="hour" size="20"></td></tr>
                            <tr><td>Minute (Time):</td><td><input type="text" name="minute" size="20"></td></tr>
                            <tr><td>Assigned Vet:</td><td><input type="text" name="assigned_vet" size="20"></td></tr>
                        </table>
                    </td>
                    <td>
                        <h3>Search customer and pet profile</h3>
                        <table>
                            <tr><td>Customer username (<= 8 characters):</td><td><input type="text" name="existing_cust_name" size="20"></td></tr>
                            <tr><td>Pet name (<= 8 characters):</td><td><input type="text" name="existing_pet_name" size="20"></td></tr>
                            <tr><td>Species: </td> <td><%= new DropDownMenus().expertisesDropDown_JSP("existing_species", "existing_species") %></td></tr>
                        </table>            
                    </td>
                </tr>
            </table>
            <p><input type="submit" value="Create"></p>
        </form>
    </body>
</html>
