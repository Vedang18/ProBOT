require('dotenv-extended').load();
require('es6-promise').polyfill();
require('isomorphic-fetch');

var luisApi = process.env.LUIS_MODEL_URL;
function doquery(query){
    return fetch(luisApi+query)
	.then(function(response) {
		if (response.status >= 400) {
			throw new Error("Bad response from server");
		}
		return response.json();
	})
	.then(function(stories) {
		// console.log(JSON.stringify(stories));
    });
}

console.log(doquery('book room conf1 at 5'));