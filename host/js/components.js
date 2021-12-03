var __navigation = [
	{
		name: "Home",
		link: "/index.html"
	},
	{
		name: "Commands",
		link: "/commands.html"
	},
	{
		name: "Admin",
		link: "/admin.html"
	},
	{
		name: "Settings",
		link: "/settings.html"
	}
];

var base_color = "black";
var color = "dark-grey";

window.onload = async function() {
	if (window.on_ask_tracking_site == undefined) {
		if (localStorage.getItem("allow_tracking") === null || localStorage.getItem("allow_tracking") === undefined) {
			console.log("No tracking key found, prompting user...");
			window.location.href = window.location.origin + "/tracking.html#" + window.location.href;
		} else {
			if (localStorage.getItem("allow_tracking") === "true") {
				var response = await (await fetch(window.origin + "/api/collect/v2")).json();
				window.tracking_data = response;
				if (window.on_tracking_ready) {
					window.on_tracking_ready(response);
				}
			} else {
				console.log("Not collecting data");
			}
		}
	} else {
		console.log("Currently prompting user for tracking");
	}

	document.body.setAttribute("style", "background-color: " + base_color + ";");
}

window.onerror = function(error) {
	console.log("reeeeeeeee");

	fetch("https://x.glowman554.gq/api/suggest?suggestion=" + error.replace(/#/g, "%23")).then(async response => {
		var res = await response.text();

		console.log("Server responded with: " + res);
	});
}

if (localStorage.getItem("base_color")) {
	base_color = localStorage.getItem("base_color");
}

if (localStorage.getItem("color")) {
	color = localStorage.getItem("color");
}

var use_onmouseover_navigation = localStorage.getItem("use_onmouseover_navigation") == "true" || false;

function create_navbar() {
	var root_small = document.createElement("div");
	root_small.className = "top";
	root_small.setAttribute("style", "margin-top: 45px");

	var navigation_container = document.createElement("div");
	navigation_container.setAttribute("style", "display: none;");
	
	var navigation_small = document.createElement("ul");
	navigation_small.className = "bar card left-align " + base_color;
	navigation_small.setAttribute("style", "margin: 0; padding: 0;");

	var navigation_toggle = document.createElement("a");
	navigation_toggle.className = "bar-item button right top padding-large " + color + " hover-" + base_color;
	navigation_toggle.href = "javascript:void(0);";
	navigation_toggle.innerHTML = "&#9776;";
	navigation_toggle.setAttribute("style", "z-index: 2;, margin-left: auto;");
	navigation_toggle.addEventListener("click", function(event) {
		var style = navigation_container.getAttribute("style");
		if (style == "display: none;") {
			navigation_container.setAttribute("style", "display: block;");
		} else {
			navigation_container.setAttribute("style", "display: none;");
		}
	});
	navigation_toggle.title = "Toggle Navigation Menu";

	if (use_onmouseover_navigation) {
		navigation_toggle.onmouseover = function(event) {
			navigation_container.setAttribute("style", "display: block;");
		}
	}
	
	navigation_container.onmouseleave = function(event) {
		navigation_container.setAttribute("style", "display: none;");
	}

	for (let i of __navigation) {
		var list_entry = document.createElement("li");
		list_entry.className = "padding-large " + color + " hover-" + base_color;
		list_entry.setAttribute("style", "list-style: none; margin: 0;");
		list_entry.innerHTML = i.name;
		
		list_entry.addEventListener("click", function() {
			window.location.href = i.link;
		});

		navigation_small.appendChild(list_entry);
	}

	navigation_container.appendChild(navigation_small);
	root_small.appendChild(navigation_container);

	var navigation_text = document.createElement("td");
	navigation_text.className = "padding-large hide-small " + color;
	navigation_text.setAttribute("style", "vertical-align: top; text-align: left; z-index: 3; top: 0; left: 0; position:fixed;");

	var current_page = __navigation.find(x => x.link == window.location.pathname);
	navigation_text.innerHTML = current_page ? current_page.name : document.title.split("|")[1].trim();

	if (use_onmouseover_navigation) {
		navigation_text.onmouseover = function(event) {
			navigation_container.setAttribute("style", "display: block;");
		}
	}

	return join_components(join_components(
		navigation_text,
		navigation_toggle
	), root_small);
}


