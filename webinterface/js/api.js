export function get_api_path() {
	if (localStorage.getItem("api_path")) {
		return localStorage.getItem("api_path");
	}
	
	return "";
}

export async function api_request(url) {
	var token = localStorage.getItem("token");

	var api_path = get_api_path() + url;

	console.log("Requesting " + api_path + " with token '" + token + "'");

	if (token) {
		return await (await fetch(api_path + `${api_path.indexOf("?") != -1 ? "&" : "?"}token=${token}`)).text();
	} else {
		return await (await fetch(api_path)).text();
	}
}