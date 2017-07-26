$(document).ready(function (){
	$('.result-container').hide();	

	$('#first-file').change(function() {
	    $('#first-file-info').html(this.files[0].name);
	    $('#first-file-area').val('');
	});

    $('#second-file').change(function() {
	    $('#second-file-info').html(this.files[0].name);
	    $('#second-file-area').val('');
	});

    $('#first-file-area').change(function() {
        $('#first-file-info').empty();
        console.log("before" + $('#first-file').val());
        $('#first-file').val('');
        console.log("after" + $('#first-file').val());

    });

	$('#second-file-area').change(function() {
	    $('#second-file-info').empty();
	    $('#second-file').val('');

	});

    $('#Go').click(function() {
    	getData();
    });
});

function getOptions() {
	var firstFile = $('#first-file')[0].files[0];
	var secondFile = $('#second-file')[0].files[0];

	var firstFileArea = $('#first-file-area').val();
	var secondFileArea = $('#second-file-area').val();


	var firstFileDelim = $('#first-delim').val();
	var secondFileDelim = $('#second-delim').val();
	
	var firstFileColumn = $('#first-col').val();
	var secondFileColumn = $('#second-col').val();
	
	var options = new FormData();
    console.log("firstFile ");
    console.log(firstFile);

    console.log("firstFileArea ");
    console.log(firstFileArea);


	if (typeof firstFile != 'undefined') {
	    console.log("wrong");
	    options.append("firstFile", firstFile);
	}
	if (typeof secondFile != 'undefined') {
	    options.append("secondFile", secondFile);
	}


	options.append("firstFileTextArea", firstFileArea);
	options.append("secondFileTextArea", secondFileArea);

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
