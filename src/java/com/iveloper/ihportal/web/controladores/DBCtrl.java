/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.ihportal.web.controladores;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 *
 * @author Alex
 */
@WebServlet(name = "DBCtrl", urlPatterns = {"/DBCtrl"})
public class DBCtrl extends HttpServlet {

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

        if (op.equalsIgnoreCase("configurar")) {
            System.out.println("Entrando a configurar base de datos");
            configurarBD(request, response);
        }
    }

    public void configurarBD(HttpServletRequest request, HttpServletResponse response) {

        String hostname = request.getParameter("hostname");
        String port = request.getParameter("port");
        String database = request.getParameter("database");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String dbvendor = request.getParameter("dbvendor");
        String serienotaria = request.getParameter("serienotaria");

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(System.getProperty("pwdCrpt"));
        password = encryptor.encrypt(password);
        OutputStream output = null;
        Properties props = null;
        try {
            
            String path = getServletContext().getRealPath("/WEB-INF/configuration.properties");          
            output = new FileOutputStream(path);
            props = new Properties();

            props.setProperty("datasource.hostname", hostname);
            props.setProperty("datasource.port", port);
            props.setProperty("datasource.database", database);
            props.setProperty("datasource.username", username);
            props.setProperty("datasource.password","ENC("+password+")");
            props.setProperty("datasource.dbvendor", dbvendor);
            props.setProperty("datosnotaria.serie", serienotaria);

            props.store(output, null);
        } catch (IOException io) {
            System.out.println("ERROR: en DBCtrl.configurarBD " + io);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.out.println("ERROR: en DBCtrl.configurarBD " + e);
                }
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
