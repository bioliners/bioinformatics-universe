$(document).ready( function() {
	var tab = "#" + $('#tab').text();
	var sequenceTab = "#" + $('#sequence-tab').text();
	mainNavbar(tab);
	sequnceNavbar(sequenceTab);
});

function mainNavbar(tab) {
	$(tab).addClass('active');
}

function sequnceNavbar(tab) {
	$(tab).addClass('active');
}