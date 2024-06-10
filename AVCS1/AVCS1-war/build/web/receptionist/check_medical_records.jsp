<%-- 
    Document   : check_medical_records
    Created on : Mar 3, 2024, 9:31:27 PM
    Author     : lowsi
--%>

<%@page import="JSP_utils.DropDownMenus"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check Medical Record Page</title>
    </head>
    <body>
        <a href='Login'>Return Home Page</a>
        <form action='Retrieve_medical_records' method='POST'>
            <p>Enter the information of the customer, the pet and its species:</p>
            <table>
                <tr><td>Customer username: </td> <td><input type="text" name="cust_uname" size="20"></td></tr>
                <tr><td>Pet name:</td> <td><input type="text" name="pet_name" size="15"></td></tr>
                <tr><td>Species:</td> <td><%= new DropDownMenus().expertisesDropDown_JSP("species", "species") %></td></tr>
            </table>
            <p><input type='submit' value='Search'></p>
        </form>
    </body>
</html>
