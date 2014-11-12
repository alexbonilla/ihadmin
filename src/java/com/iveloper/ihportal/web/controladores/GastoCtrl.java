/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.ihportal.web.controladores;

import com.iveloper.db.Conexion;
import com.iveloper.entidades.Gasto;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
@WebServlet(name = "GastoCtrl", urlPatterns = {"/GastoCtrl"})
public class GastoCtrl extends HttpServlet {

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
        if (op.equalsIgnoreCase("buscarGastos")) {
            System.out.println("Entró a buscarGastos");
            buscarGastos(request, response);
        } else if (op.equalsIgnoreCase("guardarNuevoGasto")) {
            System.out.println("Entró a guardarNuevoGasto");
            guardarNuevoGasto(request, response);
        } else if (op.equalsIgnoreCase("obtenerGasto")) {
            System.out.println("Entró a obtenerGasto");
            obtenerGasto(request, response);
        } else if (op.equalsIgnoreCase("buscarGastosHoy")) {
            System.out.println("Entró a buscarGastosHoy");
            buscarGastosHoy(request, response);
        }
        out.close();
    }

    public void guardarNuevoGasto(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        String descripcion = request.getParameter("descripcion") == null ? null : request.getParameter("descripcion");
        BigDecimal valor = request.getParameter("valor") == null || request.getParameter("valor").equals("") ? BigDecimal.ZERO : new BigDecimal(request.getParameter("valor"));

        if (descripcion != null && valor != null) {
            Gasto gasto = new Gasto();
            gasto.setDescripcion(descripcion);
            gasto.setValor(valor);
            gasto.setFechacreacion(new Date());

            try {

                c.conectar();
                int result = c.guardarGasto(gasto);
                if (result != 0) {
                    System.out.println("Se guardó gasto " + gasto.getDescripcion());
                } else {
                    System.out.println("NO se guardó gasto " + gasto.getDescripcion());
                }

            } catch (Exception e) {
                System.out.println("" + e);
            } finally {
                c.desconectar();
            }
        }
    }

    public void obtenerGasto(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        String xml = "";
        Gasto gasto = null;
        PrintWriter pw = null;
        String idgasto = request.getParameter("idgasto") == null ? null : request.getParameter("idgasto");
        if (idgasto != null) {
            try {
                pw = response.getWriter();

                c.conectar();
                gasto = c.obtenerGasto(Integer.parseInt(idgasto));

                xml = "<?xml version='1.0' encoding='UTF-8' ?>";
                xml += "<gastos>";

                xml += "<gasto>";
                xml += "<idgasto>" + gasto.getIdgasto() + "</idgasto>";
                xml += "<descripcion>" + gasto.getDescripcion() + "</descripcion>";
                xml += "<valor>" + gasto.getValor() + "</valor>";
                xml += "<fechacreacion>" + gasto.getFechacreacion() + "</fechacreacion>";

                xml += "</gasto>";

                xml += "</gastos>";
                pw.println(xml);

            } catch (Exception e) {
                System.out.println("" + e);
            }
        }

        c.desconectar();

    }

    public void buscarGastosHoy(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        String xml = "";

        // today    
        Calendar cal = new GregorianCalendar();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        java.util.Date fechaInicio = cal.getTime();

        cal.setTime(fechaInicio);

        // tomorrow    
        cal.add(Calendar.DATE, 1);

        java.util.Date fechaFinal = cal.getTime();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        System.out.println("fechaInicioStr: " + df.format(fechaInicio));
        System.out.println("fechaFinalStr: " + df.format(fechaFinal));

        try {
            PrintWriter pw = response.getWriter();

            c.conectar();
            ArrayList<Gasto> gastos = c.busquedaGastos(fechaInicio, fechaFinal);
            Iterator<Gasto> gastosItr = gastos.iterator();

            BigDecimal totalGastos = new BigDecimal(BigInteger.ZERO);
            xml = "<?xml version='1.0' encoding='UTF-8' ?>";
            xml += "<gastos>";

            while (gastosItr.hasNext()) {
                Gasto gasto = gastosItr.next();

                xml += "<gasto>";
                xml += "<idgasto>" + gasto.getIdgasto() + "</idgasto>";
                xml += "<descripcion>" + gasto.getDescripcion() + "</descripcion>";
                xml += "<valor>" + gasto.getValor() + "</valor>";
                xml += "<fechacreacion>" + df.format(gasto.getFechacreacion()) + "</fechacreacion>";
                xml += "</gasto>";
                totalGastos = totalGastos.add(gasto.getValor());
            }

            xml += "<totalGastos>" + totalGastos + "</totalGastos>";

            xml += "</gastos>";
            pw.println(xml);

        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            c.desconectar();
        }
    }

    public void buscarGastos(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        String xml = "";

        String fechaInicioStr = request.getParameter("fechaInicio") == null || request.getParameter("fechaInicio").equals("") ? null : request.getParameter("fechaInicio");
        String fechaFinalStr = request.getParameter("fechaFinal") == null || request.getParameter("fechaFinal").equals("") ? null : request.getParameter("fechaFinal");

        System.out.println("fechaInicioStr: " + fechaInicioStr);
        System.out.println("fechaFinalStr: " + fechaFinalStr);

        java.util.Date fechaInicio = null;
        java.util.Date fechaFinal = null;

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        try {
            // today    
            Calendar cal = new GregorianCalendar();

            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            fechaInicio = fechaInicioStr == null ? cal.getTime() : df.parse(fechaInicioStr);
            //tomorrow
            cal.add(Calendar.DATE, 1);
            fechaFinal = fechaFinalStr == null ? cal.getTime() : df.parse(fechaFinalStr);
        } catch (ParseException e) {
            System.out.println("ERROR en TramiteCtrl.buscarTramites: " + e);
        }

        try {
            PrintWriter pw = response.getWriter();

            c.conectar();
            ArrayList<Gasto> gastos = c.busquedaGastos(fechaInicio, fechaFinal);
            Iterator<Gasto> gastosItr = gastos.iterator();

            BigDecimal totalGastos = new BigDecimal(BigInteger.ZERO);
            xml = "<?xml version='1.0' encoding='UTF-8' ?>";
            xml += "<gastos>";

            while (gastosItr.hasNext()) {
                Gasto gasto = gastosItr.next();

                xml += "<gasto>";
                xml += "<idgasto>" + gasto.getIdgasto() + "</idgasto>";
                xml += "<descripcion>" + gasto.getDescripcion() + "</descripcion>";
                xml += "<valor>" + gasto.getValor() + "</valor>";
                xml += "<fechacreacion>" + df.format(gasto.getFechacreacion()) + "</fechacreacion>";
                xml += "</gasto>";
                totalGastos = totalGastos.add(gasto.getValor());
            }

            xml += "<totalGastos>" + totalGastos + "</totalGastos>";

            xml += "</gastos>";
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
