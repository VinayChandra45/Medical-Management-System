var priceButton = document.getElementById("inlineRadio1") ;
var upvoteButton = document.getElementById("inlineRadio2");
var searchButton = document.getElementById("searchButton");

priceButton.addEventListener("change", function(){
	//console.log("hi");
	searchButton.click();
})

upvoteButton.addEventListener("change", function(){
	//console.log("hello");
	searchButton.click();
})