<%-- 
    Document   : documentos
    Created on : 21/09/2014, 11:01:15 PM
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

        <title>Notaría WebApp - Documentos Anulados</title>



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
                                <i class="fa fa-folder-open"></i>
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
                            <a href="javascript:;" class="active">
                                <i class=" fa fa-cogs"></i>
                                <span>Configuración</span>
                            </a>
                            <ul class="sub">
                                <li><a  href="basedatos.jsp">Servidor</a></li>
                                <li><a  href="usuarios.jsp">Usuarios</a></li>
                                <li><a  href="#">Servicios</a></li>
                                <li class="active"><a  href="documentos.jsp">Documentos</a></li>
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

                        <div class="col-md-12">                            
                            <section class="panel">
                                <header class="panel-heading">
                                    Documentos Anulados
                                </header>
                                <div class="panel-body">
                                    <div class="adv-table">
                                        <table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="documentos-anulados-table">
                                            <thead>
                                                <tr>
                                                    <th></th>
                                                    <th>Documento</th>
                                                    <th class='hidden-phone'>Fecha de Creación</th>
                                                    <th class='hidden-phone'>Id Benef</th>
                                                    <th>Beneficiario</th>
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
        <script src="js/documentos_anulados.js"></script>
        <script type="text/javascript">
            /* Formating function for row details */
            function fnFormatDetails(oTable, nTr)
            {
                var aData = oTable.fnGetData(nTr);
                var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
                sOut += '<tr><td></td><td><button onclick="reutilizarDocumento(\'' + aData[1] + '\');">reutilizar</button></td></tr>';
                sOut += '</table>';
                return sOut;
            }

            function agregarDetalles(oTable, idrow) {

                var nCloneTd = document.createElement('td');
                nCloneTd.innerHTML = '<img src="assets/advanced-datatable/examples/examples_support/details_open.png">';
                nCloneTd.className = "center";


                $('#' + idrow).prepend('<td><img src="assets/advanced-datatable/examples/examples_support/details_open.png"></td>');

                /*
                 * Initialse DataTables, with no sorting on the 'details' column
                 */
                oTable = $('#documentos-anulados-table').dataTable({
                    "bDestroy": true,
                    "bPaginate": false,
                    "bFilter": false,
                    "bInfo": false,
                    "aoColumnDefs": [
                        {"bSortable": false, "aTargets": [0]}
                    ],
                    "aaSorting": [[1, 'asc']],
                    "aoColumns": [
                        /*index*/ null,
                        /* Subject Name */ null,
                        /* Address */ null,
                        /* LinkedWithCompany */ null,
                        /* Work Tel */ null
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

