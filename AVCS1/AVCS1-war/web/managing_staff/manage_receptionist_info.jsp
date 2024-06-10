<%-- 
    Document   : manage_receptionist_info
    Created on : Feb 16, 2024, 10:10:13 PM
    Author     : lowsi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Receptionist Info Page</title>
    </head>
    <body>
        <a href="Login">Return Home Page</a>
        <p>From here, you can search for a receptionist, update a receptionist's information, or delete a receptionist's account.</p>
        <form method='POST' action="Search_receptionist">
            <p>Enter receptionist's username to search: <input type="text" name='receptionist_uname' size='20'> <input type="submit" value="Search"></p>
        </form>
    </body>
</html>
