$(document).ready(function (){
	$('.result-container').hide();

    $("#first").validate( {
        rules: {
            "first-col": {
                digits: true
            }
        },
        messages: {
            "first-col": {
                digits: "Column should be an integer."
            }
        }
    });



    $("#second").validate( {
        rules: {
            "second-col": {
                digits: true,
                maxlength: 2
            }
        },
        messages: {
            "second-col": {
                digits: "Column should be an integer."
            }
        }
    });



    $("#first-col").change(function() {
        $("#first-col").valid();
    });

    $("#second-col").change(function() {
        $("#second-col").valid();
    });



	$('#first-file').change(function() {
	    $('#first-file-info').html(this.files[0].name);
	    $('#first-file-area').val('');
	    $('.result-container').hide();
	});

    $('#second-file').change(function() {
	    $('#second-file-info').html(this.files[0].name);
	    $('#second-file-area').val('');
	    $('.result-container').hide();
	});

    $('#first-file-area').change(function() {
        $('#first-file-info').empty();
        $('#first-file').val('');
        $('.result-container').hide();
    });

	$('#second-file-area').change(function() {
	    $('#second-file-info').empty();
	    $('#second-file').val('');
	    $('.result-container').hide();

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

	if (typeof firstFile != 'undefined') {
	    options.append("firstFile", firstFile);
	}
	if (typeof secondFile != 'undefined') {
	    options.append("secondFile", secondFile);
	}

 	if (firstFileDelim != null) {
	    options.append("firstFileDelim", firstFileDelim);
	}
	if (firstFileDelim != null) {
	    options.append("secondFileDelim", secondFileDelim);
	}

    if (firstFileArea != '') {
        options.append("firstFileTextArea", firstFileArea);
    }
    if (secondFileArea != '') {
        options.append("secondFileTextArea", secondFileArea);
    }

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

function error(jqXHR, textStatus, errorThrown) {
	window.alert("Error happened!");
	console.log(jqXHR.responseText);
	console.log(jqXHR);
	console.log("=======");
	console.log(textStatus);
	console.log("=======");
	console.log(errorThrown);
}
