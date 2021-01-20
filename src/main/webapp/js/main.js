class QuantityInput {
  constructor(self, decreaseText, increaseText) {
    // Create input
    this.input = document.createElement('input');
    this.input.value = '8';
    this.input.type = 'number';
    this.input.name = 'numColors';
    this.input.min = '2';
    this.input.max = '15';
    this.input.pattern = '[0-9]{2}';

    // Get text for buttons
    this.decreaseText = decreaseText || 'Decrease quantity';
    this.increaseText = increaseText || 'Increase quantity';

    // Button constructor
    function Button(text, className){
      this.button = document.createElement('button');
      this.button.type = 'button';
      this.button.innerHTML = text;
      this.button.title = text;
      this.button.classList.add(className);

      return this.button;
    }

    // Create buttons
    this.subtract = new Button(this.decreaseText, 'sub');
    this.add = new Button(this.increaseText, 'add');

    // Add functionality to buttons
    this.subtract.addEventListener('click', () => this.change_quantity(-1));
    this.add.addEventListener('click', () => this.change_quantity(1));

    // Add input and buttons to wrapper
    self.appendChild(this.subtract);
    self.appendChild(this.input);
    self.appendChild(this.add);
  }

  change_quantity(change) {
    // Get current value
    let quantity = Number(this.input.value);

    // Ensure quantity is a valid number
    if (isNaN(quantity)) quantity = 1;

    // Change quantity
    if (quantity + change > Number(this.input.max)) {
      quantity = Number(this.input.max);
    } else if (quantity + change < Number(this.input.min)) {
      quantity = Number(this.input.min);
    } else {
      quantity += change;
    }

    // Ensure quantity is always a number
    quantity = Math.max(quantity, 1);

    // Output number
    this.input.value = String(quantity);
  }
}

let numColours = document.getElementById('ncol');
numColours.changeQuantity = new QuantityInput(numColours);

let fileUpload = document.getElementById("fileUpload");
let fileUploadTxt = document.getElementById("fileUploadTxt");
fileUpload.onchange = function() {
  let files = this.files;
  if (files.length > 0) {
    fileUploadTxt.innerHTML = files.length + " image(s) selected"
  }
};

let infoBtn = document.getElementById("infoBtn");
let closeInfoBtn = document.getElementById("closeInfo");
let infoOverlay = document.getElementById("info");
infoBtn.onclick = function() {
  infoOverlay.style.opacity = "0.95";
  infoOverlay.style.zIndex = "1000";
}
closeInfoBtn.onclick = function() {
  infoOverlay.style.opacity = "0";
  infoOverlay.style.zIndex = "-1";
}