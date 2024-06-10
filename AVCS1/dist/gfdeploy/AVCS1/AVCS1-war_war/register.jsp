<%-- 
    Document   : register
    Created on : Jan 30, 2024, 10:41:20 PM
    Author     : lowsi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Page</title>
    </head>
    <body>
        <div style="text-align: center; margin-top: 50px;">
            <h1>Registration Page</h1>
            <br> <br> <br>
            <h3>If you are registering accounts for vets and receptionists, you have to wait for approvals from managing staffs.</h3>
            <form action="Register_vets_receptionists" method="POST">
                <label for="userType">Select the account type you would like to register:</label>
                <select id="userType" name="userType">
                    <option value="vet">Vet</option>
                    <option value="receptionist">Receptionist</option>
                </select>
                <table style="margin-left: auto; margin-right: auto;">
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="uname" size="20"></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="text" name="pwd" size="20"></td>
                    </tr>
                </table>
                <p><input type="submit" value="Register"></p>
            </form>
            <a href="login.jsp">Return Login Page</a>
        </div>
    </body>
</html>
