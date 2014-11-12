package com.iveloper.db;

import com.iveloper.comprobantes.modelo.CampoAdicional;
import com.iveloper.comprobantes.modelo.DetAdicional;
import com.iveloper.comprobantes.modelo.Detalle;
import com.iveloper.comprobantes.modelo.Impuesto;
import com.iveloper.comprobantes.modelo.InfoTributaria;
import com.iveloper.comprobantes.modelo.TotalImpuesto;
import com.iveloper.comprobantes.modelo.factura.Factura;
import com.iveloper.comprobantes.modelo.factura.InfoFactura;
import com.iveloper.entidades.Abono;
import com.iveloper.entidades.AutorizacionMensaje;
import com.iveloper.entidades.Cliente;
import com.iveloper.entidades.Cuenta;
import com.iveloper.entidades.DetallesCuenta;
import com.iveloper.entidades.Documento;
import com.iveloper.entidades.Emisor;
import com.iveloper.entidades.Gasto;
import com.iveloper.entidades.ResumenAutorizacion;
import com.iveloper.entidades.ResumenFactura;
import com.iveloper.entidades.RetencionRecibida;
import com.iveloper.entidades.Servicio;
import com.iveloper.entidades.Tramite;
import com.iveloper.entidades.TramiteItems;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

public class Conexion {

    private Connection con;
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String MSSQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String PGSQL_DRIVER = "org.postgresql.Driver";
    private static final String MYSQL_DBMS = "mysql";
    private static final String MSSQL_DBMS = "sqlserver";
    private static final String PGSQL_DBMS = "postgresql";

    private String driver = MYSQL_DRIVER;
    private String dbms = MYSQL_DBMS;

    private String host = "localhost";
    private String port = "5432";
    private String database = "notaria";
    private String user = "postgres";
    private String password = "notaria3";

