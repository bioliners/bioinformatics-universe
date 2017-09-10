$(document).ready( function() {
	var tab = "#" + $('#tab').text();
	var subnavigationTab = "#" + $('#subnavigation-tab').text();
	mainNavbar(tab);
	sequnceNavbar(subnavigationTab);
});

function mainNavbar(tab) {
	$(tab).addClass('active');
}

function sequnceNavbar(tab) {
	$(tab).addClass('active');
}