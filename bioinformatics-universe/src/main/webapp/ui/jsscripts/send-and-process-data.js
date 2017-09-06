$(document).ready(function (){
	$('.result-container').hide();
	takeCareOfValidators();
	takeCareOfFields();

    $('#Go').click(function() {
    	options = getOptions();
    	getData(options);
    });
});


function getData(options) {	
	$.ajax({
	      type: 'POST',
	      url: 'process-request',
	      data : options,
	      success: processRetrievedData,
	      error: error,
	      contentType: false,
	      processData: false,
	      dataType:'text',
	      enctype: 'multipart/form-data'
	    });
}

function processRetrievedData(data) {
	$('#results-load').attr("href", data);
	$('.result-container').show();
}

function error(jqXHR, textStatus, errorThrown) {
	window.alert("Error happened!");
	console.log(jqXHR);
}