$(document).ready(function (){
    $('#Go').click(function() {
    	console.log("hay")
    	getData();
    });
});

function getOptions() {
	var firstFile = $('#first-file').val();
	var secondFile = $('#second-file').val();
	
	console.log("firstFile ");
	console.log(firstFile);
	
	var firstFileDelim = $('#first-delim').val();
	var secondFileDelim = $('#second-delim').val();
	
	var firstFileColumn = $('#first-col').val();
	var secondFileColumn = $('#second-col').val();
	
	var options = new FormData();
	
	options.append("firstFile", firstFile);
//	options.append("secondFile", secondFile);
//	options.append("firstFileDelim", firstFileDelim);
//	options.append("secondFileDelim", secondFileDelim);
//	options.append("firstFileColumn", firstFileColumn);
//	options.append("secondFileColumn", secondFileColumn);

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
	      processData: false
	    });
}

function processRetrievedData(data) {
	$('#results').attr("href", data);
	$('#results').show();
}

function error() {
	window.alert("Error happened!");
}
