<%-- 
    Document   : manage_staff_info
    Created on : Jan 31, 2024, 8:38:32 PM
    Author     : lowsi
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <a href="Login">Return Home Page</a> <br>
        <title>Manage Staff's Info Page</title>
    </head>
    <body>
        <h3>Manage the staff's information here:</h3> 
        <form method="POST" action="Add_staff_info">
            <h3>Add new staff</h3>
            <p>The username and password cannot exceed 8 characters respectively, it is either M or F for the gender.</p>
            <table>
                <tr>
                    <td>Username:</td>
                    <td><input type="text" name="uname" size="20"></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="text" name="pwd" size="20"></td>
                </tr>
                <tr>
                    <td>Gender:</td>
                    <td><input type="text" name="gender" size="20"></td>
                </tr>
            </table>
            <p><input type="submit" value="Add"></p>
        </form>
	<form method="POST" action="Search_staff_info">
            <h3>Search for staff(s)</h3>
            <table>
                <tr>
                    <td>Username:</td>
                    <td><input type="text" name="uname" size="20"></td>
                </tr>
            </table>
            <p><input type="submit" value="Search"></p>
        </form>
        
    </body>
</html>
