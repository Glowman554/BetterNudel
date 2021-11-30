var debug = false;

function debug_log(msg) {
	if (debug) {
		print("[jsplugin_bootstrap DEBUG] " + msg + "!");
	}
}

var on_load = function() {
	debug_log("No on_load function defined");
	throw new Error('Please specify a on_load function like \'on_load = function() {}\'');
};

function load_java_class(class_name) {
	debug_log("Loading java class: " + class_name);

	return Java.type(class_name);
}

var api = {};
var console = {};
var fs = {};

var global = {};

function setup_global_console() {
	debug_log("Setting up global console");

	console.log = function(msg) {
		print("[JsPlugin] " + msg);
	};

	console.warn = function(msg) {
		print("[JsPlugin] [warn] " + msg);
	};

	console.error = function(msg) {
		print("[JsPlugin] [error] " + msg);
	};

	console.info = function(msg) {
		print("[JsPlugin] [info] " + msg);
	};

	console.debug = function(msg) {
		print("[JsPlugin] [debug] " + msg);
	};
}

function setup_global_fs() {
	debug_log("Setting up global fs");

	var file_utils = load_java_class('io.github.glowman554.nudel.utils.FileUtils');

	fs.readFileSync = function(path) {
		debug_log("Reading file: " + path);

		return file_utils.readFile(path);
	};

	fs.writeFileSync = function(path, data) {
		debug_log("Writing file: " + path);

		file_utils.writeFile(path, data);
	};

	fs.appendFileSync = function(path, data) {
		debug_log("Appending file: " + path);

		var old_data = fs.readFileSync(path);
		var new_data = old_data + data;
		fs.writeFileSync(path, new_data);
	};

	fs.removeSync = function(path) {
		var java_file_class = load_java_class('java.io.File');
		
		new java_file_class(path).delete();
	};
}

function setup_global_globals() {
	debug_log("Setting up global globals");

	var main = load_java_class('io.github.glowman554.nudel.Main');
	var discord = load_java_class('io.github.glowman554.nudel.discord.Discord').discord;
	var js_plugin_api = load_java_class('io.github.glowman554.nudel.plugin.JSPluginApi');
	var array_utils = load_java_class('io.github.glowman554.nudel.utils.ArrayUtils');

	global.java = {
		main: main,
		discord: discord,
		api: js_plugin_api,
		array_utils: array_utils
	};

	global.prefix = discord.commandManager.prefix;
	global.config_file = main.config_file;
	global.http_host_path = main.http_host_path;
	global.http_host_url = main.http_host_url;
	global.arg_parser = main.parser;

	global.info = {
		registered_commands: [],
		registered_paths: []
	};

	global.constant = {
		slash_command_type: {
			CHAT_INPUT: 1,
			USER: 2,
			MESSAGE: 3
		},
		slash_command_parameter_type: {
			STRING: 3,	
			INTEGER: 4,
			BOOLEAN: 5,
			USER: 6,
			CHANNEL: 7,
			ROLE: 8,
			MENTIONABLE: 9,
			NUMBER: 10
		}
	};

	debug_log("Loaded global object: " + JSON.stringify(global));
}

