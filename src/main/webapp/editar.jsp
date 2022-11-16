<%@page import="com.emergente.modelo.Producto"%>
<%
    Producto lib = (Producto) request.getAttribute("lib");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body bgcolor="EDDCFD" >
    <center>
        <h1><%= (lib.getId() == 0) ? "Nuevo Registro" : "Editar Registro "%></h1>

        <form action="MainController" method="post">

            <input type="hidden" name="id" value="${lib.id}">
            <table>
                <tr>
                    <td>Producto</td>
                    <td><input type="text" name="producto" value="${lib.producto}"></td>
                </tr>
                <tr>
                    <td>Precio</td>
                    <td><input type="text" name="precio" value="${lib.precio}"></td>
                </tr>
                <tr>
                    <td>Cantidad</td>
                    <td><input type="text" name="cantidad" value="${lib.cantidad}"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit"></td>
                </tr>
            </table>
        </form>
                </center>        
    </body>
</html>
