// example for a loadable js plugin

on_load = function() {
	api.register_command("eval", "Eval js code!", "Use '" + global.prefix + "eval [code]' to eval js code.", "eval", function(event) {
		if (event.args.length == 0) {
			event.commandFail("You need to specify a code to eval!");
		} else {
			var code = api.helper.join_java_array(event.args, " ");
			event.commandSuccess("" + eval(code));
		}
	});

	api.register_slash_command("eval", "eval", function(event) {
		var code = event.getOption("code").getAsString();;
		var result = "" + eval(code);

		event.reply(result).queue();
	}, function() {
		console.info("eval command registered!");
	
		var params = [ api.create_slash_parameter("code", "Code to eval", global.constant.slash_command_parameter_type.STRING, true, null) ];
		var registerer = api.create_slash_registerer("eval", "Eval js code!", global.constant.slash_command_type.CHAT_INPUT, params);
		registerer.register();
	});
}