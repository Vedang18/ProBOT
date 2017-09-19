require('es6-promise').polyfill();
require('isomorphic-fetch');


var prorigoRestEndpoint = process.env.PRORIGO_REST_SERVER;
function getAllHolidays(callback, errCallback){
    fetch(prorigoRestEndpoint + '/api/holidays/all')
    .then(function(response) {
        if (response.status >= 400) {
            throw new Error("Bad response from server");
        }
        return response.json();
    }).then(function(jsonResponse){
        callback(jsonResponse);
    }).catch(function(err){
        errCallback(err);
    });;
}

function getHolidays(callback, errCallback, body){
    var headers = {
        "Content-Type": "application/json",
        "Accept": "application/json"
    };
    fetch(prorigoRestEndpoint + '/api/holidays', {method: 'POST', body: JSON.stringify(body), headers: headers})
    .then(function(response){
        if (response.status >= 400) {
            throw new Error("Bad response from server");
        }
        return response.json();
    }).then(function(jsonResponse){
        callback(jsonResponse);
    }).catch(function(err){
        errCallback(err);
    })
}

module.exports = {
    getAllHolidays: getAllHolidays,
    getHolidays: getHolidays
}