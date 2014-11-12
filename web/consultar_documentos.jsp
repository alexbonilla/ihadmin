<%-- 
    Document   : newjsp
    Created on : 05/06/2014, 02:39:14 PM
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
        <meta name="author" content="Mosaddek">
        <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
        <link rel="shortcut icon" href="img/favicon.png">

        <title>Notaría WebApp - Trámites</title>

        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-reset.css" rel="stylesheet">
        <!--external css-->
        <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />

        <link rel="stylesheet" type="text/css" href="assets/bootstrap-fileupload/bootstrap-fileupload.css" />
        <link rel="stylesheet" type="text/css" href="assets/bootstrap-wysihtml5/bootstrap-wysihtml5.css" />
        <link rel="stylesheet" type="text/css" href="assets/bootstrap-datepicker/css/datepicker.css" />
        <link rel="stylesheet" type="text/css" href="assets/bootstrap-timepicker/compiled/timepicker.css" />
        <link rel="stylesheet" type="text/css" href="assets/bootstrap-colorpicker/css/colorpicker.css" />
        <link rel="stylesheet" type="text/css" href="assets/bootstrap-daterangepicker/daterangepicker-bs3.css" />
        <link rel="stylesheet" type="text/css" href="assets/bootstrap-datetimepicker/css/datetimepicker.css" />
        <link rel="stylesheet" type="text/css" href="assets/jquery-multi-select/css/multi-select.css" />


        <!-- Custom styles for this template -->
        <link href="css/style.css" rel="stylesheet">
        <link href="css/style-responsive.css" rel="stylesheet" />

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
        <!--[if lt IE 9]>
          <script src="js/html5shiv.js"></script>
          <script src="js/respond.min.js"></script>
        <![endif]-->
        <script>

            window.dhx_globalImgPath = "img/";

        </script>
        <link rel="STYLESHEET" type="text/css" href="css/dhtmlxcombo.css">
        <script  src="js/dhtmlxcommon.js"></script>
        <script  src="js/dhtmlxcombo.js"></script> 
        <script src="js/ext/dhtmlxcombo_whp.js" type="text/javascript"></script>        
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
                                <!--                                <li><a href="#"><i class=" fa fa-suitcase"></i>Perfil</a></li>
                                                                <li><a href="#"><i class="fa fa-cog"></i> Configuracion</a></li>
                                                                <li><a href="#"><i class="fa fa-bell-o"></i> Notificaciones</a></li>-->
                                <li><a href="login.html"><i class="fa fa-key"></i> Cerrar sesión</a></li>
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
                            <a href="javascript:;" class="active" >
                                <i class="fa fa-tasks"></i>
                                <span>Trámites</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="nuevo_tramite.jsp">Nuevo Trámite</a></li>                                
                                <li class="active"><a  href="consultar_tramites.jsp">Consultar Trámites</a></li>                                
                            </ul>
                        </li>
                        <% if (cuentaSesion.getRoles().contains("admin")) {%>
                        <!--                        <li class="sub-menu">
                                                    <a href="javascript:;" >
                                                        <i class="fa fa-bar-chart-o"></i>
                                                        <span>Ingresos/Egresos</span>
                                                    </a>
                                                    <ul class="sub">
                                                        <li><a  href="nuevo_egreso.jsp">Registrar Egreso</a></li>
                                                        <li><a  href="ingresos.jsp">Consultas</a></li>
                                                    </ul>
                                                </li>-->
                        <%}%>
                        <% if (cuentaSesion.getRoles().contains("admin")) {%>
                        <li class="sub-menu">
                            <a href="javascript:;" >
                                <i class=" fa fa-minus-square"></i>
                                <span>Gastos</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="gastos.jsp">Registrar Gastos</a></li>
                            </ul>
                        </li>
                        <%}%>  
                        <% if (cuentaSesion.getRoles().contains("admin")) {%>
                        <li class="sub-menu">
                            <a href="javascript:;" >
                                <i class=" fa fa-cogs"></i>
                                <span>Configuración</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="basedatos.jsp">Servidor</a></li>
                                <li><a  href="usuarios.jsp">Usuarios</a></li>
                                <li><a  href="#">Servicios</a></li>
                                <li><a  href="documentos.jsp">Documentos</a></li>
                            </ul>
                        </li>
                        <%}%>                        
                        <!--                        <li>
                                                    <a  href="login.html">
                                                        <i class="fa fa-user"></i>
                                                        <span>Login Page</span>
                                                    </a>
                                                </li>                                            -->
                    </ul>
                    <!-- sidebar menu end-->
                </div>
            </aside>
            <!--sidebar end-->
            <!--main content start-->
            <section id="main-content">
                <section class="wrapper">
                    <!-- page start-->
                    <!--date picker start-->
                    <div class="row">
                        <div class="col-md-12">
                            <section class="panel">
                                <header class="panel-heading">
                                    Búsqueda de Documentos - Criterios de búsqueda
                                    <span class="tools pull-right">
                                        <a href="javascript:;" class="fa fa-chevron-down"></a>
                                        <!--<a href="javascript:;" class="fa fa-times"></a>-->
                                    </span>
                                </header>
                                <div class="panel-body">
                                    <div class="form-group">                                        
                                        <div class="form-group col-md-12">
                                            <label for="comboTipoServicio" class="control-label col-md-3">Tipo</label>
                                            <div class="col-md-4">
                                                <select id="comboTipoServicio" name="comboTipoServicios" onchange="obtenerServicios()" class="form-control">
                                                    <option value="diligencias" selected>Diligencias</option>
                                                    <option value="escrituras">Escrituras</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group col-md-12">
                                            <label class="control-label col-md-3">Descripción</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <div id="comboServicios" name="comboServicios"></div>
                                                </div>
                                            </div>
                                        </div>                                                                                                                        
                                        <div class="form-group col-md-12">
                                            <label class="control-label col-md-3">Fecha</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large" data-date="01/01/2014" data-date-format="mm/dd/yyyy">
                                                    <span class="input-group-addon">Desde</span>
                                                    <input type="text" class="form-control dpd1" id="fechaInicio" name="fechaInicio">
                                                    <span class="input-group-addon">Hasta</span>
                                                    <input type="text" class="form-control dpd2" id="fechaFinal" name="fechaFinal">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <button class="btn btn-info" onclick="buscarDocumentos()"><i class="fa fa-search"></i></button>
                                            </div>                                            
                                        </div>                                        
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <section class="panel">
                                <header class="panel-heading">
                                    Búsqueda de Documentos - Resultados de búsqueda
                                </header>
                                <div class="panel-body">
                                    <div class="adv-table">
                                        <table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="documentos-table" width="100%">
                                            <thead>
                                                <tr>
                                                    <th></th>
                                                    <th class='hidden-phone'>Cliente</th>
                                                    <th class='hidden-phone'>Documento</th>
                                                    <th >Descripción</th>
                                                    <th>Fecha</th>
                                                    <th>Usuario</th>
                                                    <th>#Factura</th>
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

                    <!-- page end-->
                    <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="documentosTramite" class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                                    <h4 class="modal-title">Documentos en el trámite</h4>
                                </div>                                
                            </div>
                        </div>
                    </div> 
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

        <script type="text/javascript" src="assets/fuelux/js/spinner.min.js"></script>
        <script type="text/javascript" src="assets/bootstrap-fileupload/bootstrap-fileupload.js"></script>
        <script type="text/javascript" src="assets/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
        <script type="text/javascript" src="assets/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
        <script type="text/javascript" src="assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
        <script type="text/javascript" src="assets/bootstrap-daterangepicker/moment.min.js"></script>
        <script type="text/javascript" src="assets/bootstrap-daterangepicker/daterangepicker.js"></script>
        <script type="text/javascript" src="assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
        <script type="text/javascript" src="assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
        <script type="text/javascript" src="assets/jquery-multi-select/js/jquery.multi-select.js"></script>
        <script type="text/javascript" src="assets/jquery-multi-select/js/jquery.quicksearch.js"></script>

        <!--common script for all pages-->
        <script src="js/common-scripts.js"></script>
        <!--this page  script only-->        
        <script src="js/advanced-form-components.js"></script>
        <script src="js/ajax.js"></script>        
        <% if (cuentaSesion.getRoles().contains("admin") || cuentaSesion.getRoles().contains("query")) {%>
        <script src="js/documentos.js"></script>
        <%}%>

        <%}%>
    </body>
</html>


