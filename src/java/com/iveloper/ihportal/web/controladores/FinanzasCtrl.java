/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.ihportal.web.controladores;

import com.iveloper.db.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
@WebServlet(name = "FinanzasCtrl", urlPatterns = {"/FinanzasCtrl"})
public class FinanzasCtrl extends HttpServlet {

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
        if (op.equalsIgnoreCase("consultarIngresos")) {
            System.out.println("Entró a consultarIngresos");
            consultarIngresos(request, response);
        } else if (op.equalsIgnoreCase("guardarNuevoServicio")) {
//            System.out.println("Entró a guardarNuevoServicio");
//            guardarNuevoServicio(request, response);
        } else if (op.equalsIgnoreCase("obtenerNumSerie")) {
//            System.out.println("Entró a obtenerNumSerie");
//            obtenerNumSerie(request, response);
        } else if (op.equalsIgnoreCase("obtenerServicio")) {
//            obtenerServicio(request, response);
        }

        out.close();
    }

    public void consultarIngresos(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");          
        Conexion c = new Conexion(path);
        String xml = "";
        PrintWriter pw = null;
        String tipo = request.getParameter("tipo") == null || request.getParameter("tipo").equals("") || request.getParameter("tipo").equals("null") ? null : request.getParameter("tipo");
        String descripcion = request.getParameter("descripcion") == null || request.getParameter("descripcion").equals("") || request.getParameter("descripcion").equals("null") ? null : request.getParameter("descripcion");
        String usuario = request.getParameter("usuario") == null || request.getParameter("usuario").equals("") || request.getParameter("usuario").equals("null") ? null : request.getParameter("usuario");
        String idcliente = request.getParameter("idcliente") == null || request.getParameter("idcliente").equals("") || request.getParameter("idcliente").equals("null") ? null : request.getParameter("idcliente");
        String fechaInicioStr = request.getParameter("fechaInicio") == null || request.getParameter("fechaInicio").equals("") ? null : request.getParameter("fechaInicio");
        String fechaFinalStr = request.getParameter("fechaFinal") == null || request.getParameter("fechaFinal").equals("") ? null : request.getParameter("fechaFinal");
        String canceladoStr = request.getParameter("cancelado") == null || request.getParameter("cancelado").equals("") ? null : request.getParameter("cancelado");

        System.out.println("usuario: " + usuario);
        System.out.println("idcliente: " + idcliente);
        System.out.println("fechaInicioStr: " + fechaInicioStr);
        System.out.println("fechaFinalStr: " + fechaFinalStr);
        System.out.println("canceladoStr: " + canceladoStr);

        java.util.Date fechaInicio = null;
        java.util.Date fechaFinal = null;
        int cancelado = canceladoStr == null ? -1 : Integer.parseInt(canceladoStr);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        try {
            fechaInicio = fechaInicioStr == null ? null : df.parse(fechaInicioStr);
            fechaFinal = fechaFinalStr == null ? null : df.parse(fechaFinalStr);
        } catch (ParseException e) {
            System.out.println("ERROR en TramiteCtrl.buscarTramites: " + e);
        }
        try {
            pw = response.getWriter();

            c.conectar();
            xml = c.consultaIngresos(tipo, descripcion, usuario, idcliente, fechaInicio, fechaFinal, cancelado);

            pw.println(xml);

        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            c.desconectar();
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
