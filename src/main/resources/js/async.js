function startExport() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8081/export/startExport", true); // Use the full URL of your Spring Boot server
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                document.getElementById("exportStatus").innerHTML = "Export started successfully.";
            } else {
                document.getElementById("exportStatus").innerHTML = "Export failed.";
            }
        }
    };
    xhr.send();
}

function downloadFile() {
    window.location.href = "http://localhost:8081/export/download"; // Use the full URL of your Spring Boot server
}
