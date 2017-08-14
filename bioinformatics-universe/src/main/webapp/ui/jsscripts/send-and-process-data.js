function getData() {
	var options = getOptions();
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