export function get_color() {
	var base_color = "black";
	var color = "dark-grey";

	if (localStorage.getItem("base_color")) {
		base_color = localStorage.getItem("base_color");
	}
	
	if (localStorage.getItem("color")) {
		color = localStorage.getItem("color");
	}

	return {
		base_color: base_color,
		color: color
	};
}

export const root_style = {
	padding: "0px",
	margin: "0px",
	fontFamily: "verdana, sans-serif",
	fontSize: "15px",
	lineHeight: "1.5"
};