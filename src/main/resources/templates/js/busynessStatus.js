

function primaryButtonClick() {
  target = document.getElementById("color");
  if (target != null) {
    target.className = "border-start border-start-4 border-start-primary px-3 mb-3";
  }
}

function infoButtonClick() {
  target = document.getElementById("color");
  if (target != null) {
    target.className = "border-start border-start-4 border-start-info px-3 mb-3";
  }
}

function successButtonClick() {
  target = document.getElementById("color");
  if (target != null) {
    target.className = "border-start border-start-4 border-start-success px-3 mb-3";
  }
}

function warningButtonClick() {
  target = document.getElementById("color");
  if (target != null) {
    target.className = "border-start border-start-4 border-start-warning px-3 mb-3";
  }
}

function dangerButtonClick() {
  target = document.getElementById("color");
  if (target != null) {
    target.className = "border-start border-start-4 border-start-danger px-3 mb-3";
  }
}