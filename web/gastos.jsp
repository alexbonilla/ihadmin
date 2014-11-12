<%-- 
    Document   : index
    Created on : 22/05/2014, 09:14:33 AM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.iveloper.entidades.Cuenta" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="Alex">
        <link rel="shortcut icon" href="img/favicon.png">

        <title>Notaría WebApp - Gastos</title>



        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-reset.css" rel="stylesheet">
        <!--external css-->
        <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />

        <!-- Custom styles for this template -->
        <link href="css/style.css" rel="stylesheet">
        <link href="css/style-responsive.css" rel="stylesheet" />

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
        <!--[if lt IE 9]>
          <script src="js/html5shiv.js"></script>
          <script src="js/respond.min.js"></script>
        <![endif]-->       

    </head>

    <body>
        <% Cuenta cuentaSesion = (Cuenta) session.getAttribute("cuentaSesion"); %>
        <% if (cuentaSesion != null) { %>       
        <section id="container" class="">
            <!--header start-->
            <header class="header white-bg">
                <div class="sidebar-toggle-box">
                    <div data-original-title="Toggle Navigation" data-placement="right" class="fa fa-bars tooltips"></div>
                </div>
                <!--logo start-->
                <a href="nuevo_tramite.jsp" class="logo" >Escritorio de <span>Notaría</span></a>
                <!--logo end-->

                <div class="top-nav ">
                    <ul class="nav pull-right top-menu">
                        <li>
                            <input type="text" class="form-control search" placeholder="Search">
                        </li>
                        <!-- user login dropdown start-->
                        <li class="dropdown">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="username">usuario</span>
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu extended logout">
                                <div class="log-arrow-up"></div>
                                <li><a href="CuentaCtrl?op=cerrar_sesion"><i class="fa fa-key"></i> Cerrar sesión</a></li>
                            </ul>
                        </li>
                        <!-- user login dropdown end -->
                    </ul>
                </div>
            </header>
            <!--header end-->
            <!--sidebar start-->
            <aside>
                <div id="sidebar"  class="nav-collapse ">
                    <!-- sidebar menu start-->
                    <ul class="sidebar-menu" id="nav-accordion">                        
                        <li class="sub-menu">
                            <a href="javascript:;"  >
                                <i class="fa fa-tasks"></i>
                                <span>Trámites</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="nuevo_tramite.jsp">Nuevo Trámite</a></li>                                                                
                                    <% if (cuentaSesion.getRoles().contains("admin")) {%>
                                <li><a  href="consultar_tramites.jsp">Consultar Trámites</a></li> 
                                    <%}%>
                            </ul>
                        </li>
                        <% if (cuentaSesion.getRoles().contains("admin")) {%>
                        <li class="sub-menu">
                            <a href="javascript:;" class="active">
                                <i class=" fa fa-minus-square"></i>
                                <span>Gastos</span>
                            </a>
                            <ul class="sub">
                                <li class="active"><a  href="gastos.jsp">Registrar Gastos</a></li>
                            </ul>
                        </li>
                        <%}%>
                        <% if (cuentaSesion.getRoles().contains("admin")) {%>
                        <li class="sub-menu">
                            <a href="javascript:;">
                                <i class=" fa fa-cogs"></i>
                                <span>Configuración</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="basedatos.jsp">Servidor</a></li>
                                <li><a  href="usuarios.jsp">Usuarios</a></li>
                                <li><a  href="#">Servicios</a></li>                                
                            </ul>
                        </li>
                        <%}%> 
                        <% if (cuentaSesion.getRoles().contains("admin") || cuentaSesion.getRoles().contains("query")) {%>
                        <li class="sub-menu">
                            <a href="javascript:;" >
                                <i class=" fa fa-cogs"></i>
                                <span>Documentos</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="consultar_documentos.jsp">Consultar Documentos</a></li>
                                <% if (cuentaSesion.getRoles().contains("admin")) {%>
                                <li><a  href="documentos_anulados.jsp">Documentos Anulados</a></li>
                                <%}%>                                                                                           
                            </ul>
                        </li>
                        <%}%>
                    </ul>
                    <!-- sidebar menu end-->
                </div>
            </aside>
            <!--sidebar end-->
            <!--main content start-->
            <section id="main-content">
                <section class="wrapper">
                    <!-- page start-->
                    <!--multiple select start-->
                    <div class="row">
                        <div class="col-md-4">
                            <section class="panel col-md-12">
                                <header class="panel-heading">
                                    Registrar gasto
                                </header>
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label for="descripcion_gasto">Descripción</label>
                                        <div>
                                            <input id="descripcion_gasto" name="descripcion_gasto" class="form-control" type="text" placeholder="Descripción" >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="valor_gasto">Valor</label>
                                        <div>
                                            <input id="valor_gasto" name="valor_gasto" class="form-control" type="text" placeholder="0.00" >
                                        </div>
                                    </div>                                                                                                                                              
                                    <button type="submit" class="btn btn-info" onclick="guardarNuevoGasto()">Guardar</button>
                                </div>                                
                            </section>                            
                        </div>
                        <div class="col-md-8">                            
                            <section class="panel">
                                <header class="panel-heading">
                                    Gastos
                                </header>
                                <div class="panel-body">
                                    <div class="adv-table">
                                        <table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="gastos-table">
                                            <thead>
                                                <tr>                                                    
                                                    <th>Descripción</th>
                                                    <th class='hidden-phone'>Fecha</th>
                                                    <th>Valor</th>
                                                </tr>
                                            </thead>
                                            <tbody>

                                            </tbody>
                                        </table>

                                    </div>
                                </div>
                            </section>
                        </div>                        
                    </div>
                    <!--multiple select end-->

                    <!-- page end-->
                </section>
            </section>
            <!--main content end-->
            <!--footer start-->
            <footer class="site-footer">
                <div class="text-center">
                    2014 &copy; notariaApp by Iveloper.
                    <a href="#" class="go-top">
                        <i class="fa fa-angle-up"></i>
                    </a>
                </div>
            </footer>
            <!--footer end-->
        </section>

        <!-- js placed at the end of the document so the pages load faster -->
        <!--<script src="js/jquery.js"></script>-->
        <script type="text/javascript" language="javascript" src="assets/advanced-datatable/media/js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script class="include" type="text/javascript" src="js/jquery.dcjqaccordion.2.7.js"></script>
        <script src="js/jquery.scrollTo.min.js"></script>
        <script src="js/jquery.nicescroll.js" type="text/javascript"></script>
        <script src="js/respond.min.js" ></script>


        <script type="text/javascript" language="javascript" src="assets/advanced-datatable/media/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="assets/data-tables/DT_bootstrap.js"></script>        

        <!--this page plugins-->

        <!--common script for all pages-->
        <script src="js/common-scripts.js"></script>
        <!--this page  script only-->
        <script src="js/ajax.js"></script>
        <script src="js/gastos.js"></script>        
        <%}%>
    </body>
</html>
