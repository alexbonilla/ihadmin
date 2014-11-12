<%-- 
    Document   : ingresos
    Created on : 09/06/2014, 05:02:10 PM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="Mosaddek">
        <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
        <link rel="shortcut icon" href="img/favicon.png">

        <title>Notaría WebApp - Ingresos</title>

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

        <section id="container" class="">
            <!--header start-->
            <header class="header white-bg">
                <div class="sidebar-toggle-box">
                    <div data-original-title="Toggle Navigation" data-placement="right" class="fa fa-bars tooltips"></div>
                </div>
                <!--logo start-->
                <a href="old_operations.html" class="logo" >Escritorio de <span>Notaría</span></a>
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
                                <li><a href="#"><i class=" fa fa-suitcase"></i>Perfil</a></li>
                                <li><a href="#"><i class="fa fa-cog"></i> Configuracion</a></li>
                                <li><a href="#"><i class="fa fa-bell-o"></i> Notificaciones</a></li>
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
                            <a href="javascript:;">
                                <i class="fa fa-tasks"></i>
                                <span>Trámites</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="index.html">Nuevo Trámite</a></li>                                
                                <li><a  href="tramites.jsp">Consultar Trámites</a></li>                                
                            </ul>
                        </li>
                        <li class="sub-menu">
                            <a href="javascript:;" class="active">
                                <i class="fa fa-bar-chart-o"></i>
                                <span>Ingresos/Egresos</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="nuevo_egreso.jsp">Registrar Egreso</a></li>
                                <li class="active"><a  href="ingresos.jsp">Consultas</a></li>
                            </ul>
                        </li>                        
                        <li class="sub-menu">
                            <a href="javascript:;" >
                                <i class=" fa fa-cogs"></i>
                                <span>Configuración</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="morris.html">Servidor</a></li>
                                <li><a  href="chartjs.html">Usuarios</a></li>
                                <li><a  href="flot_chart.html">Servicios</a></li>
                                <li><a  href="flot_chart.html">Documentos</a></li>
                            </ul>
                        </li>
                        <li>
                            <a  href="login.html">
                                <i class="fa fa-user"></i>
                                <span>Login Page</span>
                            </a>
                        </li>                                            
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
                                    Valores por Documentos - Criterios de búsqueda
                                    <span class="tools pull-right">
                                        <a href="javascript:;" class="fa fa-chevron-down"></a>
                                        <!--<a href="javascript:;" class="fa fa-times"></a>-->
                                    </span>
                                </header>
                                <div class="panel-body">
                                    <div class="form-horizontal tasi-form">
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Tipo</label>  
                                            <div class="col-md-4">                                            
                                                <select class="form-control input-sm m-bot15" id="comboTipoServicio" name="comboTipoServicios" onchange="obtenerServicios()">
                                                    <option value="null" selected>Todos</option>
                                                    <option value="diligencias" selected>Diligencias</option>
                                                    <option value="escrituras">Escrituras</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Servicio</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <div id="comboServicios" name="comboServicios"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Usuario</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <div id="comboUsuarios" name="comboUsuarios"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Cliente</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <div id="comboClientes" name="comboClientes"></div>
                                                </div>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Estado</label>
                                            <div class="col-md-4">
                                                <select class="form-control input-sm m-bot15" onchange="buscarTramites()" id="comboEstado" name="comboEstado">
                                                    <option value="-1">Todos</option>
                                                    <option value="0">No cancelado</option>
                                                    <option value="1">Cancelado</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
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
                                                <button class="btn btn-info" onclick="consultarIngresos()"><i class="fa fa-search"></i></button>
                                            </div>                                            
                                        </div>                                        
                                    </div>
                                </div>
                            </section>
                            <section class="panel">
                                <header class="panel-heading">
                                    Valores por Documentos - Resultados de búsqueda
                                </header>
                                <div class="panel-body">
                                    <div class="form-horizontal tasi-form">                                        
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Resultados</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <input id="cantRegistros" type="text" disabled="" style="align:right;"></input>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Subtotal</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <input id="subtotalRegistros" type="text" disabled="" style="align:right;"></input>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">IVA</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <input id="ivaRegistros" type="text" disabled="" style="align:right;"></input>
                                                </div>
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Total</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <input id="totalRegistros" type="text" disabled="" style="align:right;"></input>
                                                </div>
                                            </div>
                                        </div>                                        
                                    </div>
                                    <div class="panel-body">
                                        <div class="adv-table">
                                            <table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="ingresos-documentos">
                                                <thead>
                                                    <tr>
                                                        <th class='hidden-phone'># Documento</th>
                                                        <th class='hidden-phone'>Beneficiario</th>
                                                        <th>Descripción</th>
                                                        <th>Tipo</th>
                                                        <th>Precio</th>
                                                        <th class='hidden-phone'>IVA</th>                                                    
                                                        <th class='hidden-phone'>Total</th>
                                                        <th class='hidden-phone'># Trámite</th>
                                                        <th class='hidden-phone'>Cancelado</th>
                                                        <th class='hidden-phone'>Fecha</th>
                                                        <th class='hidden-phone'>Usuario</th>
                                                        <th class='hidden-phone'>Id Cliente</th>
                                                        <th class='hidden-phone'>Cliente</th>
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
                    <!--date picker end-->

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
        <script src="js/finanzas.js"></script>        
    </body>
</html>