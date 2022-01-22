var base_api = "http://localhost:8888/api/v2/login"

function stop_login(login_id) {
	return new Promise((resolve, reject) => {
		fetch(base_api + "/stop?login_id=" + login_id).then(response => response.text()).then(response => {
			resolve(response);
		});
	});
}

function start_login(discord_tag) {
	return new Promise((resolve, reject) => {
		fetch(base_api + "/start").then(response => response.json()).then(response => {
			resolve(response.id);
		});
	});
}

function status_login(login_id) {
	return new Promise((resolve, reject) => {
		fetch(base_api + "/status?login_id=" + login_id).then(response => response.json()).then(response => {
			resolve(response.token);
		});
	});
}

function check_login(token) {
	return new Promise((resolve, reject) => {
		fetch(base_api + "/check?token=" + token).then(response => response.text()).then(response => {
			resolve(response);
		});
	});
}

async function login(discord_tag) {
	let login_id = await start_login(discord_tag);

	console.log("-auth " + login_id)

	let token = null;
	do {
		token = await status_login(login_id);

		if (token == null) {
			await new Promise(resolve => setTimeout(resolve, 1000));
		}
	} while (token == null);

	return token;
}

login().then(async token => {
    console.log(token);
    console.log(await check_login(token));
});