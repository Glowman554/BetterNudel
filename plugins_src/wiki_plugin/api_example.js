import {existsSync} from "https://deno.land/std/fs/mod.ts";

var base_api = "http://localhost:8888/api/v2"

function start_login() {
	return new Promise((resolve, reject) => {
		fetch(base_api + "/login/start").then(response => response.json()).then(response => {
			resolve(response.id);
		});
	});
}

function status_login(login_id) {
	return new Promise((resolve, reject) => {
		fetch(base_api + "/login/status?login_id=" + login_id).then(response => response.json()).then(response => {
			resolve(response.token);
		});
	});
}

function wiki_create(token, page_title, page_text) {
	var page_title_encoded = btoa(page_title);
	var page_text_encoded = btoa(page_text);

	return new Promise((resolve, reject) => {
		fetch(base_api + "/wiki/page/create?token=" + token + "&page_title=" + page_title_encoded + "&page_text=" + page_text_encoded).then(response => response.json()).then(response => {
			resolve(response);
		});
	});
}

function wiki_get(page_id) {
	return new Promise((resolve, reject) => {
		fetch(base_api + "/wiki/page/get?page_id=" + page_id).then(response => response.json()).then(response => {
			resolve(response);
		});
	});
}

function wiki_edit(token, page_id, page_title, page_text) {
	var page_title_encoded = btoa(page_title);
	var page_text_encoded = btoa(page_text);

	return new Promise((resolve, reject) => {
		fetch(base_api + "/wiki/page/edit?token=" + token + "&page_id=" + page_id + "&page_title=" + page_title_encoded + "&page_text=" + page_text_encoded).then(response => response.json()).then(response => {
			resolve(response);
		});
	});
}

function wiki_list() {
	return new Promise((resolve, reject) => {
		fetch(base_api + "/wiki/page/list").then(response => response.json()).then(response => {
			resolve(response);
		});
	});
}

function wiki_delete(token, page_id) {
	return new Promise((resolve, reject) => {
		fetch(base_api + "/wiki/page/delete?token=" + token + "&page_id=" + page_id).then(response => response.json()).then(response => {
			resolve(response);
		});
	});
}

function wiki_changelog() {
	return new Promise((resolve, reject) => {
		fetch(base_api + "/wiki/page/changelog").then(response => response.json()).then(response => {
			resolve(response);
		});
	});
}

async function login() {
	let login_id = await start_login();

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

async function main() {
	var token = "";
	if (existsSync(".token.txt")) {
		token = await Deno.readTextFile(".token.txt");
	} else {
		token = await login();
		await Deno.writeTextFile(".token.txt", token);
	}

	console.log(token);

	let page_title = "test";
	let page_text = "test";

	let response = await wiki_create(token, page_title, page_text);
	console.log(response);

	let page_id = response.page_id;

	page_title = "test2";
	page_text = "test2";

	response = await wiki_edit(token, page_id, page_title, page_text);

	let page = await wiki_get(page_id);
	console.log(page);

	let pages = await wiki_list();
	console.log(pages);

	response = await wiki_delete(token, page_id);
	console.log(response);

	response = await wiki_changelog();
	console.log(response);
}

main();