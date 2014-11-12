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

        <title>Notaría WebApp - Nuevo Usuario</title>



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
                                <li class="active"><a  href="usuarios.jsp">Usuarios</a></li>
                                <li><a  href="#">Servicios</a></li>
                                <li><a  href="documentos.jsp">Documentos</a></li>
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
                                    Nuevo usuario
                                </header>
                                <div id="NuevoUsuario" name="NuevoUsuario">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label for="usuario">Usuario</label>
                                            <div>
                                                <input id="usuario" name="usuario" class="form-control" type="text" placeholder="usuario" >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="clave">Clave</label>
                                            <div>
                                                <input id="clave" name="clave" class="form-control" type="password" placeholder="clave" >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="usuario">Repetir Clave</label>
                                            <div>
                                                <input id="rclave" name="rclave" class="form-control" type="password" placeholder="clave" >
                                            </div>
                                        </div>                                    
                                        <div class="form-group">
                                            <label for="tipo">Tipo</label>
                                            <div>
                                                <select id="tipo" name="tipo" class="form-control">
                                                    <option value="admin" selected>Administrador</option>
                                                    <option value="usuario">Usuario</option>
                                                    <option value="query">Consultas</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="mail">Correo</label>
                                            <div>
                                                <input id="mail" name="mail" class="form-control" type="text" placeholder="correo" >
                                            </div>
                                        </div>                                                                        
                                        <button type="button" class="btn btn-info" onclick="guardarNuevoUsuario()">Crear usuario</button>
                                    </div> 
                                </div>
                            </section>                            
                        </div>
                        <div class="col-md-8">                            
                            <section class="panel">
                                <header class="panel-heading">
                                    Usuarios
                                </header>
                                <div class="panel-body">
                                    <div class="adv-table">
                                        <table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="usuarios-table">
                                            <thead>
                                                <tr>
                                                    <th></th>
                                                    <th>Usuario</th>
                                                    <th class='hidden-phone'>Rol</th>
                                                    <th class='hidden-phone'>Correo</th>
                                                    <th>Activo</th>
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

        <script src="js/form-validation-script.js"></script>
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
        <script src="js/form-component.js"></script> 
        <script src="js/advanced-form-components.js"></script> 
        <script src="js/ajax.js"></script>
        <script src="js/usuarios.js"></script>
        <script type="text/javascript">
                                            /* Formating function for row details */
                                            function fnFormatDetails(oTable, nTr)
                                            {
                                                var aData = oTable.fnGetData(nTr);
                                                var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
                                                sOut += '<tr><td>correo:</td><td><input id="' + aData[1] +
                                                        '-nuevo-correo" type="text" value="' + $('#email-' + aData[1]).val() + '"></input></td></tr>';
                                                sOut += '<tr><td>Tipo:</td>';
                                                sOut += '<td><select id="' + aData[1] + '-nuevo-tipo" name="' + aData[1] + '-nuevo-tipo" class="form-control">';
                                                if ($('#tipo-' + aData[1]).val() == 'admin') {
                                                    sOut += '<option value="admin" selected >Administrador</option>';
                                                    sOut += '<option value="usuario">Usuario</option>';
                                                } else {
                                                    sOut += '<option value="admin">Administrador</option>';
                                                    sOut += '<option value="usuario" selected >Usuario</option>';
                                                }
                                                sOut += '</select></td></tr>';
                                                sOut += '<tr><td>Activo:</td>';
                                                sOut += '<td><select id="' + aData[1] + '-estado" name="' + aData[1] + '-estado" class="form-control">';
                                                if ($('#estado-' + aData[1]).val() == 'si') {
                                                    sOut += '<option value="si" selected >Si</option>';
                                                    sOut += '<option value="no">No</option>';
                                                } else {
                                                    sOut += '<option value="si">Si</option>';
                                                    sOut += '<option value="no" selected >No</option>';
                                                }
                                                sOut += '</select></td></tr>';
                                                sOut += '<tr><td>nueva clave:</td><td><input id="' + aData[1] +
                                                        '-nueva-clave" type="password" placeholder="escriba nueva clave"></input></td></tr>';

                                                sOut += '<tr><td></td><td><button onclick="modificarUsuario(\'' + aData[1] + '\');">modificar</button></td></tr>';
                                                sOut += '</table>';
                                                return sOut;
                                            }

                                            function agregarDetalles(oTable, idrow) {
                                                /*
                                                 * Insert a 'details' column to the table
                                                 */
//                                                                var nCloneTh = document.createElement('th');
                                                var nCloneTd = document.createElement('td');
                                                nCloneTd.innerHTML = '<img src="assets/advanced-datatable/examples/examples_support/details_open.png">';
                                                nCloneTd.className = "center";

//                                                                $('#servicios_tramite thead tr').each(function() {
//                                                                    this.insertBefore(nCloneTh, this.childNodes[0]);
//                                                                });

                                                $('#' + idrow).prepend('<td><img src="assets/advanced-datatable/examples/examples_support/details_open.png"></td>');
//                                                            $('#servicios_tramite tbody tr').each(function() {
//                                                                this.insertBefore(nCloneTd.cloneNode(true), this.childNodes[0]);
//                                                            });

                                                /*
                                                 * Initialse DataTables, with no sorting on the 'details' column
                                                 */
                                                oTable = $('#usuarios-table').dataTable({
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
//                                                                alert('Primero: ' + $('#' + idrow).html());
                                                    var nTr = $(this).parents('tr')[0];
//                                                                var nTr = $(this).parent().parent()[0];
//                                                                var nTr = oTable.$('#' + idrow);
//                                                                alert('Segundo: ' + oTable.$('#' + idrow));
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
