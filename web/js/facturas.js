var myTable = $('#documentos-table').dataTable({
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
        /*claveacceso*/null,
        /*#factura*/null,
        /*fechaemision*/null,
        /*cliente*/null,
        /*estado*/null
    ]
});

var Script = function() {
//    consultarFacturasAutorizadas();
}();

function consultarFacturas() {
    var fechaInicio = document.getElementById("fechaInicio").value;
    var fechaFinal = document.getElementById("fechaFinal").value;
    var idcliente = document.getElementById("idcliente").value;
    var comboEstado = document.getElementById("comboEstado");
    var estado = comboEstado.options[comboEstado.selectedIndex].value;

    url = "FacturaCtrl?op=buscarFacturas";
    url = url + "&fechaInicio=" + fechaInicio + "&fechaFinal=" + fechaFinal + "&idcliente=" + idcliente + "&estado=" + estado;
    ai = new AJAXInteraction(url, cargarFacturas, "XML");
    ai.doGet();

}

function cargarFacturas(result) {
    myTable.fnClearTable();

    var xmlresult = result.getElementsByTagName('factura');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var factura = xmlresult[i];
        html += "<tr id='" + factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "-row'>";

        html += "<td class='hidden-phone'>" + factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + "<input id='claveacceso-" + factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "' name='claveacceso-" + factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "' type='hidden' value='" + factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "'></input>"
                + factura.getElementsByTagName('numfactura')[0].firstChild.nodeValue + "</td>";
        html += "<td >" + factura.getElementsByTagName('fechaemision')[0].firstChild.nodeValue + "</td>";
        html += "<td >" + factura.getElementsByTagName('idcliente')[0].firstChild.nodeValue + "</td>";
        html += "<td>" + factura.getElementsByTagName('estado')[0].firstChild.nodeValue + "</td>";
        html += "</tr>";
        document.getElementById("documentos-table").tBodies[0].innerHTML = html;

        agregarDetalles(myTable, factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "-row");

        html = "";
    }

}
function consultarMensajes(claveacceso) {
    

    url = "FacturaCtrl?op=consultarMensajes";
    url = url + "&claveacceso=" + claveacceso;
    ai = new AJAXInteraction(url, cargarMensajes, "XML");
    ai.doGet();

}

function cargarMensajes(result) {

    var xmlresult = result.getElementsByTagName('mensaje');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var mensaje = xmlresult[i];
        html += "<tr>";
        html += "<td>" + mensaje.getElementsByTagName('identificador')[0].firstChild.nodeValue + "</td>";
        html += "<td>" + mensaje.getElementsByTagName('tipo')[0].firstChild.nodeValue + "</td>";
        html += "<td >" + mensaje.getElementsByTagName('mensajeStr')[0].firstChild.nodeValue + "</td>";
        html += "<td >" + mensaje.getElementsByTagName('infoadicional')[0].firstChild.nodeValue + "</td>";
        html += "</tr>";
        document.getElementById("mensajes-autorizacion-"+mensaje.getElementsByTagName('claveacceso')[0].firstChild.nodeValue).tBodies[0].innerHTML = html;
    }

}

function fnFormatDetails(oTable, nTr)
{
    var aData = oTable.fnGetData(nTr);

    var sOut = '';

    if(aData[5] === 'AUTORIZADO'){
        sOut += "<a href='FacturaCtrl?op=verFacturaAutorizada&claveacceso=" + aData[1] + "' target='_blank'>Ver factura</a>";
        sOut += "<br>";
    }    
    sOut += '<button onclick="consultarMensajes(\'' + aData[1] + '\');">Consultar Mensajes</button>';
    sOut += '<table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="mensajes-autorizacion-' + aData[1] + '">';
    sOut += '<thead>';
    sOut += '<tr>';
    sOut += '<th>Identificador</th>';
    sOut += '<th>Tipo</th>';
    sOut += '<th>Mensaje</th>';
    sOut += '<th>Info Adicional</th>';
    sOut += '</tr>';
    sOut += '</thead>';
    sOut += '<tbody>';

    sOut += '</tbody>';
    sOut += '</table>';

    return sOut;
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
    oTable = $('#documentos-table').dataTable({
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
            /*claveacceso*/null,
            /*#factura*/null,
            /*fechaemision*/null,
            /*cliente*/null,
            /*estado*/null

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