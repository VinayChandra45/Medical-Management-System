document.getElementById("byDateExcel").addEventListener("click", function () {
	date = document.getElementById("datePicker").value;    
	let accept = confirm("Generate Excel report for " + date);
	if(!accept) return;	
	console.log(date);
    url = base + "excel/" + "date/" + date;
    var fileName = "report" + date + ".xls";
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

document.getElementById("byMonthExcel").addEventListener("click", function () {
    let accept = confirm("Generate Monthly Excel report");
	if(!accept) return;
	month = parseInt(document.getElementById("monthPicker").value);
	year = document.getElementById("yearPicker").value;
	console.log(month)
	//console.log(date);
    url = base + "excel/" + "month/" + month + "/" + year;
    var fileName = "monthlyreport - "+month+"/"+year+".xls";
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


document.getElementById("byRangeExcel").addEventListener("click", function () {
	date1 = document.getElementById("rangePicker1").value;
	date2 = document.getElementById("rangePicker2").value;
	var d1 = Date.parse(date1);
	var d2 = Date.parse(date2);
	if (d1 >= d2) {
	    alert ("Starting date cannot be greater than or equal to ending date");
		return;
	}
	let accept = confirm("Generate Excel report from " + date1 + " to " + date2);
	if(!accept) return;
	console.log(date1, date2)
	//console.log(date);
    url = base + "excel/" + "range/" + date1 + "/" + date2;
    var fileName = "rangereport"+date1+"to"+date2+".xls";
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