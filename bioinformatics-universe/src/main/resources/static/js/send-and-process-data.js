$(document).ready(function (){
	$('.result-container').hide();
	takeCareOfValidators();
	takeCareOfFields();

    $('#Go').click(function() {
    	options = getOptions();
    	getData(options);
    });

    $('#GoAsync').click(function() {
    	options = getOptions();
    	getDataAsync(options);
    	getIfReady();
    });
});

function getIfReady() {
    var jobId = $("#jobId").text();
    var fileGetter = setInterval(function() {tryToGetFileName(jobId)}, 60000);
    if ($('#results-load').attr("href") != '#') {
        clearInterval(fileGetter);
    }
}

function tryToGetFileName(jobId) {
    $.ajax({
      type: 'GET',
      url: 'get-filename',
      dataType:'text',
      contentType: 'application/json',
      data: jobId,
      success: processRetrievedDataAsync,
      error: error
    });
}

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

function getDataAsync(options) {
	$.ajax({
	      type: 'POST',
	      url: 'process-request',
	      data : options,
	      success: function (data) {
	        $("#jobId").text(data);
	        console.log("Job is launched");
	      },
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

function processRetrievedDataAsync(data) {
    if (data != 'notReady') {
        $('#results-load').attr('href', data);
        $('.result-container').show();
	}
}

function error(jqXHR, textStatus, errorThrown) {
	window.alert('Error happened!');
	console.log(jqXHR);
}