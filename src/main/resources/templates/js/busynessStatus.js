function busynessStatusButton(busynessStatus) {
    console.log("busynessStatus: " + busynessStatus)
    const target = document.getElementById("accountStatusColor");
    if (target != null) {
        let colorText = (Number.isInteger(busynessStatus) && busynessStatus >= 0 && busynessStatus <= 4) ?
            ["primary", "info", "success", "warning", "danger"][busynessStatus]
            : "dark" /* 変だったりしたら、灰色 */
        console.log(colorText)
        target.className = "border-start border-start-4 border-start-" + colorText + "px-3 mb-3";
    }
}
