function stop_login(login_id) {
	return new Promise((resolve, reject) => {
		fetch("https://x.glowman554.gq/auth/login/stop?login_id=" + login_id).then(response => response.text()).then(response => {
			resolve(response);
		});
	});
}

function start_login(discord_tag) {
	return new Promise((resolve, reject) => {
		fetch("https://x.glowman554.gq/auth/login/start?discord_id=" + discord_tag.replace("#", "%23")).then(response => response.json()).then(response => {
			resolve(response.id);
		});
	});
}

function status_login(login_id) {
	return new Promise((resolve, reject) => {
		fetch("https://x.glowman554.gq/auth/login/status?login_id=" + login_id).then(response => response.json()).then(response => {
			resolve(response.token);
		});
	});
}

function self_message(message, login_token) {
	return new Promise((resolve, reject) => {
		fetch("https://x.glowman554.gq/api/self_message?body=" + encodeURIComponent(message) + "&login_token=" + login_token).then(response => response.text()).then(response => {
			resolve(response);
		});
	});
}

function av_scan(url, login_token) {
	return new Promise((resolve, reject) => {
		fetch("https://x.glowman554.gq/api/av_scan?url=" + encodeURIComponent(url) + "&login_token=" + login_token).then(response => response.text()).then(response => {
			resolve(response);
		});
	});
}

async function login(discord_tag) {
	let login_id = await start_login(discord_tag);
	let token = null;
	do {
		token = await status_login(login_id);

		if (token == null) {
			await new Promise(resolve => setTimeout(resolve, 1000));
		}
	} while (token == null);

	return token;
}

login("Glowman554#4152").then(token => {
	self_message("Hello, world!", token).then(response => {
		console.log(response);
	});

	av_scan("https://github.com/asdasdasdasdsadsa/wanacry/raw/master/WannaCry.EXE", token).then(response => {
		console.log(response);
	});
});
