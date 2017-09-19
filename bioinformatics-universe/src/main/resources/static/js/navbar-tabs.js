$(document).ready( function() {
	var mainTab = "#" + $('#mainTab').text();
	var subnavigationTab = "#" + $('#subnavigation-tab').text();
	mainNavbar(mainTab);
	sequnceNavbar(subnavigationTab);
});

function mainNavbar(tab) {
	$(tab).addClass('active');
}

function sequnceNavbar(tab) {
	$(tab).addClass('active');
}