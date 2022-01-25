import { get_color } from "../js/color";

export default function Heading(props) {
	var h1_style = {
		padding: "50px 16px",
	}
	return (
		<header className={"container center " + get_color().base_color} style={h1_style}>
			<h1 className="margin xxxlarge">
				{props.title}
			</h1>
			<p className="xlarge">
				{props.subtitle}
			</p>
		</header>
	)
}
