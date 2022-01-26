var monthlyRadioButton = document.getElementById("monthly-analysis") ;
var specialistRadioButton = document.getElementById("specialist-analysis");
var docPerSpecButton = document.getElementById("docPerspec-analysis");
var yearInput = document.getElementById("ddlYears");
var searchHiddenButton = document.getElementById("hidden-button");

monthlyRadioButton.addEventListener("change", function(){
	//console.log("hiiiii");
	searchHiddenButton.click();
})

specialistRadioButton.addEventListener("change", function(){
	//console.log("helloooooo");
	searchHiddenButton.click();
})

docPerSpecButton.addEventListener("change", function(){
	//console.log("helloooooo");
	searchHiddenButton.click();
})

ddlYears.addEventListener("change", function(){
	//console.log("helloooooo");
	searchHiddenButton.click();
})

