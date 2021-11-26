var api_path = window.origin;

function needs_token() {
	return new Promise((resolve, reject) => {
		fetch(api_path + '/api/check-token').then(async res => {
			var text = await res.text();
			if (text.startsWith('OK')) {
				resolve(false);
			} else if (text.startsWith('Token cannot be null')) {
				resolve(true);
			} else {
				reject("Unknown: " + text);
			}
		});
	});
}

function check_token(token) {
	return new Promise((resolve, reject) => {
		fetch(api_path + "/api/check-token?token=" + token).then(async res => {
			var text = await res.text();
			if (text.startsWith('OK')) {
				resolve(true);
			} else if (text.startsWith('Invalid token')) {
				resolve(false);
			} else {
				reject("Unknown error: " + text);
			}
		});
	});
}

function render_token_input_internal() {
	var heading = create_heading(3, "Please login");
	var token_input = create_input("Login token", "le token", console.log);
	token_input.querySelector("input").setAttribute("id", "token_input");

	var submit_button = create_button("Set Token", (button) => {
		button.innerText = "Checking...";
		button.disabled = true;

		var token = document.getElementById("token_input").value;

		check_token(token).then(async (valid) => {
			if (valid) {
				button.innerText = "Token valid";
				button.disabled = true;

				localStorage.setItem("token", token);
				location.reload();
			} else {
				button.innerText = "Invalid token";
				button.disabled = false;
			}
		});
	});

	return create_card(join_components(heading, token_input, submit_button));
}

async function render_token_input() {
	var result = document.createElement('div');

	if (await needs_token()) {
		if (localStorage.getItem("token")) {
			if (await check_token(localStorage.getItem("token"))) {
				return undefined;
			} else {
				result.appendChild(render_token_input_internal());
			}
		} else {
			result.appendChild(render_token_input_internal());
		}
	} else {
		return undefined;
	}

	return result;
}

async function api_request(api_path) {
	var token = localStorage.getItem("token");

	if (token) {
		return await (await fetch(api_path + `${api_path.indexOf("?") != -1 ? "&" : "?"}token=${token}`)).text();
	} else {
		return await (await fetch(api_path)).text();
	}
}

async function render_api_endpoints() {
	var endpoints = JSON.parse(await api_request("/api/endpoints"));
	
	endpoints = endpoints.filter(endpoint => endpoint != "/");
	endpoints = endpoints.map(endpoint => `<a href="${new URL(endpoint, api_path).toString()}">${endpoint}</a>`);

	return create_card(join_components(create_heading(2, "API Endpoints"), create_list(endpoints)));
}

function render_native_command(command) {
	var name = create_heading(3, command.name);

	var perms = command.permission ? create_text("Tis command needs special permissions: " + command.permission) : from_html("");

	var help_table = create_table([
		["Short help", command.help.short],
		["Long help", command.help.long]
	]);

	return create_card(join_components(name, perms, help_table));
}

async function render_commands_list() {
	var commands = JSON.parse(await api_request("/api/commands"));

	var command_div = document.createElement('div');

	commands.commands.forEach(command => {
		command_div.appendChild(render_native_command(command));
	})

	var slash_commands_table = [["Name", "Permission"]];

	commands.slash_commands.forEach(command => {
		slash_commands_table.push(["/" + command.name, command.permission ? command.permission : "No permission needed"]);
	});


	return create_card(join_components(
		join_components(
			create_heading(2, "Native commands"),
			command_div
		),
		join_components(
			create_heading(2, "Slash commands"),
			create_table(slash_commands_table)
		)
	));
}

async function render_uptime() {
	var uptime = JSON.parse(await api_request("/api/uptime"));

	return create_card(join_components(
		create_heading(2, "Uptime"),
		create_text(uptime["uptime-dhms"])
	));
}

async function render_load_plugin() {
	var heading = create_heading(3, "Load plugin");
	var plugin_input = create_input("Plugin url", "le plugin", console.log);
	plugin_input.querySelector("input").setAttribute("id", "plugin_input");

	var submit_button = create_button("Load", async (button) => {
		button.innerText = "Loading...";
		button.disabled = true

		var plugin_url = document.getElementById("plugin_input").value;

		if (plugin_url.trim() == "") {
			button.innerText = "Invalid url";
			button.disabled = false;
			return;
		}

		var result = await api_request("/api/load-plugin?url=" + plugin_url);

		console.log(result);
		alert(result);

		button.innerText = "Loaded";
		button.disabled = false;
	});

	return create_card(join_components(heading, plugin_input, submit_button));
}

async function render_set_status() {
	var heading = create_heading(3, "Set status");
	var status_input = create_input("Status", "le status", console.log);
	status_input.querySelector("input").setAttribute("id", "status_input");

	var submit_button = create_button("Set", async (button) => {
		button.innerText = "Setting...";
		button.disabled = true

		var status = document.getElementById("status_input").value;

		if (status.trim() == "") {
			button.innerText = "Invalid status";
			button.disabled = false;
			return;
		}

		var result = await api_request("/api/status?status=" + status);

		console.log(result);
		alert(result);

		button.innerText = "Set";
		button.disabled = false;
	});

	return create_card(join_components(heading, status_input, submit_button));
}

async function render_send_message() {
	var heading = create_heading(3, "Send message");
	var message_input = create_input("Message", "le message", console.log);
	message_input.querySelector("input").setAttribute("id", "message_input");

	var channel_input = create_input("Channel", "le channel", console.log);
	channel_input.querySelector("input").setAttribute("id", "channel_input");

	var submit_button = create_button("Send", async (button) => {
		button.innerText = "Sending...";
		button.disabled = true

		var message = document.getElementById("message_input").value;
		var channel = document.getElementById("channel_input").value;

		if (message.trim() == "") {
			button.innerText = "Invalid message";
			button.disabled = false;
			return;
		}

		var result = await api_request("/api/message?body=" + message + "&channel=" + channel);

		console.log(result);
		alert(result);

		button.innerText = "Sent";
		button.disabled = false;
	});

	return create_card(join_components(heading, message_input, channel_input, submit_button));
}

function render_uploaded_file_internal(file) {
	var uploader_card = create_card(join_components(
		create_heading(3, "Uploader"),
		create_text("Discord username: " + file.uploader.name + "#" + file.uploader.discriminator),
		from_html("<img src='" + file.uploader.avatar_url + "' style='width: 100px; height: 100px; border-radius: 50%; position: relative; top: -100px; left: -50px; float: right;' />"),
		create_input("Message user", "le message", (value, input) => {
			input.disabled = true;
			input.innerText = "Sending...";
			api_request("/api/message?body=" + value + "&channel=" + file.uploader.id).then(result => {
				alert(result);
				input.innerText = "";
				input.disabled = false;
			});
		})
	));


	return create_card(join_components(
		create_heading(3, `<a href='${file.download_url}'>${file.original_name}</a>`),
		create_text("Uploaded at: " + new Date(file.upload_time).toLocaleString()),
		uploader_card,
	));
}

async function render_uploaded_files_list() {
	var files = JSON.parse(await api_request("/api/upload"));

	var files_div = document.createElement('div');

	files.forEach(file => {
		files_div.appendChild(render_uploaded_file_internal(file));
	});

	return create_card(join_components(
		create_heading(2, "Uploaded files"),
		files_div
	));
}