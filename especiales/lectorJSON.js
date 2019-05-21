let fs = require("fs");

fs.readFile("data.json", "utf-8", (err, data) => {
    if (err) {
        console.log("ERROR DE LECTURA");
    } else {
        var info = JSON.parse(data);
        console.log(info)
    }
});