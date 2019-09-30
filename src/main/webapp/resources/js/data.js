$(document).ready(function() {
    $.get("http://localhost:8080/trains", function(data) {
        console.log(data);
        for (var i = 0; i < data.length; i++) {
            console.log(data[i]);
            updateRow(data[i]);
        }
    });
});

function updateRow(dataRow) {
    var tableElement = $("#example");
    console.log ("Table element: " + tableElement.toString());
    tableElement.append(createRow(dataRow));
}

function createRow(rowData) {
    var trElement = "<tr id='trainid" + rowData.trainId +  "'>";

    trElement += "<td>" + rowData.name + "</td>";
    trElement += "<td>" + rowData.coordinates + "</td>";
    trElement += "<td>" + rowData.destination + "</td>";
    trElement += "<td>" + rowData.speed + "</td>";
    trElement += "</tr>";

    return trElement;
}
