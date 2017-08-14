$(document).ready(function (){
	$('.result-container').hide();
	takeCareOfValidators();
	takeCareOfFields();

    $('#Go').click(function() {
    	getData();
    });
});

function getOptions() {
    var data = new FormData();

	if (typeof $('#first-file')[0] != 'undefined') {

		$.each($('#first-file')[0].files, function(i, file) {
            data.append('listOfFiles', file);
        });

    }

	var fileDelim = $('#first-delim').val();
	var fileColumn = $('#first-col').val();
	var identityThreshold = $('#identity-threshold').val();
	var coverageThreshold = $('#coverage-threshold').val();
	var evalueThreshold = $('#evalue-threshold').val();


 	if (typeof fileDelim != 'undefined') {
        if (fileDelim != null) {
	        options.append("fileDelim", fileDelim);
	    }
	}
	if (typeof fileColumn != 'undefined') {
	    if (fileColumn != '') {
 		    options.append("fileColumn", fileColumn);
 		}
	}
	if (typeof identityThreshold != 'undefined') {
	    if (identityThreshold != '') {
 		    options.append("identityThreshold", identityThreshold);
 		}
	}
	if (typeof coverageThreshold != 'undefined') {
	    if (coverageThreshold != '') {
 		    options.append("coverageThreshold", coverageThreshold);
 		}
	}
	if (typeof evalueThreshold != 'undefined') {
	    if (evalueThreshold != '') {
 		    options.append("evalueThreshold", evalueThreshold);
 		}
	}


    if ($('#subnavigation-tab').text() == "create-cogs") {
        options.append("commandToBeProcessedBy", "create-cogs");
    }

	return options;
}


