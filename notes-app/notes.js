console.log("Starting Notes.js app");
const lodsh = require("lodash");
//console.log(module);

module.exports.age = 25;

var addNote = (title, body) => {
  console.log("Note Title: ", title);
  console.log("Note: ", body);
};

module.exports.addNumber = (num1, num2) => {
  var sum = num1 + num2;
  console.log(`You wish to add the following values: ${num1} and ${num2}`);
  console.log("Performing calculations");
  return "Result: " + sum;
};

var getNote = title => {
  console.log("Getting note..", title);
  console.log();
};

var getall = () => {
  console.all("Retrieving all notes");
};

module.exports = {
  addNote
};
