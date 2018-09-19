console.log("Starting Notes.js app");
//console.log(module);

module.exports.age = 25;
module.exports.addnote = () => {
    console.log("Add note");
    return 'New note';
};
module.exports.addNumber = (num1,num2) => {
    var sum = num1 + num2;
    console.log(`You wish to add the following values: ${num1} and ${num2}`);
    console.log("Performing calculations");
    return 'Result: ' + sum;
}
