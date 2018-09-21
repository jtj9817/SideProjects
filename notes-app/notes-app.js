console.log("Starting app...");
console.log("The following commands are available: list, add, view,edit, remove");
//Requirements
const fs = require("fs");
const notes = require("./notes.js");
const lodsh = require("lodash");
var command = process.argv[2];
console.log("Command you entered: ", command);
if(command === "add"){
    console.log("You selected to add notes!");
}
else if(command === "list"){
    console.log("You selected to view a list of notes")
}
else if(command ==="read"){
    console.log("You selected to view a particular note");
}
else if(command === "edit"){
    console.log("You selected to edit a note");
}
else if(command === "remove"){
    console.log("You selected to remove a note!");
}
else{
    console.log("Command not recognized");
}

//The following code are past exercises
/*
//const os = require('os');
//var userName = os.userInfo();


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


fs.appendFile('greetings.txt', `Hello ${userName.username} ! You are ${notes.age} !`, function(err){
    if(err)
    {
        console.log('Unable to write to the file');
    }
    console.log('Successfully written file')
}); */
