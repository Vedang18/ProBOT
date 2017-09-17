require('es6-promise').polyfill();
require('isomorphic-fetch');


var prorigoRestEndpoint = process.env.PRORIGO_REST_SERVER;
function getAllHolidays(){
    return fetch(prorigoRestEndpoint + '/api/holidays/all')
	.then(function(response) {
		if (response.status >= 400) {
			throw new Error("Bad response from server");
		}
		return response.json();
	});
}

module.exports = {
    getAllHolidays: getAllHolidays
}