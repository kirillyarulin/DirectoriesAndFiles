
window.onload = function (ev) {
    let btns = document.querySelectorAll("td.list-of-files-column button");

    for (let i = 0; i < btns.length; i++) {
        btns.item(i).addEventListener("click", function (ev) {
            fillModalWindow(+this.previousElementSibling.value);
        });
    }


    $("#exampleModalCenter").on("hide.bs.modal", clearModalWindow);
};


function fillModalWindow(id) {
    for (let j = 0; j < directories.length; j++) {
        if (directories[j].id === id) {
            files = directories[j].internalFiles;
            break;
        }
    }

    for (let k = 0; k < files.length; k++) {
        let tbody = document.getElementById("js-modal-body");
        let tr = document.getElementById("modal-row-template").content.cloneNode(true);;
        let fileName = tr.getElementById("js-file-column_name");
        fileName.innerText = files[k].name;
        fileName.setAttribute("title", files[k].name);

        if (files[k].directory) {
            tr.getElementById("js-size-column").innerText = "<DIR>"
        } else {
            tr.getElementById("js-size-column").innerText = sizeFormat(files[k].size);
        }

        tbody.appendChild(tr);
    }
}


function clearModalWindow() {
    let tbody = document.getElementById("js-modal-body");
    let children =  tbody.childNodes;

    for (let i = 0; i < children.length; i++) {
        if (children[i].nodeName === "TR") {
            tbody.removeChild(children[i])
        }
    }
}


function sizeFormat(sizeInBytes) {
    if (sizeInBytes < 1024) {
        return sizeInBytes + "bytes";
    } else if (sizeInBytes < Math.pow(1024, 2)) {
        return +(sizeInBytes / 1024).toFixed(3) + "Kb";
    } else if (sizeInBytes < Math.pow(1024, 3)) {
        return +(sizeInBytes / Math.pow(1024, 2)).toFixed(3)+ "Mb";
    } else if (sizeInBytes < Math.pow(1024, 4)) {
        return +(sizeInBytes / Math.pow(1024, 3)).toFixed(3) + "Gb";
    } else if (sizeInBytes < Math.pow(1024, 5)) {
        return +(sizeInBytes / Math.pow(1024, 4)).toFixed(3) + "Tb";
    } else if (sizeInBytes < Math.pow(1024, 6)) {
        return +(sizeInBytes / Math.pow(1024, 5)).toFixed(3) + "Pb";
    } else {
        return +(sizeInBytes / Math.pow(1024, 6)).toFixed(3) + "Eb";
    }
}






