$(document).ready(function (){
	$('.result-container').hide();
	takeCareOfValidators();
	takeCareOfFields();

    $('#Go').click(function() {
    	getData();
    });
});

function takeCareOfValidators() {
    jQuery.validator.addMethod("clearIncorrect", function(value, element) {
        if (!$.isNumeric(value) || (value.slice(0,1) == 0)) {
            $(element).val('');
            return false;
        } else {
            return true;
        }
    }, "Column should be a number, greater than 0.");


    var valid1 = $("#first").validate( {
        rules: {
            "first-col": {
                clearIncorrect: true,
                digits: true
            }
        }
    });
    var valid2 = $("#second").validate( {
        rules: {
            "second-col": {
                clearIncorrect: true,
                digits: true
            }
        }
    });

    $("#first-col").keyup(function() {
        $("#first-col").valid();
    });
    $("#second-col").keyup(function() {
        $("#second-col").valid();
    });
    $("#first-col").focusout(function() {
        valid1.resetForm();
        valid2.resetForm();
    });
    $("#second-col").focusout(function() {
        valid1.resetForm();
        valid2.resetForm();
    });
}

function takeCareOfFields() {
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

    $('#first-file-area').keyup(function() {
        $('#first-file-info').empty();
        $('#first-file').val('');
        $('.result-container').hide();
    });

	$('#second-file-area').keyup(function() {
	    $('#second-file-info').empty();
	    $('#second-file').val('');
	    $('.result-container').hide();

	});
}

function getOptions() {
	var firstFile = $('#first-file')[0].files[0];
	if (typeof $('#second-file')[0] != 'undefined') {
	    var secondFile = $('#second-file')[0].files[0];
    }
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

 	if (typeof firstFileDelim != 'undefined') {
        if (firstFileDelim != null) {
	        options.append("firstFileDelim", firstFileDelim);
	    }
	}
	if (typeof secondFileDelim != 'undefined') {
	    if (secondFileDelim != null) {
	        options.append("secondFileDelim", secondFileDelim);
	    }
	}

    if (typeof firstFileArea != 'undefined') {
        if (firstFileArea != '') {
            options.append("firstFileTextArea", firstFileArea);
        }
    }
    if (typeof secondFileArea != 'undefined') {
        if (secondFileArea != '') {
            options.append("secondFileTextArea", secondFileArea);
        }
    }

	if (typeof firstFileColumn != 'undefined') {
	    if (firstFileColumn != '') {
 		    options.append("firstFileColumn", firstFileColumn);
 		}
	}
	if (typeof secondFileColumn != 'undefined') {
	    if (secondFileColumn != '') {
		    options.append("secondFileColumn", secondFileColumn);
		}
	}

    if ($('#subnavigation-tab').text() == "make-unique") {
        options.append("commandToBeProcessedBy", "make-unique");
    }
    if ($('#subnavigation-tab').text() == "get-by-name") {
        options.append("commandToBeProcessedBy", "get-by-name");
    }
	return options;
}


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
