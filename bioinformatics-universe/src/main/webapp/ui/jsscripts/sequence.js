$(document).ready(function (){
	$('.result-container').hide();	
		
    $('#Go').click(function() {
    	getData();    	
    });
});

function getOptions() {
	var firstFile = $('#first-file')[0].files[0];
	var secondFile = $('#second-file')[0].files[0];
	
	var firstFileDelim = $('#first-delim').val();
	var secondFileDelim = $('#second-delim').val();
	
	var firstFileColumn = $('#first-col').val();
	var secondFileColumn = $('#second-col').val();
	
	var options = new FormData();
	
	options.append("firstFile", firstFile);
	options.append("secondFile", secondFile);
	options.append("firstFileDelim", firstFileDelim);
	options.append("secondFileDelim", secondFileDelim);
	
	if (firstFileColumn != '') {
		options.append("firstFileColumn", firstFileColumn);
	}
	if (secondFileColumn != '') {
		options.append("secondFileColumn", secondFileColumn);
	}
	
	return options;
}


function getData() {
	var options = getOptions();
	$.ajax({
	      type: 'POST',
	      url: 'get-by-name',
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

function error() {
	window.alert("Error happened!");
}
