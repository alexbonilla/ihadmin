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


        <link rel="stylesheet" type="text/css" href="assets/bootstrap-datepicker/css/datepicker.css" />
        <link rel="stylesheet" type="text/css" href="assets/bootstrap-daterangepicker/daterangepicker-bs3.css" />

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
                    <!--state overview start-->
                    <% if (cuentaSesion.getRoles().contains("admin")) {%>
                    <div class="row state-overview">
                        <div class="col-lg-3 col-sm-6">
                            <section class="panel">
                                <div class="symbol terques">
                                    <i class="fa  fa-folder-open"></i>
                                </div>
                                <div class="value">
                                    <h1 class="count">
                                        0
                                    </h1>
                                    <p>en trámites</p>
                                </div>
                            </section>
                        </div>
                        <div class="col-lg-3 col-sm-6">
                            <section class="panel">
                                <div class="symbol yellow">
                                    <i class="fa fa-usd"></i>
                                </div>
                                <div class="value">
                                    <h1 class=" count2">
                                        0
                                    </h1>
                                    <p>en adicionales</p>
                                </div>
                            </section>
                        </div>
                        <div class="col-lg-3 col-sm-6">
                            <section class="panel">
                                <div class="symbol blue">
                                    <i class="fa fa-money"></i>
                                </div>
                                <div class="value">
                                    <h1 class=" count3">
                                        0
                                    </h1>
                                    <p>en abonos</p>
                                </div>
                            </section>
                        </div>
                        <div class="col-lg-3 col-sm-6">
                            <section class="panel">
                                <div class="symbol red">
                                    <i class="fa fa-shopping-cart"></i>
                                </div>
                                <div class="value">
                                    <h1 class=" count4">
                                        0
                                    </h1>
                                    <p>en gastos</p>
                                </div>
                            </section>
                        </div>
                    </div>
                    <%}%>
                    <!--state overview end-->
                    <!--date picker start-->
                    <div class="row">
                        <div class="col-md-12">
                            <section class="panel">
                                <header class="panel-heading">
                                    Búsqueda de Trámites - Criterios de búsqueda
                                    <span class="tools pull-right">
                                        <a href="javascript:;" class="fa fa-chevron-down"></a>
                                        <!--<a href="javascript:;" class="fa fa-times"></a>-->
                                    </span>
                                </header>
                                <div class="panel-body">
                                    <div class="form-horizontal tasi-form">
                                        <% if (cuentaSesion.getRoles().contains("admin") || cuentaSesion.getRoles().contains("query")) {%>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Usuario</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <div id="comboUsuarios" name="comboUsuarios"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <%}%>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Id Cliente</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <div id="comboClientes" name="comboClientes"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">Nombre Cliente</label>
                                            <div class="col-md-4">
                                                <div class="input-group input-large">
                                                    <div id="comboNombreClientes" name="comboNombreClientes"></div>
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
                                                <button class="btn btn-info" onclick="buscarTramites()"><i class="fa fa-search"></i></button>
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
                                    Búsqueda de Trámites - Resultados de búsqueda
                                </header>
                                <div class="panel-body">
                                    <div class="adv-table">
                                        <table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="tramites-table" width="100%">
                                            <thead>
                                                <tr>
                                                    <th></th>
                                                    <th># Trámite</th>
                                                    <th class='hidden-phone'>Fecha</th>
                                                    <th class='hidden-phone'>Usuario</th>
                                                    <th>Cliente</th>
                                                    <th class='hidden-phone'>Documentos</th>
                                                    <th class='hidden-phone'>SubTotal-Retencion<div id="subtotalTramites"></div></th>                                                    
                                            <th class='hidden-phone'>IVA<div id="ivaTramites" ></div></th>
                                            <th class='hidden-phone'>Total<div id="totalTramites" ></div></th>
                                            <th class='hidden-phone'>Adicionales<div id="totaladicionalTramites"></div></th>
                                            <th class='hidden-phone'>Abonos<div id="abonosTramites"></div></th>
                                            <th>Pendiente</th>
                                            <th class='hidden-phone'>Cancelado</th>
                                            <th class='hidden-phone'>Facturado</th>
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
        <script type="text/javascript" src="assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="assets/bootstrap-daterangepicker/daterangepicker.js"></script>
        <!--common script for all pages-->
        <script src="js/common-scripts.js"></script>
        <!--this page  script only-->        
        <script src="js/advanced-form-components.js"></script>
        <script src="js/ajax.js"></script>        
        <% if (cuentaSesion.getRoles().contains("admin") || cuentaSesion.getRoles().contains("query")) {%>
        <script src="js/tramites.js"></script>
        <%} else {%>
        <script src="js/tramites_lim.js"></script>
        <%}%>
        <script type="text/javascript">
                                                    /* Formating function for row details */
                                                    function fnFormatDetails(oTable, nTr)
                                                    {
                                                        var aData = oTable.fnGetData(nTr);

                                                        var sOut = '';
            <% if (cuentaSesion.getRoles().contains("admin")) {%>
                                                        sOut += '<table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" >';
                                                        sOut += '<tbody>';
                                                        sOut += '<tr><td><input id="' + aData[1] + '-total-abonar" type="text" placeholder="0.00"></input><button onclick="abonarTramite(\'' + aData[1] + '\');">Abonar</button></td></tr>';
                                                        if ($('#facturado-' + aData[1]).val() === 'Si') {
                                                            sOut += '<tr><td><input id="' + aData[1] +
                                                                    '-retencion" type="text" placeholder="0.00"></input><button onclick="guardarRetencionRecibida(\'' +
                                                                    aData[1] + '\');">Retenido</button></td></tr>';
                                                            sOut += '<tr><td><input id="' + aData[1] +
                                                                    '-numfactura" type="hidden" value="0"></input><button onclick="facturarTramite(' +
                                                                    aData[1] + ');">Ver Factura</button></td></tr>';
                                                            sOut += '<tr><td><input id="' + aData[1] +
                                                                    '-nuevo-numfactura" type="text" value="' + $('#numfactura-' + aData[1]).val() + '"></input><button onclick="cambiarNumFacturaTramite(' + aData[1] + ');">Cambiar No. Factura</button></td></tr>';
                                                        } else {
                                                            sOut += '<tr><td><input id="' + aData[1] +
                                                                    '-numfactura" type="text" placeholder="número de factura"></input><button onclick="facturarTramite(' + aData[1] + ');">Facturar</button></td></tr>';
                                                        }
                                                        sOut += '<tr><td><button onclick="anularTramite(' + aData[1] + ');">Anular Trámite</button></td></tr>';
                                                        sOut += '</tbody>';
                                                        sOut += '</table>';
            <%}%>
                                                        sOut += '<button onclick="consultarDocumentos(\'' + aData[1] + '\');">Consultar Documentos</button>';
                                                        sOut += '<table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="documentos-tramite-' + aData[1] + '">';
                                                        sOut += '<thead>';
                                                        sOut += '<tr>';
                                                        sOut += '<th># Documento</th>';
                                                        sOut += '<th class="hidden-phone"># Trámite</th>';
                                                        sOut += '<th class="hidden-phone">Beneficiario</th>';
                                                        sOut += '<th >Descripción</th>';
                                                        sOut += '<th class="hidden-phone">Precio</th>';
                                                        sOut += '<th class="hidden-phone">Adicional</th>';
                                                        sOut += '<th class="hidden-phone"></th>';
                                                        sOut += '</tr>';
                                                        sOut += '</thead>';
                                                        sOut += '<tbody>';

                                                        sOut += '</tbody>';
                                                        sOut += '</table>';

                                                        sOut += '<button onclick="consultarAbonos(\'' + aData[1] + '\');">Consultar Abonos</button>';
                                                        sOut += '<table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="abonos-tramite-' + aData[1] + '">';
                                                        sOut += '<thead>';
                                                        sOut += '<tr>';
                                                        sOut += '<th class="hidden-phone"># Trámite</th>';
                                                        sOut += '<th>Fecha Abono</th>';
                                                        sOut += '<th>Valor</th>';
                                                        sOut += '</tr>';
                                                        sOut += '</thead>';
                                                        sOut += '<tbody>';

                                                        sOut += '</tbody>';
                                                        sOut += '</table>';

                                                        return sOut;
                                                    }

                                                    function actualizarAbonoTotal(idtramite) {
                                                        var credito = document.getElementById(idtramite + '-credito-abono');
                                                        var abono = document.getElementById(idtramite + '-nuevo-abono');
                                                        var totalAbono = document.getElementById(idtramite + '-total-abonar');
                                                        totalAbono.value = Number(credito.value) + Number(abono.value);
                                                        totalAbono.value = roundNumber(totalAbono.value, 2);
                                                    }

                                                    function agregarDetalles(oTable, idrow) {

                                                        /*
                                                         * Insert a 'details' column to the table
                                                         */
                                                        var nCloneTd = document.createElement('td');
                                                        nCloneTd.innerHTML = '<img src="assets/advanced-datatable/examples/examples_support/details_open.png">';
                                                        nCloneTd.className = "center";
                                                        $('#' + idrow).prepend('<td><img src="assets/advanced-datatable/examples/examples_support/details_open.png"></td>');


                                                        /*
                                                         * Initialse DataTables, with no sorting on the 'details' column
                                                         */
                                                        oTable = $('#tramites-table').dataTable({
                                                            "bDestroy": true,
                                                            "bPaginate": false,
                                                            "bFilter": false,
                                                            "bInfo": false,
                                                            "aoColumnDefs": [
                                                                {"bSortable": false, "aTargets": [0]}
                                                            ],
                                                            "aaSorting": [[1, 'desc']],
                                                            "aoColumns": [
                                                                /*info*/null,
                                                                /*# Trámite*/null,
                                                                /*Fecha*/null,
                                                                /*Usuario*/null,
                                                                /*Cliente*/null,
                                                                /*Cant. Documentos*/null,
                                                                /*SubTotal-Retencion*/null,
                                                                /*IVA*/null,
                                                                /*Total*/null,
                                                                /*Adicionales*/null,
                                                                /*Abonos*/null,
                                                                /*Pendiente*/null,
                                                                /*Cancelado*/null,
                                                                /*Facturado*/null

                                                            ]
                                                        });

                                                        /* Add event listener for opening and closing details
                                                         * Note that the indicator for showing which row is open is not controlled by DataTables,
                                                         * rather it is done here
                                                         */
                                                        $('#' + idrow + ' td img').click(function() {
                                                            var nTr = $(this).parents('tr')[0];
                                                            if (oTable.fnIsOpen(nTr))
                                                            {
                                                                /* This row is already open - close it */
                                                                this.src = "assets/advanced-datatable/examples/examples_support/details_open.png";
                                                                oTable.fnClose(nTr);
                                                            }
                                                            else
                                                            {
                                                                /* Open this row */
                                                                this.src = "assets/advanced-datatable/examples/examples_support/details_close.png";
                                                                oTable.fnOpen(nTr, fnFormatDetails(oTable, nTr), 'details');
                                                            }
                                                        });
                                                    }
        </script>
        <%}%>
    </body>
</html>


