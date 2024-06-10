<%-- 
    Document   : manage_vet_info
    Created on : Feb 16, 2024, 6:08:02 PM
    Author     : lowsi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Vet Info Page</title>
    </head>
    <body>
        <a href="Login">Return Home Page</a>
        <p>From here, you can search for a vet, update a vet's information, or delete a vet's account.</p>
        <form method='POST' action="Search_vet">
            <p>Enter vet's username to search: <input type="text" name='vet_uname' size='20'> <input type="submit" value="Search"></p>
        </form>
        
    </body>
</html>
