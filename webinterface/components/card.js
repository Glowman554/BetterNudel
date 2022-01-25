import { get_color } from "../js/color";

export default function Card(props) {

	var div_style = {
		margin: "1rem",
		padding: "1rem",
		"border-left-width": "2px",
		"border-left-style": "solid"
	};

	return (
		<div style={div_style} className={"card " + get_color().color}>
			{props.children}
		</div>
	)
}