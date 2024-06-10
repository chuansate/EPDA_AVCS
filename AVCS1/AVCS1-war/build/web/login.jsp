<%-- 
    Document   : login
    Created on : Jan 30, 2024, 9:14:16 PM
    Author     : lowsi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <div style="text-align: center; margin-top: 50px;">
            <h1>APU Veterinary Clinic System</h1>
            <br> <br> <br>
            <form action="Login" method="POST">
                <label for="userType">Select User Type:</label>
                <select id="userType" name="userType">
                    <option value="managingStaff">Managing Staff</option>
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
                <p><input type="submit" value="Login"></p>
            </form>
            <a href="register.jsp">New User Registration</a>
        </div>
    </body>
</html>