    public Conexion(String ruta_configuracion_db) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(ruta_configuracion_db));
            host = props.getProperty("datasource.hostname");
            port = props.getProperty("datasource.port");
            database = props.getProperty("datasource.database");
            user = props.getProperty("datasource.username");
            password = props.getProperty("datasource.password");

            String dbvendor_str = props.getProperty("datasource.dbvendor");
            setDBVendor(Integer.parseInt(dbvendor_str));
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Conexion(String ruta_configuracion_db, int dbid) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(ruta_configuracion_db));
            host = props.getProperty("datasource.hostname");
            port = props.getProperty("datasource.port");
            database = props.getProperty("datasource.database") + String.format("%03d", dbid);
            user = props.getProperty("datasource.username");
            password = props.getProperty("datasource.password");

            String dbvendor_str = props.getProperty("datasource.dbvendor");
            setDBVendor(Integer.parseInt(dbvendor_str));
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Conexion(String host, String port, String database, String user, String password, int dbvendor) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        setDBVendor(dbvendor);
    }

    private void setDBVendor(int dbvendor) {
        switch (dbvendor) {
            case 0:
                driver = MYSQL_DRIVER;
                dbms = MYSQL_DBMS;
                break;
            case 1:
                driver = MSSQL_DRIVER;
                dbms = MSSQL_DBMS;
                break;
            case 2:
                driver = PGSQL_DRIVER;
                dbms = PGSQL_DBMS;
                break;
            default:
                driver = MYSQL_DRIVER;
                dbms = MYSQL_DBMS;
                break;
        }
    }

    public void conectar() throws Exception {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ce) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ce);
        }
        try {

            switch (dbms) {
                case MYSQL_DBMS:
                    this.con = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + database, user, password);
                    break;
                case MSSQL_DBMS:
                    this.con = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + ";"
                            + "databaseName=" + database + ";user=" + user + ";password=" + password + ";");
                    break;
                case PGSQL_DBMS:
                    this.con = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + database, user, password);
                    break;
                default:
                    this.con = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + database, user, password);
                    break;
            }

            System.out.println("EVENTO: Conexión exitosa con la base de datos");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean desconectar() {
        try {
            if (con != null && !con.isClosed()) {
                this.con.close();
            }
            return (true);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return (false);
        }
    }

    public boolean estaConectado() {
        try {
            return !(this.con.isClosed()) && this.con.isValid(2);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Connection getCon() {
        return con;
    }

    public Cuenta obtenerCuentaPorId(int idcuenta) {

        Cuenta c = null;

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM cuentas WHERE idcuenta = ?; ");
            st.setInt(1, idcuenta);
            ResultSet rs = st.executeQuery();
            if (rs.first()) {
                String cuenta = rs.getString("cuenta");
                Timestamp fechacreacion = rs.getTimestamp("fechacreacion");
                Timestamp ultimoacceso = rs.getTimestamp("ultimoacceso");
                String roles = rs.getString("roles");
                int id = rs.getInt("idcuenta");
                byte[] pwd = rs.getBytes("pwd");
                byte[] salt = rs.getBytes("salt");
                String mail = rs.getString("mail");

                c = new Cuenta(id, cuenta, fechacreacion, ultimoacceso, roles, mail, pwd, salt);
                DetallesCuenta dc = this.obtenerDetalleCuentaPorId(idcuenta);
                c.setDetallesCuenta(dc);

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (c);
    }

    public DetallesCuenta obtenerDetalleCuentaPorId(int idcuenta) {

        DetallesCuenta dc = null;

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM detalles_cuenta WHERE idcuenta = ?; ");
            st.setInt(1, idcuenta);
            ResultSet rs = st.executeQuery();
            if (rs.first()) {
                dc = new DetallesCuenta();

                String idcliente = rs.getString("idcliente");
                int idempresa = rs.getInt("idempresa");
                String nombre_cliente = rs.getString("nombre_cliente");
                String nombre_empresa = rs.getString("nombre_empresa");
                String logo_empresa = rs.getString("logo_empresa");

                dc.setIdcliente(idcliente);
                dc.setIdempresa(idempresa);
                dc.setNombre_cliente(nombre_cliente);
                dc.setNombre_empresa(nombre_empresa);
                dc.setLogo_empresa(logo_empresa);

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (dc);
    }

    public ResumenAutorizacion obtenerResumenAutorizacion(String claveacceso) {
        ResumenAutorizacion resumen = null;

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM autorizaciones WHERE claveacceso = ?;");
            st.setString(1, claveacceso);
            ResultSet rs = st.executeQuery();
            if (rs.first()) {
                resumen = new ResumenAutorizacion();
                String autorizacion = rs.getString("autorizacion");
                String fechaautorizacion = rs.getString("fechaautorizacion");
                String contenidoxml = rs.getString("contenidoxml");
                String estado = rs.getString("estado");
                String idcliente = rs.getString("idcliente");
                int tipodocumento = rs.getInt("tipodocumento");

                resumen.setClaveacceso(claveacceso);
                resumen.setAutorizacion(autorizacion);
                resumen.setFechaautorizacion(fechaautorizacion);
                resumen.setContenidoxml(contenidoxml);
                resumen.setContenidoxml(contenidoxml);
                resumen.setEstado(estado);
                resumen.setIdcliente(idcliente);
                resumen.setTipodocumento(tipodocumento);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (resumen);
    }

    public List<ResumenFactura> obtenerListaResumenFacturas(String idcliente, Date fechainicio, Date fechafin, String estado) {
        List<ResumenFactura> resumenes = new ArrayList();

        try {
            PreparedStatement st = null;

            String query = "SELECT * FROM resumenes_facturas";
            String conditions = " WHERE ";

            if (!(idcliente == null && fechainicio == null && fechafin == null && estado == null)) {
                query = query + conditions;
            }

            if (idcliente != null) {
                query = query + " idcliente = '" + idcliente + "' ";
                if (!(fechainicio == null && fechafin == null && estado == null)) {
                    query = query + " AND ";
                }
            }

            if (fechainicio != null) {
                query = query + " fechaemision >= '" + new Timestamp(fechainicio.getTime()) + "' ";
                if (!(fechafin == null && estado == null)) {
                    query = query + " AND ";
                }
            }
            if (fechafin != null) {
                query = query + " fechaemision <= '" + new Timestamp(fechafin.getTime()) + "' ";
                if (!(estado == null)) {
                    query = query + " AND ";
                }
            }
            if (estado != null) {
                query = query + " estado = '" + estado + "' ";
            }
            Logger.getLogger(Conexion.class.getName()).log(Level.INFO, "Query: {0}", query);
            query = query + ";";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ResumenFactura resumen = new ResumenFactura();
                String claveacceso = rs.getString("claveacceso");
                String numfactura = rs.getString("numfactura");
                String fechaemision = rs.getString("fechaemision");
                BigDecimal importetotal = rs.getBigDecimal("importetotal");
                idcliente = rs.getString("idcliente");
                estado = rs.getString("estado");
                resumen.setClaveacceso(claveacceso);
                resumen.setNumfactura(numfactura);
                resumen.setFechaemision(fechaemision);
                resumen.setIdcliente(idcliente);
                resumen.setImportetotal(importetotal);
                resumen.setEstado(estado);
                resumenes.add(resumen);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (resumenes);
    }
    
    public List<AutorizacionMensaje> obtenerMensajesAutorizacion(String claveacceso) {
        
        List<AutorizacionMensaje> mensajes = new ArrayList();

        try {
            PreparedStatement st = null;

            String query = "SELECT * FROM autorizaciones_mensajes WHERE claveacceso = ?";            
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, claveacceso);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                AutorizacionMensaje mensaje = new AutorizacionMensaje();
                
                String identificador = rs.getString("identificador");
                String tipo = rs.getString("tipo");
                String mensajeStr = rs.getString("mensaje");
                String infoadicional = rs.getString("infoadicional");
                
                mensaje.setClaveacceso(claveacceso);
                mensaje.setIdentificador(identificador);
                mensaje.setTipo(tipo);
                mensaje.setMensaje(mensajeStr);
                mensaje.setInfoadicional(infoadicional);
                
                mensajes.add(mensaje);

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (mensajes);
    }

    public boolean cuentaValida(String usuario, String rol) {
        boolean resultado = false;

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM cuentas WHERE usuario = ? AND roles LIKE concat('%',?,'%') ; ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, usuario);
            st.setString(2, rol);
            ResultSet rs = st.executeQuery();
            resultado = rs.first();
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (resultado);
    }

    public ArrayList consultarCuentas() {
        ArrayList cuentas = new ArrayList();

        try {
            Statement st = this.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cuentas;");

            while (rs.next()) {
                String cuenta = rs.getString("usuario");
                Timestamp fechacreacion = rs.getTimestamp("fechacreacion");
                Timestamp ultimoacceso = rs.getTimestamp("ultimoacceso");
                String roles = rs.getString("roles");
                int id = rs.getInt("idcuenta");
                byte[] pwd = rs.getBytes("pwd");
                byte[] salt = rs.getBytes("salt");
                String mail = rs.getString("mail");
                int activo = rs.getInt("activo");

                Cuenta c = new Cuenta(id, cuenta, fechacreacion, ultimoacceso, roles, mail, pwd, salt, activo);
                DetallesCuenta dc = this.obtenerDetalleCuentaPorId(id);
                c.setDetallesCuenta(dc);
                cuentas.add(c);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (cuentas);
    }

    public Cuenta consultarCuenta(String usuario) {
        Cuenta cuenta = null;
        try {

            Statement st = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM cuentas WHERE usuario = '" + usuario + "';");

            if (rs.first()) {

                Date fechacreacion = rs.getDate("fechacreacion");
                Date ultimoacceso = null;
                try {
                    ultimoacceso = rs.getDate("ultimoacceso");
                } catch (SQLException e) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.WARNING, "consultarCuenta: '{'0'}'{0}", e);
                }

                String roles = rs.getString("roles");
                int id = rs.getInt("idcuenta");
                byte[] pwd = rs.getBytes("pwd");
                byte[] salt = rs.getBytes("salt");
                String mail = rs.getString("mail");
                int activo = rs.getInt("activo");
                Logger.getLogger(Conexion.class.getName()).log(Level.INFO, "Se obtuvo usuario a memoria");
                cuenta = new Cuenta(id, usuario, fechacreacion, ultimoacceso, roles, mail, pwd, salt, activo);
                DetallesCuenta dc = this.obtenerDetalleCuentaPorId(id);
                cuenta.setDetallesCuenta(dc);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (cuenta);
    }

    public boolean crearCuenta(Cuenta c) {
        try {
            PreparedStatement st = null;
            st = con.prepareStatement("INSERT INTO cuentas(usuario,roles,pwd,mail,salt,fechacreacion) VALUES (?,?,?,?,?,?);");
            st.setString(1, c.usuario);
            st.setString(2, c.roles);
            st.setBytes(3, c.pwd);
            st.setString(4, c.mail);
            st.setBytes(5, c.salt);
            st.setTimestamp(6, new java.sql.Timestamp((new Date()).getTime()));

            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean modificarCuenta(Cuenta c) {
        try {
            PreparedStatement st = null;
            st = con.prepareStatement("UPDATE cuentas SET roles=?, pwd=?, mail=?, salt=?, activo=?, ultimoacceso=? WHERE usuario=?;");
            st.setString(1, c.roles);
            st.setBytes(2, c.pwd);
            st.setString(3, c.mail);
            st.setBytes(4, c.salt);
            st.setInt(5, c.activo);

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            st.setString(6, sdf.format(c.ultimoacceso));
            st.setString(7, c.usuario);

            st.executeUpdate();
            st.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public int obtenerSigNumTramite() {
        int idtramite = 0;
        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            String query = "SELECT * FROM tramites WHERE usado is NULL LIMIT 1;";

            rs = st.executeQuery(query);

            if (rs.first()) {
                idtramite = rs.getInt("idtramite");
                marcarNumTramiteParaUso(idtramite);
            } else {
                query = "INSERT INTO tramite (SELECT MAX(numserie)+1,null, null, null FROM tramites);";
                int result = st.executeUpdate(query);
                if (result > 0) {
                    return obtenerSigNumTramite();
                }
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.obtenerSigNumTramite: " + exception);
        }
        return idtramite;
    }

    public int marcarNumTramiteParaUso(int idtramite) {
        int result = 0;
        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = "UPDATE tramites SET usado=1 WHERE idtramite=" + idtramite + ";";

            result = st.executeUpdate(query);

            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.marcarNumTramiteParaUso: " + exception);
        }
        return result;
    }

    public int liberarNumTramite(int idtramite) {
        int result = 0;
        try {
            Statement st = this.con.createStatement();

            String query = "UPDATE tramites SET usado=NULL WHERE idtramite=" + idtramite + ";";

            result = st.executeUpdate(query);

            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.liberarNumTramite: " + exception);
        }
        return result;
    }

    public int marcarTramiteComoAnulado(int idtramite) {
        int result = 0;
        try {
            Statement st = this.con.createStatement();
            String query = "UPDATE tramites SET anulado=1 WHERE idtramite=" + idtramite + ";";
            result = st.executeUpdate(query);
            st.close();

            marcarDocumentosComoAnulado(idtramite);
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.marcarTramiteComoAnulado: " + exception);
        }
        return result;
    }

    public int marcarDocumentosComoAnulado(int idtramite) {
        int result = 0;
        try {
            PreparedStatement st = null;
            String insert = "UPDATE documentos SET anulado=1, ultimamodificacion=? WHERE idtramite=?;";
            st = con.prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setTimestamp(1, new Timestamp((new java.util.Date()).getTime()));
            st.setInt(2, idtramite);
            result = st.executeUpdate();
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.marcarDocumentoComoAnulado: " + exception);
        }
        return result;
    }

    public int marcarDocumentoComoAnulado(String iddocumento) {
        int result = 0;
        try {
            PreparedStatement st = null;
            String insert = "UPDATE documentos SET anulado=1, ultimamodificacion=? WHERE iddocumento=?;";
            st = con.prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setTimestamp(1, new Timestamp((new java.util.Date()).getTime()));
            st.setString(2, iddocumento);
            result = st.executeUpdate();
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.marcarDocumentoComoAnulado: " + exception);
        }
        return result;
    }

    public int marcarDocumentoComoReutilizado(String iddocumento) {
        int result = 0;
        try {
            PreparedStatement st = null;
            String insert = "UPDATE documentos SET anulado=2, ultimamodificacion=? WHERE iddocumento=?;";
            st = con.prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setTimestamp(1, new Timestamp((new java.util.Date()).getTime()));
            st.setString(2, iddocumento);
            result = st.executeUpdate();
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.marcarDocumentoComoReutilizado: " + exception);
        }
        return result;
    }

    public int obtenerSigNumDocumento(String tipo) {
        int numserie = 0;
        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            String query = "SELECT * FROM seriesdocumentos_" + tipo + " WHERE usado is NULL LIMIT 1;";

            rs = st.executeQuery(query);

            if (rs.first()) {
                numserie = rs.getInt("numserie");
                marcarNumSerieParaUso(numserie, tipo);
            } else {
                query = "INSERT INTO seriesdocumentos_" + tipo + " (SELECT MAX(numserie)+1,null, null, null FROM seriesdocumentos_" + tipo + ");";
                int result = st.executeUpdate(query);
                if (result > 0) {
                    return obtenerSigNumDocumento(tipo);
                }
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.obtenerSigNumDocumento: " + exception);
        }
        return numserie;
    }

    public int marcarNumSerieParaUso(int numserie, String tipo) {
        int result = 0;
        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = "UPDATE seriesdocumentos_" + tipo + " SET usado=1 WHERE numserie=" + numserie + ";";

            result = st.executeUpdate(query);

            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.marcarNumSerieParaUso: " + exception);
        }
        return result;
    }

    public int liberarNumSerie(int numserie, String tipo) {
        int result = 0;
        try {
            Statement st = this.con.createStatement();

            String query = "UPDATE seriesdocumentos_" + tipo + " SET usado=NULL WHERE numserie=" + numserie + ";";

            result = st.executeUpdate(query);

            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.liberarNumSerie: " + exception);
        }
        return result;
    }

    public int escribirAutorizacion(String numfactura, String claveacceso, String autorizacion) {
        int result = 0;
        try {
            PreparedStatement st = null;
            String query = "UPDATE seriesfactura SET claveacceso=?, autorizacion=? WHERE numfactura=?;";
            st = con.prepareStatement(query);
            st.setString(1, claveacceso);
            st.setString(2, autorizacion);
            st.setInt(3, Integer.parseInt(numfactura));

            st.executeUpdate();
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.escribirAutorizacion: " + exception);
        }
        return result;
    }

    public Emisor obtenerEmisorPorRUC(String ruc) {

        Emisor emisor = null;

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM emisores WHERE ruc = ?; ");
            st.setString(1, ruc);
            ResultSet rs = st.executeQuery();
            if (rs.first()) {

                String razonSocial = rs.getString("razonSocial");
                String nombreComercial = rs.getString("nombreComercial");
                String resolContribEsp = rs.getString("resolContribEsp");
                String obligadoContabilidad = rs.getString("obligadoContabilidad");

                emisor = new Emisor(ruc, razonSocial, nombreComercial, resolContribEsp, obligadoContabilidad);

            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.obtenerEmisorPorRUC: " + exception);
        }
        return (emisor);
    }

    public ArrayList<Cliente> consultarClientes() {
        ArrayList clientes = new ArrayList();

        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM clientes;");

            while (rs.next()) {

                String razonsocial = rs.getString("razonsocial");
                String identificacion = rs.getString("identificacion");
                String tipoidentificacion = rs.getString("tipoidentificacion");
                String tipocliente = rs.getString("tipocliente");
                String direccion = rs.getString("direccion");
                String telefonofijo = rs.getString("telefonofijo");
                String extensionfijo = rs.getString("extensionfijo");
                String telefonomovil = rs.getString("telefonomovil");
                String email = rs.getString("email");
                BigDecimal credito = rs.getBigDecimal("credito");

                Cliente cliente = new Cliente(razonsocial, tipoidentificacion, identificacion, tipocliente, direccion, telefonofijo, extensionfijo, telefonomovil, email, credito);
                clientes.add(cliente);
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarClientes: " + exception);
        }
        return (clientes);
    }

    public Cliente consultarCliente(String identificacion) {
        Cliente cliente = null;

        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM clientes WHERE identificacion='" + identificacion + "';");

            if (rs.first()) {
                String tipodidentificacion = rs.getString("tipoidentificacion");
                String razonsocial = rs.getString("razonsocial");
                String tipocliente = rs.getString("tipocliente");
                String direccion = rs.getString("direccion");
                String telefonofijo = rs.getString("telefonofijo");
                String extensionfijo = rs.getString("extensionfijo");
                String telefonomovil = rs.getString("telefonomovil");
                String email = rs.getString("email");
                BigDecimal credito = rs.getBigDecimal("credito");

                cliente = new Cliente(razonsocial, tipodidentificacion, identificacion, tipocliente, direccion, telefonofijo, extensionfijo, telefonomovil, email, credito);

            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarServicio: " + exception);
        }
        return (cliente);
    }

    public int guardarCliente(Cliente cliente) {
        try {
            PreparedStatement st = null;
            String insert = "INSERT INTO clientes (razonsocial, tipoidentificacion, identificacion, tipocliente, direccion, telefonofijo, extensionfijo, telefonomovil, email) values (?,?,?,?,?,?,?,?,?);";
            st = con.prepareStatement(insert);
            st.setString(1, cliente.getRazonsocial());
            st.setString(2, cliente.getTipoIdentificacion());
            st.setString(3, cliente.getIdentificacion());
            st.setString(4, cliente.getTipoCliente());
            st.setString(5, cliente.getDireccion());
            st.setString(6, cliente.getTelefonoFijo());
            st.setString(7, cliente.getExtensionFijo());
            st.setString(8, cliente.getTelefonoMovil());
            st.setString(9, cliente.getEmail());

            st.executeUpdate();
            st.close();
            return 1;
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.crearCliente: " + exception);
            return 0;
        }
    }

    public ArrayList<Servicio> consultarServicios() {
        ArrayList servicios = new ArrayList();

        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM servicios;");

            while (rs.next()) {
                int id = rs.getInt("idservicio");
                String descripcion = rs.getString("descripcion");
                String tipo = rs.getString("tipo");
                BigDecimal precio = rs.getBigDecimal("precio");
                BigDecimal iva = rs.getBigDecimal("iva");
                BigDecimal total = rs.getBigDecimal("total");
                java.util.Date fechacreacion = rs.getDate("fechacreacion");
                java.util.Date ultimamodificacion = rs.getDate("ultimamodificacion");
                int preciovariable = rs.getInt("preciovariable");

                Servicio servicio = new Servicio(id, descripcion, tipo, precio, iva, total, fechacreacion, ultimamodificacion, preciovariable);
                servicios.add(servicio);
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarServicios: " + exception);
        }
        return (servicios);
    }

    public ArrayList<Servicio> consultarServicios(String tipo) {
        ArrayList servicios = new ArrayList();

        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            if (tipo != null && !tipo.equals("null")) {
                rs = st.executeQuery("SELECT * FROM servicios WHERE tipo='" + tipo + "';");
            } else {
                rs = st.executeQuery("SELECT * FROM servicios;");
            }

            while (rs.next()) {
                int id = rs.getInt("idservicio");
                String descripcion = rs.getString("descripcion");
                BigDecimal precio = rs.getBigDecimal("precio");
                BigDecimal iva = rs.getBigDecimal("iva");
                BigDecimal total = rs.getBigDecimal("total");
                java.util.Date fechacreacion = rs.getDate("fechacreacion");
                java.util.Date ultimamodificacion = rs.getDate("ultimamodificacion");
                int preciovariable = rs.getInt("preciovariable");

                Servicio servicio = new Servicio(id, descripcion, tipo, precio, iva, total, fechacreacion, ultimamodificacion, preciovariable);
                servicios.add(servicio);
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarServicios: " + exception);
        }
        return (servicios);
    }

    public Servicio consultarServicio(int id) {
        Servicio servicio = null;

        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM servicios WHERE idservicio=" + id + ";");

            if (rs.first()) {
                String descripcion = rs.getString("descripcion");
                String tipo = rs.getString("tipo");
                BigDecimal precio = rs.getBigDecimal("precio");
                BigDecimal iva = rs.getBigDecimal("iva");
                BigDecimal total = rs.getBigDecimal("total");
                java.util.Date fechacreacion = rs.getDate("fechacreacion");
                java.util.Date ultimamodificacion = rs.getDate("ultimamodificacion");
                int preciovariable = rs.getInt("preciovariable");

                servicio = new Servicio(id, descripcion, tipo, precio, iva, total, fechacreacion, ultimamodificacion, preciovariable);

            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarServicio: " + exception);
        }
        return (servicio);
    }

    public int guardarServicio(Servicio servicio) {

        int result = 0;

        try {
            PreparedStatement st = null;
            String insert = "INSERT INTO servicios (descripcion, tipo, precio, iva, total, fechacreacion, ultimamodificacion, preciovariable) values (?,?,?,?,?,?,?,?);";
            st = con.prepareStatement(insert);
            st.setString(1, servicio.getDescripcion());
            st.setString(2, servicio.getTipo());
            st.setBigDecimal(3, servicio.getPrecio());
            st.setBigDecimal(4, servicio.getIva());
            st.setBigDecimal(5, servicio.getTotal());
            st.setTimestamp(6, new Timestamp(servicio.getFechacreacion().getTime()));
            st.setTimestamp(7, new Timestamp(servicio.getUltimamodificacion().getTime()));
            st.setInt(8, servicio.isPreciovariable());
            result = st.executeUpdate();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.guardarServicio: " + exception);
        }
        return (result);
    }

    public Tramite guardarTramite(Tramite tramite) {
        try {
            PreparedStatement st = null;
            String insert = "INSERT INTO tramites (cancelado, fechacreacion, ultimamodificacion, cantdoc, subtotal, iva, total, idcliente, usuario, totaladicional) values (?,?,?,?,?,?,?,?,?,?);";
            st = con.prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setInt(1, tramite.getCancelado());
            st.setTimestamp(2, new Timestamp(tramite.getFechacreacion().getTime()));
            st.setTimestamp(3, new Timestamp(tramite.getUltimamodificacion().getTime()));
            st.setInt(4, tramite.getCantdoc());
            st.setBigDecimal(5, tramite.getSubtotal());
            st.setBigDecimal(6, tramite.getIva());
            st.setBigDecimal(7, tramite.getTotal());
            st.setString(8, tramite.getCliente().getIdentificacion());
            st.setString(9, tramite.getUsuario());
            st.setBigDecimal(10, tramite.getTotaladicional());
            st.executeUpdate();

            String query = "SELECT max(idtramite) as ultimotramite FROM tramites;";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();
            int idtramite = 0;
            if (rs.first()) {
                idtramite = rs.getInt("ultimotramite");
            }
            tramite.setIdtramite(idtramite);
            st.close();

            if (guardarDocumentos(idtramite, tramite.getDocumentos()) == 0) {
                System.out.println("No se pudo guardar documentos de tramite " + tramite.getIdtramite());
            } else {
                System.out.println("Documentos de tramite han sido guardados " + tramite.getIdtramite());
            }

            return tramite;
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.guardarTramite: " + exception);
            return null;
        }
    }

    public Tramite actualizarTramite(Tramite tramite) {
        try {
            PreparedStatement st = null;
            String update = "UPDATE tramites SET ultimamodificacion=?, cantdoc=?, subtotal=?, iva=?, total=?, totaladicional=? WHERE idtramite=?;";
            st = con.prepareStatement(update, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            st.setTimestamp(1, new Timestamp(tramite.getUltimamodificacion().getTime()));
            st.setInt(2, tramite.getCantdoc());
            st.setBigDecimal(3, tramite.getSubtotal());
            st.setBigDecimal(4, tramite.getIva());
            st.setBigDecimal(5, tramite.getTotal());
            st.setBigDecimal(6, tramite.getTotaladicional());
            st.setInt(7, tramite.getIdtramite());
            st.executeUpdate();

            st.close();

            return tramite;
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.actualizarTramite: " + exception);
            return null;
        }
    }

    public int guardarDocumentos(int idtramite, ArrayList<Documento> documentos) {
        Iterator<Documento> documentoIter = documentos.iterator();
        try {

            PreparedStatement st = null;
            String insert = "INSERT INTO documentos (iddocumento, idtramite, benefnombre, benefid, idservicio, descripcion, tipo, precio, iva, total, fechacreacion, ultimamodificacion, preciovariable, valoradicional, descripcionadicional) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            st = con.prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            while (documentoIter.hasNext()) {
                Documento documento = documentoIter.next();
                st.setString(1, documento.getIddocumento());
                st.setInt(2, idtramite);
                st.setString(3, documento.getBenefnombre());
                st.setString(4, documento.getBenefid());
                st.setInt(5, documento.getServicio().getId());
                st.setString(6, documento.getServicio().getDescripcion());
                st.setString(7, documento.getServicio().getTipo());
                st.setBigDecimal(8, documento.getServicio().getPrecio());
                st.setBigDecimal(9, documento.getServicio().getIva());
                st.setBigDecimal(10, documento.getServicio().getTotal());
                st.setTimestamp(11, new Timestamp((new java.util.Date()).getTime()));
                st.setTimestamp(12, new Timestamp((new java.util.Date()).getTime()));
                st.setInt(13, documento.getServicio().isPreciovariable());
                st.setBigDecimal(14, documento.getValoradicional());
                st.setString(15, documento.getDescripcionadicional());
                st.executeUpdate();

            }

            st.close();

            return 1;
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.guardarDocumentos: " + exception);
            return 0;
        }
    }

    public Abono guardarAbono(Abono abono) {
        try {
            PreparedStatement st = null;
            String insert = "INSERT INTO abonos (valor, fechaabono, idtramite) values (?,?,?);";
            st = con.prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setBigDecimal(1, abono.getValor());
            st.setTimestamp(2, new Timestamp(abono.getFechaabono().getTime()));
            st.setInt(3, abono.getIdtramite());
            st.executeUpdate();

            String query = "SELECT max(idabono) as ultimoabono FROM abonos;";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();
            int idabono = 0;
            if (rs.first()) {
                idabono = rs.getInt("ultimoabono");
            }
            abono.setIdabono(idabono);
            RetencionRecibida retencion = this.obtenerRetencionRecibida(abono.getIdtramite());
            Tramite tramite = this.consultarTramite(abono.getIdtramite());
            BigDecimal totalAbonado = tramite.totalAbonos();
            BigDecimal totalTramite = tramite.getTotal();
            BigDecimal valorAdeudado = totalTramite.subtract(totalAbonado);
            if (retencion != null) {
                valorAdeudado = valorAdeudado.subtract(retencion.getValor());
            }
            valorAdeudado = valorAdeudado.setScale(2, RoundingMode.HALF_EVEN);
            System.out.println("El valor adeudado de tramite " + abono.getIdtramite() + " es ahora " + valorAdeudado);

            if (valorAdeudado.compareTo(BigDecimal.ZERO) == 0) {
                //poner cancelado
                //Credito cero y deuda cero
                String updateTramite = "UPDATE tramites SET cancelado = 1 WHERE idtramite = ?;";
                st = con.prepareStatement(updateTramite, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st.setInt(1, abono.getIdtramite());
                st.executeUpdate();
                System.out.println("Se puso tramite " + abono.getIdtramite() + " como cancelado");

                String updateCliente = "UPDATE clientes SET credito = 0 WHERE identificacion = ?;";
                st = con.prepareStatement(updateCliente, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st.setString(1, tramite.getCliente().getIdentificacion());
                st.executeUpdate();
                System.out.println("Crédito cero para cliente " + tramite.getCliente().getIdentificacion() + " " + tramite.getCliente().getRazonsocial());
            } else if (valorAdeudado.compareTo(BigDecimal.ZERO) > 0) {
                //Credito cero y deuda pendiente
                String updateCliente = "UPDATE clientes SET credito = 0 WHERE identificacion = ?;";
                st = con.prepareStatement(updateCliente, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st.setString(1, tramite.getCliente().getIdentificacion());
                st.executeUpdate();
                System.out.println("Crédito cero para cliente " + tramite.getCliente().getIdentificacion() + " " + tramite.getCliente().getRazonsocial());
            } else if (valorAdeudado.compareTo(BigDecimal.ZERO) < 0) {
                //acreditar diferencia y deuda cero
                Abono ajuste = abono;
                ajuste.setValor(valorAdeudado);
                this.guardarAbono(ajuste);
                BigDecimal valorCredito = totalAbonado.subtract(totalTramite);
                valorCredito = valorCredito.setScale(2, RoundingMode.HALF_EVEN);

                String updateCliente = "UPDATE clientes SET credito = ? WHERE identificacion = ?;";
                st = con.prepareStatement(updateCliente, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st.setBigDecimal(1, valorCredito);
                st.setString(2, tramite.getCliente().getIdentificacion());
                st.executeUpdate();
                System.out.println("Se acreditó  " + valorCredito + " a cliente " + tramite.getCliente().getIdentificacion() + " " + tramite.getCliente().getRazonsocial());
            }

            st.close();

            return abono;
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.guardarTramite: " + exception);
            return null;
        }
    }

    public ArrayList<Tramite> busquedaTramites(String usuario, String idcliente, java.util.Date fechaInicio, java.util.Date fechaFinal, int cancelado) {
        ArrayList<Tramite> tramites = new ArrayList();

        try {
            PreparedStatement st = null;

            String query = "SELECT * FROM tramites WHERE anulado=0";
            String conditions = " AND ";

            if (!(usuario == null && idcliente == null && fechaInicio == null && fechaFinal == null && cancelado == -1)) {
                query = query + conditions;
            }

            if (usuario != null) {
                query = query + " usuario = '" + usuario + "' ";
                if (!(idcliente == null && fechaInicio == null && fechaFinal == null && cancelado == -1)) {
                    query = query + " AND ";
                }
            }
            if (idcliente != null) {
                query = query + " idcliente = '" + idcliente + "' ";
                if (!(fechaInicio == null && fechaFinal == null && cancelado == -1)) {
                    query = query + " AND ";
                }
            }
            if (fechaInicio != null) {
                query = query + " fechacreacion >= '" + new Timestamp(fechaInicio.getTime()) + "' ";
                if (!(fechaFinal == null && cancelado == -1)) {
                    query = query + " AND ";
                }
            }
            if (fechaFinal != null) {
                query = query + " fechacreacion <= '" + new Timestamp(fechaFinal.getTime()) + "' ";
                if (!(cancelado == -1)) {
                    query = query + " AND ";
                }
            }
            if (cancelado != -1) {
                query = query + " cancelado = " + cancelado + " ";
            }
            query = query + ";";
            System.out.println("Query de busqueda de tramites: " + query);

            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Tramite tramite = new Tramite();
                int idtramite = rs.getInt("idtramite");
                int canceladoVar = rs.getInt("cancelado");
                Date fechacreacion = new Date(rs.getTimestamp("fechacreacion").getTime());
                Date ultimamodificacion = new Date(rs.getTimestamp("ultimamodificacion").getTime());
                int cantdoc = rs.getInt("cantdoc");
                BigDecimal subtotal = rs.getBigDecimal("subtotal");
                BigDecimal iva = rs.getBigDecimal("iva");
                BigDecimal total = rs.getBigDecimal("total");
                BigDecimal totaladicional = rs.getBigDecimal("totaladicional");
                String idclienteVar = rs.getString("idcliente");
                String usuarioVar = rs.getString("usuario");
                int facturado = rs.getInt("facturado");
                int numfactura = rs.getInt("numfactura");

                tramite.setIdtramite(idtramite);
                tramite.setCancelado(canceladoVar);
                tramite.setFechacreacion(fechacreacion);
                tramite.setUltimamodificacion(ultimamodificacion);
                tramite.setCantdoc(cantdoc);
                tramite.setDocumentos(this.consultarDocumentos(idtramite));
                tramite.setSubtotal(subtotal);
                tramite.setIva(iva);
                tramite.setTotal(total);
                tramite.setTotaladicional(totaladicional);
                tramite.setCliente(this.consultarCliente(idclienteVar));
                tramite.setUsuario(usuarioVar);
                tramite.setAbonos(this.consultarAbonos(idtramite));
                tramite.setFacturado(facturado);
                tramite.setNumfactura(numfactura);

                tramites.add(tramite);
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.busquedaTramites: " + exception);
        }
        return (tramites);
    }

    public ArrayList<Tramite> busquedaTramitesHoy(String username, java.util.Date fechaInicio, java.util.Date fechaFinal) {
        ArrayList<Tramite> tramites = new ArrayList();

        try {
            PreparedStatement st = null;

            String query = "SELECT * FROM tramites WHERE anulado=0 AND fechacreacion >= '" + new Timestamp(fechaInicio.getTime()) + "' AND fechacreacion <= '" + new Timestamp(fechaFinal.getTime()) + "' ";
            if (username != null && !username.equals("")) {
                query = query + " AND usuario ='" + username + "';";
            } else {
                query = query + ";";
            }
            System.out.println("Query de busqueda de tramites: " + query);

            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Tramite tramite = new Tramite();
                int idtramite = rs.getInt("idtramite");
                int canceladoVar = rs.getInt("cancelado");
                Date fechacreacion = new Date(rs.getTimestamp("fechacreacion").getTime());
                Date ultimamodificacion = new Date(rs.getTimestamp("ultimamodificacion").getTime());
                int cantdoc = rs.getInt("cantdoc");
                BigDecimal subtotal = rs.getBigDecimal("subtotal");
                BigDecimal iva = rs.getBigDecimal("iva");
                BigDecimal total = rs.getBigDecimal("total");
                BigDecimal totaladicional = rs.getBigDecimal("totaladicional");
                String idclienteVar = rs.getString("idcliente");
                String usuarioVar = rs.getString("usuario");
                int facturado = rs.getInt("facturado");
                int numfactura = rs.getInt("numfactura");

                tramite.setIdtramite(idtramite);
                tramite.setCancelado(canceladoVar);
                tramite.setFechacreacion(fechacreacion);
                tramite.setUltimamodificacion(ultimamodificacion);
                tramite.setCantdoc(cantdoc);
                tramite.setDocumentos(this.consultarDocumentos(idtramite));
                tramite.setSubtotal(subtotal);
                tramite.setIva(iva);
                tramite.setTotal(total);
                tramite.setTotaladicional(totaladicional);
                tramite.setCliente(this.consultarCliente(idclienteVar));
                tramite.setUsuario(usuarioVar);
                tramite.setAbonos(this.consultarAbonos(idtramite));
                tramite.setFacturado(facturado);
                tramite.setNumfactura(numfactura);

                tramites.add(tramite);
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.busquedaTramites: " + exception);
        }
        return (tramites);
    }

    public Tramite consultarTramite(int idtramite) {
        Tramite tramite = new Tramite();

        try {
            PreparedStatement st = null;

            String query = "SELECT * FROM tramites WHERE idtramite = " + idtramite + ";";

            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();

            if (rs.first()) {
                int canceladoVar = rs.getInt("cancelado");
                Date fechacreacion = new Date(rs.getTimestamp("fechacreacion").getTime());
                Date ultimamodificacion = new Date(rs.getTimestamp("ultimamodificacion").getTime());
                int cantdoc = rs.getInt("cantdoc");
                BigDecimal subtotal = rs.getBigDecimal("subtotal");
                BigDecimal iva = rs.getBigDecimal("iva");
                BigDecimal total = rs.getBigDecimal("total");
                String idclienteVar = rs.getString("idcliente");
                String usuarioVar = rs.getString("usuario");
                int facturado = rs.getInt("facturado");
                int numfactura = rs.getInt("numfactura");

                tramite.setIdtramite(idtramite);
                tramite.setCancelado(canceladoVar);
                tramite.setFechacreacion(fechacreacion);
                tramite.setUltimamodificacion(ultimamodificacion);
                tramite.setCantdoc(cantdoc);
                tramite.setDocumentos(this.consultarDocumentos(idtramite));
                tramite.setSubtotal(subtotal);
                tramite.setIva(iva);
                tramite.setTotal(total);
                tramite.setCliente(this.consultarCliente(idclienteVar));
                tramite.setUsuario(usuarioVar);
                tramite.setAbonos(this.consultarAbonos(idtramite));
                tramite.setFacturado(facturado);
                tramite.setNumfactura(numfactura);

            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarTramite: " + exception);
        }
        return (tramite);
    }

    public ArrayList<Abono> consultarAbonos(int idtramite) {
        ArrayList<Abono> abonos = new ArrayList();

        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM abonos WHERE idtramite=" + idtramite + ";");

            while (rs.next()) {
                int idabono = rs.getInt("idabono");
                BigDecimal valor = rs.getBigDecimal("valor");
                Date fechaabono = new Date(rs.getTimestamp("fechaabono").getTime());

                Abono abono = new Abono();
                abono.setIdabono(idabono);
                abono.setValor(valor);
                abono.setIdtramite(idtramite);
                abono.setFechaabono(fechaabono);

                abonos.add(abono);
//                System.out.println("Tramite " + idtramite + " tiene abono con fecha " + abono.getFechaabono() + " por valor de " + abono.getValor());

            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarAbonos: " + exception);
        }
        return (abonos);
    }

    public ArrayList<Documento> consultarDocumentos(int idtramite) {
        ArrayList<Documento> documentos = new ArrayList();

        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM documentos WHERE idtramite=" + idtramite + " AND anulado=0;");

            while (rs.next()) {
                String iddocumento = rs.getString("iddocumento");
                String benefnombre = rs.getString("benefnombre");
                String benefid = rs.getString("benefid");
                BigDecimal valoradicional = rs.getBigDecimal("valoradicional");
                String descripcionadicional = rs.getString("descripcionadicional");

                int idservicio = rs.getInt("idservicio");
                String descripcion = rs.getString("descripcion");
                String tipo = rs.getString("tipo");
                BigDecimal precio = rs.getBigDecimal("precio");

                Servicio servicio = new Servicio();
                servicio.setId(idservicio);
                servicio.setDescripcion(descripcion);
                servicio.setTipo(tipo);
                servicio.setPrecio(precio);

                Documento documento = new Documento();
                documento.setIddocumento(iddocumento);
                documento.setTramite(idtramite);
                documento.setBenefnombre(benefnombre);
                documento.setBenefid(benefid);
                documento.setServicio(servicio);
                documento.setValoradicional(valoradicional);
                documento.setDescripcionadicional(descripcionadicional);

                documentos.add(documento);

            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarDocumentos: " + exception);
        }
        return (documentos);
    }

    public ArrayList<Documento> busquedaDocumentos(String descripcion, String tipo, java.util.Date fechaInicio, java.util.Date fechaFinal) {
        ArrayList<Documento> documentos = new ArrayList();

        try {
            PreparedStatement st = null;

            String query = "SELECT * FROM documentos WHERE anulado=0";
            String conditions = " AND ";

            if (!(descripcion == null && tipo == null && fechaInicio == null && fechaFinal == null)) {
                query = query + conditions;
            }

            if (descripcion != null) {
                query = query + " idservicio = " + descripcion + " ";
                if (!(tipo == null && fechaInicio == null && fechaFinal == null)) {
                    query = query + " AND ";
                }
            }
            if (tipo != null) {
                query = query + " tipo = '" + tipo + "' ";
                if (!(fechaInicio == null && fechaFinal == null)) {
                    query = query + " AND ";
                }
            }
            if (fechaInicio != null) {
                query = query + " fechacreacion >= '" + new Timestamp(fechaInicio.getTime()) + "' ";
                if (!(fechaFinal == null)) {
                    query = query + " AND ";
                }
            }
            if (fechaFinal != null) {
                query = query + " fechacreacion <= '" + new Timestamp(fechaFinal.getTime()) + "' ";
            }

            query = query + ";";
            System.out.println("Query de busqueda de documentos: " + query);

            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String iddocumento = rs.getString("iddocumento");
                String benefnombre = rs.getString("benefnombre");
                String benefid = rs.getString("benefid");
                BigDecimal valoradicional = rs.getBigDecimal("valoradicional");
                String descripcionadicional = rs.getString("descripcionadicional");
                String descripcion_str = rs.getString("descripcion");

                int idservicio = rs.getInt("idservicio");
                int idtramite = rs.getInt("idtramite");
                tipo = rs.getString("tipo");
                BigDecimal precio = rs.getBigDecimal("precio");

                Servicio servicio = new Servicio();
                servicio.setId(idservicio);
                servicio.setDescripcion(descripcion_str);
                servicio.setTipo(tipo);
                servicio.setPrecio(precio);

                Documento documento = new Documento();
                documento.setIddocumento(iddocumento);
                documento.setTramite(idtramite);
                documento.setBenefnombre(benefnombre);
                documento.setBenefid(benefid);
                documento.setServicio(servicio);
                documento.setValoradicional(valoradicional);
                documento.setDescripcionadicional(descripcionadicional);

                documentos.add(documento);

            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.busquedaDocumentos: " + exception);
        }
        return (documentos);
    }

    public ArrayList<Documento> consultarDocumentosAnulados() {
        ArrayList<Documento> documentos = new ArrayList();

        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM documentos WHERE anulado=1 ORDER BY fechacreacion;");

            while (rs.next()) {
                String iddocumento = rs.getString("iddocumento");
                String benefnombre = rs.getString("benefnombre");
                String benefid = rs.getString("benefid");
                BigDecimal valoradicional = rs.getBigDecimal("valoradicional");
                String descripcionadicional = rs.getString("descripcionadicional");
                Date fechacreacion = rs.getDate("fechacreacion");

                int idservicio = rs.getInt("idservicio");
                int idtramite = rs.getInt("idtramite");
                String descripcion = rs.getString("descripcion");
                String tipo = rs.getString("tipo");
                BigDecimal precio = rs.getBigDecimal("precio");

                Servicio servicio = new Servicio();
                servicio.setId(idservicio);
                servicio.setDescripcion(descripcion);
                servicio.setTipo(tipo);
                servicio.setPrecio(precio);

                Documento documento = new Documento();
                documento.setIddocumento(iddocumento);
                documento.setTramite(idtramite);
                documento.setBenefnombre(benefnombre);
                documento.setBenefid(benefid);
                documento.setServicio(servicio);
                documento.setValoradicional(valoradicional);
                documento.setDescripcionadicional(descripcionadicional);
                documento.setFechacreacion(fechacreacion);

                documentos.add(documento);

            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarDocumentosAnulados: " + exception);
        }
        return (documentos);
    }

    public String consultaIngresos(String tiposervicio, String descripcion, String usuario, String idcliente, java.util.Date fechaInicio, java.util.Date fechaFinal, int cancelado) {
        StringBuilder xml = new StringBuilder();
        System.out.println("Dentro de Conexion.consultaIngresos");
        try {
            PreparedStatement st = null;

            String query = "SELECT "
                    + "d.iddocumento, d.benefid, d.benefnombre, d.descripcion, d.tipo, d.precio, d.iva, d.total,"
                    + "t.idtramite, t.cancelado, t.fechacreacion, t.usuario,"
                    + "c.identificacion, c.razonsocial"
                    + " FROM documentos as d, tramites as t, clientes as c"
                    + " WHERE d.idtramite = t.idtramite"
                    + " AND t.idcliente = c.identificacion";

            String conditions = " AND";
            if (!(tiposervicio == null && descripcion == null && usuario == null && idcliente == null && fechaInicio == null && fechaFinal == null && cancelado == -1)) {
                query = query + conditions;
            }

            if (tiposervicio != null) {
                query = query + " d.tipo = '" + tiposervicio + "' ";
                if (!(descripcion == null && usuario == null && idcliente == null && fechaInicio == null && fechaFinal == null && cancelado == -1)) {
                    query = query + " AND ";
                }
            }

            if (descripcion != null) {
                query = query + " d.descripcion = '" + descripcion + "' ";
                if (!(usuario == null && idcliente == null && fechaInicio == null && fechaFinal == null && cancelado == -1)) {
                    query = query + " AND ";
                }
            }

            if (usuario != null) {
                query = query + " t.usuario = '" + usuario + "' ";
                if (!(idcliente == null && fechaInicio == null && fechaFinal == null && cancelado == -1)) {
                    query = query + " AND ";
                }
            }
            if (idcliente != null) {
                query = query + " c.idcliente = '" + idcliente + "' ";
                if (!(fechaInicio == null && fechaFinal == null && cancelado == -1)) {
                    query = query + " AND ";
                }
            }
            if (fechaInicio != null) {
                query = query + " t.fechacreacion >= '" + new Timestamp(fechaInicio.getTime()) + "' ";
                if (!(fechaFinal == null && cancelado == -1)) {
                    query = query + " AND ";
                }
            }
            if (fechaFinal != null) {
                query = query + " t.fechacreacion <= '" + new Timestamp(fechaFinal.getTime()) + "' ";
                if (!(cancelado == -1)) {
                    query = query + " AND ";
                }
            }
            if (cancelado != -1) {
                query = query + " t.cancelado = " + cancelado + " ";
            }
            query = query + ";";
            System.out.println("Query de busqueda de ingresos por documentos: " + query);

            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();

            xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
            xml.append("<Registros>");
            BigDecimal subtotal = BigDecimal.ZERO;
            BigDecimal iva = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            int cantRegistros = 0;
            while (rs.next()) {
                xml.append("<registro>");
                xml.append("<iddocumento>").append(rs.getString("iddocumento")).append("</iddocumento>");
                xml.append("<benefid>").append(rs.getString("benefid")).append("</benefid>");
                xml.append("<benefnombre>").append(rs.getString("benefnombre")).append("</benefnombre>");
                xml.append("<descripcion>").append(rs.getString("descripcion")).append("</descripcion>");
                xml.append("<tipo>").append(rs.getString("tipo")).append("</tipo>");
                xml.append("<precio>").append(rs.getBigDecimal("precio")).append("</precio>");
                xml.append("<iva>").append(rs.getBigDecimal("iva")).append("</iva>");
                xml.append("<total>").append(rs.getBigDecimal("total")).append("</total>");
                xml.append("<idtramite>").append(rs.getInt("idtramite")).append("</idtramite>");
                String canceladoStr = rs.getInt("cancelado") == 0 ? "No" : "Si";
                xml.append("<cancelado>").append(canceladoStr).append("</cancelado>");
                xml.append("<fechacreacion>").append(new Date(rs.getTimestamp("fechacreacion").getTime())).append("</fechacreacion>");
                xml.append("<usuario>").append(rs.getString("usuario")).append("</usuario>");
                xml.append("<identificacion>").append(rs.getString("identificacion")).append("</identificacion>");
                xml.append("<razonsocial>").append(rs.getString("razonsocial")).append("</razonsocial>");
                xml.append("</registro>");
                subtotal = subtotal.add(rs.getBigDecimal("precio"));
                subtotal = subtotal.setScale(2, RoundingMode.HALF_EVEN);
                cantRegistros++;
            }

            iva = subtotal.multiply(new BigDecimal("0.12"));
            iva = iva.setScale(2, RoundingMode.HALF_EVEN);

            total = subtotal.add(iva);
            total = total.setScale(2, RoundingMode.HALF_EVEN);

            xml.append("<cantRegistros>").append(cantRegistros).append("</cantRegistros>");
            xml.append("<subtotalRegistros>").append(subtotal).append("</subtotalRegistros>");
            xml.append("<ivaRegistros>").append(iva).append("</ivaRegistros>");
            xml.append("<totalRegistros>").append(total).append("</totalRegistros>");
            xml.append("</Registros>");
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultaIngresos: " + exception);
        }
        return (xml.toString());
    }

    public ArrayList<TramiteItems> consultarTramiteItems(int idtramite) {
        ArrayList<TramiteItems> tramiteItems = new ArrayList();

        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT idservicio, descripcion, descripcionadicional, precio, iva, count(iddocumento) as cantidad, sum(precio) as subtotal, sum(total) as total FROM documentos WHERE idtramite = " + idtramite + " GROUP BY idservicio, descripcion, descripcionadicional, precio, iva;");

            while (rs.next()) {
                int idservicio = rs.getInt("idservicio");
                String descripcion = rs.getString("descripcion");
                String descripcionadicional = rs.getString("descripcionadicional");
                if (descripcionadicional != null && !descripcionadicional.equals("")) {
                    descripcion = descripcion + "-" + descripcionadicional;
                }
                BigDecimal precio = rs.getBigDecimal("precio");
                BigDecimal iva = rs.getBigDecimal("iva");
                int cantidad = rs.getInt("cantidad");
                BigDecimal subtotal = rs.getBigDecimal("subtotal");
                BigDecimal total = rs.getBigDecimal("total");

                TramiteItems tramiteItem = new TramiteItems(idtramite, idservicio, descripcion, precio, iva, cantidad, subtotal, total);

                tramiteItems.add(tramiteItem);
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarAbonos: " + exception);
        }
        return (tramiteItems);
    }

    public boolean setTramiteFacturado(Tramite tramite) {
        try {
            PreparedStatement st = con.prepareStatement("UPDATE tramites SET facturado=?, numfactura=? WHERE idtramite=?;");
            st.setInt(1, tramite.getFacturado());
            st.setInt(2, tramite.getNumfactura());
            st.setInt(3, tramite.getIdtramite());
            st.executeUpdate();
            st.close();

            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }

    public int guardarFactura(String claveacceso, Factura factura) throws UnsupportedEncodingException, JAXBException, ParseException, IOException {
        int result = 0;

        InfoTributaria infoTributaria = factura.getInfoTributaria();
        InfoFactura infoFactura = factura.getInfoFactura();
        List<TotalImpuesto> totalconimpuestos = factura.getInfoFactura().getTotalImpuesto();
        List<Detalle> detalles = factura.getDetalle();
        List<CampoAdicional> infoadicional = factura.getCampoAdicional();

        guardarInfoTributaria(infoTributaria);
        guardarInfoFactura(claveacceso, infoFactura);

        if (totalconimpuestos != null) {
            guardarTotalConImpuestos(claveacceso, totalconimpuestos);
        } else {
            System.out.println("Total con impuestos es nulo.");
        }
        if (detalles != null) {
            guardarDetalles(claveacceso, detalles);
        } else {
            System.out.println("Detalles es nulo.");
        }
        if (infoadicional != null) {
            guardarInfoAdicional(claveacceso, infoadicional);
        } else {
            System.out.println("Infoadicional es nulo.");
        }

        return result;
    }

    private int guardarInfoTributaria(InfoTributaria infoTributaria) {
        int result = 0;

        String query = "";
        try {
            PreparedStatement st = null;
            query = "INSERT INTO ce_infotributaria values(?,?,?,?,?,?,?,?,?,?,?);";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setInt(1, Integer.parseInt(infoTributaria.getAmbiente()));
            st.setInt(2, Integer.parseInt(infoTributaria.getTipoEmision()));
            st.setString(3, infoTributaria.getRazonSocial());
            st.setString(4, infoTributaria.getNombreComercial());
            st.setString(5, infoTributaria.getRuc());
            st.setString(6, infoTributaria.getClaveAcceso());
            st.setString(7, infoTributaria.getCodDoc());
            st.setString(8, infoTributaria.getEstab());
            st.setString(9, infoTributaria.getPtoEmi());
            st.setString(10, infoTributaria.getSecuencial());
            st.setString(11, infoTributaria.getDirMatriz());
            st.executeUpdate();
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.guardarInfoTributaria: " + exception + " Query: " + query);
        }

        return result;
    }

    private int guardarInfoFactura(String claveacceso, InfoFactura infoFactura) throws ParseException {
        int result = 0;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        java.util.Date fechaEmision = df.parse(infoFactura.getFechaEmision());

        String query = "";
        try {
            PreparedStatement st = null;
            query = "INSERT INTO ce_infofactura values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setTimestamp(1, new java.sql.Timestamp(fechaEmision.getTime()));
            st.setString(2, infoFactura.getDirEstablecimiento());
            st.setString(3, ((infoFactura.getContribuyenteEspecial() == null) ? null : infoFactura.getContribuyenteEspecial()));
            st.setString(4, infoFactura.getObligadoContabilidad());
            st.setString(5, infoFactura.getTipoIdentificacionComprador());
            st.setString(6, infoFactura.getRazonSocialComprador());
            st.setString(7, infoFactura.getIdentificacionComprador());
            st.setBigDecimal(8, infoFactura.getTotalSinImpuestos());
            st.setBigDecimal(9, infoFactura.getTotalDescuento());
            st.setString(10, claveacceso);
            st.setBigDecimal(11, infoFactura.getPropina());
            st.setBigDecimal(12, infoFactura.getImporteTotal());
            st.setString(13, infoFactura.getMoneda());
            st.executeUpdate();
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.guardarInfoFactura: " + exception + " Query: " + query);
        }
        return result;
    }

    private int guardarTotalConImpuestos(String claveacceso, List<TotalImpuesto> totalconimpuestos) throws ParseException {
        int result = 0;
        String query = "";
        try {
            PreparedStatement st = null;
            query = "INSERT INTO ce_totalconimpuestos values(?,?,?,?,?,?);";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int counter = 0;
            for (TotalImpuesto totalimpuesto : totalconimpuestos) {
                counter++;
                st.setInt(1, Integer.parseInt(totalimpuesto.getCodigo()));
                st.setString(2, totalimpuesto.getCodigoPorcentaje());
                st.setBigDecimal(3, totalimpuesto.getBaseImponible());
                st.setBigDecimal(4, totalimpuesto.getValor());
                st.setInt(5, counter);
                st.setString(6, claveacceso);
                st.executeUpdate();
            }
            result = counter;
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.guardarTotalConImpuestos: " + exception + " Query: " + query);
        }
        return result;
    }

    private int guardarDetalles(String claveacceso, List<Detalle> detalles) throws ParseException {
        int result = 0;
        String query = "";
        try {
            PreparedStatement st = null;
            query = "INSERT INTO ce_detalles values(?,?,?,?,?,?,?,?,?);";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int counter = 0;
            for (Detalle detalle : detalles) {
                counter++;
                System.out.println("Guardando detalle " + counter);
                st.setString(1, detalle.getCodigoPrincipal());
                st.setString(2, detalle.getCodigoAuxiliar());
                st.setString(3, detalle.getDescripcion());
                st.setBigDecimal(4, detalle.getCantidad());
                st.setBigDecimal(5, detalle.getPrecioUnitario());
                st.setBigDecimal(6, detalle.getDescuento());
                st.setBigDecimal(7, detalle.getPrecioTotalSinImpuesto());
                st.setInt(8, counter);
                st.setString(9, claveacceso);
                st.executeUpdate();
                System.out.println("Guardado detalle " + counter);

                List<Impuesto> impuestos = detalle.getImpuesto();
                if (impuestos != null) {
                    guardarImpuestosDetalle(claveacceso, counter, impuestos);
                } else {
                    System.out.println("Impuestos es nulo.");
                }

                List<DetAdicional> detallesadicionales = detalle.getDetAdicional();
                if (detallesadicionales != null) {
                    guardarDetallesAdicionalesDetalle(claveacceso, counter, detallesadicionales);
                } else {
                    System.out.println("Detalles Adicionales es nulo.");
                }
            }
            result = counter;
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.guardarDetalles: " + exception + " Query: " + query);
        }
        return result;
    }

    private int guardarImpuestosDetalle(String claveacceso, int iddetalle, List<Impuesto> impuestos) throws ParseException {
        int result = 0;
        String query = "";
        try {
            PreparedStatement st = null;
            query = "INSERT INTO ce_impuestos values(?,?,?,?,?,?,?,?);";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int counter = 0;
            for (Impuesto impuesto : impuestos) {
                counter++;
                System.out.println("Guardando impuesto " + counter);
                st.setInt(1, Integer.parseInt(impuesto.getCodigo()));
                st.setString(2, impuesto.getCodigoPorcentaje());
                st.setBigDecimal(3, impuesto.getTarifa());
                st.setBigDecimal(4, impuesto.getBaseImponible());
                st.setBigDecimal(5, impuesto.getValor());
                st.setInt(6, counter);
                st.setInt(7, iddetalle);
                st.setString(8, claveacceso);
                st.executeUpdate();
                System.out.println("Guardado impuesto " + counter);
            }
            result = counter;
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.guardarImpuestosDetalle: " + exception + " Query: " + query);
        }
        return result;
    }

    private int guardarDetallesAdicionalesDetalle(String claveacceso, int iddetalle, List<DetAdicional> detallesadicionales) throws ParseException {
        int result = 0;
        String query = "";
        try {
            PreparedStatement st = null;
            query = "INSERT INTO ce_detallesadicionales values(?,?,?,?,?);";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int counter = 0;
            for (DetAdicional detalleadicional : detallesadicionales) {
                counter++;
                st.setString(1, detalleadicional.getNombre());
                st.setString(2, detalleadicional.getValor());
                st.setInt(3, counter);
                st.setInt(4, iddetalle);
                st.setString(5, claveacceso);
                st.executeUpdate();
            }
            result = counter;
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.guardarDetallesAdicionalesDetalle: " + exception + " Query: " + query);
        }
        return result;
    }

    private int guardarInfoAdicional(String claveacceso, List<CampoAdicional> infoadicional) throws ParseException {
        int result = 0;
        String query = "";
        try {
            PreparedStatement st = null;
            query = "INSERT INTO ce_infoadicional values(?,?,?,?);";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int counter = 0;
            for (CampoAdicional campoadicional : infoadicional) {
                counter++;
                st.setString(1, campoadicional.getNombre());
                st.setString(2, campoadicional.getValor());
                st.setInt(3, counter);
                st.setString(4, claveacceso);
                st.executeUpdate();
            }
            result = counter;
            st.close();
        } catch (SQLException exception) {
            System.out.println((new java.util.Date()) + " ERROR:  Al ejecutar Conexion.guardarInfoAdicional: " + exception + " Query: " + query);
        }
        return result;
    }

    public String obtenerClaveAccesoTramite(Tramite tramite) {
        String numeroComprobante = String.format("%09d", tramite.getIdtramite());
        String claveacceso = null;
        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT claveacceso FROM ce_infotributaria WHERE secuencial = '" + numeroComprobante + "';");

            if (rs.first()) {
                claveacceso = rs.getString("claveacceso");
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.obtenerClaveAccesoTramite con tramite: " + exception);
        }
        return claveacceso;
    }

    public String obtenerClaveAccesoTramite(int idtramite) {
        String numeroComprobante = String.format("%09d", idtramite);
        String claveacceso = null;
        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT claveacceso FROM ce_infotributaria WHERE secuencial = '" + numeroComprobante + "';");

            if (rs.first()) {
                claveacceso = rs.getString("claveacceso");
                System.out.println("Clave de acceso es " + claveacceso);
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.obtenerClaveAccesoTramite con idtramite: " + exception);
        }
        return claveacceso;
    }

    public int obtenerNumFactura(int idtramite) {

        int numfactura = 0;
        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT numfactura FROM tramites WHERE idtramite = '" + idtramite + "';");

            if (rs.first()) {
                numfactura = rs.getInt("numfactura");
                System.out.println("El numero de factura es " + numfactura);
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.obtenerClaveAccesoTramite con idtramite: " + exception);
        }
        return numfactura;
    }

    public RetencionRecibida obtenerRetencionRecibida(int idtramite) {
        RetencionRecibida retencion = null;
        int numfactura = this.obtenerNumFactura(idtramite);
        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM retenciones_recibidas WHERE numfactura = " + numfactura + ";");

            if (rs.first()) {
                BigDecimal valor = rs.getBigDecimal("valor");
                Date fecharegistro = new Date(rs.getTimestamp("fecharegistro").getTime());
                int id = rs.getInt("id");
                retencion = new RetencionRecibida();
                retencion.setId(id);
                retencion.setFecharegistro(fecharegistro);
                retencion.setValor(valor);
                retencion.setNumfactura(numfactura);
                System.out.println("Retencion " + retencion.getId() + " obtenida con valor " + retencion.getValor());
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.obtenerRetencionRecibida: " + exception);
        }

        return retencion;
    }

    public RetencionRecibida guardarRetencionRecibida(RetencionRecibida retencion, int idtramite) {
        try {
            PreparedStatement st = null;
            String insert = "INSERT INTO retenciones_recibidas (valor, fecharegistro, numfactura) values (?,?,?);";
            st = con.prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setBigDecimal(1, retencion.getValor());
            st.setTimestamp(2, new Timestamp(retencion.getFecharegistro().getTime()));
            st.setInt(3, retencion.getNumfactura());
            st.executeUpdate();

            String query = "SELECT max(id) as ultimaretencion FROM retenciones_recibidas;";
            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();
            int id = 0;
            if (rs.first()) {
                id = rs.getInt("ultimaretencion");
            }
            retencion.setId(id);

            Tramite tramite = this.consultarTramite(idtramite);
            BigDecimal totalAbonado = tramite.totalAbonos();
            BigDecimal totalTramite = tramite.getTotal();
            BigDecimal valorAdeudado = totalTramite.subtract(totalAbonado).subtract(retencion.getValor());
            valorAdeudado = valorAdeudado.setScale(2, RoundingMode.HALF_EVEN);
            System.out.println("El valor adeudado de tramite " + idtramite + " es ahora " + valorAdeudado);

            if (valorAdeudado.compareTo(BigDecimal.ZERO) == 0) {
                //poner cancelado
                //Credito cero y deuda cero
                String updateTramite = "UPDATE tramites SET cancelado = 1 WHERE idtramite = ?;";
                st = con.prepareStatement(updateTramite, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st.setInt(1, idtramite);
                st.executeUpdate();
                System.out.println("Se puso tramite " + idtramite + " como cancelado");

                String updateCliente = "UPDATE clientes SET credito = 0 WHERE identificacion = ?;";
                st = con.prepareStatement(updateCliente, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st.setString(1, tramite.getCliente().getIdentificacion());
                st.executeUpdate();
                System.out.println("Crédito cero para cliente " + tramite.getCliente().getIdentificacion() + " " + tramite.getCliente().getRazonsocial());
            } else if (valorAdeudado.compareTo(BigDecimal.ZERO) > 0) {
                //Credito cero y deuda pendiente
                String updateCliente = "UPDATE clientes SET credito = 0 WHERE identificacion = ?;";
                st = con.prepareStatement(updateCliente, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st.setString(1, tramite.getCliente().getIdentificacion());
                st.executeUpdate();
                System.out.println("Crédito cero para cliente " + tramite.getCliente().getIdentificacion() + " " + tramite.getCliente().getRazonsocial());
            } else if (valorAdeudado.compareTo(BigDecimal.ZERO) < 0) {
                //devolver diferencia y deuda cero
                Abono ajuste = new Abono();
                ajuste.setValor(valorAdeudado);
                this.guardarAbono(ajuste);
                System.out.println("Se realizó ajuste sobre abonos por   " + valorAdeudado + " para devolver al cliente");
            }

            st.close();

            return retencion;
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.guardarRetencionRecibida: " + exception);
            return null;
        }
    }

    public int guardarGasto(Gasto gasto) {
        try {
            PreparedStatement st = null;
            String insert = "INSERT INTO gastos (descripcion, valor, fechacreacion) values (?,?,?);";
            st = con.prepareStatement(insert);
            st.setString(1, gasto.getDescripcion());
            st.setBigDecimal(2, gasto.getValor());
            st.setTimestamp(3, new Timestamp(gasto.getFechacreacion().getTime()));
            st.executeUpdate();
            st.close();
            return 1;
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.crearCliente: " + exception);
            return 0;
        }
    }

    public ArrayList<Gasto> busquedaGastos(java.util.Date fechaInicio, java.util.Date fechaFinal) {
        ArrayList<Gasto> gastos = new ArrayList();

        try {
            PreparedStatement st = null;

            String query = "SELECT * FROM gastos WHERE fechacreacion >= '" + new Timestamp(fechaInicio.getTime()) + "' AND fechacreacion <= '" + new Timestamp(fechaFinal.getTime()) + "'; ";

            System.out.println("Query de busqueda de gastos: " + query);

            st = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Gasto gasto = new Gasto();

                int idgasto = rs.getInt("idgasto");
                String descripcion = rs.getString("descripcion");
                BigDecimal valor = rs.getBigDecimal("valor");
                Date fechacreacion = new Date(rs.getTimestamp("fechacreacion").getTime());

                gasto.setIdgasto(idgasto);
                gasto.setDescripcion(descripcion);
                gasto.setValor(valor);
                gasto.setFechacreacion(fechacreacion);

                gastos.add(gasto);
            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.busquedaGastos: " + exception);
        }
        return (gastos);
    }

    public Gasto obtenerGasto(int idgasto) {
        Gasto gasto = null;

        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = null;
            rs = st.executeQuery("SELECT * FROM gastos WHERE idgasto=" + idgasto + ";");

            if (rs.first()) {
                gasto = new Gasto();

                String descripcion = rs.getString("descripcion");
                BigDecimal valor = rs.getBigDecimal("valor");
                Date fechacreacion = new Date(rs.getTimestamp("fechacreacion").getTime());

                gasto.setIdgasto(idgasto);
                gasto.setDescripcion(descripcion);
                gasto.setValor(valor);
                gasto.setFechacreacion(fechacreacion);

            }
            rs.close();
            st.close();
        } catch (SQLException exception) {
            System.out.println("ERROR: Al ejecutar Conexion.consultarServicio: " + exception);
        }
        return (gasto);
    }
}
