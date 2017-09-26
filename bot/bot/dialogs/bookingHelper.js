var attendees = require('./attendees').attendees;

function getNamesByPrefix(pre){
    pre = pre.toLowerCase();
    var regex = new RegExp('^' + pre + '|' + ' ' + pre);
    var matchedNames = [];
    attendees.forEach((name) => {
        if (regex.test(name.toLowerCase())){
            matchedNames.push(name);
        }
    });
    return matchedNames;
}

module.exports = {
    getNamesByPrefix: getNamesByPrefix
}
