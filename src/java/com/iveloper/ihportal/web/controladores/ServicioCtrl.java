/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.ihportal.web.controladores;

import com.iveloper.db.Conexion;
import com.iveloper.entidades.Servicio;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

/**
 *
 * @author Alex
 */
@WebServlet(name = "ServicioCtrl", urlPatterns = {"/ServicioCtrl"})
public class ServicioCtrl extends HttpServlet {

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
        if (op.equalsIgnoreCase("obtenerServicios")) {
            System.out.println("Entró a obtenerServicios");
            response.setContentType("text/plain;charset=UTF-8"); // Esto es para poder escribir la respuesta en el iframe
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            obtenerServiciosJSON(request, response);
        } else if (op.equalsIgnoreCase("guardarNuevoServicio")) {
            System.out.println("Entró a guardarNuevoServicio");
            guardarNuevoServicio(request, response);
        } else if (op.equalsIgnoreCase("obtenerNumSerie")) {
            System.out.println("Entró a obtenerNumSerie");
            obtenerNumSerie(request, response);
        } else if (op.equalsIgnoreCase("obtenerServicio")) {
            obtenerServicio(request, response);
        }

        out.close();
    }

    public void obtenerServicios(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        String xml = "";
        ArrayList servicios = new ArrayList();
        PrintWriter pw = null;

        try {
            pw = response.getWriter();

            c.conectar();
            servicios = c.consultarServicios();

            xml = "<?xml version='1.0' encoding='UTF-8' ?>";
            xml += "<Servicios>";

            for (int i = 0; i < servicios.size(); i++) {
                Servicio servicio = (Servicio) servicios.get(i);

                xml += "<servicio>";
                xml += "<id>" + servicio.getId() + "</id>";
                xml += "<descripcion>" + servicio.getDescripcion() + "</descripcion>";
                xml += "<tipo>" + servicio.getTipo() + "</tipo>";
                xml += "<precio>" + servicio.getPrecio() + "</precio>";
                xml += "<iva>" + servicio.getIva() + "</iva>";
                xml += "<total>" + servicio.getTotal() + "</total>";
                xml += "<fechacreacion>" + servicio.getFechacreacion() + "</fechacreacion>";
                xml += "<ultimamodificacion>" + servicio.getUltimamodificacion() + "</ultimamodificacion>";
                xml += "<preciovariable>" + servicio.isPreciovariable() + "</preciovariable>";
                xml += "</servicio>";

            }

            xml += "</Servicios>";
            pw.println(xml);

        } catch (Exception e) {
            System.out.println("" + e);
        }

        c.desconectar();

    }

    public void guardarNuevoServicio(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        String descripcion = request.getParameter("descripcion");
        String tipo = request.getParameter("tipo");
        BigDecimal precio = (request.getParameter("precio") == null ? BigDecimal.ZERO : new BigDecimal(request.getParameter("precio")));
        precio = precio.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal iva = precio.multiply(new BigDecimal("0.12"));
        iva = iva.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal total = precio.add(iva);
        total = total.setScale(2, RoundingMode.HALF_EVEN);

        if (descripcion != null && tipo != null && !precio.equals(BigDecimal.ZERO)) {
            Servicio servicio = new Servicio();
            servicio.setDescripcion(descripcion);
            servicio.setTipo(tipo);
            servicio.setPrecio(precio);
            servicio.setIva(iva);
            servicio.setTotal(total);
            servicio.setFechacreacion(new Date());
            servicio.setUltimamodificacion(new Date());
            try {
                c.conectar();
                int result = c.guardarServicio(servicio);
                if (result != 0) {
                    System.out.println("Se guardó servicio " + servicio.getDescripcion());
                } else {
                    System.out.println("NO se guardó servicio " + servicio.getDescripcion());
                }
                obtenerServiciosJSON(request, response);

            } catch (Exception e) {
                System.out.println("" + e);
            } finally {
                c.desconectar();
            }
        }
    }

    public void obtenerServicio(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        String xml = "";
        Servicio servicio = null;
        PrintWriter pw = null;
        String idservicio = request.getParameter("id") == null ? null : request.getParameter("id");
        int id = idservicio == null ? 0 : Integer.parseInt(idservicio);
        if (id != 0) {
            try {
                pw = response.getWriter();

                c.conectar();
                servicio = c.consultarServicio(id);

                xml = "<?xml version='1.0' encoding='UTF-8' ?>";
                xml += "<Servicios>";

                xml += "<servicio>";
                xml += "<id>" + servicio.getId() + "</id>";
                xml += "<descripcion>" + servicio.getDescripcion() + "</descripcion>";
                xml += "<tipo>" + servicio.getTipo() + "</tipo>";
                xml += "<precio>" + servicio.getPrecio() + "</precio>";
                xml += "<iva>" + servicio.getIva() + "</iva>";
                xml += "<total>" + servicio.getTotal() + "</total>";
                xml += "<fechacreacion>" + servicio.getFechacreacion() + "</fechacreacion>";
                xml += "<ultimamodificacion>" + servicio.getUltimamodificacion() + "</ultimamodificacion>";
                xml += "<preciovariable>" + servicio.isPreciovariable() + "</preciovariable>";
                xml += "</servicio>";

                xml += "</Servicios>";
                pw.println(xml);

            } catch (Exception e) {
                System.out.println("" + e);
            }
        }

        c.desconectar();

    }

    public void obtenerServiciosJSON(HttpServletRequest request, HttpServletResponse response) {

        String tipo = request.getParameter("tipo") == null ? null : request.getParameter("tipo");

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        ArrayList servicios = new ArrayList();
        PrintWriter pw = null;

        try {
            pw = response.getWriter();

            c.conectar();
            servicios = c.consultarServicios(tipo);

            pw.println(toJSON(servicios));

        } catch (Exception e) {
            System.out.println("" + e);
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
            Servicio servicio = (Servicio) it.next();

            if (servicio != null) {
                strBld.append("[");
                strBld.append(servicio.getId());
                strBld.append(", \"");
                strBld.append(servicio.getDescripcion());
                strBld.append("\"]");
            }
            if (it.hasNext()) {
                strBld.append(",");
            }

        } while (true);
        strBld.append("]");

        return strBld.toString();
    }

    public void obtenerNumSerie(HttpServletRequest request, HttpServletResponse response) {

        String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");
        Conexion c = new Conexion(path);
        String xml = "";
        Servicio servicio = null;
        PrintWriter pw = null;
        String idservicio = request.getParameter("idservicio") == null ? null : request.getParameter("idservicio");
        int id = (idservicio == null ? 0 : Integer.parseInt(idservicio));

        String cantidadStr = request.getParameter("cantidad") == null ? null : request.getParameter("cantidad");
        int cantidad = (cantidadStr == null ? 0 : Integer.parseInt(cantidadStr));

        int numSerie;
        if (id != 0) {
            try {
                pw = response.getWriter();

                c.conectar();
                servicio = c.consultarServicio(id);

                xml = "<?xml version='1.0' encoding='UTF-8' ?>";
                xml += "<Servicios>";

                for (int i = 0; i < cantidad; i++) {
                    xml += "<servicio>";
                    numSerie = c.obtenerSigNumDocumento(servicio.getTipo());
                    Calendar now = Calendar.getInstance();
                    int year = now.get(Calendar.YEAR);
                    String yearInString = String.valueOf(year);
                    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                    encryptor.setPassword(System.getProperty("pwdCrpt"));
                    Properties props = new EncryptableProperties(encryptor);
                    props.load(new FileInputStream(path));
                    String serienotaria = props.getProperty("datosnotaria.serie");

                    xml += "<numSerie>" + yearInString + "-" + serienotaria + "-" + servicio.getInicialTipo() + numSerie + "</numSerie>";
                    xml += "</servicio>";
                }

                xml += "</Servicios>";
                pw.println(xml);

            } catch (Exception e) {
                System.out.println("" + e);
            }
        }

        c.desconectar();

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
