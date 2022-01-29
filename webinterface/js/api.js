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

export function process_url(url) {
	var token = localStorage.getItem("token");

	var api_path = get_api_path() + url;

	if (token) {
		return api_path + `${api_path.indexOf("?") != -1 ? "&" : "?"}token=${token}`;
	} else {
		return api_path;
	}
}

export function has_valid_token() {
	return new Promise(async (resolve, reject) => {
		if (localStorage.getItem("token")) {
			api_request("/api/v2/login/check").then(data => {
				data = JSON.parse(data);

				if (data.msg == "ok") {
					resolve(true);
				} else {
					localStorage.removeItem("token");
					resolve(false);
				}
			});
		} else {
			resolve(false);
		}
	});
}