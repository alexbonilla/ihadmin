/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.ihportal.web.controladores;

import com.iveloper.db.Conexion;
import com.iveloper.entidades.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
@WebServlet(name = "ClienteCtrl", urlPatterns = {"/ClienteCtrl"})
public class ClienteCtrl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        PrintWriter out = response.getWriter();
        String op = request.getParameter("op");
        if (op.equalsIgnoreCase("obtenerClientes")) {
            System.out.println("Entró a obtenerClientes");
            response.setContentType("text/plain;charset=UTF-8"); // Esto es para poder escribir la respuesta en el iframe
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            obtenerClientesJSON(request, response);
        } else if (op.equalsIgnoreCase("obtenerNombresClientes")) {
            System.out.println("Entró a obtenerNombresClientes");
            response.setContentType("text/plain;charset=UTF-8"); // Esto es para poder escribir la respuesta en el iframe
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            obtenerNombresClientesJSON(request, response);
        } else if (op.equalsIgnoreCase("guardarNuevoCliente")) {
            System.out.println("Entró a guardarNuevoCliente");
            response.setContentType("text/plain;charset=UTF-8"); // Esto es para poder escribir la respuesta en el iframe
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            guardarNuevoCliente(request, response);
        } else if (op.equalsIgnoreCase("obtenerCliente")) {
            obtenerCliente(request, response);
        }
        out.close();
    }

    public void obtenerClientesJSON(HttpServletRequest request, HttpServletResponse response) {
        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        ArrayList clientes = new ArrayList();
        PrintWriter pw = null;

        try {
            pw = response.getWriter();

            c.conectar();
            clientes = c.consultarClientes();

            pw.println(toJSON(clientes));

        } catch (Exception e) {
            System.out.println("" + e);
        }

        c.desconectar();

    }

    public void obtenerNombresClientesJSON(HttpServletRequest request, HttpServletResponse response) {
        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        ArrayList clientes = new ArrayList();
        PrintWriter pw = null;

        try {
            pw = response.getWriter();

            c.conectar();
            clientes = c.consultarClientes();

            pw.println(toJSONNombres(clientes));

        } catch (Exception e) {
            System.out.println("" + e);
        }

        c.desconectar();

    }

    public void obtenerCliente(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        String xml = "";
        Cliente cliente = null;
        PrintWriter pw = null;
        String identificacion = request.getParameter("identificacion") == null ? null : request.getParameter("identificacion");
        if (identificacion != null) {
            try {
                pw = response.getWriter();

                c.conectar();
                cliente = c.consultarCliente(identificacion);

                xml = "<?xml version='1.0' encoding='UTF-8' ?>";
                xml += "<Clientes>";

                xml += "<cliente>";
                xml += "<identificacion>" + cliente.getIdentificacion() + "</identificacion>";
                xml += "<tipoidentificacion>" + cliente.getTipoIdentificacion() + "</tipoidentificacion>";
                xml += "<razonsocial>" + cliente.getRazonsocial() + "</razonsocial>";
                xml += "<tipocliente>" + cliente.getTipoCliente() + "</tipocliente>";
                xml += "<direccion>" + cliente.getDireccion() + "</direccion>";
                xml += "<telefonofijo>" + cliente.getTelefonoFijo() + "</telefonofijo>";
                xml += "<extensionfijo>" + cliente.getExtensionFijo() + "</extensionfijo>";
                xml += "<telefonomovil>" + cliente.getTelefonoMovil() + "</telefonomovil>";
                xml += "<email>" + cliente.getEmail() + "</email>";
                xml += "<credito>" + cliente.getCredito() + "</credito>";

                xml += "</cliente>";

                xml += "</Clientes>";
                pw.println(xml);

            } catch (Exception e) {
                System.out.println("" + e);
            }
        }

        c.desconectar();

    }

    public static String toJSON(List elementos) {

        StringBuilder strBld = new StringBuilder();

        Iterator it = elementos.iterator();
        strBld.append("[");
        do {
            if (!it.hasNext()) {
                break;
            }
            Cliente cliente = (Cliente) it.next();

            if (cliente != null) {
                strBld.append("[\"");
                strBld.append(cliente.getIdentificacion());
                strBld.append("\", \"");
                strBld.append(cliente.getIdentificacion());
                strBld.append("\"]");
            }
            if (it.hasNext()) {
                strBld.append(",");
            }

        } while (true);
        strBld.append("]");

        return strBld.toString();
    }

    public static String toJSONNombres(List elementos) {

        StringBuilder strBld = new StringBuilder();

        Iterator it = elementos.iterator();
        strBld.append("[");
        do {
            if (!it.hasNext()) {
                break;
            }
            Cliente cliente = (Cliente) it.next();

            if (cliente != null) {
                strBld.append("[\"");
                strBld.append(cliente.getIdentificacion());
                strBld.append("\", \"");
                strBld.append(cliente.getRazonsocial());
                strBld.append("\"]");
            }
            if (it.hasNext()) {
                strBld.append(",");
            }

        } while (true);
        strBld.append("]");

        return strBld.toString();
    }

    public void guardarNuevoCliente(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        String xml = "";
        String identificacion = request.getParameter("identificacion") == null ? null : request.getParameter("identificacion");
        String tipoidentificacion = request.getParameter("tipoidentificacion") == null ? null : request.getParameter("tipoidentificacion");
        String razonsocial = request.getParameter("razonsocial") == null ? null : request.getParameter("razonsocial");
        String direccion = request.getParameter("direccion");
        String telefonofijo = request.getParameter("telefonofijo");
        String telefonomovil = request.getParameter("telefonomovil");
        String email = request.getParameter("email");

        if (identificacion != null && tipoidentificacion != null && razonsocial != null) {
            Cliente cliente = new Cliente(razonsocial, tipoidentificacion, identificacion, null, direccion, telefonofijo, null, telefonomovil, email, null);

            PrintWriter pw = null;
            try {
                pw = response.getWriter();

                c.conectar();
                int result = c.guardarCliente(cliente);
                if (result != 0) {
                    System.out.println("Se guardó cliente " + cliente.getIdentificacion());
                } else {
                    System.out.println("NO se guardó cliente " + cliente.getIdentificacion());
                }
                obtenerClientesJSON(request, response);

            } catch (Exception e) {
                System.out.println("" + e);
            } finally {
                c.desconectar();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
