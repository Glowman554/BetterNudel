const WebUntis = require("webuntis");
const fs = require("fs");

function merge_obj(obj1, obj2) {
	for(var key in obj2) {
		if (typeof obj2[key] === 'object' && obj2[key] !== null && !(obj2[key] instanceof Array)) {
			console.log(`Merging sub-object ${key}`);
			if (obj1[key] === undefined) {
				obj1[key] = {};
			}
			obj1[key] = merge_obj(obj1[key], obj2[key]);
		} else {
			console.log(`Merging ${key}`);
			obj1[key] = obj2[key];
		}
	}

	return obj1;
}

async function getClassServicesFor(untis, rangeStart, rangeEnd, validateSession = true) {
	if (validateSession && !(await untis.validateSession())) throw new Error('Current Session is not valid');
	const response = await untis.axios({
		method: 'GET',
		url: `/WebUntis/api/classreg/classservices`,
		params: {
			startDate: untis.convertDateToUntis(rangeStart),
			endDate: untis.convertDateToUntis(rangeEnd),
		},
		headers: {
			Cookie: untis._buildCookies(),
		},
	});
	if (typeof response.data.data !== 'object') {
		throw new Error('Server returned invalid data.');
	}

	return response.data.data;
}

var untis_auth_store = {
};

var untis_config = {
	weburl: "neilo.webuntis.com",
	school: "GSZ Balingen"
};

async function fetch_homework() {
	var homework = {};
	var class_services = {};

	for (let x of Object.keys(untis_auth_store)) {
		var untis = new WebUntis(untis_config.school, untis_auth_store[x].username, untis_auth_store[x].password, untis_config.weburl);

		console.log("Trying to authenticate with " + untis_auth_store[x].username + " credentials ...");

		try {
			await untis.login();

			homework = merge_obj(
				homework,
				await untis.getHomeWorksFor(new Date(), new Date(new Date().setDate(new Date().getDate() + 7)))
			);

			class_services = merge_obj(
				class_services,
				await getClassServicesFor(untis, new Date(), new Date(new Date().setDate(new Date().getDate() + 1)))
			);

			await untis.logout();

			console.log("untis", "Authentication successful!");
		} catch (e) {
			console.log("untis", "Error while authenticating with untis: " + e);
		}
	}

	var homework_string = "";

	for (let x of homework.homeworks) {
		var subject = homework.lessons.find(lesson => lesson.id == x.lessonId).subject;
		var due_date = WebUntis.convertUntisDate(x.dueDate).toLocaleDateString();

		homework_string += `**${subject}**: ${x.text} muss bis zum \`${due_date}\` erledigt sein!\n`;
	}

	// for (let x of ha_store) {
	// 	var now = new Date(Date.now());

	// 	if (x.to_be_finished_by.year < now.getFullYear() || x.to_be_finished_by.month < now.getMonth() + 1 || x.to_be_finished_by.day < now.getDate()) {
	// 		continue;
	// 	}

	// 	homework_string += `**${x.type}**: ${x.notes} muss bis zum \`${x.to_be_finished_by.day}/${x.to_be_finished_by.month}/${x.to_be_finished_by.year}\` erledigt sein!\n`;
	// }

	if (homework_string == "") {
		homework_string = "Keine Aufgaben fuer heute!";
	}

	var class_services_string = "";
	for (let x of class_services.classRoles) {
		var due_date = WebUntis.convertUntisDate(x.endDate).toLocaleDateString();
		class_services_string += `**${x.duty.label}**: ${x.foreName} ${x.longName} bis ${due_date}\n`;
	}

	return homework_string + "\n\n" + class_services_string;
}


var argv_cpy = Object.assign([], process.argv);
argv_cpy.splice(0, 2);

var output_file = argv_cpy.shift();

while (argv_cpy.length > 0) {
	var arg = argv_cpy.shift();
	console.log("arg", arg);
	untis_auth_store[arg] = {
		username: arg.split(":")[0],
		password: arg.split(":")[1],
	};
}


fetch_homework().then(homework => {
	console.log("Writing to file " + output_file + " ...");
	fs.writeFileSync(output_file, homework);
});
