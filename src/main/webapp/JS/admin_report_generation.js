document.getElementById("admin_excel").addEventListener("click", function () {
	let chart_criteria = $("input[type='radio'][name='inlineRadioOptions']:checked").val();
	console.log(chart_criteria);
	let filename;
	let displayname;
	let year = document.getElementById("ddlYears").value;
	if (chart_criteria === "specialist"){
		filename = "specialist" + year;
		displayname = "all specialist in year " + year;
	}
	if (chart_criteria === "docPerspec"){
		filename = "docperspec" + year;
		displayname = "all doctors per specialisation in year " + year; 
	}
	if (chart_criteria === "month"){
		console.log("here");
		filename = "month" + year;
		displayname = "all months in year " + year;
	}
	console.log(chart_criteria, displayname);
	let accept = confirm("Generate Excel report for " + displayname);
	if(!accept) return;	
	//console.log(date);
    url = base + "excel/" + "admin/"+chart_criteria;
    fileName = filename + ".xls";
    $.ajax({
        method: "POST",
        url: url,
        cache: false,
        xhr: function () {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 2) {
                    if (xhr.status == 200) {
                        xhr.responseType = "blob";
                    } else {
                        xhr.responseType = "text";
                    }
                }
            };
            return xhr;
        },
        success: function (data) {
            //Convert the Byte Data to BLOB object.
            var blob = new Blob([data], { type: "application/ms-excel" });

            //Check the Browser type and download the File.
            var isIE = false || !!document.documentMode;
            if (isIE) {
                window.navigator.msSaveBlob(blob, fileName);
            } else {
                var url = window.URL || window.webkitURL;
                link = url.createObjectURL(blob);
                var a = $("<a />");
                a.attr("download", fileName);
                a.attr("href", link);
                $("body").append(a);
                a[0].click();
                $("body").remove(a);
            }
        }
    });
});


document.getElementById("admin_pdf").addEventListener("click", function () {
	let chart_criteria = $("input[type='radio'][name='inlineRadioOptions']:checked").val();
	console.log(chart_criteria);
	let filename;
	let displayname;
	let year = document.getElementById("ddlYears").value;
	if (chart_criteria === "specialist"){
		filename = "specialist" + year;
		displayname = "all specialist in year " + year;
	}
	if (chart_criteria === "docPerspec"){
		filename = "docperspec" + year;
		displayname = "all doctors per specialisation in year " + year; 
	}
	if (chart_criteria === "month"){
		console.log("here");
		filename = "month" + year;
		displayname = "all months in year " + year;
	}
	console.log(chart_criteria, displayname);
	let accept = confirm("Generate Pdf report for " + displayname);
	if(!accept) return;	
	//console.log(date);
    url = base + "pdf/" + "admin/"+chart_criteria;
    fileName = filename + ".pdf";
    $.ajax({
        method: "POST",
        url: url,
        cache: false,
        xhr: function () {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 2) {
                    if (xhr.status == 200) {
                        xhr.responseType = "blob";
                    } else {
                        xhr.responseType = "text";
                    }
                }
            };
            return xhr;
        },
        success: function (data) {
            //Convert the Byte Data to BLOB object.
            var blob = new Blob([data], { type: "application/octetstream" });

            //Check the Browser type and download the File.
            var isIE = false || !!document.documentMode;
            if (isIE) {
                window.navigator.msSaveBlob(blob, fileName);
            } else {
                var url = window.URL || window.webkitURL;
                link = url.createObjectURL(blob);
                var a = $("<a />");
                a.attr("download", fileName);
                a.attr("href", link);
                $("body").append(a);
                a[0].click();
                $("body").remove(a);
            }
        }
    });
});