function setup_global_api() {
	debug_log("Setting up global api");

	var js_plugin_api = global.java.api;

	api.register_command = function(command_name, command_short_help, command_long_help, permission, exec_function) {
		debug_log("Registering command: " + command_name + " (short help: '" + command_short_help + "', long help: '" + command_long_help + "', permission: '" + (permission ? permission : "") + "')");

		global.info.registered_commands.push(global.prefix + command_name);

		js_plugin_api.register_command(command_name, command_short_help, command_long_help, permission, exec_function);
	};

	api.register_slash_command = function(command_name, permission, exec_function, register_function) {
		debug_log("Registering slash command: " + command_name + " (permission: '" + (permission ? permission : "") + "')");

		global.info.registered_commands.push(command_name);

		js_plugin_api.register_slash_command(command_name, permission, exec_function, register_function);
	};

	api.create_slash_parameter = function(name, description, type, required, choices) {
		debug_log("Creating slash parameter: " + name + " (description: '" + description + "', type: '" + type + "', required: '" + required + "', choices: '" + (choices ? choices : "") + "')");

		return js_plugin_api.create_slash_parameter(name, description, type, required, choices);
	};

	api.create_slash_registerer = function(name, description, type, options) {
		debug_log("Creating slash registerer: " + name + " (description: '" + description + "', type: '" + type);

		return {
			reg: js_plugin_api.create_slash_registerer(name, description, type, options),
			register: function() {
				debug_log("Registering slash registerer: " + name);

				js_plugin_api.do_slash_command_register(this.reg);
			}
		};
	};

	api.register_http_handler = function(path, handler_function) {
		debug_log("Registering http handler: " + path);

		global.info.registered_paths.push(path);

		js_plugin_api.register_http_handler(path, handler_function);
	};

	api.network = {};

	api.network.request = function(url) {
		debug_log("Requesting url: " + url);

		return js_plugin_api.request(url);
	};

	api.network.request_json = function(url) {
		debug_log("Requesting json url: " + url);

		return JSON.parse(js_plugin_api.request(url));
	};

	api.network.download = function(url, path) {
		debug_log("Downloading url: " + url + " to path: " + path);

		js_plugin_api.download(url, path);
	};

	api.helper = {};

	api.helper.send_file = function(channel, path, message) {
		debug_log("Sending file: " + path + " to channel: " + channel + (message ? " with message: '" + message + "'" : ""));

		var java_file_class = load_java_class('java.io.File');

		if (message) {
			var result = channel.sendMessage(message);
			result.addFile(new java_file_class(path));
			result.queue();
		} else {
			var result = channel.sendFile(new java_file_class(path));
			result.queue();
		}
	};

	api.helper.send_message_by_id = function(channel_id, message) {
		debug_log("Sending message: '" + message + "' to channel: " + channel_id);

		var channel = global.java.discord.jda.getGuildChannelById(channel_id);

		if (!channel) {
			var user = global.java.discord.jda.getUserById(channel_id);

			if (!user) {
				throw new Error("Could not find channel or user with id: " + channel_id);
			} else {
				channel = user.openPrivateChannel().complete();
			}
		}

		channel.sendMessage(message).queue();
	};

	api.helper.send_file_by_id = function(channel_id, path, message) {
		debug_log("Sending file: " + path + " to channel: " + channel_id + (message ? " with message: '" + message + "'" : ""));

		var channel = global.java.discord.jda.getGuildChannelById(channel_id);

		if (!channel) {
			var user = global.java.discord.jda.getUserById(channel_id);

			if (!user) {
				throw new Error("Could not find channel or user with id: " + channel_id);
			} else {
				channel = user.openPrivateChannel().complete();
			}
		}

		api.helper.send_file(channel, path, message);
	};

	api.helper.join_java_array = function(array, join_seq) {
		debug_log("Joining java array: " + array + " with join char: '" + join_seq + "'");

		return global.java.array_utils.stringify(array, join_seq);
	};

	api.enable_debug = function() {
		console.info("Enabling debug!");

		debug = true;
	};

	api.disable_debug = function() {
		console.info("Disabling debug!");

		debug = false;
	};

	api.import = function(path_or_url) {

		var code = "";

		if (path_or_url.startsWith("http://") || path_or_url.startsWith("https://")) {
			debug_log("Importing url: " + path_or_url);

			code = api.network.request(path_or_url);
		} else {
			debug_log("Importing file: " + path_or_url);

			code = fs.readFileSync(path_or_url);
		}

		code = code.replace("const ", "var ");

		var exports = {};
		var module = {
			exports: exports
		};

		eval(code);

		return exports;
	}
}

function is_no_load() {
	debug_log("Looking for no_load variable");

	try {
		if (no_load) {
			debug_log("Found no_load variable");

			return true;
		} else {
			debug_log("No no_load variable found");

			return false;
		}
	} catch (e) {
		debug_log("No no_load variable found");

		return false;
	}
}

function bootstrap() {
	debug_log("Bootstrapping");

	if (is_no_load()) {
		return false;
	}

	setup_global_globals();
	setup_global_console();
	setup_global_fs();
	setup_global_api();

	debug_log("Bootstrapped");
	return true;
}