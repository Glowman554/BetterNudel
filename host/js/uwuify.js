// example for a loadable js plugin

var uwu_data = {
	faces: [":3", "OwO", "UwU", "uwu", "owo", "qwq", "^w^"],
	replacings: [
		{ toReplace: /(?:r|l)/g, with: "w" },
		{ toReplace: /n([aeiou])/g, with: "ny$1" },
		{ toReplace: /ove/g, with: "uv" },
		{ toReplace: /ame/g, with: "ayme" }
	]
};

function uwuify(text) {
	text = text.toLowerCase();

	for (var i = 0; i < uwu_data.replacings.length; i++) {
		text = text.replace(uwu_data.replacings[i].toReplace, uwu_data.replacings[i].with);
	}

	if (text[0].match(/[a-z]/i)) {
		text = text[0] + "-" + text
	}

	if (text[text.length - 1].match(/[a-z]/i)) {
		text += "\~\~";
	}

	text += " " + uwu_data.faces[Math.floor(Math.random() * uwu_data.faces.length)];

	return text;
}

on_load = function() {
	api.register_command("uwuify", "UwUify a text!", "Use '" + global.prefix + "uwuify [text]' to uwuify a text.", null, function(event) {
		if (event.args.length == 0) {
			event.commandFail("You need to specify a text to uwuify!");
		} else {
			var text = api.helper.join_java_array(event.args, " ");
			event.commandSuccess(uwuify(text));
		}
	});

	api.register_slash_command("uwuify", null, function(event) {
		var text = event.getOption("text").getAsString();;
		var result = uwuify(text);

		event.reply(result).queue();
	}, function() {
		console.info("uwuify command registered!");

		var params = [ api.create_slash_parameter("text", "Text to uwuify", global.constant.slash_command_parameter_type.STRING, true, null) ];
		var registerer = api.create_slash_registerer("uwuify", "UwUify a text!", global.constant.slash_command_type.CHAT_INPUT, params);
		registerer.register();
	});
}