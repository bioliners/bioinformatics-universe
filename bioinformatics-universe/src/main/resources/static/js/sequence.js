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