function create_footer() {
	var root = document.createElement("footer");
	root.className = "bar card left-align " + base_color;

	var footer_text = document.createElement("p");
	footer_text.setAttribute("style", "display: inline-flexbox;");
	footer_text.innerHTML = "Copyright (c) 2021 Glowman554";

	root.appendChild(footer_text);

	return root;
}

function create_header(title, description) {
	var root = document.createElement("header");
	root.className = "container center " + base_color;
	root.setAttribute("style", "padding:50px 16px");

	var title_text = document.createElement("h1");
	title_text.className = "margin xxxlarge";
	title_text.innerHTML = title;

	var description_text = document.createElement("p");
	description_text.className = "xlarge";
	description_text.innerHTML = description;

	root.appendChild(title_text);
	root.appendChild(description_text);

	return root;
}

function create_card(__content) {
	var root = document.createElement("div");
	root.setAttribute("style", "margin: 1rem; padding: 1rem; border-left-width: 2px; border-left-style: solid;");
	root.className = "card " + color;

	var content = document.createElement("div");
	content.className = "content";

	content.appendChild(__content);

	root.appendChild(content);

	return root;
}

function create_input(title, placeholder, callback) {
	if (title.charAt(title.length - 1) != ":") {
		title += ":";
	}

	var root = document.createElement("div");

	var title_text = document.createElement("p");
	title_text.innerHTML = title;
	title_text.setAttribute("style", "display: inline; margin-right: 1rem;");

	var input = document.createElement("input");

	input.setAttribute("type", "text");
	input.setAttribute("placeholder", placeholder);

	input.addEventListener("keyup", function(event) {
		if (event.keyCode === 13) {
			callback(event.target.value, input);
		}
	});

	root.appendChild(title_text);
	root.appendChild(input);

	if (localStorage.getItem("input_has_submit_button") != null && localStorage.getItem("input_has_submit_button") == "true" && callback && callback != console.log) {
		var submit_button = document.createElement("button");
		submit_button.innerHTML = "Submit";
		submit_button.className = "button " + base_color + " hover-" + color;
		submit_button.setAttribute("style", "margin-left: 0px;");
		submit_button.addEventListener("click", function(event) {
			callback(input.value, input);
		});
		root.appendChild(submit_button);
	}

	return root;
}

function create_button(text, callback) {
	var root = document.createElement("button");

	root.className = "button " + base_color + " hover-" + color;
	root.innerHTML = text;

	root.addEventListener("click", function(event) {
		callback(root);
	});

	return root;
}

function create_markdown_component(__content) {
	try {
		if (showdown == undefined) {
			var error_text = document.createElement("p");
			error_text.innerHTML = "Markdown component requires showdown.js";
			return error_text;
		}
	} catch (e) {
		var error_text = document.createElement("p");
		error_text.innerHTML = "Markdown component requires showdown.js";
		return error_text;
	}

	const markdown_render = new showdown.Converter().makeHtml(__content);

	var root = document.createElement("div");
	root.innerHTML = markdown_render;

	return root;
}

function create_list(items) {
	var root = document.createElement("ul");

	for (let i of items) {
		var item = document.createElement("li");
		item.innerHTML = i;

		root.appendChild(item);
	}

	return root;
}

function create_table(content) {
	var root = document.createElement("table");
	root.className = "table table-all " + color + " border-" + color;


	for (let i of content) {
		var row = document.createElement("tr");
		row.className = "border-" + color + " " + base_color;

		for (let j of i) {
			var cell = document.createElement("td");
			cell.innerHTML = j;

			row.appendChild(cell);
		}

		root.appendChild(row);
	}

	return root;
}

function create_text(text) {
	var root = document.createElement("p");
	root.innerHTML = text;

	return root;
}

function join_components(...components) {
	var root = document.createElement("div");

	for (let i of components) {
		root.appendChild(i);
	}

	return root;
}

function create_heading(level, text) {
	var root = document.createElement("h" + level);
	root.innerHTML = text;

	return root;
}

function create_markdown_fetchable(url, name) {
	var id = "markdown-" + name.replace(/ /g, "-");

	var button = create_button(name, async button => {
		button.innerText = "Fetching...";
		button.disabled = true;

		var md = await (await fetch(url)).text();
		console.log(md);

		document.getElementById(id).append(create_markdown_component(md));

		button.innerText = name;
	});

	var container = from_html("<div id=\"" + id + "\"></div>");

	return join_components(button, container);
}


function from_html(__content) {
	return document.createRange().createContextualFragment(__content);
}
