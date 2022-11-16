package com.emergente.controlador;

import com.emergente.modelo.Producto;
import com.emergente.utiles.ConexionDB;
import com.mysql.cj.PreparedQuery;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String op;
            op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";

            ArrayList<Producto> lista = new ArrayList<Producto>();
            ConexionDB canal = new ConexionDB();

            Connection conn = canal.conectar();
            PreparedStatement ps;
            ResultSet rs;

            if (op.equals("list")) {

                //obtener la lista de registros
                String sql = "select * from productos";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Producto lib = new Producto();
                    lib.setId(rs.getInt("id"));
                    lib.setProducto(rs.getString("producto"));
                    lib.setPrecio(rs.getFloat("precio"));
                    lib.setCantidad(rs.getInt("cantidad"));

                    lista.add(lib);

                }
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("index.jsp").forward(request, response);

            }
            if (op.equals("nuevo")) {
                //nuevo registro

                Producto li = new Producto();

                request.setAttribute("lib", li);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            }
            if (op.equals("editar")) {

                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "select * from productos where id=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                Producto li = new Producto();
                while (rs.next()) {

                    li.setId(rs.getInt("id"));
                    li.setProducto(rs.getString("producto"));
                    li.setPrecio(rs.getFloat("precio"));
                    li.setCantidad(rs.getInt("cantidad"));
                }

                request.setAttribute("lib", li);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            }
            if (op.equals("eliminar")) {
                //eliminar

                int id = Integer.parseInt(request.getParameter("id"));

                String sql = "delete from productos where id=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                response.sendRedirect("MainController");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            int id = Integer.parseInt(request.getParameter("id"));
            String producto = request.getParameter("producto");
            float precio = Float.parseFloat(request.getParameter("precio")) ;
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));

            Producto lib = new Producto();

            lib.setId(id);
            lib.setProducto(producto);
            lib.setPrecio(precio);
            lib.setCantidad(cantidad);

            ConexionDB canal = new ConexionDB();

            Connection conn = canal.conectar();

            PreparedStatement ps;

            if (id == 0) {
                //nuevo registro
                String sql = "insert into productos (producto,precio,cantidad) values(?,?,?)";

                ps = conn.prepareStatement(sql);
                ps.setString(1, lib.getProducto());
                ps.setFloat(2, lib.getPrecio());
                ps.setInt(3, lib.getCantidad());
                ps.executeUpdate();
                response.sendRedirect("MainController");
            } else {
                String sql = "UPDATE productos set producto=?,precio=?,cantidad=? where id=?";
                ps = conn.prepareStatement(sql);

                ps.setString(1, lib.getProducto());
                ps.setFloat(2, lib.getPrecio());
                ps.setInt(3, lib.getCantidad());
                ps.setInt(4, lib.getId());
                ps.executeUpdate();
                response.sendRedirect("MainController");
            }

        } catch (SQLException e) {
            System.out.println("Error en SQL" + e.getMessage());
        }

    }

}
