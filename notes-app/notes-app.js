console.log("Starting app...");
const fs = require("fs");
const notes = require("./notes.js");
const lodsh = require("lodash");
const yrgs = require("yargs");

var usrCMD = process.argv[2];
var yrgsCMD = yrgs.argv;
console.log("Process Args", usrCMD);
console.log("Yargs cmd:", yrgsCMD);
console.log("Command Received:", usrCMD);
if (usrCMD === "add") {
  console.log("Adding Note");
  //console.log("Note Title: ", yrgsCMD.title, yrgsCMD.body);
  notes.addNote(yrgsCMD.title, yrgsCMD.body);
} else if (usrCMD === "list") {
  notes.getAll();
  //console.log("Listing Notes");
} else if (usrCMD === "read") {
  notes.getNote(yrgsCMD.title);
  //console.log("Reading Notes");
} else if (usrCMD === "remove") {
  notes.re
  //console.log("Removing Notes");
} else {
  console.log("Command not recognized");
}

//The following code are past exercises
/*
//const os = require('os');
//var userName = os.userInfo();

//The following code are past exercises
/*
console.log("Beginning testing of lodash functions");
console.log(lodsh.isString("The quick brown fox"));
console.log(lodsh.isString("Hello world!"));
var array = lodsh.uniq(['Josh', 1, 'Josh2', 1,2,3,4,5]);
console.log(array);
var resultAddNotes = notes.addnote();
console.log(resultAddNotes);
var sumNumbers = notes.addNumber(10,500);
console.log(sumNumbers);
console.log(userName);
*/